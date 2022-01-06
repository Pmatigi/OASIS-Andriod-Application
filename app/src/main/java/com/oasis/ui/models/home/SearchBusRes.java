package com.oasis.ui.models.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SearchBusRes implements Serializable{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("date_of_journey")
    @Expose
    private String dateOfJourney;
    @SerializedName("bus_list")
    @Expose
    private List<BusList> busList = null;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public String getDateOfJourney() {
        return dateOfJourney;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.dateOfJourney = dateOfJourney;
    }

    public List<BusList> getBusList() {
        return busList;
    }

    public void setBusList(List<BusList> busList) {
        this.busList = busList;
    }

}