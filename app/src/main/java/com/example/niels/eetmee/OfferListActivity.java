package com.example.niels.eetmee;

import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class OfferListActivity extends AppCompatActivity implements OfferRequest.Callback {


    private FusedLocationProviderClient mFusedLocationClient;
    private int MY_PERMISSIONS_REQUEST_LOCATION = 99;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        Log.d("hallo", "haaloo");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            if (checkLocationPermission()) {
                System.out.println("Locatie opvragen toegestaan");
            }
            Log.d("LOCATION: ,", "Permission was niet geaccepteerd");
        }
        Log.d("hallo", "haaloo");
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d("LOCATION: ,", "LOCATION WAS NULL");
                        }
                        Log.d("LOCATION: ,", "" + location.getLatitude());
                    }
                });


        // Dit moet nog weg
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();
            Log.d("JOsdf", MYREF.toString());

        }
        int direction = getIntent().getIntExtra("afkomst", 0);

        setContentView(R.layout.offer_list_activity);
        OfferRequest request = new OfferRequest(this);

        if (direction == 1) {
            request.getOffers1(this);
        }
        if (direction == 2) {
            request.getOffers2(this);
        }




    }

    @Override
    public void gotOffers(ArrayList<Offer> offers) {
        Log.d("HOi", "JOSDF");
        ListView offerlist = findViewById(R.id.OfferListView);
        offerlist.setAdapter(new OfferAdapter(this, 0, offers));
        offerlist.setOnItemClickListener(new onItemmClickListener());
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

