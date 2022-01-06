package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CancelBookingReq  {

    @SerializedName("booking_id")
    @Expose
    private String bookingId;


    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public CancelBookingReq(String bookingId) {
        this.bookingId = bookingId;
    }
}