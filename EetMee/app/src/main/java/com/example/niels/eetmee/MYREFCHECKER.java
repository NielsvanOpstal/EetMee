package com.example.niels.eetmee;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.niels.eetmee.MainActivity.MYREF;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class MYREFCHECKER {
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
