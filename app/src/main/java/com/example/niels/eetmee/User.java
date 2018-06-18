package com.example.niels.eetmee;

import java.util.ArrayList;

public class User {
    private String name;
    private String bio;
    private String[] reviews;
    private int[] ratings;
    private Diet diet;
    private ArrayList<String> joinedOffers = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setReviews(String[] reviews) {
        this.reviews = reviews;
    }

    public void setRatings(int[] ratings) {
        this.ratings = ratings;
    }

    public void setDiet(Diet diet) {
        this.diet = diet;
    }

    public String getName() {
        return name;
    }

    public String getBio() {
        return bio;
    }

    public String[] getReviews() {
        return reviews;
    }

    public int[] getRatings() {
        return ratings;
    }

    public Diet getDiet() {
        return diet;
    }

    public ArrayList<String> getJoinedOffers() {
        return joinedOffers;
    }

    public void addDinner(String offerKey) {
        joinedOffers.add(offerKey);
    }

    public void removeDinner(String offerKey) {
        joinedOffers.remove(offerKey);
    }
}
