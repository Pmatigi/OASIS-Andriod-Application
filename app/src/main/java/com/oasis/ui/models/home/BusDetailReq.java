package com.oasis.ui.models.home;



public class BusDetailReq {

    private String bus_id;
    private String source_id;
    private String destination_id;
    private String date_of_journey;
    private String time_of_journey;

    public BusDetailReq(String busId, String sourceId, String destinationId, String dateOfJourney, String timeOfJourney) {
        this.bus_id = busId;
        this.source_id = sourceId;
        this.destination_id = destinationId;
        this.date_of_journey = dateOfJourney;
        this.time_of_journey = timeOfJourney;
    }

    public String getBusId() {
        return bus_id;
    }

    public void setBusId(String busId) {
        this.bus_id = busId;
    }

    public String getSourceId() {
        return source_id;
    }

    public void setSourceId(String sourceId) {
        this.source_id = sourceId;
    }

    public String getDestinationId() {
        return destination_id;
    }

    public void setDestinationId(String destinationId) {
        this.destination_id = destinationId;
    }

    public String getDateOfJourney() {
        return date_of_journey;
    }

    public void setDateOfJourney(String dateOfJourney) {
        this.date_of_journey = dateOfJourney;
    }

    public String getTimeOfJourney() {
        return time_of_journey;
    }

    public void setTimeOfJourney(String timeOfJourney) {
        this.time_of_journey = timeOfJourney;
    }

}
