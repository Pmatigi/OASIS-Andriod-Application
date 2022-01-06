package com.oasis.ui.models.home;

public class SearchBusInfoRequest {
    String source_id,destination_id,date_of_journey,time_of_journey;

    public SearchBusInfoRequest(String sourceId, String destinationId
            ,String date_of_journey,String time_of_journey) {
        this.source_id = sourceId;
        this.destination_id = destinationId;
        this.date_of_journey = date_of_journey;
        this.time_of_journey = time_of_journey;
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

    public String getDate_of_journey() {
        return date_of_journey;
    }

    public void setDate_of_journey(String date_of_journey) {
        this.date_of_journey = date_of_journey;
    }

    public String getTime_of_journey() {
        return time_of_journey;
    }

    public void setTime_of_journey(String time_of_journey) {
        this.time_of_journey = time_of_journey;
    }
}
