package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import static com.example.niels.eetmee.MainActivity.mAuth;

// TODO: koken en eten alleen mogelijk maken wanneer bio is ingevuld
// TODO: switch maken van de if statements

public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);

        findViewById(R.id.IkWilKokenButton).setOnClickListener(this);
        findViewById(R.id.IkWilEtenButton).setOnClickListener(this);
        findViewById(R.id.ProfielAanpassenButton).setOnClickListener(this);
        findViewById(R.id.MyOffersButton).setOnClickListener(this);
        findViewById(R.id.JoinedOffersButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.IkWilKokenButton:         startActivity(new Intent(BaseActivity.this, MakeOfferActivity.class));
                                                break;

            case R.id.IkWilEtenButton:          Intent eatIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                eatIntent.putExtra("afkomst", RequestType.ALLOFFERS);
                                                startActivity(eatIntent);
                                                break;

            case R.id.ProfielAanpassenButton:   startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
                                                break;

            case R.id.MyOffersButton:           Intent myIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                myIntent.putExtra("afkomst", RequestType.MYOFFERS);
                                                startActivity(myIntent);
                                                break;

            case R.id.JoinedOffersButton:       Intent joinedIntent =new Intent(BaseActivity.this, OfferListActivity.class);
                                                joinedIntent.putExtra("afkomst", RequestType.JOINEDOFFERS);
                                                startActivity(joinedIntent);
                                                break;
        }
    }
}
