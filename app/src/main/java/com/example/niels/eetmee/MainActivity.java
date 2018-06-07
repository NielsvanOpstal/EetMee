



package com.example.niels.eetmee;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private static final String TAG = "MainActivity";
    public static DatabaseReference MYREF;

    protected static  FirebaseAuth mAuth;
    private TextView statusTextView;
    private TextView detailTextView;
    private EditText emailField;
    private EditText passwordField;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        MYREF = database.getReference();

        statusTextView = findViewById(R.id.StatusTextView);
        detailTextView = findViewById(R.id.DetailTextView);
        emailField = findViewById(R.id.EmailField);
        passwordField = findViewById(R.id.PasswordField);

        findViewById(R.id.SignInButton).setOnClickListener(this);
        findViewById(R.id.RegisterButton).setOnClickListener(this);
        findViewById(R.id.SignOutButton).setOnClickListener(this);
        findViewById(R.id.VerifyEmailButton).setOnClickListener(this);
        findViewById(R.id.ContinueButton).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser user) {
//        hideProgressDialog();  ??
        if (user != null) {
            statusTextView.setText(getString(R.string.users_email,
                    user.getEmail(), user.isEmailVerified()));
            detailTextView.setText(getString(R.string.firebase_status_fmt, user.getUid()));

            findViewById(R.id.LogInButtons).setVisibility(View.GONE);
            findViewById(R.id.EmailPasswordField).setVisibility(View.GONE);
            findViewById(R.id.SignedInButtons).setVisibility(View.VISIBLE);

            findViewById(R.id.VerifyEmailButton).setEnabled(!user.isEmailVerified());
        } else {
            statusTextView.setText(R.string.signed_out);
            detailTextView.setText(null);

            findViewById(R.id.LogInButtons).setVisibility(View.VISIBLE);
            findViewById(R.id.EmailPasswordField).setVisibility(View.VISIBLE);
            findViewById(R.id.SignedInButtons).setVisibility(View.GONE);
        }
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateForm()) {
            return;
        }

//        showProgressDialog(); ??

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                            startActivity(new Intent(MainActivity.this, EditProfileActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // [START_EXCLUDE]
//                        hideProgressDialog(); ??
                        // [END_EXCLUDE]
                    }

                });
        // [END create_user_with_email]
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
                            Toast.makeText(MainActivity.this, "Authentication failed.",
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
        Log.d("HOI", "HOI");
//        startActivity(new Intent(MainActivity.this, MainActivity.class));
    }

    private void sendEmailVerification() {
        // Disable button
        findViewById(R.id.VerifyEmailButton).setEnabled(false);

        // Send verification email
        // [START send_email_verification]
        final FirebaseUser user = mAuth.getCurrentUser();
        user.sendEmailVerification()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        // Re-enable button
                        findViewById(R.id.VerifyEmailButton).setEnabled(true);

                        if (task.isSuccessful()) {
                            Toast.makeText(MainActivity.this,
                                    "Verification email sent to " + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e(TAG, "sendEmailVerification", task.getException());
                            Toast.makeText(MainActivity.this,
                                    "Failed to send verification email.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        // [END_EXCLUDE]
                    }
                });
        // [END send_email_verification]
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.RegisterButton) {
            createAccount(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.SignInButton) {
            signIn(emailField.getText().toString(), passwordField.getText().toString());
        } else if (i == R.id.SignOutButton) {
            signOut();
        } else if (i == R.id.VerifyEmailButton) {
            sendEmailVerification();
        }
        else if (i == R.id.ContinueButton) {
            startActivity(new Intent(MainActivity.this, BaseActivity.class));
        }
    }
}
