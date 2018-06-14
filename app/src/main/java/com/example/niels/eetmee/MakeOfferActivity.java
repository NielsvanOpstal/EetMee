package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

// TODO: van Time zo'n draai dingetje maken.
// TODO: niet een lijst maar een opvolging van invulbare dingen
public class MakeOfferActivity extends AppCompatActivity {

    static String PUSHKEY;

    private EditText what;
    private String whatText;
    private EditText costs;
    private int costsInt = -1;
    private EditText persons;
    private int personsInt = -1;
    private EditText time;
    private String timeText;
    private CheckBox together;
    private boolean togetherBool;
    private CheckBox pickUp;
    private boolean pickUpBool;
    private String userID;


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
        time = findViewById(R.id.TimeEditText);
        together = findViewById(R.id.TogetherCheckbox);
        pickUp = findViewById(R.id.PickupCheckbox);

    }

    public void ContiueClicked(View view) {
        whatText = what.getText().toString();
        costsInt = Integer.parseInt(costs.getText().toString());
        personsInt = Integer.parseInt(persons.getText().toString());
        timeText = time.getText().toString();
        togetherBool = together.isChecked();
        pickUpBool = pickUp.isChecked();

        if (ValidateForm()) {
            Offer newOffer = new Offer();
            newOffer.setWhat(whatText);
            newOffer.setCosts(costsInt);
            newOffer.setPersons(personsInt);
            newOffer.setTime(timeText);
            newOffer.setEatTogheter(togetherBool);
            newOffer.setPickup(pickUpBool);
            newOffer.setUserID(userID);
            newOffer.setPersonsLeft(personsInt);
//            PUSHKEY = MYREF.child("offers").push().getKey();
//            MYREF.child("offers").child(PUSHKEY).setValue(newOffer);
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

        if (costsInt < 0) {
            costs.setError("Required.");
            valid = false;
        }
        else {
            costs.setError(null);
        }


        if (personsInt < 0) {
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
}
