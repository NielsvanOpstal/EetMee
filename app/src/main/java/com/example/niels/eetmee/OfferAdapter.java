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

import java.util.ArrayList;
// TODO: crashvrij maken wanneer er geen coordinaten zijn
// TODO: tijd printen in 00:00 ipv 00:0
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
        Offer currentOffer = offers.get(position);
        TextView what = convertView.findViewById(R.id.WhatField);
        TextView cost = convertView.findViewById(R.id.CostField);
        TextView distance = convertView.findViewById(R.id.DistanceField);
        TextView time = convertView.findViewById(R.id.TimeField);

        Address address = currentOffer.getAddress();
        float[] results = new float[1];
        Location.distanceBetween(lat, lng, address.getLat(), address.getLng(), results);
        float dist = results[0] / 1000;
        what.setText(currentOffer.getWhat());
        cost.setText("kosten: " +currentOffer.getCosts());
        time.setText(currentOffer.printTime());
        distance.setText("afstand:" + dist + "km");

        return convertView;
    }
}


