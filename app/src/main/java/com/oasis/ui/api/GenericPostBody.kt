package com.oasis.ui.api.GenericPostBody

import com.google.gson.annotations.SerializedName
import com.oasis.BuildConfig

data class GenericPostBody(
    @SerializedName("method")
    var method: String? = "",

    @SerializedName("entityname")
    var entityname: String? = "",

    @SerializedName("data")
    var data: Any?)
{

    @SerializedName("appid")
    val appid: String = "oasis"
}