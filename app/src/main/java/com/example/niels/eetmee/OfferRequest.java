package com.example.niels.eetmee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;
import static com.example.niels.eetmee.MainActivity.MYREF;

public class OfferRequest {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotOffers(ArrayList<Offer> offers);
        void gotOffersError(String message);
    }

    public OfferRequest(Context context) {
        context = context;
        Log.d("ik heb ze", "JA IK AAAAAAAAAAAAAAAaa");

    }

    public void getOffers(Callback aActivity) {
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        Log.d("ik heb ze", "JA IK BBBBBBBBBBBBBBBBBBBBBBBB");

        MYREF.child("offers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("ik heb ze", "JA IK CCCCCCCCCCCCCCCCCCCCCcc");

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    offers.add(snapshot.getValue(Offer.class));
                    Log.d("AAAA", snapshot.toString());
                }
//                for (Offer offer:offers) {
//
//                    Log.d("OFFERDATA", offer.getWhat());
//                    Log.d("OFFERDATA", "" + offer.getCosts());
//                    Log.d("OFFERDATA", offer.getTime());
//                    Log.d("OFFERDATA", "" + offer.getPersons());
//
//                }
                activity.gotOffers(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("ik heb ze", "JA IK EEEEEEEEEEEEEeeee");

                activity.gotOffersError(databaseError.getMessage());
            }
        });
    }
}
