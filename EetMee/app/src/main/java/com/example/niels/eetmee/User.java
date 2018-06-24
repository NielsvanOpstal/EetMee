package com.example.niels.eetmee;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
    private String name;
    private String bio;
    private ArrayList<Review> reviews =  new ArrayList<>();
    private Diet diet;
    private ArrayList<String> joinedOffers = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public void setBio(String bio) {
        this.bio = bio;
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

    public Diet getDiet() {
        return diet;
    }

    public ArrayList<String> getJoinedOffers() {
        return joinedOffers;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void addDinner(String offerKey) {
        joinedOffers.add(offerKey);
    }

    public void removeDinner(String offerKey) {
        joinedOffers.remove(offerKey);
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

}
