package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class OfferListActivity extends AppCompatActivity implements OfferRequest.Callback {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Dit moet nog weg
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();
        }
        setContentView(R.layout.offer_list_activity);
        OfferRequest request = new OfferRequest(this);
        request.getOffers(this);

    }

    public void viewDetails(View view) {
        startActivity(new Intent(OfferListActivity.this, DetailActivity.class));
    }

    @Override
    public void gotOffers(ArrayList<Offer> offers) {
        Log.d("HOi", "JOSDF");
        ListView offerlist = findViewById(R.id.OfferListView);
        offerlist.setAdapter(new OfferAdapter(this, 0, offers));
    }


    @Override
    public void gotOffersError(String message) {
        Log.d("Heeft gewerkt", "NOPE");
    }
}
