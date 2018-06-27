/*
EetMee
Niels van Opstal 11021519

This activity shows the information about an user. If the profile is the of the user viewing it, he
can edit it.
 */
package com.example.niels.eetmee.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;


import com.example.niels.eetmee.R;
import com.example.niels.eetmee.Adapters.ReviewAdapter;
import com.example.niels.eetmee.Classes.User;

import static com.example.niels.eetmee.Activities.LoginActivity.mAuth;

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
        Intent intent = new Intent(UserInfoActivity.this, EditProfileActivity.class);
        intent.putExtra("fromInfo", true);
        startActivity(intent);
        finish();
    }


}
