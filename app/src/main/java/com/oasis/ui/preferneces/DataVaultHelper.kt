package com.oasis.ui.preferneces.DataVaultHelper

import android.content.Context
import com.oasis.ui.utils.Util.Util
import com.sybase.persistence.DataVaultException
import com.sybase.persistence.PrivateDataVault
import java.util.ArrayList

/**
 * @author A1
 */

class DataVaultHelper
{

    /**
     * @param context
     * @param username
     * @param password
     */
    fun saveDataValue(context: Context, vaultName: String, username: String, password: String, pin: String,
                      userId: String,token: String)
    {
        try
        {
            var vault: PrivateDataVault? = null

            PrivateDataVault.init(context)
            if (!PrivateDataVault.vaultExists(vaultName))
            {
                vault = PrivateDataVault.createVault(vaultName, DVPassWord, "salt")
            } else
            {
                vault = PrivateDataVault.getVault(vaultName)
            }

            if (vault!!.isLocked)
            {
                vault.unlock(DVPassWord, "salt")
                vault.setString("userName", username)
                vault.setString("userPassword", password)
                vault.setString("pin", pin)
                vault.setString("userId", userId)
                vault.setString("token", token)


            } else
            {
                vault.setString("userName", username)
                vault.setString("userPassword", password)
                vault.setString("pin", pin)
                vault.setString("userId", userId)
                vault.setString("token", token)

            }
            vault.lock()
            // Log.v("App Data Vault Save", "Saved");

        } catch (e1: DataVaultException)
        {
            e1.printStackTrace()
            // Log.v("App Data Vault Save", "Not Saved");

        }

    }

    /**
     * @param context
     * @param vaultName
     * @return
     */
    fun getVault(context: Context, vaultName: String): ArrayList<String>?
    {
       // Util.showToast(context,"datavault-"+vaultName)
        var mVault: PrivateDataVault? = null
        PrivateDataVault.init(context)
        val crenditials = ArrayList<String>()
        try
        {
            if (PrivateDataVault.vaultExists(vaultName))
            {
                mVault = PrivateDataVault.getVault(vaultName)
                if (mVault!!.isLocked)
                {
                    mVault.unlock(DataVaultHelper.DVPassWord, "salt")

                    val existingUName = mVault.getString("userName")
                   // Util.showToast(context,"datavault existingUName-"+existingUName)

                    val existingPwd = mVault.getString("userPassword")
                    val existingUserId = mVault.getString("userId")
                    val existingToken = mVault.getString("token")
                    var pin = ""
                    try
                    {
                        pin = mVault.getString("pin")
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        pin = ""
                    }

                    crenditials.add(existingUName)
                    crenditials.add(existingPwd)
                    crenditials.add(pin)
                    crenditials.add(existingUserId)
                    crenditials.add(existingToken)

                } else
                {
                    val existingUName = mVault.getString("userName")
                  //  Util.showToast(context,"datavault existingUName-"+existingUName)

                    val existingPwd = mVault.getString("userPassword")
                    val existingUserId = mVault.getString("userId")
                    val existingToken = mVault.getString("token")
                    var pin = ""
                    try
                    {
                        pin = mVault.getString("pin")
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        pin = ""
                    }

                    crenditials.add(existingUName)
                    crenditials.add(existingPwd)
                    crenditials.add(pin)
                    crenditials.add(existingUserId)
                    crenditials.add(existingToken)
                }
                mVault.lock()
            }
        } catch (e: DataVaultException)
        {
           // Util.showErrorToast(context,"datavault exception-"+e.toString())

            e.printStackTrace()
            return null
        }

        return crenditials
    }

    fun deleteVault(context: Context, vaultName: String): Boolean
    {
        try
        {
            PrivateDataVault.init(context)
            if (PrivateDataVault.vaultExists(vaultName))
            {
                PrivateDataVault.deleteVault(vaultName)
            }
            // Log.v("Logout...C","Logout");
        } catch (e1: DataVaultException)
        {
            e1.printStackTrace()
            return false
        }

        return true

    }

    companion object
    {
        var DVPassWord = "OASIS" // password for the Data
        var APP_VAULTNAME = "com.oasis"
    }

}