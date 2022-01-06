package com.oasis.ui.models.register
import android.text.TextUtils
import androidx.databinding.BaseObservable

class RegisterInfoBean () : BaseObservable() {

    var username: String = ""
    var password: String = ""
    var confirmPassword: String = ""
    var email: String = ""
    var phone: String = ""
    var cin: String = ""

    fun isDataValid(): Int {
        if (TextUtils.isEmpty(username) && TextUtils.isEmpty(password)&& TextUtils.isEmpty(confirmPassword)
            && TextUtils.isEmpty(email)&& TextUtils.isEmpty(phone)&& TextUtils.isEmpty(cin)) {
            return 0
        } else if (TextUtils.isEmpty(username)) {
            return 1
        } else if (TextUtils.isEmpty(password)) {
            return 2
        } else if (TextUtils.isEmpty(confirmPassword)) {
            return 3
        } else if (TextUtils.isEmpty(email)) {
            return 4
        } else if (TextUtils.isEmpty(phone)) {
            return 5
        } else if (TextUtils.isEmpty(cin)) {
            return 6
        }  else {
            return 7
        }
    }


}