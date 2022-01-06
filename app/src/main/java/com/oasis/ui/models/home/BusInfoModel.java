package com.oasis.ui.models.home;

import java.io.Serializable;

public class BusInfoModel implements Serializable {
    String id,bus_name,bus_number,source_id,destination_id,total_seats,total_sleeper,total_sitting
            ,bus_type_id,is_ac,operator_id,created_at,updated_at;

    public BusInfoModel(String id, String bus_name, String bus_number, String source_id,
                        String destination_id, String total_seats, String total_sleeper,
                        String total_sitting, String bus_type_id, String is_ac, String operator_id,
                        String created_at, String updated_at) {
        this.id = id;
        this.bus_name = bus_name;
        this.bus_number = bus_number;
        this.source_id = source_id;
        this.destination_id = destination_id;
        this.total_seats = total_seats;
        this.total_sleeper = total_sleeper;
        this.total_sitting = total_sitting;
        this.bus_type_id = bus_type_id;
        this.is_ac = is_ac;
        this.operator_id = operator_id;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBus_name() {
        return bus_name;
    }

    public void setBus_name(String bus_name) {
        this.bus_name = bus_name;
    }

    public String getBus_number() {
        return bus_number;
    }

    public void setBus_number(String bus_number) {
        this.bus_number = bus_number;
    }

    public String getSource_id() {
        return source_id;
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getDestination_id() {
        return destination_id;
    }

    public void setDestination_id(String destination_id) {
        this.destination_id = destination_id;
    }

    public String getTotal_seats() {
        return total_seats;
    }

    public void setTotal_seats(String total_seats) {
        this.total_seats = total_seats;
    }

    public String getTotal_sleeper() {
        return total_sleeper;
    }

    public void setTotal_sleeper(String total_sleeper) {
        this.total_sleeper = total_sleeper;
    }

    public String getTotal_sitting() {
        return total_sitting;
    }

    public void setTotal_sitting(String total_sitting) {
        this.total_sitting = total_sitting;
    }

    public String getBus_type_id() {
        return bus_type_id;
    }

    public void setBus_type_id(String bus_type_id) {
        this.bus_type_id = bus_type_id;
    }

    public String getIs_ac() {
        return is_ac;
    }

    public void setIs_ac(String is_ac) {
        this.is_ac = is_ac;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
