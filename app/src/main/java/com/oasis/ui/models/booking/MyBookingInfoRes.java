package com.oasis.ui.models.booking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MyBookingInfoRes implements Serializable {

                @SerializedName("error")
                @Expose
                private Boolean error;
                @SerializedName("bookings")
                @Expose
                private Bookings bookings;

                public Boolean getError() {
                return error;
        }

                public void setError(Boolean error) {
                this.error = error;
        }

                public Bookings getBookings() {
                return bookings;
        }

                public void setBookings(Bookings bookings) {
                this.bookings = bookings;
        }

        }
