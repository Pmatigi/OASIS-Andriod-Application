package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MakePaymentReq {

    @SerializedName("stripe_token")
    @Expose
    private String stripeToken;
    @SerializedName("booking_id")
    @Expose
    private String bookingId;

    public String getStripeToken() {
        return stripeToken;
    }

    public void setStripeToken(String stripeToken) {
        this.stripeToken = stripeToken;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public MakePaymentReq(String stripeToken, String bookingId) {
        this.stripeToken = stripeToken;
        this.bookingId = bookingId;
    }
}