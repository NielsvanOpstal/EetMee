/*
EetMee
Niels van Opstal 11021519

This adapter fills the ListView in the OfferListActivity. It calculates the distance from an offer to
the user and fills an offer_list_item with the needed data
 */
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

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static com.example.niels.eetmee.OfferListActivity.PERMISSION_NOT_GIVEN;

public class OfferAdapter extends ArrayAdapter<Offer> {

    private ArrayList<Offer> offers;
    private double lat, lng;
    private DecimalFormat numberFormat;


    public OfferAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> objects, double aLat, double aLng) {
        super(context, resource, objects);

        offers = objects;
        lat = aLat;
        lng = aLng;

//        If there is a current location (lat < 100) calculate distance and sort the objects based on distance
        if (lat < 100) {
            numberFormat = new DecimalFormat("#.00");
            for (Offer offer : offers) {
                float[] results = new float[1];
                Location.distanceBetween(lat, lng, offer.getLat(), offer.getLng(), results);
                offer.setDistance(results[0] / 1000);
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

//        lat is 100 if there was no location
        if (lat < PERMISSION_NOT_GIVEN) {

//            formats the distance to 2 decimals and fills the distance TextView
            distance.setText("afstand:" + numberFormat.format(currentOffer.getDistance()) + "km");
        } else {
            distance.setText("Huidige locatie onbekend");
        }

//        Fills the other textViews
        what.setText(currentOffer.getWhat());
        cost.setText("€ " +currentOffer.getCosts());

        SimpleDateFormat fm = new SimpleDateFormat("k:mm");
        time.setText(fm.format(currentOffer.getDateTime()));

        return convertView;
    }
}


