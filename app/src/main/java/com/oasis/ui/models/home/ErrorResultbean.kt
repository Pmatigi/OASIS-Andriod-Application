package com.oasis.ui.models.ErrorResultbean

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by dkd
 * on
 * 26,April,2019
 */

data class ErrorResultbean(
    @SerializedName("Message")
    var message: String? = null,
    @SerializedName("LogNo")
    val logNo: String? = null,
    @SerializedName("System")
    val system: String? = null,
    @SerializedName("Field")
    val field: String? = null,
    @SerializedName("Type")
    var type: String? = null,
    @SerializedName("Number")
    val number: String? = null,
    @SerializedName("MessageV4")
    val MessageV4: String? = null,
    @SerializedName("MessageV3")
    val MessageV3: String? = null,
    @SerializedName("Parameter")
    val parameter: String? = null,
    @SerializedName("Id")
    val id: String? = null,
    @SerializedName("Row")
    val row: Int? = 0,
    @SerializedName("LogMsgNo")
    val logMsgNo: String? = null,
    @SerializedName("MessageV2")
    val MessageV2: String? = null,
    @SerializedName("MessageV1")
    val messageV: String? = null) : Serializable