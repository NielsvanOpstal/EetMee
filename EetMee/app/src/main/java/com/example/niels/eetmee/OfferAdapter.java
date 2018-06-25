package com.example.niels.eetmee;

import android.content.Context;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

//TODO: sort on distance
public class OfferAdapter extends ArrayAdapter<Offer> {

    ArrayList<Offer> offers;
    double lat;
    double lng;

    public OfferAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> objects, double aLat, double aLng) {
        super(context, resource, objects);

        offers = objects;
        lat = aLat;
        lng = aLng;

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.offer_list_item, parent, false);
        }

//        Gets the current offer and finds the views in the offer_list_item
        Offer currentOffer = offers.get(position);
        TextView what = convertView.findViewById(R.id.WhatField);
        TextView cost = convertView.findViewById(R.id.CostField);
        TextView distance = convertView.findViewById(R.id.DistanceField);
        TextView time = convertView.findViewById(R.id.TimeField);

//         lat is 100 if there was no location found
        if (lat < 100) {

//            Calculates the distance between users current location and offer location and fills distanceTextivew
            float[] results = new float[1];
            Location.distanceBetween(lat, lng, currentOffer.getLat(), currentOffer.getLng(), results);
            float dist = results[0] / 1000;
            distance.setText("afstand:" + dist + "km");

        } else {
            distance.setText("Do not know current location");
        }

//        Fills the other textViews
        what.setText(currentOffer.getWhat());
        cost.setText("kosten: " +currentOffer.getCosts());

        SimpleDateFormat fm = new SimpleDateFormat("k:mm");
        time.setText(fm.format(currentOffer.getDateTime()));

        return convertView;
    }
}


