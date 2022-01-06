package com.oasis.ui.models.profile;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AboutUsRes
{

    @SerializedName("error")
    @Expose
    private Boolean error;
    @SerializedName("page_data")
    @Expose
    private PageData pageData;

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public PageData getPageData() {
        return pageData;
    }

    public void setPageData(PageData pageData) {
        this.pageData = pageData;
    }

}
