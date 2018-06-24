package com.example.niels.eetmee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class UserRequest {

    private UserRequestType type;
    private UserRequest.Callback activity;

    public interface Callback {
        void gotUser(User user, UserRequestType type);
        void gotUserError(String message);
    }


    public void getUser(UserRequest.Callback aActivity, UserRequestType aType, String userID) {
        final UserRequestType type = aType;
//        TODO: Catch achtig iets fixen voor wanneer datasnapshot null teruggeeft
        activity = aActivity;
        Log.d("USERUSERSER", userID);
        MYREF.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("userrequest", dataSnapshot.toString());
                User user = dataSnapshot.getValue(User.class);
                activity.gotUser(user, type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotUserError(databaseError.getMessage());
                Log.d("userrequest", "niet gelukt");
            }
        });
    }
}
