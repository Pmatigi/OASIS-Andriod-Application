package com.oasis.ui.screen_authentication
import am.appwise.components.ni.NoInternetDialog
import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.Window
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.AppController
import com.oasis.R
import com.oasis.databinding.LayoutLoginScreenBinding
import com.oasis.ui.BaseActivity
import com.oasis.ui.callbacks.OnLoginClickCallback
import com.oasis.ui.models.login.LoginInfoBean
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util
import com.oasis.ui.viewmodels.LoginViewModel.LoginViewModel
import kotlinx.android.synthetic.main.layout_login_screen.*
import org.json.JSONObject
import java.lang.Exception

class LoginScreen : BaseActivity() , OnLoginClickCallback {

    private lateinit var loginInfoBean: LoginInfoBean
    private lateinit var mainBinding: LayoutLoginScreenBinding
    private lateinit var loginViewModel: LoginViewModel
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.layout_login_screen)
        init()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onResume() {
        super.onResume()
    }


    private fun init() {
        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        loginInfoBean = LoginInfoBean()
        mainBinding.loginInfoBean = loginInfoBean
        mainBinding.onLoginClickCallback = this
        tv_forgot_password.setOnClickListener {
            startActivity(Intent(this, ForgotPasswordActivity::class.java))
            overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
            finish()
        }
    }


    override fun onNavigateToRegisterClick(view: View) {

        startActivity(Intent(this, RegistrationScreen::class.java))
        finish()
    }

    override fun onLoginClick(view: View) {
        Util.hideKeyboard(this)
        try {
            submitLogin()
        }catch (e:Exception){
            e.printStackTrace()
        }
    }


    private fun submitLogin() {
        mainBinding.etUsername.clearFocus()
        mainBinding.etPassword.clearFocus()
       showLoadingWithMessage( "Loading...")
        var username: String = "Oasisbus@gmail.com"
        var password: String = "123456"
        loginViewModel.loginUser(username, password).observe(this, Observer {
            Util.showLog("LoginResponse", "" + it.data)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("messages")
                        val jsonObj1=JSONObject(messageArr)
                        var message=""

                        if(jsonObj1.has("email") && jsonObj1.getString("email")!=null) {
                            message = jsonObj1.getString("email")
                            Util.showErrorToast(this,message.substring(2,message.length-3))
                        }else  if(jsonObj1.has("password") && jsonObj1.getString("password")!=null) {
                            message = jsonObj1.getString("password")
                            Util.showErrorToast(this,message.substring(2,message.length-3))
                        }else {
                            Util.showErrorToast(this,messageArr.substring(2,message.length-3))
                        }
                    }else{
                        Util.showToast(this,jsonObj.getString("messages"));
                        AppController.USERNAME = username
                        AppController.PASSWORD = password
                        val userId=jsonObj.getJSONObject("results").getJSONObject("user").getString("id");
                        AppController.USERID = userId
                        AppController.TOKEN ="Bearer "+ jsonObj.getJSONObject("results").getString("token")

                        val dataVault = DataVaultHelper()
                        dataVault.saveDataValue(this, DataVaultHelper.APP_VAULTNAME, loginInfoBean.username, loginInfoBean.password, "",userId,"Bearer "+jsonObj.getJSONObject("results").getString("token"))

                        startActivity(Intent(this, HomeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                        finish()
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

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }
}
