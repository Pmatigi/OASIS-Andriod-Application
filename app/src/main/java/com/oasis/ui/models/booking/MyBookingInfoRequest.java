package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MyBookingInfoRequest {

    @SerializedName("customer_id")
    @Expose
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public MyBookingInfoRequest(String customerId) {
        this.customerId = customerId;
    }
}