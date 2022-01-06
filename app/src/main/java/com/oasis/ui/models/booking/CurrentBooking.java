package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CurrentBooking implements Serializable {

    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("starting_point")
    @Expose
    private String startingPoint;
    @SerializedName("destination_point")
    @Expose
    private String destinationPoint;
    @SerializedName("booking_date_time")
    @Expose
    private String bookingDateTime;
    @SerializedName("boarding_point")
    @Expose
    private String boardingPoint;
    @SerializedName("drop_point")
    @Expose
    private String dropPoint;
    @SerializedName("passenger_name")
    @Expose
    private Object passengerName;
    @SerializedName("ticket_id")
    @Expose
    private String ticketId;
    @SerializedName("pnr_no")
    @Expose
    private String pnrNo;
    @SerializedName("ticket_pdf")
    @Expose
    private String ticket_pdf;
    @SerializedName("total_fare_accepted")
    @Expose
    private String totalFareAccepted;
    @SerializedName("seat_number")
    @Expose
    private List<String> seatNumber = null;

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getStartingPoint() {
        return startingPoint;
    }

    public void setStartingPoint(String startingPoint) {
        this.startingPoint = startingPoint;
    }

    public String getDestinationPoint() {
        return destinationPoint;
    }

    public void setDestinationPoint(String destinationPoint) {
        this.destinationPoint = destinationPoint;
    }

    public String getBookingDateTime() {
        return bookingDateTime;
    }

    public void setBookingDateTime(String bookingDateTime) {
        this.bookingDateTime = bookingDateTime;
    }

    public String getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(String boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public String getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(String dropPoint) {
        this.dropPoint = dropPoint;
    }

    public Object getPassengerName() {
        return passengerName;
    }

    public void setPassengerName(Object passengerName) {
        this.passengerName = passengerName;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getPnrNo() {
        return pnrNo;
    }

    public void setPnrNo(String pnrNo) {
        this.pnrNo = pnrNo;
    }

    public String getTotalFareAccepted() {
        return totalFareAccepted;
    }

    public void setTotalFareAccepted(String totalFareAccepted) {
        this.totalFareAccepted = totalFareAccepted;
    }

    public List<String> getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(List<String> seatNumber) {
        this.seatNumber = seatNumber;
    }


    public String getTicket_pdf() {
        return ticket_pdf;
    }

    public void setTicket_pdf(String ticket_pdf) {
        this.ticket_pdf = ticket_pdf;
    }
}