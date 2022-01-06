package com.oasis.ui.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UploadImgReq {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("profile_picture")
    @Expose
    private String profile_picture;

    public UploadImgReq(String user_id, String profile_picture) {
        this.user_id = user_id;
        this.profile_picture = profile_picture;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getProfile_picture() {
        return profile_picture;
    }

    public void setProfile_picture(String profile_picture) {
        this.profile_picture = profile_picture;
    }
}
