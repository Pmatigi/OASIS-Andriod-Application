package com.oasis

import android.app.Application
import android.location.Location
import com.oasis.ui.models.login.LoginRes
import android.os.StrictMode
import androidx.multidex.MultiDexApplication


class AppController:MultiDexApplication() {


    override fun onCreate()
    {
        super.onCreate()
        mInstance = this

        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())
    }

    fun init(): AppController?
    {
        if (mInstance == null)
        {
            mInstance = this
        }
        return mInstance
    }


    companion object
    {
        var USERNAME = ""
        var PASSWORD = ""
        var USERID = ""
        var TOKEN = ""
        var mInstance: AppController? = null
    }

}