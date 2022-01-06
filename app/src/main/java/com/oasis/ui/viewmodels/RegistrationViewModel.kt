package com.oasis.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.DataRepository.DataRepository
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.register.RegisterRes

class RegistrationViewModel (application: Application) : AndroidViewModel(application) {
    private val dataRepo: DataRepository = DataRepository(application, ApiInterface.getApiService())
    fun registerUser(  username:String, password:String, confirm_password:String, email:String,phone_number:String, cin_number:String): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.register(username, password,confirm_password,email,phone_number,cin_number)
    }

}