package com.example.niels.eetmee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

//        Get the current Review
        Review currentReview = reviews.get(position);

//        Find the views from the review_item
        TextView nameView = convertView.findViewById(R.id.ReviewNameTextField);
        TextView dateView = convertView.findViewById(R.id.ReviewDateTextField);
        TextView reviewView = convertView.findViewById(R.id.ReviewTextTextField);

//        Set the date in the day - month - year format
        Date date = currentReview.getDate();
        DateFormat df = new SimpleDateFormat("dd-M-yyyy");

//        Fills the views
        dateView.setText(df.format(date));
        nameView.setText(currentReview.getReviewWriter());
        reviewView.setText(currentReview.getReview());

        return convertView;
    }
}