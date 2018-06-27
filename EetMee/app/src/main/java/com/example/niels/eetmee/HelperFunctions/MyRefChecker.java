/*
EetMee
Niels van Opstal 11021519

This class lets you check whether there is still a reference to the Firebase database and authentication
 */
package com.example.niels.eetmee.HelperFunctions;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niels.eetmee.Activities.LoginActivity.MYREF;
import static com.example.niels.eetmee.Activities.LoginActivity.mAuth;

public class MyRefChecker {

//    Checks the Firebase database and authentication references, if null: get the again
    public void checker() {
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();
        }
        if (mAuth.getUid() == null) {
            mAuth = FirebaseAuth.getInstance();
        }
    }
}
