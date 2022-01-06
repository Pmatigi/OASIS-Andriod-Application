package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class BusRating implements Serializable {

    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("review")
    @Expose
    private String review;
    @SerializedName("ratings")
    @Expose
    private String ratings;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

}