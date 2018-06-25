package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import static com.example.niels.eetmee.MainActivity.mAuth;


public class BaseActivity extends AppCompatActivity implements View.OnClickListener, UserRequest.Callback {

    static public MYREFCHECKER myrefchecker;
    UserRequest userRequest;
    boolean profileFilled = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);

        myrefchecker = new MYREFCHECKER();
        myrefchecker.checker();

        findViewById(R.id.IkWilKokenButton).setOnClickListener(this);
        findViewById(R.id.IkWilEtenButton).setOnClickListener(this);
        findViewById(R.id.ProfielAanpassenButton).setOnClickListener(this);
        findViewById(R.id.MyOffersButton).setOnClickListener(this);
        findViewById(R.id.JoinedOffersButton).setOnClickListener(this);

        userRequest = new UserRequest();
        Log.d("USERID", mAuth.getCurrentUser().getUid());
        userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());
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
                                                    eatIntent.putExtra("afkomst", RequestType.ALLOFFERS);
                                                    startActivity(eatIntent);
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }

            case R.id.ProfielAanpassenButton:   startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                break;

            case R.id.MyOffersButton:           if (profileFilled) {
                                                    Intent myIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                    myIntent.putExtra("afkomst", RequestType.MYOFFERS);
                                                    startActivity(myIntent);
                                                    break;
                                                } else {
                                                    Toast.makeText(this, "vul eerst je profiel aan", Toast.LENGTH_SHORT).show();
                                                    startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                    break;
                                                }

            case R.id.JoinedOffersButton:       if (profileFilled) {
                                                    Intent joinedIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                    joinedIntent.putExtra("afkomst", RequestType.JOINEDOFFERS);
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
    public void gotUser(User user, UserRequestType type) {
        if (!TextUtils.isEmpty(user.getName())) {
            profileFilled = true;
        }

    }

    @Override
    public void gotUserError(String message) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        myrefchecker.checker();
        if (!profileFilled) {
            userRequest.getUser(this, UserRequestType.CURRENTUSER, mAuth.getUid());
        }
    }
}
