package com.oasis.ui.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ForgotPwdInfoRequest {

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("otp")
    @Expose
    private String otp;

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("confirm_password")
    @Expose
    private String confirm_password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ForgotPwdInfoRequest(String email, String otp, String password, String confirm_password) {
        this.email = email;
        this.otp = otp;
        this.password = password;
        this.confirm_password = confirm_password;
    }


    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }
}
