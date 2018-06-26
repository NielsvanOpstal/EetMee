/*
EetMee
Niels van Opstal 11021519

A review class. Used in the User class.
 */

package com.example.niels.eetmee;

import java.io.Serializable;
import java.util.Date;

public class Review implements Serializable {
    private String review, reviewWriter;
    private Date date;

    public void setReview(String review) {
        this.review = review;
    }

    public void setReviewWriter(String reviewWriter) {
        this.reviewWriter = reviewWriter;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getReview() {
        return review;
    }

    public String getReviewWriter() {
        return reviewWriter;
    }

    public Date getDate() {
        return date;
    }
}
