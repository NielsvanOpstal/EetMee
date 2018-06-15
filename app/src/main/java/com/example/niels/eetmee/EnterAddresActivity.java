package com.example.niels.eetmee;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
//TODO: de response classes fixen
//TODO: coordinaten in het adress zetten.
public class EnterAddresActivity extends AppCompatActivity implements CoordinatesRequest.Callback{

    private Offer offer;
    private Address adress;

    private EditText street;
    private EditText houseNumber;
    private EditText extra;
    private EditText postalCode;
    private EditText city;

    private String streetString;
    private int houseNumberInt = -1;
    private String extraString;
    private String postalCodeString;
    private String cityString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_adress_activity);

        street = findViewById(R.id.EnterStreet);
        houseNumber = findViewById(R.id.EnterNumber);
        extra = findViewById(R.id.EnterExtra);
        postalCode = findViewById(R.id.EnterPostal);
        city = findViewById(R.id.EnterCity);

        offer = (Offer) getIntent().getSerializableExtra("offermade");


    }

    public void adressadded(View view) {

        streetString = street.getText().toString();
        houseNumberInt = Integer.parseInt(houseNumber.getText().toString());
        extraString = extra.getText().toString();
        postalCodeString = postalCode.getText().toString();
        cityString = city.getText().toString();

        if (validateForms()) {
            adress = new Address();
            adress.setStreet(streetString);
            adress.setHouseNumber(houseNumberInt);
            adress.setExtra(extraString);
            adress.setPostalCode(postalCodeString);
            adress.setCity(cityString);
            String addressString = postalCodeString + "," + cityString + "," + "," + streetString + "," + houseNumber;
//            String addressString = postalCodeString + "," +  houseNumber + "," + streetString;


            CoordinatesRequest coordinatesRequest = new CoordinatesRequest(this, addressString);
            coordinatesRequest.getCoordinates(this);

        }
    }

    private boolean validateForms () {
        boolean valid = true;

        if (TextUtils.isEmpty(streetString)) {
            street.setError("required");
            valid = false;
        }
        else {
            street.setError(null);
        }
        if (houseNumberInt == -1) {
            houseNumber.setError("required");
            valid = false;
        }
        else {
            houseNumber.setError(null);
        }
        if (TextUtils.isEmpty(postalCodeString)) {
            postalCode.setError("required");
            valid = false;
        }
        else {
            postalCode.setError(null);
        }
        if (TextUtils.isEmpty(cityString)) {
            city.setError("required");
            valid = false;
        }
        else {
            city.setError(null);
        }
        return valid;
    }


    @Override
    public void gotCoordinates(double[] latlong) {
        Log.d("ja", "" + latlong);
        adress.setLat(latlong[0]);
        adress.setLng(latlong[1]);

        offer.setAddress(adress);
        Intent intent = new Intent(EnterAddresActivity.this, DietActivity.class);
        intent.putExtra("offerfromadress", offer);
        startActivity(intent);
    }

    @Override
    public void gotCoordinatesError(String message) {
//        TODO: fixen
        Log.d("HOi", "ALOASDFO");

    }
}
