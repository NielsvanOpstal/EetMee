package com.example.niels.eetmee;

import java.io.Serializable;

public class Offer implements Serializable {
    private String what;
    private int costs;
    private String time;
    private int persons;
    private String adress;
    private boolean eatTogheter;
    private boolean pickup;
    private String userID;
    private Diet diet;
    private String firebaseKey;
    private int personsLeft;
    private Adress location;

    public void setWhat(String what) {
        this.what = what;
    }

    public void setCosts(int costs) {
        this.costs = costs;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setPersons(int persons) {
        this.persons = persons;
    }

    public void setAdress(String adress) {
        this.adress = adress;
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

    public void setLocation(Adress location) {
        this.location = location;
    }

    public String getWhat() {
        return what;
    }

    public int getPersonsLeft() {
        return personsLeft;
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

    public String getAdress() {
        return adress;
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

    public void decrementPersons() {
        this.personsLeft -= 1;
    }

    public Adress getLocation() {
        return location;
    }
}
