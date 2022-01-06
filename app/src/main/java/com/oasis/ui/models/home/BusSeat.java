package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class BusSeat implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("seat_no")
    @Expose
    private String seatNo;
    @SerializedName("seat_type")
    @Expose
    private String seatType;
    @SerializedName("is_booked")
    @Expose
    private Integer isBooked;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public String getSeatType() {
        return seatType;
    }

    public void setSeatType(String seatType) {
        this.seatType = seatType;
    }

    public Integer getIsBooked() {
        return isBooked;
    }

    public void setIsBooked(Integer isBooked) {
        this.isBooked = isBooked;
    }

}