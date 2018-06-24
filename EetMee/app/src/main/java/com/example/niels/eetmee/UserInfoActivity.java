package com.example.niels.eetmee;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.example.niels.eetmee.MainActivity.mAuth;

public class UserInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        TextView nameView = findViewById(R.id.InfoNameTextView);
        TextView bioView = findViewById(R.id.InfoBioTextView);
        ListView reviewList = findViewById(R.id.InfoReviewList);
        Intent intentReceived = getIntent();

        User user = (User) intentReceived.getSerializableExtra("user");
        String userID = intentReceived.getStringExtra("userID");
        Log.d("USERTJE", user.getReviews().toString());

        nameView.setText(user.getName());
        bioView.setText(user.getBio());
        reviewList.setAdapter(new ReviewAdapter(this, 0, user.getReviews()));

        if (userID.equals(mAuth.getUid())) {
            findViewById(R.id.EditProfileButton).setVisibility(View.VISIBLE);
        }

    }

    public void EditProfile(View view) {
        startActivity(new Intent(UserInfoActivity.this, EditProfileActivity.class));
    }

    class ReviewAdapter extends ArrayAdapter<Review> {

        ArrayList<Review> reviews;

        public ReviewAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Review> objects) {
            super(context, resource, objects);

            reviews = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.review_item, parent, false);
            }
            Review currentReview = reviews.get(position);
            TextView nameView = convertView.findViewById(R.id.ReviewNameTextField);
            TextView dateView = convertView.findViewById(R.id.ReviewDateTextField);
            TextView reviewView = convertView.findViewById(R.id.ReviewTextTextField);

            Date date = currentReview.getDate();
            DateFormat df = new SimpleDateFormat("dd-M-yyyy");


            dateView.setText(df.format(date));
            nameView.setText(currentReview.getReviewWriter());
            reviewView.setText(currentReview.getReview());

            return convertView;
        }
    }
}
