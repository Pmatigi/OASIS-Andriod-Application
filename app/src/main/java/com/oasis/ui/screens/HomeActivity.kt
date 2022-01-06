package com.oasis.ui.screens

import Constant.Companion.EXIT_APP
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.oasis.R
import com.oasis.ui.BaseActivity
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.utils.Util.Util
import com.ril.utils.ActionDialog
import java.lang.Exception

class HomeActivity : BaseActivity() {
    var  profilePhoto: Bitmap?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        try {

            val appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.navigation_home, R.id.navigation_booking, R.id.navigation_profile
                )
            )
            navView.setupWithNavController(navController)
        }catch (e:Exception){
            //Util.showToast(this,e.toString())
        }
    }

    override fun onBackPressed() {
        showExitAlert()
    }
    private fun showExitAlert()
    {
        ActionDialog(this, "Exit", EXIT_APP, object : ActionDialog.OnDialogClick
        {
            override fun reject(inputText: String, type: Int)
            {
            }

            override fun accept(inputText: String, type: Int)
            {
                finish()
            }
        }, "Exit", "Cancel").show()

    }

}
