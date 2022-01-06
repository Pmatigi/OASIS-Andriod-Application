package com.oasis.ui.models.profile;

import com.google.gson.annotations.SerializedName;

public class GetProfileReq {
    @SerializedName("user_id")
    public String UserId;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public GetProfileReq(String userId) {
        UserId = userId;
    }
}
