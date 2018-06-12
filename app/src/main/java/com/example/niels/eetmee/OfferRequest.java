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
import static com.example.niels.eetmee.MainActivity.mAuth;

public class OfferRequest {

    private Context context;
    private Callback activity;

    public interface Callback {
        void gotOffers(ArrayList<Offer> offers);
        void gotOffersError(String message);
    }

    public OfferRequest(Context context) {
        context = context;
    }

    public void getOffers1(Callback aActivity) {
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();

        MYREF.child("offers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    offers.add(snapshot.getValue(Offer.class));
                }
                activity.gotOffers(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotOffersError(databaseError.getMessage());
            }
        });
    }

    public void getOffers2(Callback aActivity) {
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();

        MYREF.child("offers").orderByChild("userID").equalTo(mAuth.getCurrentUser().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    offers.add(snapshot.getValue(Offer.class));
                }
                activity.gotOffers(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotOffersError(databaseError.getMessage());
            }
        });
    }
}
