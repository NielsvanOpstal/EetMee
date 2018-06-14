package com.example.niels.eetmee;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// TODO: niet verder laten gaan totdat goed adress is ingevuld.
public class CoordinatesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private String adress;
    private Context context;
    private Callback activity;
    private String APIKEY = "AIzaSyBLgAOfXM0AEAW_XXAmQpxNgC9qxRMO2Do";

    public interface Callback {
        void gotCoordinates(double[] latlong);
        void gotCoordinatesError(String message);
    }

    public CoordinatesRequest(Context aContext, String aAdress) {
        adress = aAdress;
        context = aContext;
    }

    public void getCoordinates(Callback aActivity) {
        activity = aActivity;

        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?address=" + adress + "&key=" + APIKEY, null, this, this);
//        https://maps.googleapis.com/maps/api/geocode/json?address=5554NP,Valkenswaard&key=AIzaSyBLgAOfXM0AEAW_XXAmQpxNgC9qxRMO2Do
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://maps.googleapis.com/maps/api/geocode/json?address=5554NP,Valkenswaard&key=AIzaSyBLgAOfXM0AEAW_XXAmQpxNgC9qxRMO2Do", null, this, this);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        activity.gotCoordinatesError(error.getMessage());
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Log.d("something", response.toString());
            JSONObject coordinates = response.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("bounds").getJSONObject("northeast");
            // Kan dit mooier?
            double[] latlng = new double[2];
            latlng[0] = coordinates.getDouble("lat");;
            latlng[1] = coordinates.getDouble("lng");
            activity.gotCoordinates(latlng);
        }
        catch (JSONException e) {
            Log.d("something", e.getMessage());
        }
    }
}
