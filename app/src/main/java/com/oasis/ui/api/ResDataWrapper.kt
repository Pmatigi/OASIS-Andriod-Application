package com.oasis.ui.api

import com.google.gson.annotations.SerializedName



class ResDataWrapper<T>(
    @SerializedName("statusCode")
    var statusCode: Int = 0,

    @SerializedName("message")
    var message: String = "",

    @SerializedName("data")
    var data: T? = null)
{
    companion object
    {
        fun <T> success(statusCode: Int, msg: String, data: T?): ResDataWrapper<T>
        {
            return ResDataWrapper(statusCode, msg, data)
        }

        fun <T> error(statusCode: Int, msg: String): ResDataWrapper<T>
        {
            return ResDataWrapper(statusCode, msg, null)
        }
    }


    fun isSuccessFull(): Boolean
    {
        return (statusCode in 200..299)
    }

}