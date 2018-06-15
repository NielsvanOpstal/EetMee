package com.example.niels.eetmee;

import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

public class Offer implements Serializable {
    private String what;
    private int costs;
    private String time;
    private int persons;
    private boolean eatTogheter;
    private boolean pickup;
    private String userID;
    private Diet diet;
    private String firebaseKey;
    private int personsLeft;
    private Address address;
    private ArrayList<String> eaters = new ArrayList<>();


    public void setWhat(String what) {
        this.what = what;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setEaters(ArrayList<String> eaters) {
        this.eaters = eaters;
    }

    public void setTime(String time) {
        this.time = time;
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

    public void setAddress(Address address) {
        this.address = address;
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

    public String getTime() {
        return time;
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

    public Address getAddress() {
        return address;
    }

    public boolean addEater(String userID) {
        Log.d("EATERS", eaters.toString());
        if (eaters.contains(userID)) {
            return false;
        }
        else if (eaters.size() == persons) {
            return false;
        }
        else {
            Log.d("EATERS", eaters.toString());
            eaters.add(userID);
            Log.d("EATERS", eaters.toString());
            return true;
        }

    }

    public void removeEater(String userID) {
        Log.d("EATERS", eaters.toString());
        eaters.remove(userID);
        Log.d("EATERS", userID);
        Log.d("EATERS", eaters.toString());
    }
}
