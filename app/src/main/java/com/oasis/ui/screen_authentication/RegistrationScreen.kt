package com.oasis.ui.screen_authentication
import am.appwise.components.ni.NoInternetDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.AppController
import com.oasis.R
import com.oasis.ui.viewmodels.RegistrationViewModel
import com.oasis.databinding.LayoutRegistrationScreenBinding
import com.oasis.ui.callbacks.OnRegisterClickCallback
import com.oasis.ui.models.register.RegisterInfoBean
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util
import kotlinx.android.synthetic.main.layout_registration_screen.*
import java.util.regex.Pattern
import android.util.Patterns
import com.oasis.ui.BaseActivity
import org.json.JSONObject

class RegistrationScreen :BaseActivity(),OnRegisterClickCallback {

   // private lateinit var noInternetDialog: NoInternetDialog
    private lateinit var registerInfoBean: RegisterInfoBean
    private lateinit var mainBinding: LayoutRegistrationScreenBinding
    private lateinit var registerViewModel: RegistrationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.layout_registration_screen)
        init()
    }

    private fun init()
    {
        registerViewModel = ViewModelProviders.of(this).get(RegistrationViewModel::class.java)
        registerInfoBean = RegisterInfoBean()
        mainBinding.registerInfoBean = registerInfoBean
        mainBinding.onRegisterClickCallback=this
    }

    override fun onNextClick(view: View) {
        Util.hideKeyboard(this)
        if (registerInfoBean.username.isEmpty())
        {
            mainBinding.etUsername.error = "Please enter Username"
            mainBinding.etUsername.requestFocus()
            return
        }
        if (registerInfoBean.password.isEmpty())
        {
            mainBinding.etPassword.error = "Please enter Password"
            mainBinding.etPassword.requestFocus()
            return
        }
        if (registerInfoBean.password.trim().length<6)
        {
            mainBinding.etPassword.error = "Password must be at least 6 characters"
            mainBinding.etPassword.requestFocus()
            return
        }
        if (registerInfoBean.confirmPassword.isEmpty())
        {
            mainBinding.etConfirmPassword.error = "Please enter confirm password"
            mainBinding.etConfirmPassword.requestFocus()
            return
        }
        if (!(registerInfoBean.confirmPassword.trim()).equals(registerInfoBean.password.trim()))
        {
            mainBinding.etConfirmPassword.error = "Password and confirm password must be same"
            mainBinding.etConfirmPassword.requestFocus()
            return
        }
        tv_back.text="Back"
        lin_lay_card1.visibility=View.GONE
        lin_lay_card2.visibility=View.VISIBLE
    }

    override fun onRegisterClick(view: View) {
        Util.hideKeyboard(this)
        if (registerInfoBean.email.isEmpty())
        {
            mainBinding.etEmail.error = "Please enter email id"
            mainBinding.etEmail.requestFocus()
            return
        }
        if (!isValidEmail(registerInfoBean.email))
        {
            mainBinding.etEmail.error = "Please enter valid email id"
            mainBinding.etEmail.requestFocus()
            return
        }
        if (registerInfoBean.phone.isEmpty())
        {
            mainBinding.etPhone.error = "Please enter Phone number"
            mainBinding.etPhone.requestFocus()
            return
        }
        if (!isValidPhone(registerInfoBean.phone))
        {
            mainBinding.etPhone.error = "Please enter valid Phone number"
            mainBinding.etPhone.requestFocus()
            return
        }
        if (registerInfoBean.cin.isEmpty())
        {
            mainBinding.etCIN.error = "Please enter CIN number"
            mainBinding.etCIN.requestFocus()
            return
        }
        submitRegister()
    }

    private fun submitRegister() {
        mainBinding.etUsername.clearFocus()
        mainBinding.etPassword.clearFocus() 
        mainBinding.etConfirmPassword.clearFocus()
        mainBinding.etEmail.clearFocus()  
        mainBinding.etPhone.clearFocus()
        mainBinding.etCIN.clearFocus()
        showLoadingWithMessage( "Loading...")
        registerViewModel.registerUser(registerInfoBean.username.trim(), registerInfoBean.password.trim(),
            registerInfoBean.confirmPassword.trim(), registerInfoBean.email.trim(),
            registerInfoBean.phone.trim(), registerInfoBean.cin.trim()).observe(this, Observer {
            Util.showLog("registerUserResponse", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj=JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("messages")
                       // Util.showErrorToast(this,messageArr.substring(0,messageArr.length-1))
                       // Util.showErrorToast(this,"Email Id or User Name already exists.")

                        val jsonObj1=JSONObject(messageArr)
                        var message=""
                        if(jsonObj1.has("username") && jsonObj1.getString("username")!=null) {
                            message = jsonObj1.getString("username")
                            Util.showErrorToast(this,message.substring(2,message.length-3))
                        }else if(jsonObj1.has("email") && jsonObj1.getString("email")!=null) {
                            message = jsonObj1.getString("email")
                            Util.showErrorToast(this,message.substring(2,message.length-3))
                        }
                    }else{
                        Util.showToast(this,jsonObj.getString("messages"));
                        AppController.USERNAME = registerInfoBean.username
                        AppController.PASSWORD = registerInfoBean.password
                        val userId=jsonObj.getJSONObject("results").getJSONObject("user").getString("id");
                        AppController.USERID = userId
                        AppController.TOKEN = "Bearer "+jsonObj.getJSONObject("results").getString("token")
                        val dataVault = DataVaultHelper()
                        dataVault.saveDataValue(this, DataVaultHelper.APP_VAULTNAME, registerInfoBean.username, registerInfoBean.password,
                        "",userId,"Bearer "+jsonObj.getJSONObject("results").getString("token"))
                        startActivity(Intent(this, HomeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                        finish()
                    }

                } else
                {
                    Util.showErrorToast(this, it.message)
                }
            } else
            {
                Util.showErrorToast(this, Constant.ERROR_TRY_AGAIN)
            }
        })
    }

    override fun onNavigateToLoginClick(view: View) {
        if(tv_back.text.toString().equals("Back to Login")) {
            startActivity(Intent(this, LoginScreen::class.java))
            finish();
        }else{
            tv_back.text="Back to Login"
            lin_lay_card1.visibility=View.VISIBLE
            lin_lay_card2.visibility=View.GONE
        }
    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    private fun isValidPhone(mobile: String): Boolean {
        return mobile.matches(PHONE)
    }

    val EMAIL_ADDRESS = Pattern.compile(
         "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
         "\\@" +
         "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
         "(" +
         "\\." +
         "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
         ")+")

    val PHONE = Regex(
         "[6-9][0-9]{9}")

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
