package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Bookings implements Serializable {

    @SerializedName("current_booking")
    @Expose
    private List<CurrentBooking> currentBooking = null;
    @SerializedName("past_booking")
    @Expose
    private List<CurrentBooking> pastBooking = null;
    @SerializedName("cancelled_booking")
    @Expose
    private List<CurrentBooking> cancelledBooking = null;

    public List<CurrentBooking> getCurrentBooking() {
        return currentBooking;
    }

    public void setCurrentBooking(List<CurrentBooking> currentBooking) {
        this.currentBooking = currentBooking;
    }

    public List<CurrentBooking> getPastBooking() {
        return pastBooking;
    }

    public void setPastBooking(List<CurrentBooking> pastBooking) {
        this.pastBooking = pastBooking;
    }

    public List<CurrentBooking> getCancelledBooking() {
        return cancelledBooking;
    }

    public void setCancelledBooking(List<CurrentBooking> cancelledBooking) {
        this.cancelledBooking = cancelledBooking;
    }

}