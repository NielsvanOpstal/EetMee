package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, UserRequest.Callback {

    Offer offer;
    MapView mapView;
    FirebaseUser user;
    Button joinButton;
    Button unJoinButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
//                Log.d("USER", user.toString());
                setContentView(R.layout.detail_activity);

                offer = (Offer) getIntent().getSerializableExtra("offer");
                TextView what = findViewById(R.id.DetailWhatField);

                what.setText(offer.getWhat());

                mapView = findViewById(R.id.mapView);
                mapView.onCreate(savedInstanceState);
                mapView.getMapAsync(this);
                joinButton = findViewById(R.id.JoinButton);
                unJoinButton = findViewById(R.id.UnJoinButton);

                Log.d("USERUSERUSER", Calendar.getInstance().getTime().toString());
                ArrayList<String> eaters = offer.getEaters();


                Log.d("JOINEDOFFERS", eaters.toString());
                Log.d("JOINEDOFFERS", mAuth.getUid());
                if (eaters.contains(mAuth.getUid())) {
                    unJoinButton.setVisibility(VISIBLE);
                    joinButton.setVisibility(GONE);
                }
                else {
                    unJoinButton.setVisibility(GONE);
                    joinButton.setVisibility(VISIBLE);
                }

                Date date = Calendar.getInstance().getTime();
                Date offerDate = offer.getDateTime();
                if (date.after(offerDate) && offer.getEaters().contains(mAuth.getUid())) {
                    findViewById(R.id.RateButton).setVisibility(VISIBLE);
                }



            }
            else {
                startActivity(new Intent(DetailActivity.this, MainActivity.class));
            }

    }

    public void JoinDinner(View view) {
        Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();

        if(offer.addEater(user.getUid())) {

            MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

            findViewById(R.id.UnJoinButton).setVisibility(VISIBLE);
            view.setVisibility(GONE);

            UserRequest request = new UserRequest(this);
            request.getUser(this);

        }
        else {
//            Toast.makeText(this, "Er ging iets mis :(\n Ben je al ingeschreven?", Toast.LENGTH_SHORT).show();
        }
    }

    public void UnJoinButton(View view) {
        offer.removeEater(user.getUid());
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);
        findViewById(R.id.JoinButton).setVisibility(VISIBLE);
        view.setVisibility(GONE);

        UserRequest request = new UserRequest(this);
        request.getUser(this);


    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d("MAPMAP", "1");
        Address address = offer.getAddress();
        LatLng latLng = new LatLng(address.getLat(), address.getLng());
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("title"));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        try {
            Log.d("MAPMAP", "2");
            MapsInitializer.initialize(this);
        }
        catch (Exception e) {
            Log.d("MAPMAP", "3");
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
        Toast.makeText(this, user.getUid(), Toast.LENGTH_SHORT).show();
        offer.removeEater(user.getUid());
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

    }


    @Override
    public void gotUser(User user) {
        Log.d("USERUSERUSER", user.getJoinedOffers().toString());
        Log.d("USERUSERUSER", offer.getFirebaseKey());
        if (user.getJoinedOffers().contains(offer.getFirebaseKey())) {
            user.removeDinner(offer.getFirebaseKey());
        }
        else {
            user.addDinner(offer.getFirebaseKey());
        }
        MYREF.child("Users").child(mAuth.getUid()).setValue(user);
    }

    @Override
    public void gotUserError(String message) {
//        TODO: netjes afhandelen
        Log.d("userrequest", "Er gign iets mis");
    }
}
