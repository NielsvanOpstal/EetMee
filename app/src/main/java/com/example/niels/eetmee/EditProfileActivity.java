package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

// TODO: make it load your current bio and name en dieet


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, UserRequest.Callback {

    private EditText name;
    private EditText bio;
    private String aName;
    private String aBio;
    private Diet diet;

    Switch vegetarian;
    Switch vegan;
    Switch nuts;
    Switch peanuts;
    Switch lactose;
    Switch gluten;
    Switch soy;
    Switch shellfish;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        if (mAuth.getCurrentUser() != null) {
            UserRequest userRequest = new UserRequest(this);
            userRequest.getUser(this);

            diet = new Diet();
            // Edit texts
            name = findViewById(R.id.EditProfileName);
            bio = findViewById(R.id.EditProfileBio);

            // Button
            findViewById(R.id.EditProfileSumbit).setOnClickListener(this);

            vegetarian = findViewById(R.id.VegetarianSwitch);
            vegan = findViewById(R.id.VeganSwitch);
            nuts = findViewById(R.id.NutSwitch);
            peanuts = findViewById(R.id.PeanutSwitch);
            lactose = findViewById(R.id.LactoseSwitch);
            gluten = findViewById(R.id.GlutenSwitch);
            soy = findViewById(R.id.SoySwitch);
            shellfish = findViewById(R.id.ShellfishSwitch);
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
            newUser.setDiet(checkCheckBoxes());
            MYREF.child("Users").child(mAuth.getUid()).setValue(newUser);
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
//        vegetarian = findViewById(R.id.VegetarianSwitch);
//        vegan = findViewById(R.id.VeganSwitch);
//        nuts = findViewById(R.id.NutSwitch);
//        peanuts = findViewById(R.id.PeanutSwitch);
//        lactose = findViewById(R.id.LactoseSwitch);
//        gluten = findViewById(R.id.GlutenSwitch);
//        soy = findViewById(R.id.SoySwitch);
//        shellfish = findViewById(R.id.ShellfishSwitch);

        diet.vegetarian = vegetarian.isChecked();
        diet.vegan = vegan.isChecked();
        diet.nutAllergy = nuts.isChecked();
        diet.peanutAllergy = peanuts.isChecked();
        diet.lactoseAllergy = lactose.isChecked();
        diet.glutenAllergy = gluten.isChecked();
        diet.soyAllergy = soy.isChecked();
        diet.shellfishAllergy = shellfish.isChecked();
        return diet;
    }

    @Override
    public void gotUser(User user) {
        Log.d("userrequest", "Hoi");
        Log.d("userrequest", user.toString());
        String userName = user.getName();
        String userBio = user.getBio();
        Diet userDiet = user.getDiet();
        if (userName != null) {
            name.setText(userName);
            aName = userName;
        }
        if (userBio != null) {
            bio.setText(userBio);
            aBio = userBio;
        }
        if (userDiet != null) {
            if (userDiet.vegetarian) {
                Log.d("userrequest", userDiet.toString());
            }
            else{
                Log.d("userrequest", "halllooo");
            }
            diet = userDiet;
            setSwitches();
        }

    }

    private void setSwitches() {
        vegetarian.setChecked(diet.vegetarian);
        vegan.setChecked(diet.vegan);
        nuts.setChecked(diet.nutAllergy);
        peanuts.setChecked(diet.peanutAllergy);
        lactose.setChecked(diet.lactoseAllergy);
        gluten.setChecked(diet.glutenAllergy);
        soy.setChecked(diet.soyAllergy);
        shellfish.setChecked(diet.shellfishAllergy);
    }

    @Override
    public void gotUserError(String message) {
        Log.d("userrequest", message);
    }
}
