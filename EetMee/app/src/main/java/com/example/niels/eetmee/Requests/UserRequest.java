/*
EetMee
Niels van Opstal 11021519

This request request the user from the Firebase databse based on the userID given in the getUser function.
 */
package com.example.niels.eetmee.Requests;

import android.support.annotation.NonNull;


import com.example.niels.eetmee.Classes.User;
import com.example.niels.eetmee.Enums.UserRequestType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;


import static com.example.niels.eetmee.Activities.LoginActivity.MYREF;

public class UserRequest {

    private UserRequest.Callback activity;

    public interface Callback {
        void gotUser(User user, UserRequestType type);
        void gotUserError(String message);
    }


    public void getUser(UserRequest.Callback aActivity, UserRequestType aType, String userID) {

        final UserRequestType type = aType;

        activity = aActivity;

//        Gets the user from the given userID
        MYREF.child("Users").child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                Casts the gotten datasnapshot to an User class
                User user = dataSnapshot.getValue(User.class);
                activity.gotUser(user, type);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                activity.gotUserError(databaseError.getMessage());
            }
        });
    }
}
