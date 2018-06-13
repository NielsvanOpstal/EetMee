package com.example.niels.eetmee;

import java.io.Serializable;

public class Adress implements Serializable{
    private String Street;
    private int HouseNumber;
    private String extra;
    private String PostalCode;
    private String city;
    private double lat;
    private double lng;

    public void setStreet(String street) {
        Street = street;
    }

    public void setHouseNumber(int houseNumber) {
        HouseNumber = houseNumber;
    }

    public void setExtra(String building) {
        extra = building;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    public void setCity(String place) {
        this.city = place;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double len) {
        this.lng = len;
    }

    public String getStreet() {
        return Street;
    }

    public int getHouseNumber() {
        return HouseNumber;
    }

    public String getExtra() {
        return extra;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public String getCity() {
        return city;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }
}
