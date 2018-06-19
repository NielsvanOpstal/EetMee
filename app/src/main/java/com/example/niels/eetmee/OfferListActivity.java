package com.example.niels.eetmee;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ListView;


import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class OfferListActivity extends AppCompatActivity implements OfferRequest.Callback {


    private FusedLocationProviderClient mFusedLocationClient;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    private double lat;
    private double lng;

    private RequestType requestType;

    private static String dateString;

    private static OfferRequest request;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("hallo", "haaloo");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (checkLocationPermission()) {
                System.out.println("Locatie opvragen toegestaan");
            }
            Log.d("LOCATION: ,", "Permission was niet geaccepteerd");
        }

        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("LOCATION: ,", "" + location.getLatitude());
                            lat = location.getLatitude();
                            lng = location.getLongitude();
                        }
                    }
                });



        // Dit moet nog weg
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();

        }
        requestType = (RequestType) getIntent().getSerializableExtra("afkomst");

        setContentView(R.layout.offer_list_activity);
        request = new OfferRequest(this);

        Date date = Calendar.getInstance().getTime();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        dateString = df.format(date);


        switch (requestType) {
            case ALLOFFERS:     request.getAllOffers(this, dateString);
                                break;
            case MYOFFERS:      request.getMyOffers(this);
                                break;
            case JOINEDOFFERS:  request.getJoinedOffers(this);
                                break;


        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            if (requestType == RequestType.ALLOFFERS) {
                MenuInflater inflater = getMenuInflater();
                inflater.inflate(R.menu.actionbar, menu);
                return true;
            }
            return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d("USERUSERUSER", "JAAA");
        DialogFragment newDateFragment = new DatePickerFragment();
        newDateFragment.show(getFragmentManager(), "datePicker");
        return true;
    }

    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            dateString = Integer.toString(dayOfMonth) + "-" + Integer.toString(month + 1) + "-" + Integer.toString(year);
//          TODO: hoe kom ik bij de outer class???
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


    @Override
    public void gotOffers(ArrayList<Offer> offers) {
        ListView offerlist = findViewById(R.id.OfferListView);
        offerlist.setAdapter(new OfferAdapter(this, 0, offers, lat, lng));
        offerlist.setOnItemClickListener(new onItemmClickListener());
    }

    @Override
    public void onResume() {
        super.onResume();

        switch (requestType) {
            case ALLOFFERS:     request.getAllOffers(this, dateString);
                break;
            case MYOFFERS:      request.getMyOffers(this);
                break;
            case JOINEDOFFERS:  request.getJoinedOffers(this);
                break;
        }

    }


    @Override
    public void gotOffersError(String message) {
        // TODO: goede error message terug geven.
        Log.d("Heeft gewerkt", "NOPE");
    }

    public class onItemmClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Offer offerClicked = (Offer) parent.getItemAtPosition(position);
            Intent intent = new Intent(OfferListActivity.this, DetailActivity.class);
            intent.putExtra("offer", offerClicked);
            startActivity(intent);
        }
    }
    private boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(OfferListActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(OfferListActivity.this)
                        .setTitle("Permission needed")
                        .setMessage("Allow permission to get your location")
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions( OfferListActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(OfferListActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}

