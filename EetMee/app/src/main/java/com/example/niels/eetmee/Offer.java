/*
EetMee
Niels van Opstal 11021519

This class is an offer. It contains a comparable to sort offers based on the distance to a user.
It also contains a Diet class.
 */
package com.example.niels.eetmee;


import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Offer implements Serializable, Comparable {
    private String what, firebaseKey, address, dateString, userID;
    private int costs, persons, personsLeft;
    private Date dateTime;
    private boolean eatTogheter, pickup;
    private Diet diet;
    private double lat, lng;
    private ArrayList<String> eaters = new ArrayList<>();
    private float distance;

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public void setWhat(String what) {
        this.what = what;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setEaters(ArrayList<String> eaters) {
        this.eaters = eaters;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public void setEatTogheter(boolean eatTogheter) {
        this.eatTogheter = eatTogheter;
    }

    public void setPickup(boolean pickup) {
        this.pickup = pickup;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public void setFirebaseKey(String firebaseKey) {
        this.firebaseKey = firebaseKey;
    }

    public void setPersonsLeft(int personsLeft) {
        this.personsLeft = personsLeft;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getWhat() {
        return what;
    }

    public int getPersonsLeft() {
        return persons - eaters.size();
    }

    public int getCosts() {
        return costs;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public int getPersons() {
        return persons;
    }

    public boolean isEatTogheter() {
        return eatTogheter;
    }

    public boolean isPickup() {
        return pickup;
    }

    public String getUserID() {
        return userID;
    }

    public Diet getDiet() {
        return diet;
    }

    public String getFirebaseKey() {
        return firebaseKey;
    }

    public ArrayList<String> getEaters() {
        return eaters;
    }

    public String getAddress() {
        return address;
    }

    public String getDateString() {
        return dateString;
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public float getDistance() {
        return distance;
    }

    public boolean addEater(String userID) {

//        Adds an eater to an offer if joiner is not yet in eaters and there is still place left
        if (eaters.contains(userID)) {
            return false;
        } else if (eaters.size() == persons) {
            return false;
        } else {
            eaters.add(userID);
            return true;
        }

    }

    public void removeEater(String userID) {

//        Removes an eater
        eaters.remove(userID);
    }


    @Override
    public int compareTo(@NonNull Object o) {
        int compareDistance = (int) ((Offer) o).getDistance();
        return (int) this.distance - compareDistance;
    }
}
