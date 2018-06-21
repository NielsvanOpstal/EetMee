package com.example.niels.eetmee;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import static com.example.niels.eetmee.UserRequestType.ALTERCURRENTUSER;
import static com.example.niels.eetmee.UserRequestType.CURRENTUSER;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, UserRequest.Callback {

    Offer offer;
    MapView mapView;
    FirebaseUser user;
    Button joinButton;
    Button unJoinButton;
    User currentUser;
    User offerCreater;
    UserRequest userRequest;
    TextView nameTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) {
//                Log.d("USER", user.toString());
                setContentView(R.layout.detail_activity);

                offer = (Offer) getIntent().getSerializableExtra("offer");

                TextView whatTextView = findViewById(R.id.DetailWhatField);
                whatTextView.setText(offer.getWhat());

                nameTextView = findViewById(R.id.DetailNameField);

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

                userRequest = new UserRequest();
                userRequest.getUser(this, UserRequestType.OFFERCREATER, offer.getUserID());
                userRequest.getUser(this, CURRENTUSER, mAuth.getUid());

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

            userRequest.getUser(this, ALTERCURRENTUSER, mAuth.getUid());

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

        userRequest.getUser(this, ALTERCURRENTUSER, mAuth.getUid());


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

        Log.d("USERUSERUSER", "hier" );
        LayoutInflater inflater = getLayoutInflater();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setMessage("Type hier je bedankje!")
                .setView(input)
                .setPositiveButton("verstuur!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reviewText = input.getText().toString();
                        if (TextUtils.isEmpty(reviewText)) {
                            input.setError("Vul het bedankje in!");
                        }
                        else {
                            Review review = new Review();
                            review.setReviewWriter(currentUser.getName());
                            review.setDate(Calendar.getInstance().getTime());
                            review.setReview(reviewText);
                            offerCreater.addReview(review);
                            MYREF.child("Users").child(offer.getUserID()).setValue(offerCreater);
                        }
                    }
                })
                .setNegativeButton("Annuleer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();



    }




    @Override
    public void gotUser(User user, UserRequestType type) {

        if (type == CURRENTUSER) {
            currentUser = user;
        }
        else if (type == ALTERCURRENTUSER) {
            if (user.getJoinedOffers().contains(offer.getFirebaseKey())) {
                user.removeDinner(offer.getFirebaseKey());
            } else {
                user.addDinner(offer.getFirebaseKey());
            }
            MYREF.child("Users").child(mAuth.getUid()).setValue(user);
        }

        else {
            offerCreater = user;
            nameTextView.setText(user.getName());
        }
    }

    @Override
    public void gotUserError(String message) {
//        TODO: netjes afhandelen
        Log.d("userrequest", "Er gign iets mis");
    }

    public void show_info_button_clicked(View view) {
        Intent intent = new Intent(DetailActivity.this, UserInfoActivity.class);
        intent.putExtra("user", offerCreater);
        startActivity(intent);
    }
}

