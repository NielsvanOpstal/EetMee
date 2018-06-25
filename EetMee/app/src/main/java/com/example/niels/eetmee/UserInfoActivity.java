package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import static com.example.niels.eetmee.MainActivity.mAuth;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

//        Find the textviews
        TextView nameView = findViewById(R.id.InfoNameTextView);
        TextView bioView = findViewById(R.id.InfoBioTextView);
        ListView reviewList = findViewById(R.id.InfoReviewList);

//        Get the intent and the extra's from the intent
        Intent intentReceived = getIntent();
        User user = (User) intentReceived.getSerializableExtra("user");
        String userID = intentReceived.getStringExtra("userID");

//        Fills the textviews and sets an adapter on the listview
        nameView.setText(user.getName());
        bioView.setText(user.getBio());
        reviewList.setAdapter(new ReviewAdapter(this, 0, user.getReviews()));

//        If the user is viewing his own profile, make edit button visible
        if (userID.equals(mAuth.getUid())) {
            findViewById(R.id.EditProfileButton).setVisibility(View.VISIBLE);
        }

    }

    public void EditProfile(View view) {
//        If button clicked, go to EditProfileActivity
        startActivity(new Intent(UserInfoActivity.this, EditProfileActivity.class));
    }


}
