package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oasis.ui.models.home.PassengersDetail;

import java.util.List;

public class BookingReq {

    @SerializedName("bus_id")
    @Expose
    private String busId;
    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("from_id")
    @Expose
    private String fromId;
    @SerializedName("to_id")
    @Expose
    private String toId;
    @SerializedName("total_fare")
    @Expose
    private String totalFare;
    @SerializedName("date_of_journey")
    @Expose
    private String dateOfJourney;
    @SerializedName("time_of_journey")
    @Expose
    private String timeOfJourney;
    @SerializedName("seat_no")
    @Expose
    private List<String> seatNo = null;
    @SerializedName("passengers_detail")
    @Expose
    private List<PassengersDetail> passengersDetail = null;

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

    public List<String> getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(List<String> seatNo) {
        this.seatNo = seatNo;
    }

    public List<PassengersDetail> getPassengersDetail() {
        return passengersDetail;
    }

    public void setPassengersDetail(List<PassengersDetail> passengersDetail) {
        this.passengersDetail = passengersDetail;
    }

}