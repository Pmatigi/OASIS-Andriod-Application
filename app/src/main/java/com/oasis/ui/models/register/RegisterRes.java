package com.oasis.ui.models.register;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.oasis.ui.models.login.Results;

public class RegisterRes {

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("results")
    @Expose
    private Results results;
    @SerializedName("messages")
    @Expose
    private String messages;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Results getResults() {
        return results;
    }

    public void setResults(Results results) {
        this.results = results;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

}
