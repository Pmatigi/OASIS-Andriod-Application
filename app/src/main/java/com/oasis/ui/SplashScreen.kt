package com.oasis.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.oasis.AppController
import com.oasis.R
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screen_authentication.LoginScreen
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util


class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       // Util.showToast(this,"On SplashScreen")
        try {
            setContentView(R.layout.layout_splash_screen)
            startSplash()
        }catch (e:Exception){
            Util.showErrorToast(this,"Exception-"+e.toString())
        }
    }

    private fun startSplash() {
        try {
            val dataVault = DataVaultHelper()
            val userDetails = dataVault.getVault(this, DataVaultHelper.APP_VAULTNAME)
           // Util.showToast(this,"userDetails-"+userDetails)

            Handler().postDelayed({
                if (userDetails!!.isNotEmpty()) {
                //    Util.showToast(this,"userDetails[0]-"+userDetails[0])
                    if(userDetails[3]==null){
                        dataVault.deleteVault(this,DataVaultHelper.APP_VAULTNAME)
                        startActivity(Intent(this, LoginScreen::class.java))
                        finish()
                    } else if (userDetails[0] != null) {
                       // Util.showToast(this, "HomeScreen Called")
                        startActivity(Intent(this, HomeActivity::class.java))
                        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                        finish()
                    } else {
                        //Util.showToast(this, "LoginScreen Called")
                        startActivity(Intent(this, LoginScreen::class.java))
                        finish()
                    }
                } else {
                 //   Util.showToast(this, "LoginScreen1 Called")
                    startActivity(Intent(this, LoginScreen::class.java))
                    finish()
                }
            }, 2500)
        }catch (e:Exception){
            Util.showErrorToast(this,"Exception datavault-"+e.toString())

        }
    }

}
