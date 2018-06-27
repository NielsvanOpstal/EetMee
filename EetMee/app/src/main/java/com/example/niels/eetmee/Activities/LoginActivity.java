/*
EetMee
Niels van Opstal 11021519

This activity is the login activity. Here the user can login, register, get send a verification mail
and logout. It is done by Firebase Authentication via the email-password login function
 */
package com.example.niels.eetmee.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.niels.eetmee.R;
import com.example.niels.eetmee.Classes.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

public class LoginActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "LoginActivity";

    public static DatabaseReference MYREF;
    public static  FirebaseAuth mAuth;
    private TextView statusTextView, detailTextView, emailField, passwordField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Gets the reference tot he Firebase database and the Firebase Auth
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setLogLevel(Logger.Level.DEBUG);
        MYREF = database.getReference();
        mAuth = FirebaseAuth.getInstance();

//        Finds the textviews and fiels
        statusTextView = findViewById(R.id.StatusTextView);
        detailTextView = findViewById(R.id.DetailTextView);
        emailField = findViewById(R.id.EmailField);
        passwordField = findViewById(R.id.PasswordField);

//        Sets the onItemClickListeneres on the buttons
        findViewById(R.id.SignInButton).setOnClickListener(this);
        findViewById(R.id.RegisterButton).setOnClickListener(this);
        findViewById(R.id.SignOutButton).setOnClickListener(this);
        findViewById(R.id.VerifyEmailButton).setOnClickListener(this);
        findViewById(R.id.ContinueButton).setOnClickListener(this);

    }

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {

//        Checks if there is an user (user != null)
        if (user != null) {
//            Fills teh statustextView and detailTextView
            statusTextView.setText(getString(R.string.users_email,
                    user.getEmail(), user.isEmailVerified()));
            detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

//            Update the buttons and fields
            findViewById(R.id.LogInButtons).setVisibility(View.GONE);
            findViewById(R.id.EmailPasswordField).setVisibility(View.GONE);
            findViewById(R.id.SignedInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.VerifyEmailButton).setEnabled(!user.isEmailVerified());
        } else {

//            Fills the statusTextView and detailTextView
            statusTextView.setText(R.string.signed_out);
            detailTextView.setText(null);

//            Update the buttons and field
            findViewById(R.id.LogInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.EmailPasswordField).setVisibility(View.VISIBLE);
            findViewById(R.id.SignedInButtons).setVisibility(View.GONE);
        }
    }

    private void createAccount(String email, String password) {
        if (!validateForm()) {
            return;
        }

        // Start creating user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

//                            Create a profile for the new user and set him in the firebase Database
                            User profile = new User();
                            profile.setName("");
                            MYREF.child("Users").child(mAuth.getUid()).setValue(profile);
                            startActivity(new Intent(LoginActivity.this, EditProfileActivity.class));
                        } else {

                            // If sign in fails, display a message to the user.
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }


    private boolean validateForm() {
        boolean valid = true;

        String email = emailField.getText().toString();
        if (TextUtils.isEmpty(email)) {
            emailField.setError("Required.");
            valid = false;
        } else {
            emailField.setError(null);
        }

        String password = passwordField.getText().toString();
        if (TextUtils.isEmpty(password)) {
            passwordField.setError("Required.");
            valid = false;
        } else {
            passwordField.setError(null);
        }

        return valid;
    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog();  ??

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
                        if (!task.isSuccessful()) {
                            statusTextView.setText(R.string.auth_failed);
                        }
//                        hideProgressDialog(); ??
                        // [END_EXCLUDE]
                    }
                });
        // [END sign_in_with_email]
    }

    private void signOut() {
        mAuth.signOut();
        updateUI(null);
//        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.VerifyEmailButton).setEnabled(false);

        // Send verification email
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // Re-enable button
                        findViewById(R.id.VerifyEmailButton).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(LoginActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    @Override
    public void onClick(View v) {

//        Checks which button is clicked and act accordingly
        int i = v.getId();
        if (i == R.id.RegisterButton) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.SignInButton) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.SignOutButton) {
            signOut();
        } else if (i == R.id.VerifyEmailButton) {
            sendEmailVerification();
        } else if (i == R.id.ContinueButton) {
            startActivity(new Intent(LoginActivity.this, BaseActivity.class));
        }
    }
}
