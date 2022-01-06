package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusDetailRes implements Serializable {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("bus_name")
    @Expose
    private String busName;
    @SerializedName("bus_number")
    @Expose
    private String busNumber;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("reached_time")
    @Expose
    private String reachedTime;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("total_fare")
    @Expose
    private String totalFare;
    @SerializedName("source_id")
    @Expose
    private String sourceId;
    @SerializedName("source_name")
    @Expose
    private String sourceName;
    @SerializedName("destination_id")
    @Expose
    private String destinationId;
    @SerializedName("destination_name")
    @Expose
    private String destinationName;
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
    @SerializedName("bus_seat_type")
    @Expose
    private String busSeatType;
    @SerializedName("bus_type_title")
    @Expose
    private String busTypeTitle;
    @SerializedName("is_ac")
    @Expose
    private String isAc;
    @SerializedName("is_available")
    @Expose
    private String isAvailable;
    @SerializedName("operator_id")
    @Expose
    private String operatorId;
    @SerializedName("operator_name")
    @Expose
    private String operatorName;
    @SerializedName("boarding_point")
    @Expose
    private List<String> boardingPoint = null;
    @SerializedName("drop_point")
    @Expose
    private List<String> dropPoint = null;
    @SerializedName("available_seats")
    @Expose
    private Integer availableSeats;
    @SerializedName("upper_seats")
    @Expose
    private List<UpperSeat> upperSeats = null;
    @SerializedName("lower_seats")
    @Expose
    private List<LowerSeat> lowerSeats = null;
    @SerializedName("bus_amenities")
    @Expose
    private List<BusAmenity> busAmenities = null;
    @SerializedName("bus_images")
    @Expose
    private List<BusImage> busImages = null;
    @SerializedName("bus_ratings")
    @Expose
    private List<Object> busRatings = null;
    @SerializedName("bus_average_ratings")
    @Expose
    private Integer busAverageRatings;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getReachedTime() {
        return reachedTime;
    }

    public void setReachedTime(String reachedTime) {
        this.reachedTime = reachedTime;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getTotalFare() {
        return totalFare;
    }

    public void setTotalFare(String totalFare) {
        this.totalFare = totalFare;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getDestinationId() {
        return destinationId;
    }

    public void setDestinationId(String destinationId) {
        this.destinationId = destinationId;
    }

    public String getDestinationName() {
        return destinationName;
    }

    public void setDestinationName(String destinationName) {
        this.destinationName = destinationName;
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

    public String getBusSeatType() {
        return busSeatType;
    }

    public void setBusSeatType(String busSeatType) {
        this.busSeatType = busSeatType;
    }

    public String getBusTypeTitle() {
        return busTypeTitle;
    }

    public void setBusTypeTitle(String busTypeTitle) {
        this.busTypeTitle = busTypeTitle;
    }

    public String getIsAc() {
        return isAc;
    }

    public void setIsAc(String isAc) {
        this.isAc = isAc;
    }

    public String getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(String isAvailable) {
        this.isAvailable = isAvailable;
    }

    public String getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(String operatorId) {
        this.operatorId = operatorId;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public List<String> getBoardingPoint() {
        return boardingPoint;
    }

    public void setBoardingPoint(List<String> boardingPoint) {
        this.boardingPoint = boardingPoint;
    }

    public List<String> getDropPoint() {
        return dropPoint;
    }

    public void setDropPoint(List<String> dropPoint) {
        this.dropPoint = dropPoint;
    }

    public Integer getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(Integer availableSeats) {
        this.availableSeats = availableSeats;
    }

    public List<UpperSeat> getUpperSeats() {
        return upperSeats;
    }

    public void setUpperSeats(List<UpperSeat> upperSeats) {
        this.upperSeats = upperSeats;
    }

    public List<LowerSeat> getLowerSeats() {
        return lowerSeats;
    }

    public void setLowerSeats(List<LowerSeat> lowerSeats) {
        this.lowerSeats = lowerSeats;
    }

    public List<BusAmenity> getBusAmenities() {
        return busAmenities;
    }

    public void setBusAmenities(List<BusAmenity> busAmenities) {
        this.busAmenities = busAmenities;
    }

    public List<BusImage> getBusImages() {
        return busImages;
    }

    public void setBusImages(List<BusImage> busImages) {
        this.busImages = busImages;
    }

    public List<Object> getBusRatings() {
        return busRatings;
    }

    public void setBusRatings(List<Object> busRatings) {
        this.busRatings = busRatings;
    }

    public Integer getBusAverageRatings() {
        return busAverageRatings;
    }

    public void setBusAverageRatings(Integer busAverageRatings) {
        this.busAverageRatings = busAverageRatings;
    }

}