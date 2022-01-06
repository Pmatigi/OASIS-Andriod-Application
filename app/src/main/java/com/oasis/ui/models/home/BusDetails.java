package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusDetails implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bus_name")
    @Expose
    private String busName;
    @SerializedName("bus_number")
    @Expose
    private String busNumber;
    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("destination_id")
    @Expose
    private String destinationId;
    @SerializedName("total_seats")
    @Expose
    private String totalSeats;
    @SerializedName("total_sleeper")
    @Expose
    private String totalSleeper;
    @SerializedName("total_sitting")
    @Expose
    private String totalSitting;
    @SerializedName("bus_type_id")
    @Expose
    private String busTypeId;
    @SerializedName("is_ac")
    @Expose
    private String isAc;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("is_available")
    @Expose
    private String isAvailable;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("bus_seats")
    @Expose
    private List<BusSeat> busSeats = null;
    @SerializedName("booked_seats")
    @Expose
    private List<Object> bookedSeats = null;
    @SerializedName("amenities")
    @Expose
    private List<BusAmenity> amenities = null;
    @SerializedName("bus_images")
    @Expose
    private List<BusImage> busImages = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBusName() {
        return busName;
    }

    public void setBusName(String busName) {
        this.busName = busName;
    }

    public String getBusNumber() {
        return busNumber;
    }

    public void setBusNumber(String busNumber) {
        this.busNumber = busNumber;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getTotalSeats() {
        return totalSeats;
    }

    public void setTotalSeats(String totalSeats) {
        this.totalSeats = totalSeats;
    }

    public String getTotalSleeper() {
        return totalSleeper;
    }

    public void setTotalSleeper(String totalSleeper) {
        this.totalSleeper = totalSleeper;
    }

    public String getTotalSitting() {
        return totalSitting;
    }

    public void setTotalSitting(String totalSitting) {
        this.totalSitting = totalSitting;
    }

    public String getBusTypeId() {
        return busTypeId;
    }

    public void setBusTypeId(String busTypeId) {
        this.busTypeId = busTypeId;
    }

    public String getIsAc() {
        return isAc;
    }

    public void setIsAc(String isAc) {
        this.isAc = isAc;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<BusSeat> getBusSeats() {
        return busSeats;
    }

    public void setBusSeats(List<BusSeat> busSeats) {
        this.busSeats = busSeats;
    }

    public List<Object> getBookedSeats() {
        return bookedSeats;
    }

    public void setBookedSeats(List<Object> bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    public List<BusAmenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<BusAmenity> amenities) {
        this.amenities = amenities;
    }

    public List<BusImage> getBusImages() {
        return busImages;
    }

    public void setBusImages(List<BusImage> busImages) {
        this.busImages = busImages;
    }

}