package com.oasis.ui.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChangePasswordReq {


    @SerializedName("old_password")
    @Expose
    private String oldPassword;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("new_password")
    @Expose
    private String newPassword;
    @SerializedName("confirm_password")
    @Expose
    private String confirmPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public ChangePasswordReq(String userId,String oldPassword,  String newPassword, String confirmPassword) {
        this.oldPassword = oldPassword;
        this.userId = userId;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }
}