package com.oasis.ui.models.login

import android.text.TextUtils
import androidx.databinding.BaseObservable

class LoginInfoBean() : BaseObservable() {


    var username: String = "Oasisbus@gmail.com"
    var password: String = "123456"

    fun isDataValid(): Int {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)) {
            return 0
        } else if (TextUtils.isEmpty(username)) {
            return 1
        } else if (TextUtils.isEmpty(password)) {
            return 2
        } else {
            return 4
        }
    }


}