package com.example.niels.eetmee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class DetailActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        Offer offer = (Offer) getIntent().getSerializableExtra("offer");
        TextView what = findViewById(R.id.DetailWhatField);

        what.setText(offer.getWhat());
    }

    public void JoinDinner(View view) {
    }
}
