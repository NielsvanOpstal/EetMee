package com.example.niels.eetmee;

public class Offer {
    private String what;
    private int costs;
    private String time;
    private int persons;
    private String adress;
    private boolean eatTogheter;
    private boolean pickup;
    private String userID;

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

    public String getWhat() {
        return what;
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
}
