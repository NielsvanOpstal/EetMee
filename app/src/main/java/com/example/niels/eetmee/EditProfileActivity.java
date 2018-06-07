package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

// TODO: make it load your current bio and name en dieet
// TODO: make a switch system for allergies
// TODO: kan de switchchecker niet veel handigeR?

public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText name;
    private EditText bio;
    private String aName;
    private String aBio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        if (mAuth.getCurrentUser() != null) {
            // Edit texts
            name = findViewById(R.id.EditProfileName);
            bio = findViewById(R.id.EditProfileBio);

            // Button
            findViewById(R.id.EditProfileSumbit).setOnClickListener(this);
        }
        else {
            startActivity(new Intent(EditProfileActivity.this, MainActivity.class));
        }
    }

    @Override
    public void onClick(View v) {
        aName = name.getText().toString();
        aBio = bio.getText().toString();
        if (validateForm(aName, aBio)) {
            User newUser = new User();
            newUser.setName(aName);
            newUser.setBio(aBio);
            Diet diet = checkCheckBoxes();
            MYREF.child("Users").child(mAuth.getUid()).setValue(newUser);
            MYREF.child("Users").child(mAuth.getUid()).child("Diet").setValue(diet);
            startActivity(new Intent(EditProfileActivity.this, BaseActivity.class));
        }
    }

    private boolean validateForm(String aName, String aBio) {
        boolean valid = true;

        if (TextUtils.isEmpty(aName)) {
            name.setError("Required.");
            valid = false;
        }
        else {
            name.setError(null);
        }

        if (TextUtils.isEmpty(aBio)) {
            bio.setError("Required.");
            valid = false;
        }
        else {
            bio.setError(null);
        }

        return valid;
    }

    private Diet checkCheckBoxes() {
        Diet diet = new Diet();
        Switch vegetarian = findViewById(R.id.VegetarianSwitch);
        Switch vegan = findViewById(R.id.VeganSwitch);
        Switch nuts = findViewById(R.id.NutSwitch);
        Switch peanuts = findViewById(R.id.PeanutSwitch);
        Switch lactose = findViewById(R.id.LactoseSwitch);
        Switch gluten = findViewById(R.id.GlutenSwitch);
        Switch soy = findViewById(R.id.SoySwitch);
        Switch shellfish = findViewById(R.id.ShellfishSwitch);

        if (vegetarian.isChecked()) {
            diet.vegetarian = true;
        }
        if (vegan.isChecked()) {
            diet.vegan = true;
        }
        if (nuts.isChecked()) {
            diet.nutAllergy = true;
        }
        if (peanuts.isChecked()) {
            diet.peanutAllergy = true;
        }
        if (lactose.isChecked()) {
            diet.lactoseAllergy = true;
        }
        if (gluten.isChecked()) {
            diet.glutenAllergy = true;
        }
        if (soy.isChecked()) {
            diet.soyAllergy = true;
        }
        if (shellfish.isChecked()) {
            diet.shellfishAllergy = true;
        }
        return diet;
    }
}
