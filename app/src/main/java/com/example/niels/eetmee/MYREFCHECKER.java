package com.example.niels.eetmee;

import com.google.firebase.database.FirebaseDatabase;

import static com.example.niels.eetmee.MainActivity.MYREF;

public class MYREFCHECKER {
    public void checker() {
        if (MYREF == null) {
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            MYREF = database.getReference();
        }
    }
}
