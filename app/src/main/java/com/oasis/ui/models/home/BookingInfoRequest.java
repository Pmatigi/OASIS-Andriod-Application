package com.oasis.ui.models.home;

import java.util.List;

public class BookingInfoRequest {

    private List<String> seatNo = null;
    private String busId;
    private String customerId;
    private String passengerName;
    private String passengerPhone;
    private String gender;
    private String fromId;
    private String toId;
    private String totalFare;
    private String dateOfJourney;
    private String timeOfJourney;

    public List<String> getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(List<String> seatNo) {
        this.seatNo = seatNo;
    }

    public String getBusId() {
        return busId;
    }

    public void setBusId(String busId) {
        this.busId = busId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFromId() {
        return fromId;
    }

    public void setFromId(String fromId) {
        this.fromId = fromId;
    }

    public String getToId() {
        return toId;
    }

    public void setToId(String toId) {
        this.toId = toId;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public String getTimeOfJourney() {
        return timeOfJourney;
    }

    public void setTimeOfJourney(String timeOfJourney) {
        this.timeOfJourney = timeOfJourney;
    }

}
