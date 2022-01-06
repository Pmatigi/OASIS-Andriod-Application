package com.oasis.ui.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RegisterInfoRequest {

    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("confirm_password")
    @Expose
    private String confirm_password;
    @SerializedName("phone_number")
    @Expose
    private String phone_number;
    @SerializedName("cin_number")
    @Expose
    private String cin_number;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getCin_number() {
        return cin_number;
    }

    public void setCin_number(String cin_number) {
        this.cin_number = cin_number;
    }

    public RegisterInfoRequest(String username,String password, String confirm_password, String email,  String phone_number, String cin_number) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.confirm_password = confirm_password;
        this.phone_number = phone_number;
        this.cin_number = cin_number;
    }
}
