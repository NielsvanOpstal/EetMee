/*
EetMee
Niels van Opstal 11021519

This is the first activity in the process of making an offer. This activity lets the user specify
what he is going to make, for how many persons, where he lives, the cost of what he is going to make,
the date and time and whether people can join the user for dinner and/or pickup the food.

The time is specified by a TimePickerFragment.
The date is specified by a DatePickerFragment.
The Address is specified by a Google Places Autocomplete function.


 */
package com.example.niels.eetmee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;


import android.text.format.DateFormat;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;

import com.google.android.gms.location.places.Place;

import com.google.android.gms.location.places.ui.PlaceAutocomplete;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.niels.eetmee.BaseActivity.myRefChecker;
import static com.example.niels.eetmee.LoginActivity.mAuth;
public class MakeOfferActivity extends AppCompatActivity {

    AutocompleteFilter typeFilter;

    private int PLACE_AUTOCOMPLETE_REQUEST_CODE = 1;

    private EditText what, costs, persons, address;
    private String whatText, costsText, personsText, timeText, addressText;
    private static EditText date, time;
    private CheckBox together, pickUp;
    private boolean togetherBool, pickUpBool;
    private static String dateString;
    private static Calendar cal;
    private double lat, lng;

    private BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.make_offer_activity);

        myRefChecker.checker();

//        Make a typefilter for the google places autocomplete
        typeFilter = new AutocompleteFilter.Builder().setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS).build();

//         Find the fields and checkboxes
        what = findViewById(R.id.WhatEditText);
        costs = findViewById(R.id.CostEditText);
        persons = findViewById(R.id.PersonsEditText);
        time = findViewById(R.id.TimeEditText);
        date = findViewById(R.id.DateEditText);
        together = findViewById(R.id.TogetherCheckbox);
        pickUp = findViewById(R.id.PickupCheckbox);
        address = findViewById(R.id.AddressEditText);

        cal = Calendar.getInstance();

//        Creates a broadcastreceiver so that the activity finishes after the offer was fully created and not before
        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action.equals("finish_activity")) {
                    finish();
                }
            }
        };
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }


    public void ContiueClicked(View view) {

//        Get the filled in data
        whatText = what.getText().toString();
        costsText = costs.getText().toString();
        personsText = persons.getText().toString();
        timeText = time.getText().toString();
        togetherBool = together.isChecked();
        pickUpBool = pickUp.isChecked();

//        Checks if the forms are filled in
        if (ValidateForm()) {

//            Create a new Offer and fill it
            Offer newOffer = new Offer();
            newOffer.setWhat(whatText);
            newOffer.setCosts(Integer.parseInt(costsText));
            newOffer.setPersons(Integer.parseInt(personsText));
            newOffer.setEatTogheter(togetherBool);
            newOffer.setPickup(pickUpBool);
            newOffer.setUserID(mAuth.getUid());
            newOffer.setDateString(dateString);
            newOffer.setPersonsLeft(Integer.parseInt(personsText));
            newOffer.setAddress(addressText);
            newOffer.setLat(lat);
            newOffer.setLng(lng);
            newOffer.setEaters(new ArrayList<String>());

            Date dateTime = cal.getTime();
            newOffer.setDateTime(dateTime);

//            Go to DietActivity
            Intent intent = new Intent(MakeOfferActivity.this, DietActivity.class);
            intent.putExtra("offerMade", newOffer);
            startActivity(intent);
        }
    }

    private boolean ValidateForm() {

//        Checks if all the fields are filled and one or both checkboxes are checked.
        boolean valid = true;

        if (TextUtils.isEmpty(whatText)) {
            what.setError("Required.");
            valid = false;
        } else {
            what.setError(null);
        }

        if (TextUtils.isEmpty(costsText)) {
            costs.setError("Required.");
            valid = false;
        } else {
            costs.setError(null);
        }
        if (TextUtils.isEmpty(personsText)) {
            persons.setError("Required");
            valid = false;
        } else {
            persons.setError(null);
        }
        if (TextUtils.isEmpty(timeText)) {
            time.setError("Required");
            valid = false;
        } else {
            time.setError(null);
        }
        if (togetherBool || pickUpBool) {
            together.setError(null);
            pickUp.setError(null);
        } else {
            together.setError("kies een of twee opties");
            pickUp.setError("kies een of twee opties");
            valid = false;
        }
        if (TextUtils.isEmpty(addressText)){
            address.setError("Vul aub een volledig addres in");
            valid = false;
        } else {
            address.setError(null);
        }

        return valid;
    }

    public void TimeClicked(View view) {

//        Make a TimePickerFragment and show it
        DialogFragment newTimeFragment = new TimePickerFragment();
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }

    public void DateClicked(View view) {

//        Make a DatePickerFragment and show it
        DialogFragment newDateFragment = new DatePickerFragment();
        newDateFragment.show(getFragmentManager(), "datePicker");
    }

    public void pickAdress(View view) {

//        Try to launch the Google Places Autocomplete
        try {
        Intent intent = new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_OVERLAY)
                        .build(this);
        startActivityForResult(intent, PLACE_AUTOCOMPLETE_REQUEST_CODE);
        } catch (GooglePlayServicesRepairableException e) {
            Toast.makeText(MakeOfferActivity.this, "something went wrong. \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        } catch (GooglePlayServicesNotAvailableException e) {
            Toast.makeText(MakeOfferActivity.this, "something went wrong. \n" + e.getMessage(),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PLACE_AUTOCOMPLETE_REQUEST_CODE) {

//            Result came bak from Google Places autocomplete
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(this, data);
                if (place.getAddress() == null) {
                    address.setError("vul aub een volledig addres in.");
                }
                else {

//                    Get the address and coordinates from the Place class
                    addressText = place.getAddress().toString();
                    lat = place.getLatLng().latitude;
                    lng = place.getLatLng().longitude;
                    address.setText(addressText);
                }
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(this, data);

//                Show toast with error
                Toast.makeText(MakeOfferActivity.this, "something went wrong. \n"
                                + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Error", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
//                 The user canceled the operation
            }
        }
    }

    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

//            Set the picked time to the timeTextView and in a calendar object
            if (minute < 10) {
                time.setText(Integer.toString(hourOfDay) + ":0" + Integer.toString(minute));
            }
            else {
                time.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            }
            cal.set(cal.HOUR_OF_DAY, hourOfDay);
            cal.set(cal.MINUTE, minute);
            cal.set(cal.SECOND, 0);
            cal.set(cal.MILLISECOND, 0);
    }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

//          Set the start time on the current time
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

//            Create the dateString from the date picked and add the date to the calendar object
            dateString = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
            date.setText(dateString);

            cal.set(cal.DAY_OF_MONTH, dayOfMonth);
            cal.set(cal.MONTH, month);
            cal.set(cal.YEAR, year);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

//            Set the start date on the current date
            final Calendar c =Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(broadcastReceiver, new IntentFilter("finish_activity"));
    }
}
