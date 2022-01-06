package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class BookingRes {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("success_messages")
    @Expose
    private String successMessages;
    @SerializedName("booking_details")
    @Expose
    private List<BookingDetail> bookingDetails = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getSuccessMessages() {
        return successMessages;
    }

    public void setSuccessMessages(String successMessages) {
        this.successMessages = successMessages;
    }

    public List<BookingDetail> getBookingDetails() {
        return bookingDetails;
    }

    public void setBookingDetails(List<BookingDetail> bookingDetails) {
        this.bookingDetails = bookingDetails;
    }

}