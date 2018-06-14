package com.example.niels.eetmee;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    Offer offer;
    MapView mapView;
    GoogleMap googleMap;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        offer = (Offer) getIntent().getSerializableExtra("offer");
        TextView what = findViewById(R.id.DetailWhatField);

        what.setText(offer.getWhat());

        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);


    }

    public void JoinDinner(View view) {
        offer.decrementPersons();
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Address address = offer.getAddress();
        Log.d("ja", address.toString());
        Log.d("ja", "" + address.getLat());
        Log.d("ja", "" + address.getLng());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(address.getLat(),address.getLng())).title("title"));
        try {
            MapsInitializer.initialize(this);
        }
        catch (Exception e) {
            Log.d("LOCATION", e.getMessage());
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
}
