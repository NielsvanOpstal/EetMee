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

    public void getAllOffers(Callback aActivity, String dateString) {
        Log.d("USERUSERUSER", "IK BEN HIER");
        Log.d("USERUSERUSER", dateString);
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
//      TODO: dateSTring ipv 20-6-2018 invullen
        MYREF.child("offers").orderByChild("dateString").equalTo(dateString).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("USERUSERUSER", dataSnapshot.toString());
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

    public void getMyOffers(Callback aActivity) {
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        Log.d("debug", mAuth.getUid());
        MYREF.child("offers").orderByChild("userID").equalTo(mAuth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("debug", dataSnapshot.toString());
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
        activity = aActivity;
        final ArrayList<Offer> offers = new ArrayList<>();
        final ArrayList<String> joinedOffers = new ArrayList<>();
        Log.d("JOINEDOFFERS", "hier");


        MYREF.child("Users").child(mAuth.getUid()).child("joinedOffers").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    joinedOffers.add(snapshot.getValue().toString());
                }
                MYREF.child("offers").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshotOffers) {
                        for (DataSnapshot dataSnapshotOffer: dataSnapshotOffers.getChildren()) {
                            if (joinedOffers.contains(dataSnapshotOffer.getKey())) {
                                offers.add(dataSnapshotOffer.getValue(Offer.class));
                            }
                        }
                        activity.gotOffers(offers);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
