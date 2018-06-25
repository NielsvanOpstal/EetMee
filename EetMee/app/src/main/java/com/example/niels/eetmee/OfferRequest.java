package com.example.niels.eetmee;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class OfferRequest {

    private Callback activity;

    public interface Callback {
        void gotOffers(ArrayList<Offer> offers);
        void gotOffersError(String message);
    }

    public void getAllOffers(Callback aActivity, String dateString) {
//        Gets all the offer on a certain date

        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        MYREF.child("offers").orderByChild("dateString").equalTo(dateString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

//                Casts all the received offers back to the Offer class
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Offer offer = snapshot.getValue(Offer.class);
                    if (offer.getPersonsLeft() > 0) {
                        offers.add(snapshot.getValue(Offer.class));
                    }
                }
                activity.gotOffers(offers);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotOffersError(databaseError.getMessage());
            }
        });
    }

    public void getMyOffers(Callback aActivity) {
//        Gets the offer created by the user

        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        MYREF.child("offers").orderByChild("userID").equalTo(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
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

    public void getJoinedOffers(Callback aActivity) {
//        Gets allt he offer joined by the user

        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        final ArrayList<String> joinedOffers = new ArrayList<>();

//        First gets the firebasekeys from the joined offers of the user
        MYREF.child("Users").child(mAuth.getUid()).child("joinedOffers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    joinedOffers.add(snapshot.getValue().toString());
                }

//                Gets offers
                MYREF.child("offers").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotOffers) {

//                        Loops through the offers and if an offer is in the joined offers, cast it to an Offer class
                        for (DataSnapshot dataSnapshotOffer: dataSnapshotOffers.getChildren()) {
                            if (joinedOffers.contains(dataSnapshotOffer.getKey())) {
                                offers.add(dataSnapshotOffer.getValue(Offer.class));
                            }
                        }
                        activity.gotOffers(offers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        activity.gotOffersError(databaseError.getMessage());
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotOffersError(databaseError.getMessage());
            }
        });
    }
}
