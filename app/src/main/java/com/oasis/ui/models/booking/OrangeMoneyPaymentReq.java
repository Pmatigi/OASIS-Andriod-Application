package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oasis.ui.models.home.PassengersDetail;

import java.util.List;

public class OrangeMoneyPaymentReq {

    @SerializedName("payment_type")
    @Expose
    private String payment_type;
    @SerializedName("booking_id")
    @Expose
    private String booking_id;
    @SerializedName("mobile_no")
    @Expose
    private String mobile_no;

    public OrangeMoneyPaymentReq(String payment_type, String booking_id, String mobile_no) {
        this.payment_type = payment_type;
        this.booking_id = booking_id;
        this.mobile_no = mobile_no;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }
}