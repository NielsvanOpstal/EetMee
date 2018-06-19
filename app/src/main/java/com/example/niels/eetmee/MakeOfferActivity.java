package com.example.niels.eetmee;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import android.text.format.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

// TODO: van Time zo'n draai dingetje maken.
// TODO: niet een lijst maar een opvolging van invulbare dingen
public class MakeOfferActivity extends AppCompatActivity {

    static String PUSHKEY;

    private EditText what;
    private String whatText;
    private EditText costs;
    private String costsText;
    private EditText persons;
    private String personsText;
    private static TextView time;
    private String timeText;
    private static TextView date;
    private CheckBox together;
    private boolean togetherBool;
    private CheckBox pickUp;
    private boolean pickUpBool;
    private String userID;
    private static String dateString;
    private static Calendar cal;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        userID = mAuth.getCurrentUser().toString();
        if(TextUtils.isEmpty(userID)) {
            Toast.makeText(MakeOfferActivity.this, "You need to be logged in",
                    Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MakeOfferActivity.this, MainActivity.class));
        }

        setContentView(R.layout.make_offer_activity);

        // Fill the fields and checkboxes
        what = findViewById(R.id.WhatEditText);
        costs = findViewById(R.id.CostEditText);
        persons = findViewById(R.id.PersonsEditText);
        time = findViewById(R.id.TimeTextView);
        date = findViewById(R.id.DateTextVew);
        together = findViewById(R.id.TogetherCheckbox);
        pickUp = findViewById(R.id.PickupCheckbox);

        cal = Calendar.getInstance();


    }

    public void ContiueClicked(View view) {
        whatText = what.getText().toString();
        costsText = costs.getText().toString();
        personsText = persons.getText().toString();
        timeText = time.getText().toString();
        togetherBool = together.isChecked();
        pickUpBool = pickUp.isChecked();

        if (ValidateForm()) {
            Offer newOffer = new Offer();
            newOffer.setWhat(whatText);
            newOffer.setCosts(Integer.parseInt(costsText));
            newOffer.setPersons(Integer.parseInt(personsText));
            newOffer.setEatTogheter(togetherBool);
            newOffer.setPickup(pickUpBool);
            newOffer.setUserID(userID);
            newOffer.setDateString(dateString);
            newOffer.setPersonsLeft(Integer.parseInt(personsText));
            newOffer.setEaters(new ArrayList<String>());

            Date dateTime = cal.getTime();
            newOffer.setDateTime(dateTime);


            Intent intent = new Intent(MakeOfferActivity.this, EnterAddresActivity.class);
            intent.putExtra("offermade", newOffer);
            startActivity(intent);
        }
    }

    private boolean ValidateForm() {
//        Checks if all the fields are filled and one or both checkboxes are checked.
        boolean valid = true;

        if (TextUtils.isEmpty(whatText)) {
            what.setError("Required.");
            valid = false;
        }
        else {
            what.setError(null);
        }

        if (TextUtils.isEmpty(costsText)) {
            costs.setError("Required.");
            valid = false;
        }
        else {
            costs.setError(null);
        }


        if (TextUtils.isEmpty(personsText)) {
            persons.setError("Required");
            valid = false;
        }

        else {
            persons.setError(null);
        }

        if (TextUtils.isEmpty(timeText)) {
            time.setError("Required");
            valid = false;
        }

        else {
            time.setError(null);
        }

        if (togetherBool || pickUpBool) {
            together.setError(null);
            pickUp.setError(null);
        }
        else {
            together.setError("kies een of twee opties");
            pickUp.setError("kies een of twee opties");
            valid = false;
        }

        return valid;
    }

    public void TimeClicked(View view) {
        DialogFragment newTimeFragment = new TimePickerFragment();
        newTimeFragment.show(getFragmentManager(), "timePicker");
    }

    public void DateClicked(View view) {
        DialogFragment newDateFragment = new DatePickerFragment();
        newDateFragment.show(getFragmentManager(), "datePicker");
    }


    public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            time.setText(Integer.toString(hourOfDay) + ":" + Integer.toString(minute));
            cal.set(cal.HOUR_OF_DAY, hourOfDay);
            cal.set(cal.MINUTE, minute);
            cal.set(cal.SECOND, 0);
            cal.set(cal.MILLISECOND, 0);
        }


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateString = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
            date.setText(dateString);

            cal.set(cal.DAY_OF_MONTH, dayOfMonth);
            cal.set(cal.MONTH, month);
            cal.set(cal.YEAR, year);
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c =Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
    }
}
