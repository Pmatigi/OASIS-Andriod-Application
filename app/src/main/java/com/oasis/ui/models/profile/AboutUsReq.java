package com.oasis.ui.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsReq {
    @SerializedName("type")
    @Expose
    private String type;

    public AboutUsReq(String type) {
        this.type = type;
    }
}
