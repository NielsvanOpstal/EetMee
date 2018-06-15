package com.example.niels.eetmee;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {
    Offer offer;
    MapView mapView;
    FirebaseUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
                Log.d("USER", user.toString());
                setContentView(R.layout.detail_activity);

                offer = (Offer) getIntent().getSerializableExtra("offer");
                TextView what = findViewById(R.id.DetailWhatField);

                what.setText(offer.getWhat());

                mapView = findViewById(R.id.mapView);
                mapView.onCreate(savedInstanceState);
                mapView.getMapAsync(this);
            }
            else {
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }

    }

    public void JoinDinner(View view) {
        Log.d("USER", user.toString());
        Log.d("USER", user.getEmail());
        Log.d("USER", user.getUid());
        if(offer.addEater(user.getUid())) {
            Log.d("USERID", offer.getEaters().toString());
            MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);
        }
        else {
            Toast.makeText(this, "Er ging iets mis :(\n Ben je al ingeschreven?", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Address address = offer.getAddress();
        LatLng latLng = new LatLng(address.getLat(), address.getLng());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("title"));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


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

    public void RateBttonClicked(View view) {
        offer.removeEater(user.getUid());
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

    }
}
