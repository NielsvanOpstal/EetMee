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

    private Context context;
    private UserRequest.Callback activity;

    public interface Callback {
        void gotUser(User user);
        void gotUserError(String message);
    }

    public UserRequest(Context context) {
        context = context;
    }

    public void getUser(UserRequest.Callback aActivity) {
//        TODO: hier gaat iets mis
        activity = aActivity;
        Log.d("userrequest", mAuth.getCurrentUser().getUid());
        MYREF.child("Users").orderByKey().equalTo(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d("userrequest", "gelukt");
                activity.gotUser(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotUserError(databaseError.getMessage());
                Log.d("userrequest", "niet gelukt");
            }
        });
    }
}
