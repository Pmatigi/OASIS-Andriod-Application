package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PassengersDetail {

    @SerializedName("passenger_name")
    @Expose
    private String passengerName;
    @SerializedName("passenger_phone")
    @Expose
    private String passengerPhone;
    @SerializedName("passenger_email")
    @Expose
    private String passengerEmail;
    @SerializedName("passenger_gender")
    @Expose
    private String passengerGender;
    @SerializedName("passenger_age")
    @Expose
    private String passengerAge;

    public String getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public String getPassengerPhone() {
        return passengerPhone;
    }

    public void setPassengerPhone(String passengerPhone) {
        this.passengerPhone = passengerPhone;
    }

    public String getPassengerEmail() {
        return passengerEmail;
    }

    public void setPassengerEmail(String passengerEmail) {
        this.passengerEmail = passengerEmail;
    }

    public String getPassengerGender() {
        return passengerGender;
    }

    public void setPassengerGender(String passengerGender) {
        this.passengerGender = passengerGender;
    }

    public String getPassengerAge() {
        return passengerAge;
    }

    public void setPassengerAge(String passengerAge) {
        this.passengerAge = passengerAge;
    }


    public PassengersDetail(String passengerName, String passengerPhone, String passengerEmail, String passengerGender, String passengerAge) {
        this.passengerName = passengerName;
        this.passengerPhone = passengerPhone;
        this.passengerEmail = passengerEmail;
        this.passengerGender = passengerGender;
        this.passengerAge = passengerAge;
    }
}
