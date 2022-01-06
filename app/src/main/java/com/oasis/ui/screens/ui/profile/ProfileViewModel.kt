package com.oasis.ui.screens.ui.profile

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.DataRepository.DataRepository
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.profile.GetProfileRes
import com.oasis.ui.utils.Util.Util
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONException
import org.json.JSONObject
import java.io.*

class ProfileViewModel (application: Application) : AndroidViewModel(application) {

    private val dataRepo: DataRepository = DataRepository(application, ApiInterface.getApiService())
    fun changePassword(user_id: String, old_password: String,
                       new_password: String,
                       confirm_password: String,token: String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.changePassword(user_id, old_password,new_password,confirm_password,token)
    }

    fun updateProfile(user_id: String, old_password: String,
                       new_password: String,
                       confirm_password: String,token: String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.updateProfile(user_id, old_password,new_password,confirm_password,token)
    }

    fun hitUploadProfileImg( userId: String, bitmap:Bitmap, jwt_auth_token: String): LiveData<ResDataWrapper<String>> {

        Log.e("dkd", "dkd userId.........................userId...!$userId")
        return dataRepo.uploadProfileImg(userId, bitmap, jwt_auth_token)
    }

    fun getProfile( userId: String,token: String): LiveData<ResDataWrapper<GetProfileRes>> {

        Log.e("dkd", "dkd userId.........................userId...!$userId")
        return dataRepo.getProfile(userId,token)
    }
}
