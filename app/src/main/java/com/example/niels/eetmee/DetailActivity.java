package com.example.niels.eetmee;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class DetailActivity extends AppCompatActivity{
    Offer offer;
    

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);

        offer = (Offer) getIntent().getSerializableExtra("offer");
        TextView what = findViewById(R.id.DetailWhatField);

        what.setText(offer.getWhat());
    }

    public void JoinDinner(View view) {
        offer.decrementPersons();
        MYREF.child("offers").child(offer.getFirebaseKey()).setValue(offer);
    }
}
