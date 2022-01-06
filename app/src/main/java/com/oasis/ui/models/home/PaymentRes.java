package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRes {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("ticket_link")
    @Expose
    private String ticket_link;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTicket_link() {
        return ticket_link;
    }

    public void setTicket_link(String ticket_link) {
        this.ticket_link = ticket_link;
    }
}
