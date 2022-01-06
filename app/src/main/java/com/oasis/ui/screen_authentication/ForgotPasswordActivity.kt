package com.oasis.ui.screen_authentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.AppController
import com.oasis.R
import com.oasis.ui.BaseActivity
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util
import com.oasis.ui.viewmodels.LoginViewModel.LoginViewModel
import kotlinx.android.synthetic.main.activity_forgot_password.*
import kotlinx.android.synthetic.main.layout_registration_screen.*
import org.json.JSONObject

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        lin_lay_move_to_login.setOnClickListener {
            onNavigateToLoginClick()
        }

        btnSendOTP1.setOnClickListener {
            sendOTP1Validate()
        }

        btnSendOTP.setOnClickListener {
            sendOTPValidate()
        }

        btnVerifyOTP.setOnClickListener {
            forgotPwdValidate()
        }
    }

     fun sendOTP1Validate() {
          if(etEmailFP1.text.toString().trim().equals("")){
             Util.showToast(this, "Please Enter Email")
         }else if(!isValidEmail(etEmailFP1.text.toString().trim())){
              Util.showToast(this, "Please Enter Valid Email")
        }else{
            sendOTPAPI()
        }
    }

    fun sendOTPValidate() {
          if(etEmailFP.text.toString().trim().equals("")){
             Util.showToast(this, "Please Enter Email")
         }else if(!isValidEmail(etEmailFP.text.toString().trim())){
              Util.showToast(this, "Please Enter Valid Email")
        }else{
            sendOTPAPI()
        }
    }

    fun sendOTPAPI() {
            showLoading()
            loginViewModel.sendOTP(etEmailFP1.text.toString().trim()).observe(this, Observer {
                Util.showLog("sendOTPAPI", "" + it.data)
                hideLoading()
                if (it != null)
                {
                    if (it.isSuccessFull())
                    {
                        val jsonObj= JSONObject(it.data)
                        var error=jsonObj.getBoolean("error")

                        if(error){
                            var messageArr=jsonObj.getString("messages")
                            val jsonObj1= JSONObject(messageArr)
                            var message=""

                            if(jsonObj1.has("email") && jsonObj1.getString("email")!=null) {
                                message = jsonObj1.getString("email")
                                Util.showErrorToast(this,message.substring(2,message.length-3))
                            }else {
                                Util.showErrorToast(this,messageArr.substring(2,message.length-3))
                            }
                        }else{
                            Util.showToast(this,jsonObj.getString("messages"));
                            verifyOTPLayoutVisible()
                        }
                    } else {
                        Util.showErrorToast(this, it.message)
                    }
                } else
                {
                    Util.showErrorToast(this, Constant.ERROR_TRY_AGAIN)
                }
            })
        }

    fun verifyOTPLayoutVisible(){
        val emailStr=etEmailFP1.text.toString()
        lin_lay_send_otp.visibility= View.GONE
        lin_lay_card_verify_otp.visibility= View.VISIBLE
        btnSendOTP.text="Resend OTP"
        etEmailFP.setText(emailStr)
    }

    fun forgotPwdValidate() {
        if(!isValidEmail(etEmailFP.text.toString().trim())){
            Util.showToast(this, "Please Enter Valid Email")
        }else if(etEmailFP.text.toString().trim().equals("")){
            Util.showToast(this, "Please Enter Email")
        }else if(etOTP.text.toString().trim().equals("")){
            Util.showToast(this, "Please Enter OTP")
        }else{
            forgotPwdAPI()
        }
    }

    fun forgotPwdAPI() {
        if(etEmailFP.text.toString().trim().equals("")){
              Util.showToast(this, "Please Enter Email")
        }else  if(etOTP.text.toString().trim().equals("")){
            Util.showToast(this, "Please Enter OTP")
        }else  if(etPasswordChange.text.toString().trim().equals("")){
            Util.showToast(this, "Please Enter New Password")
        }else  if(etPasswordChange.text.toString().trim().length<8){
            Util.showToast(this, "New Password should be minimum 8 character")
        }else  if(etConfirmNewPassword.text.toString().trim().equals("")){
            Util.showToast(this, "Please Enter Confirm Password")
        }else  if(etConfirmNewPassword.text.toString().trim().length<8){
            Util.showToast(this, "Confirm Password should be minimum 8 character")
        }else  if(!((etPasswordChange.text.toString().trim()).equals(etConfirmNewPassword.text.toString().trim()))){
            Util.showToast(this, "New Password and Confirm Password should be same")
        }else{
            showLoading()
            loginViewModel.forgotPwd(etEmailFP.text.toString().trim(),etOTP.text.toString().trim(),etPasswordChange.text.toString().trim(),etConfirmNewPassword.text.toString().trim()).observe(this, Observer {
                Util.showLog("forgotPwdAPI", "" + it.data)
                hideLoading()
                if (it != null)
                {
                    if (it.isSuccessFull())
                    {
                        val jsonObj= JSONObject(it.data)
                        var error=jsonObj.getBoolean("error")

                        if(error){
                            var messageArr=jsonObj.getString("messages")
                            val jsonObj1= JSONObject(messageArr)
                            var message=""

                            if(jsonObj1.has("email") && jsonObj1.getString("email")!=null) {
                                message = jsonObj1.getString("email")
                                Util.showErrorToast(this,message.substring(2,message.length-3))
                            }else {
                                Util.showErrorToast(this,messageArr.substring(2,message.length-3))
                            }
                        }else{
                            Util.showToast(this,jsonObj.getString("messages"));
                            onNavigateToLoginClick()
                        }
                    } else {
                        Util.showErrorToast(this, it.message)
                    }
                } else
                {
                    Util.showErrorToast(this, Constant.ERROR_TRY_AGAIN)
                }
            })
        }
    }


    fun onNavigateToLoginClick() {
        startActivity(Intent(this, LoginScreen::class.java))
        finish();
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}