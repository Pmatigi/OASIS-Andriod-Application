package com.oasis.ui.viewmodels.LoginViewModel
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.DataRepository.DataRepository
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.login.LoginRes

class LoginViewModel(application: Application) : AndroidViewModel(application)
{
    private val dataRepo: DataRepository = DataRepository(application, ApiInterface.getApiService())
    fun loginUser(username: String, password: String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.login(username, password)
    }

    fun forgotPwd(email: String,otp: String ,password: String,newPassword: String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.forgotPwd(email,otp,password,newPassword)
    }

    fun sendOTP(email: String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.sendOTP(email)
    }

}