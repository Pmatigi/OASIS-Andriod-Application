package com.oasis.ui.utils.Util
import Constant
import android.Manifest
import android.annotation.TargetApi
import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.os.Environment
import android.util.Log
import android.view.View
import android.view.Window
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.oasis.BuildConfig
import com.oasis.R
import com.oasis.ui.SplashScreen
import com.oasis.ui.models.booking.CurrentBooking
import com.ril.utils.CustomDialog
import java.io.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by dkd on 26-05-2017.
 */

object Util
{

    private var dialog: CustomDialog? = null

    // for checking the wifi connection ..............
    fun checkWIFI(activity: Context): Boolean
    {
        val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return if (netInfo != null && netInfo.isConnected)
        {
            true

        } else if (netInfo != null && (netInfo.state == NetworkInfo.State.DISCONNECTED || netInfo.state == NetworkInfo.State.DISCONNECTING || netInfo.state == NetworkInfo.State.SUSPENDED || netInfo.state == NetworkInfo.State.UNKNOWN))
        {
            false
        } else
        {
            false
        }
    }

    fun checkNetworkStatus(activity: Context): Boolean {
        try {
            Constant.isNetworkOnline = false

            val cm = activity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            var netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
            if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED) {
                Constant.isNetworkOnline = true
            } else {
                netInfo = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                if (netInfo != null && netInfo.state == NetworkInfo.State.CONNECTED)
                    Constant.isNetworkOnline = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return Constant.isNetworkOnline
    }


    fun showToast(context: Context, msg: String)
    {
        (context as Activity).runOnUiThread {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show()
        }
    }

    fun showLog(logTag: String, msg: String)
    {
        if (BuildConfig.DEBUG)
        Log.e(logTag, msg)
    }


    fun hideKeyboard(activity: Context)
    {
        val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view = (activity as Activity).currentFocus
        if (view == null)
        {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun getVersionCode(mContext: Context): String?
    {
        var version: String? = null
        try
        {
            val pInfo = mContext.packageManager.getPackageInfo(mContext.packageName, 0)
            version = pInfo.versionName
        } catch (e: PackageManager.NameNotFoundException)
        {
            e.printStackTrace()
        }

        return version
    }


    var MY_PERMISSIONS_REQUEST_CAMERA = 124
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    fun checkCameraPermission(context: Context): Boolean
    {
        val currentAPIVersion = Build.VERSION.SDK_INT
        if (currentAPIVersion >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.CAMERA))
                {
                    val alertBuilder = AlertDialog.Builder(context)
                    alertBuilder.setCancelable(true)
                    alertBuilder.setTitle("Permission necessary")
                    alertBuilder.setMessage("Camera permission is necessary")
                    alertBuilder.setPositiveButton(android.R.string.yes, object : DialogInterface.OnClickListener
                    {
                        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
                        override fun onClick(dialog: DialogInterface, which: Int)
                        {
                            ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA), MY_PERMISSIONS_REQUEST_CAMERA)
                        }
                    })
                    val alert = alertBuilder.create()
                    alert.show()
                } else
                {
                    ActivityCompat.requestPermissions(context, arrayOf(Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO), MY_PERMISSIONS_REQUEST_CAMERA)
                }
                return false
            } else
            {
                return true
            }
        } else
        {
            return true
        }
    }


    fun setmessage(msg: String)
    {
        if (dialog!!.isShowing()) dialog!!.setTitle(msg)
    }

    // dialog while fetching data
    fun showDialog(mActivity: Context, message: String)
    {

        (mActivity as Activity).runOnUiThread {
            dialog = CustomDialog(mActivity)
            dialog!!.setTitle(message)
            dialog!!.show()
        }

    }

    fun dismissDialog()
    {
        if (dialog != null) if (dialog!!.isShowing)
        {
            dialog!!.quitDialog()
        }
    }


    fun openTimeDialog(context: Context, tv: EditText)
    {
        val mTimePicker: TimePickerDialog
        val c = Calendar.getInstance()
        val hour = c.get(Calendar.HOUR_OF_DAY)
        val minute = c.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(context, TimePickerDialog.OnTimeSetListener { timePicker, selectedHour, selectedMinute ->
            val time = "$selectedHour:$selectedMinute"

            val fmt = SimpleDateFormat("HH:mm")
            var date: Date? = null
            try
            {
                date = fmt.parse(time)
            } catch (e: ParseException)
            {

                e.printStackTrace()
            }

            val fmtOut = SimpleDateFormat("hh:mm aa")

            val formattedTime = fmtOut.format(date)

            tv.setText(formattedTime)
        }, hour, minute, false) //No 24 hour time
        mTimePicker.setTitle("Select Time")
        mTimePicker.show()
    }

    fun errorSnacBar(context: Context, msg: String)
    {
        Snackbar.make((context as AppCompatActivity).findViewById(android.R.id.content), msg, Snackbar.LENGTH_LONG).setAction("ok") { }.setActionTextColor(Color.RED).show()
    }

    fun showErrorToast(context: Context, msg: String)
    {
      //  Toasty.error(context, msg, Toast.LENGTH_SHORT, true).show()
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    fun showSuccessToast(context: Context, msg: String)
    {
      //  Toasty.success(context, msg, Toast.LENGTH_SHORT, true).show()
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    fun showInfoToast(context: Context, msg: String)
    {
        //Toasty.warning(context, msg, Toast.LENGTH_SHORT, true).show()
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()

    }

    fun capitallizeEachWord(userName: String): String
    {
        val result = StringBuilder(userName.length)
        val words = userName.split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (i in words.indices)
        {
            result.append(Character.toUpperCase(words[i][0])).append(words[i].substring(1)).append(" ")
        }

        return result.toString()
    }

    //    fun convertToPx(dp: Int): Int {
    //        // Get the screen's density scale
    //        val scale = resources.displayMetrics.density
    //        // Convert the dps to pixels, based on density scale
    //        return (dp * scale + 0.5f).toInt()
    //    }


    fun sessionDialog(context: Context)
    {
        val mDialog = Dialog(context)
        mDialog.window!!.setBackgroundDrawable(ColorDrawable(0))
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        mDialog.setContentView(R.layout.action_dialog_view)
        mDialog.setCancelable(false)

        val tvHeaderTxt = mDialog.findViewById(R.id.tvHeaderTxt) as TextView
        val tvmsg = mDialog.findViewById(R.id.tvmsg) as TextView
        tvHeaderTxt.text = "Alert"
        tvmsg.text = Constant.SESSION_EXPIRE
        val btnReject = mDialog.findViewById(R.id.btnReject) as TextView
        val btnAccept = mDialog.findViewById(R.id.btnAccept) as TextView
        btnReject.visibility = View.GONE
        btnReject.setOnClickListener { mDialog.dismiss() }
        btnAccept.setOnClickListener {
            mDialog.dismiss()
            val intentObj = Intent(context, SplashScreen::class.java)
            intentObj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            intentObj.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            intentObj.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intentObj)
            (context as AppCompatActivity).finish()

        }

        mDialog.show()
    }

    @Synchronized
    fun writeToFormFile(msg: String) {
        var writer: PrintWriter? = null
        try {
            val path = Environment.getExternalStorageDirectory().absolutePath
            //            String path = "/sdcard/download/media";
            val fout = File("$path/OASIS_BUG.txt")
            if (!fout.exists())
                fout.createNewFile()

            writer = PrintWriter(BufferedWriter(FileWriter(fout, true)))
            writer.println("\n")
            writer.println(msg)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            writer!!.close()
        }
    }

    fun rotateImage(view: ImageView){
        var anim =  RotateAnimation(0.0f, 180.0f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

//Setup anim with desired properties
        anim.setInterpolator( LinearInterpolator());
        //anim.setRepeatCount(Animation.INFINITE); //Repeat animation indefinitely
        anim.setDuration(100); //Put desired duration per anim cycle here, in milliseconds

//Start animation
        view.startAnimation(anim);
    }

    fun getDayName(dateStr:String):String{
            val format1= SimpleDateFormat("dd/MM/yyyy");
            val dt1=format1.parse(dateStr);
            val format2= SimpleDateFormat("EEEE");
            val finalDay=format2.format(dt1);
            return finalDay
    }

    fun getDayNumber(dateStr:String):String{
        val format1= SimpleDateFormat("dd/MM/yyyy");
        val dt1=format1.parse(dateStr);
        val format2= SimpleDateFormat("dd");
        val finalDay=format2.format(dt1);
        return finalDay
    }

    fun getMonthNameYear(dateStr:String):String{
        val format1= SimpleDateFormat("dd/MM/yyyy");
        val dt1=format1.parse(dateStr);
        val format2= SimpleDateFormat("MMM");
        val finalDay=format2.format(dt1);
        val format3= SimpleDateFormat("yyyy");
        val finalDay1=format3.format(dt1);
        val day=finalDay+" "+finalDay1
        return day
    }

    fun getDateFromDateTime(dateSelected: String?):String {
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val date = dateFormat.parse(dateSelected.toString());//You will get date object relative to server/client timezone wherever it is parsed
        val formatter2 =  SimpleDateFormat("dd"); //If you need time just put specific format for time like 'HH:mm:ss'
        val selectedDateStr1 = formatter2.format(date);
        return selectedDateStr1
    }

    fun getFullDateFromDateTime(dateSelected: String?):String {
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val date = dateFormat.parse(dateSelected.toString());//You will get date object relative to server/client timezone wherever it is parsed
        val formatter2 =  SimpleDateFormat("dd-MM-yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
        val selectedDateStr1 = formatter2.format(date);
        return selectedDateStr1
    }

    fun getFullDateFromDateTimeInMonthName(dateSelected: String?):String {
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val date = dateFormat.parse(dateSelected.toString());//You will get date object relative to server/client timezone wherever it is parsed
        val formatter2 =  SimpleDateFormat("MMM dd yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
        val selectedDateStr1 = formatter2.format(date);
        return selectedDateStr1
    }

    fun getDayNameFromDateTime(dateStr:String):String{
        val format1= SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val dt1=format1.parse(dateStr);
        val format2= SimpleDateFormat("EEEE");
        val finalDay=format2.format(dt1);
        return finalDay
    }

    fun getMonthNameYearFromDateTime(dateStr:String):String{
        val format1= SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val dt1=format1.parse(dateStr);
        val format2= SimpleDateFormat("MMM");
        val finalDay=format2.format(dt1);
        val format3= SimpleDateFormat("yyyy");
        val finalDay1=format3.format(dt1);
        val day=finalDay+" "+finalDay1
        return day
    }


    fun getTimeFromDateTime(dateSelected: String?):String {
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        val date = dateFormat.parse(dateSelected.toString());//You will get date object relative to server/client timezone wherever it is parsed
        val formatter2 =  SimpleDateFormat("HH:mm a"); //If you need time just put specific format for time like 'HH:mm:ss'
        val selectedDateStr1 = formatter2.format(date);
        return selectedDateStr1
    }

    fun reverseArrayList(alist: ArrayList<CurrentBooking?>): ArrayList<CurrentBooking?>? {
        // Arraylist for storing reversed elements
        // this.revArrayList = alist;
        for (i in 0 until alist.size / 2) {
            val temp: CurrentBooking? = alist[i]
            alist[i] = alist[alist.size - i - 1]
            alist[alist.size - i - 1] = temp
        }
        // Return the reversed arraylist
        return alist
    }

    fun getCompressedBitmap(imagePath: String): Bitmap {
        val maxHeight = 1500.0f
        val maxWidth = 700.0f
        var scaledBitmap: Bitmap? = null
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(imagePath, options)

        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        var imgRatio = actualWidth.toFloat() / actualHeight.toFloat()
        val maxRatio = maxWidth / maxHeight

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight
                actualWidth = (imgRatio * actualWidth).toInt()
                actualHeight = maxHeight.toInt()
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth
                actualHeight = (imgRatio * actualHeight).toInt()
                actualWidth = maxWidth.toInt()
            } else {
                actualHeight = maxHeight.toInt()
                actualWidth = maxWidth.toInt()

            }
        }

        options.inSampleSize = calculateInSampleSize(options, actualWidth, actualHeight)
        options.inJustDecodeBounds = false
        options.inDither = false
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)

        try {
            bmp = BitmapFactory.decodeFile(imagePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()

        }

        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }

        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()


        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(scaledBitmap!!)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(
            bmp,
            middleX - bmp.width / 2,
            middleY - bmp.height / 2,
            Paint(Paint.FILTER_BITMAP_FLAG)
        )

        var exif: ExifInterface? = null
        try {
            exif = ExifInterface(imagePath)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 0)
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
            } else if (orientation == 3) {
                matrix.postRotate(180f)
            } else if (orientation == 8) {
                matrix.postRotate(270f)
            }
            scaledBitmap = Bitmap.createBitmap(
                scaledBitmap,
                0,
                0,
                scaledBitmap.width,
                scaledBitmap.height,
                matrix,
                true
            )
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val out = ByteArrayOutputStream()
        scaledBitmap!!.compress(Bitmap.CompressFormat.JPEG, 85, out)

        val byteArray = out.toByteArray()

        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        val totalPixels = (width * height).toFloat()
        val totalReqPixelsCap = (reqWidth * reqHeight * 2).toFloat()

        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++
        }
        return inSampleSize
    }

}
