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
import java.util.Collections;

public class OfferAdapter extends ArrayAdapter<Offer> {

    ArrayList<Offer> offers;
    double lat;
    double lng;

    public OfferAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> objects, double aLat, double aLng) {
        super(context, resource, objects);

        offers = objects;
        lat = aLat;
        lng = aLng;

//        If there is a current location (lat < 100) calculate distance and sort the objects based on distance
        if (lat < 100) {
            for (Offer offer : offers) {
                float[] results = new float[1];
                Location.distanceBetween(lat, lng, offer.getLat(), offer.getLng(), results);
                offer.setDistance( results[0] / 1000);
            }
            Collections.sort(offers);
        }
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

//        lat is 100 if there was no location found
        if (lat < 100) {

//            Calculates the distance between users current location and offer location and fills distanceTextivew
            distance.setText("afstand:" + currentOffer.getDistance() + "km");
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


