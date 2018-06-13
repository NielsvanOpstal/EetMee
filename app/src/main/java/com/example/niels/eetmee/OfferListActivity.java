package com.example.niels.eetmee;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class OfferListActivity extends AppCompatActivity implements OfferRequest.Callback {




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // Dit moet nog weg
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();
            Log.d("JOsdf", MYREF.toString());

        }
        int direction = getIntent().getIntExtra("afkomst", 0);

        setContentView(R.layout.offer_list_activity);
        OfferRequest request = new OfferRequest(this);

        if (direction == 1) {
            request.getOffers1(this);
        }
        if (direction == 2) {
            request.getOffers2(this);
        }


    }

    @Override
    public void gotOffers(ArrayList<Offer> offers) {
        Log.d("HOi", "JOSDF");
        ListView offerlist = findViewById(R.id.OfferListView);
        offerlist.setAdapter(new OfferAdapter(this, 0, offers));
        offerlist.setOnItemClickListener(new onItemmClickListener());
    }



    @Override
    public void gotOffersError(String message) {
        // TODO: goede error message terug geven.
        Log.d("Heeft gewerkt", "NOPE");
    }

    public class onItemmClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Offer offerClicked = (Offer) parent.getItemAtPosition(position);
            Intent intent = new Intent(OfferListActivity.this, DetailActivity.class);
            intent.putExtra("offer", offerClicked);
            startActivity(intent);
        }
    }

}

