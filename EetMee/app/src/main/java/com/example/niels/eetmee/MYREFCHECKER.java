package com.example.niels.eetmee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class MYREFCHECKER {

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
