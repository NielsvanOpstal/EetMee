package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import static com.example.niels.eetmee.BaseActivity.myrefchecker;
import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;


public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener, UserRequest.Callback {

    private EditText name, bio;
    private String aName, aBio;
    private Diet diet;
    Switch vegetarian, vegan, nuts, peanuts, lactose, gluten, soy, shellfish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_edit);

        myrefchecker.checker();

//        Gets the user
        UserRequest userRequest = new UserRequest();
        userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());

        diet = new Diet();

//         Edit texts
        name = findViewById(R.id.EditProfileName);
        bio = findViewById(R.id.EditProfileBio);

//         Button
        findViewById(R.id.EditProfileSumbit).setOnClickListener(this);

//        Switches
        vegetarian = findViewById(R.id.VegetarianSwitch);
        vegan = findViewById(R.id.VeganSwitch);
        nuts = findViewById(R.id.NutSwitch);
        peanuts = findViewById(R.id.PeanutSwitch);
        lactose = findViewById(R.id.LactoseSwitch);
        gluten = findViewById(R.id.GlutenSwitch);
        soy = findViewById(R.id.SoySwitch);
        shellfish = findViewById(R.id.ShellfishSwitch);
    }

    @Override
    public void onClick(View v) {
//        Gets the name and bio by the user
        aName = name.getText().toString();
        aBio = bio.getText().toString();

//        Checks if the needed forms are filled in
        if (validateForm(aName, aBio)) {

//            If filled, make a new User class and updates the current user in Firebase
            User newUser = new User();
            newUser.setName(aName);
            newUser.setBio(aBio);
            newUser.setDiet(checkCheckBoxes());
            MYREF.child("Users").child(mAuth.getUid()).setValue(newUser);

            if(getIntent().getBooleanExtra("fromInfo", false)) {

//                If came from UserInfoActivity, go back there
                Intent intent = new Intent(EditProfileActivity.this, UserInfoActivity.class);
                intent.putExtra("user", newUser);
                intent.putExtra("userID", mAuth.getUid());
                startActivity(intent);
            } else {

//                Else, go back to the BaseActivity
                startActivity(new Intent(EditProfileActivity.this, BaseActivity.class));
            }
            finish();
        }
    }

    private boolean validateForm(String aName, String aBio) {
//        Class to check whether the forms are filled in. If there is a form missing, set an error
//        in the missing editText and return false

        boolean valid = true;

        if (TextUtils.isEmpty(aName)) {
            name.setError("Required.");
            valid = false;
        } else {
            name.setError(null);
        }

        if (TextUtils.isEmpty(aBio)) {
            bio.setError("Required.");
            valid = false;
        } else {
            bio.setError(null);
        }

        return valid;
    }

    private Diet checkCheckBoxes() {
//        Fills the diet based on the checkboxes

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
    public void gotUser(User user, UserRequestType type) {

//        Gets the current data from the user
        String userName = user.getName();
        String userBio = user.getBio();
        Diet userDiet = user.getDiet();

//        if the name, bio and/or diet are not null, set the screen based on the current profile
        if (userName != null) {
            name.setText(userName);
            aName = userName;
        }
        if (userBio != null) {
            bio.setText(userBio);
            aBio = userBio;
        }
        if (userDiet != null) {
            diet = userDiet;
            setSwitches();
        }

    }

    private void setSwitches() {

//        Set the switches based on the user's diet
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
        Toast.makeText(this, "Er ging iets mis met het ontvangen van de gebruiker \n"
                + message, Toast.LENGTH_SHORT).show();
        Log.d("Error", message);
    }
}
