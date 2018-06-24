package com.example.niels.eetmee;

import com.google.firebase.auth.FirebaseAuth;
import static com.example.niels.eetmee.MainActivity.mAuth;

public class GetCurrentUser {

    public void getCurrentUser() {
        mAuth = FirebaseAuth.getInstance();
    }
}
