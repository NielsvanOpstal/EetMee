package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.view.View;

// TODO: koken en eten alleen mogelijk maken wanneer bio is ingevuld
public class BaseActivity extends AppCompatActivity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_screen);

        findViewById(R.id.IkWilKokenButton).setOnClickListener(this);
        findViewById(R.id.IkWilEtenButton).setOnClickListener(this);
        findViewById(R.id.ProfielAanpassenButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.IkWilKokenButton) {
            startActivity(new Intent(BaseActivity.this, MakeOfferActivity.class));
        }
        if (id == R.id.IkWilEtenButton) {
            startActivity(new Intent(BaseActivity.this, OfferListActivity.class));
        }
        if (id == R.id.ProfielAanpassenButton) {
            startActivity(new Intent(BaseActivity.this, EditProfileActivity.class));
        }
    }
}
