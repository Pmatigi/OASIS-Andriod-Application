package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakeBraintreePaymentReq {

    @SerializedName("braintree_token")
    @Expose
    private String braintree_token;
    @SerializedName("booking_id")
    @Expose
    private String booking_id;

    public MakeBraintreePaymentReq(String braintree_token, String booking_id) {
        this.braintree_token = braintree_token;
        this.booking_id = booking_id;
    }

    public String getBraintree_token() {
        return braintree_token;
    }

    public void setBraintree_token(String braintree_token) {
        this.braintree_token = braintree_token;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }
}
