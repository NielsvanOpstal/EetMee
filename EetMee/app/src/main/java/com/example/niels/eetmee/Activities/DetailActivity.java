/*
EetMee
Niels van Opstal 11021519

This activity shows the details of an offer. It contains a Google Maps map which shows the location
from the address. It also gives the user the possibility to join or unjoin the offer if it is not his/her
offer or before the current time. If a user has joined an offer and the offer is past, the user can
leave a review.
If the user joins an offer but there are problems with his diet and the offer, the user gets prompted a
dialog.
The user can also be redirected to a screen that shows more info about the user that made the offeree
 */
package com.example.niels.eetmee.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niels.eetmee.Classes.Diet;
import com.example.niels.eetmee.Classes.Offer;
import com.example.niels.eetmee.R;
import com.example.niels.eetmee.Classes.Review;
import com.example.niels.eetmee.Classes.User;
import com.example.niels.eetmee.Requests.UserRequest;
import com.example.niels.eetmee.Enums.UserRequestType;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.niels.eetmee.Activities.BaseActivity.myRefChecker;
import static com.example.niels.eetmee.Activities.LoginActivity.MYREF;
import static com.example.niels.eetmee.Activities.LoginActivity.mAuth;

import static com.example.niels.eetmee.Enums.UserRequestType.CURRENTUSER;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback, UserRequest.Callback {

    Offer offer;
    MapView mapView;
    Button joinButton, unJoinButton;
    User currentUser, offerCreater;
    UserRequest userRequest;
    TextView nameTextView;
    String dietString;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

//         Check and fix the instances of the Firebase database and the Firebase Auth
        myRefChecker.checker();

        offer = (Offer) getIntent().getSerializableExtra("offer");

//        Fill the textViews
        TextView whatTextView = findViewById(R.id.DetailWhatField);
        whatTextView.setText(offer.getWhat());


        TextView timeTextView = findViewById(R.id.DetailTimeField);
        SimpleDateFormat df = new SimpleDateFormat("k:mm");
        timeTextView.setText(df.format(offer.getDateTime()));

        nameTextView = findViewById(R.id.DetailNameField);

        mapView = findViewById(R.id.mapView);
        joinButton = findViewById(R.id.JoinButton);
        unJoinButton = findViewById(R.id.UnJoinButton);
        ArrayList<String> eaters = offer.getEaters();

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

//        Gets the current date and time,
        Date date = Calendar.getInstance().getTime();
        Date offerDate = offer.getDateTime();

        if(offer.getUserID().equals(mAuth.getUid()) || date.after(offerDate) ) {

//            If the user is the cook or the date is before current time, remove the join and unjoin buttons
            joinButton.setVisibility(GONE);
            unJoinButton.setVisibility(GONE);
        } else {

//            Else, depending on if an user is already joined, show or hide the join and unjoin buttons
            if (eaters.contains(mAuth.getUid())) {
                unJoinButton.setVisibility(VISIBLE);
                joinButton.setVisibility(GONE);
            } else {
                unJoinButton.setVisibility(GONE);
                joinButton.setVisibility(VISIBLE);
            }
        }

        if (date.after(offerDate) && offer.getEaters().contains(mAuth.getUid())) {
            findViewById(R.id.RateButton).setVisibility(VISIBLE);
        }

//        Requests the current user and the user who created the offer
        userRequest = new UserRequest();
        userRequest.getUser(this, UserRequestType.OFFERCREATER, offer.getUserID());
        userRequest.getUser(this, CURRENTUSER, mAuth.getUid());
    }

    public void JoinDinner(View view) {

//        If there are diet problems (dietString is not empty) make an alerdialog containg the problems and checks if user still wants to join
        if (!TextUtils.isEmpty(dietString)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(dietString)
                    .setPositiveButton("Toch inschrijven", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                            If user still wants to join, try to join the dinner
                            if(offer.addEater(mAuth.getUid())) {

//                                If successful, update the offer in Firebase
                                MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

//                                Update the joining user in Firebase
                                currentUser.addDinner(offer.getFirebaseKey());
                                MYREF.child("Users").child(mAuth.getUid()).setValue(currentUser);

//                                Change the buttons
                                findViewById(R.id.UnJoinButton).setVisibility(VISIBLE);
                                findViewById(R.id.JoinButton).setVisibility(GONE);
                            } else {
//                                If something went wrong, show a Toast
                                Toast.makeText(DetailActivity.this, "Er ging helaas iets mis :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .setNegativeButton("Sorry, toch niet", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

//                            User doesn't want to join so nothing happens
                        }
                    });

//            Build and show the dialog
            AlertDialog dialog = builder.create();
            dialog.show();
            Log.d("DIETSTRING", "LEEG");
        } else {
            //        If there are no problems with diet

//            Try to add the user to the offer
            if(offer.addEater(mAuth.getUid())) {

//                If successful, update offer in Firebase
                MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

//                Update the joining user in Firebase
                currentUser.addDinner(offer.getFirebaseKey());
                MYREF.child("Users").child(mAuth.getUid()).setValue(currentUser);

//                Change the buttons
                findViewById(R.id.UnJoinButton).setVisibility(VISIBLE);
                view.setVisibility(GONE);
            } else {

//                If user could not join offer, show a toast
                Toast.makeText(this, "Er ging iets mis :(?", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void UnJoinButton(View view) {

//        Update the offer and updates it in Firebase
        offer.removeEater(mAuth.getUid());
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);

//        Update the user in the Firebase
        currentUser.removeDinner(offer.getFirebaseKey());
        MYREF.child("Users").child(mAuth.getUid()).setValue(currentUser);

//        change the buttons
        findViewById(R.id.JoinButton).setVisibility(VISIBLE);
        view.setVisibility(GONE);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

//        Get the coordinates of the address from the offer
        LatLng latLng = new LatLng(offer.getLat(), offer.getLng());

//        Settings from the map and add a marker
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(latLng).title("Het kook address"));
        googleMap.moveCamera(CameraUpdateFactory.zoomTo(13));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        try {

//            Initialize the map
            MapsInitializer.initialize(this);
        } catch (Exception e) {

//            If something went wrong, show a toast
            Toast.makeText(this, "Er ging iets mis met de kaart \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d("Error", e.getMessage());
        }
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();

        myRefChecker.checker();
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

//        build a dialog with an edit text and two buttons
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText input = new EditText(this);
        builder.setMessage("Type hier je bedankje!")
                .setView(input)
                .setPositiveButton("verstuur!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String reviewText = input.getText().toString();
                        if (TextUtils.isEmpty(reviewText)) {

//                            If the user did not write anything set an error
                            input.setError("Vul het bedankje in!");
                        } else {

//                            If user wrote something create a new Review and fill it and add it to the user
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

//                        If user cancels the dialog, do nothing
                    }
                });

//        Create and show the dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }


    @Override
    public void gotUser(User user, UserRequestType type) {

        if (type == CURRENTUSER) {

//            Load the received user in the current user
            currentUser = user;

//            Gets string with the problems with allergies
            dietString = dietChecker(offer.getDiet(), currentUser.getDiet());
            TextView detailsTextView = findViewById(R.id.DetailDetailsField);
            if (dietString.isEmpty()) {
                detailsTextView.setText("Geen probleem met je dieet!");
            } else {
                detailsTextView.setText(dietString);
            }
        } else {

//            Load the received user in the offercreater user and sets the name in the view
            offerCreater = user;
            nameTextView.setText(user.getName());
        }
    }

    @Override
    public void gotUserError(String message) {
        Toast.makeText(this, "Er ging iets mis met het ontvangen van de gebruiker \n"
                + message, Toast.LENGTH_SHORT).show();
        Log.d("Error", message);
    }

    public void show_info_button_clicked(View view) {

//        Goes to the profile info from the offerCreater
        Intent intent = new Intent(DetailActivity.this, UserInfoActivity.class);
        intent.putExtra("user", offerCreater);
        intent.putExtra("userID", offer.getUserID());
        startActivity(intent);
    }

    public String dietChecker(Diet offerDiet, Diet userDiet) {


//        Checks if there are problems with the diets from the user and the offer, if so: create a
//        string with the problems and returns it
        String alertString = "";
        if ((!offerDiet.vegetarian && !offerDiet.vegan) && userDiet.vegetarian) {
            alertString += "- It is not vegetarian! \n";
        }
        if (!offerDiet.vegan && userDiet.vegan) {
            alertString += "- It is not vegan! \n";
        }
        if (offerDiet.glutenAllergy && userDiet.glutenAllergy) {
            alertString += "- This offer contains gluten! \n";
        }
        if (offerDiet.lactoseAllergy && userDiet.lactoseAllergy) {
            alertString += "- This offer contains lactose! \n";
        }
        if (offerDiet.nutAllergy && userDiet.nutAllergy) {
            alertString += "- This offer contains nuts! \n";
        }
        if (offerDiet.peanutAllergy && userDiet.peanutAllergy) {
            alertString += "- This offer contains peanuts! \n";
        }
        if (offerDiet.shellfishAllergy && userDiet.shellfishAllergy) {
            alertString += "- This offer contains shellfish \n";
        }
        if (offerDiet.soyAllergy && userDiet.soyAllergy) {
            alertString += "- This offer contains soy! ";
        }

        return alertString;
    }
}

