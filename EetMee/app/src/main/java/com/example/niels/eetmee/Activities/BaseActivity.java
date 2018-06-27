/*
EetMee
Niels van Opstal 11021519

This activity is mainly a screen which redirects the user to other activities.
 */
package com.example.niels.eetmee.Activities;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.niels.eetmee.HelperFunctions.MyRefChecker;
import com.example.niels.eetmee.Enums.OfferRequestType;
import com.example.niels.eetmee.R;
import com.example.niels.eetmee.Classes.User;
import com.example.niels.eetmee.Requests.UserRequest;
import com.example.niels.eetmee.Enums.UserRequestType;

import static com.example.niels.eetmee.Activities.LoginActivity.mAuth;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener, UserRequest.Callback {

    static public MyRefChecker myRefChecker;
    UserRequest userRequest;
    boolean profileFilled = false;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);

        myRefChecker = new MyRefChecker();
        myRefChecker.checker();

        findViewById(R.id.IkWilKokenButton).setOnClickListener(this);
        findViewById(R.id.IkWilEtenButton).setOnClickListener(this);
        findViewById(R.id.MyOffersButton).setOnClickListener(this);
        findViewById(R.id.JoinedOffersButton).setOnClickListener(this);

        userRequest = new UserRequest();
        Log.d("USERID", mAuth.getCurrentUser().getUid());
        userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

//        Only creates possibility to pick date when user has requested all offers
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.base_screen_action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent intent = new Intent(BaseActivity.this, UserInfoActivity.class);
        intent.putExtra("user", user);
        intent.putExtra("userID", mAuth.getUid());
        startActivity(intent);
        return true;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.IkWilKokenButton:         if (profileFilled) {
                                                    startActivity(new Intent(BaseActivity.this, MakeOfferActivity.class));
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }

            case R.id.IkWilEtenButton:          if (profileFilled) {
                                                    Intent eatIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                    eatIntent.putExtra("afkomst", OfferRequestType.ALLOFFERS);
                                                    startActivity(eatIntent);
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }

            case R.id.MyOffersButton:           if (profileFilled) {
                                                    Intent myIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                    myIntent.putExtra("afkomst", OfferRequestType.MYOFFERS);
                                                    startActivity(myIntent);
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }

            case R.id.JoinedOffersButton:       if (profileFilled) {
                                                    Intent joinedIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                    joinedIntent.putExtra("afkomst", OfferRequestType.JOINEDOFFERS);
                                                    startActivity(joinedIntent);
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }
        }

    }

    @Override
    public void gotUser(User aUser, UserRequestType type) {

        user = aUser;
        if (!TextUtils.isEmpty(user.getName())) {
            profileFilled = true;
        }

    }

    @Override
    public void gotUserError(String message) {

        Toast.makeText(this, "Er ging iets mis :( \n" +
                message, Toast.LENGTH_SHORT).show();
        Log.d("Error", message);
    }

    @Override
    protected void onResume() {
        super.onResume();

        userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());
        myRefChecker.checker();
        if (!profileFilled) {
            userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());
        }
    }
}
