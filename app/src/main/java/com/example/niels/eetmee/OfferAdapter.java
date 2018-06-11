package com.example.niels.eetmee;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OfferAdapter extends ArrayAdapter<Offer> {

    ArrayList<Offer> offers;

    public OfferAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> objects) {
        super(context, resource, objects);

        offers = objects;
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

        what.setText(currentOffer.getWhat());
        cost.setText("kosten: " +currentOffer.getCosts());
        distance.setText(currentOffer.getAdress());
        time.setText(currentOffer.getTime());
        return convertView;
    }
}


