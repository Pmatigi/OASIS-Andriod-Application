package com.oasis.ui.screens.ui.profile

import Constant.Companion.LOGOUT_MSG
import android.Manifest
import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.*
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.R
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screen_authentication.LoginScreen
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util
import com.ril.utils.ActionDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import org.json.JSONObject
import java.io.*
import java.lang.Exception
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.checkSelfPermission
import com.bumptech.glide.Glide
import com.mikhaellopez.circularimageview.CircularImageView
import com.oasis.ui.screens.ui.booking.MyBookingsActivity
import com.squareup.picasso.Picasso

class ProfileFragment : Fragment() {

    private val INITIAL_PERMS = arrayOf( Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    internal val CAMERA_CAPTURE = 5
    internal val PERMISSIONS_REQUEST_CAMERA = 3
    lateinit var mediaStorageDir:File
    lateinit var bitmap: Bitmap
    private lateinit var profileViewModel: ProfileViewModel
    lateinit var rel_lay_select_prof:RelativeLayout
    lateinit var lin_lay_select_prof:LinearLayout
    lateinit var tv_photo:TextView
    lateinit var tv_gallery:TextView
    lateinit var tv_cancel:TextView
    lateinit var tv_phone:TextView
    lateinit var tv_user_name:TextView
    lateinit var tv_email:TextView
    var userId=""
    var userDetails=ArrayList<String>()

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //Permission code
        private val PERMISSION_CODE_GALLERY = 1001;
    }


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        profileViewModel =
            ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)
        val rel_lay_my_booking: RelativeLayout = root.findViewById(R.id.rel_lay_my_booking)
        val rel_lay_signout: RelativeLayout = root.findViewById(R.id.rel_lay_signout)
        val rel_lay_call: RelativeLayout = root.findViewById(R.id.rel_lay_call)
        val rel_lay_setting: RelativeLayout = root.findViewById(R.id.rel_lay_setting)
        rel_lay_select_prof = root.findViewById(R.id.rel_lay_select_prof)
        lin_lay_select_prof = root.findViewById(R.id.lin_lay_select_prof)
        tv_photo = root.findViewById(R.id.tv_photo)
        tv_gallery = root.findViewById(R.id.tv_gallery)
        tv_cancel = root.findViewById(R.id.tv_cancel)
        tv_phone = root.findViewById(R.id.tv_phone)
        tv_user_name = root.findViewById(R.id.tv_user_name)
        tv_email = root.findViewById(R.id.tv_email)

        val tv_title: TextView =root.findViewById(R.id.tv_title)
        tv_title.text="My Profile"
        val iv_prof_img:CircularImageView = root.findViewById(R.id.iv_prof_img)

        if((activity as HomeActivity).profilePhoto!=null){
            iv_prof_img.setImageBitmap((activity as HomeActivity).profilePhoto)

        }

        tv_photo.setOnClickListener {
            hideProfDialog()
            imageViewClick()
        }

        tv_gallery.setOnClickListener {
            hideProfDialog()
            pickGallery()
        }

        tv_cancel.setOnClickListener {
            hideProfDialog()
        }

        iv_prof_img.setOnClickListener {

            showPictureDialog()
        }

        rel_lay_setting.setOnClickListener {
          //  changePassword()
        }

        rel_lay_my_booking.setOnClickListener {
            val intent = Intent(activity, MyBookingsActivity::class.java)
            startActivity(intent)
        }

        rel_lay_signout.setOnClickListener {
            showLogoutAlert()
        }

        rel_lay_call.setOnClickListener {
            openDialogContactUs()
        }

        val back = root.findViewById(R.id.lin_lay_back) as LinearLayout
        back.visibility=View.GONE

        val dataVault = DataVaultHelper()
         userDetails = dataVault.getVault((activity as HomeActivity), DataVaultHelper.APP_VAULTNAME)!!
        if (userDetails!!.isNotEmpty()) {
            Log.e("dkd","dkd --"+userDetails[0])
            if (userDetails[0] != null) {
                userId=userDetails[3]
            }
        }
        getProfile()

        return root
    }

    fun hideProfDialog(){
        val animate = TranslateAnimation(
            0f,
            0f,
            0f,
            rel_lay_select_prof.height.toFloat()
        )
        animate.duration = 500
        animate.fillAfter = true
        rel_lay_select_prof.startAnimation(animate)
        rel_lay_select_prof.setVisibility(View.GONE)
        lin_lay_select_prof.setVisibility(View.GONE)
    }


    @RequiresApi(Build.VERSION_CODES.M)
    private fun showPictureDialog() {
        rel_lay_select_prof.visibility=View.VISIBLE
        lin_lay_select_prof.visibility=View.VISIBLE

        val animate = TranslateAnimation(
            0f,
            0f,
            rel_lay_select_prof.getHeight().toFloat(),
            0f
        )
        animate.duration = 500
        animate.fillAfter = true
        rel_lay_select_prof.startAnimation(animate)
    }


    private fun showLogoutAlert()
    {
        ActionDialog(activity as HomeActivity, "Sign Out", LOGOUT_MSG, object : ActionDialog.OnDialogClick
        {
            override fun reject(inputText: String, type: Int)
            {
            }

            override fun accept(inputText: String, type: Int)
            {
                try {
                    val mDataVaultHelper = DataVaultHelper()
                    mDataVaultHelper.deleteVault(activity!!.applicationContext, DataVaultHelper.APP_VAULTNAME)
                    val intent = Intent(activity, LoginScreen::class.java)
                    startActivity(intent)
                    activity?.finish()
                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }, "Sign Out", "Cancel").show()

    }

    private var fileUri: Uri? = null
    @RequiresApi(api = Build.VERSION_CODES.M)
    fun imageViewClick() {

        if (checkSelfPermission(context!!,
                android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context!!,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            || checkSelfPermission(context!!,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(INITIAL_PERMS, PERMISSIONS_REQUEST_CAMERA)
        }else {
            Util.writeToFormFile("Start Trip FTS Camera Issue Camera App Opened")

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", android.hardware.Camera.CameraInfo.CAMERA_FACING_BACK);
                cameraIntent.putExtra("android.intent.extras.LENS_FACING_FRONT", 0);
                cameraIntent.putExtra("android.intent.extra.USE_FRONT_CAMERA", false);
            } else {
                cameraIntent.putExtra("android.intent.extras.CAMERA_FACING", 1);
            }

            fileUri = getOutputMediaFileUri(MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE)
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri)
            Log.e("dkd","dkd CAMERA_CAPTURE called")
            startActivityForResult(cameraIntent, CAMERA_CAPTURE)

        }
    }

    fun pickGallery(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkSelfPermission(context!!,Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_DENIED){
                //permission denied
                val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE);
                //show popup to request runtime permission
                requestPermissions(permissions, PERMISSION_CODE_GALLERY);
            } else{
                //permission already granted
                pickImageFromGallery();
            }
        }
        else{
            //system OS is < Marshmallow
            pickImageFromGallery();
        }
    }

    @Override
    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        Log.e("dkd", "perm Fragment onRequestPermissionsResult");

        when (requestCode) {
            PERMISSIONS_REQUEST_CAMERA ->

                run {

                    var i = 0
                    val len = permissions.size
                    while (i < len) {
                        Log.e("dkd","dkd allow grantResults[i]="+grantResults[i])

                        val permission = permissions[i]
                        if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                            Log.e("dkd","dkd allow permission")
                            if (!shouldShowRequestPermissionRationale(permissions[i])) {
                                Util.showErrorToast((activity as HomeActivity),"You can not continue without accepting camera and storage permission.Enable permissions from Settings to continue .")
                                break
                            }else{
                                //  (activity as ParentActivityK).showDialogErrorAlert("Alert","Please Allow the permission to continue")
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(INITIAL_PERMS, PERMISSIONS_REQUEST_CAMERA)
                                    break
                                }
                                break
                            }
                        }else{
                            i++
                        }
                    }
                    if((len >0) && (i==len)) {
                        imageViewClick()
                    }
                }

            PERMISSION_CODE_GALLERY -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    pickImageFromGallery()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(activity as HomeActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun pickImageFromGallery() {
        //Intent to pick image
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_PICK_CODE)
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("dkd","CAMERA_CAPTURE onActivityResult")

        when (requestCode) {
            CAMERA_CAPTURE -> if (resultCode == Activity.RESULT_OK) {

                compressImage(fileUri?.getPath())

            }else if (resultCode == Activity.RESULT_CANCELED) {
                Util.showErrorToast((activity as HomeActivity),"Cancelled")

            }else{
                Util.showErrorToast((activity as HomeActivity),"Cancelled")
            }

            IMAGE_PICK_CODE -> if (resultCode == Activity.RESULT_OK) {

               // iv_prof_img.setImageURI(data?.data)
                Log.e("dkd","dkd data?.data.toString="+data?.data.toString())
                compressImage(data?.data.toString())

            }else if (resultCode == Activity.RESULT_CANCELED) {
                Util.showErrorToast((activity as HomeActivity),"Cancelled")

            }else{
                Util.showErrorToast((activity as HomeActivity),"Cancelled")

            }

        }
    }

    private fun getProfile() {
        (activity as HomeActivity).showLoadingWithMessage( "Loading...")

        profileViewModel.getProfile(userId,userDetails[4]).observe(this, Observer {
            Util.showLog("getProfileReq Response", "" + it)
            (activity as HomeActivity). hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    var error=it.data?.error

                    if(!error!!){
                        if(it.data?.results?.phoneNo != null) {
                            tv_phone.text = (it.data?.results?.phoneNo).toString()
                        }else{
                            tv_phone.text =  "8928535097"
                        }

                        if(it.data?.results?.username != null) {
                            tv_user_name.text = (it.data?.results?.username).toString()
                        }else{
                            tv_phone.text =  "Welcome to Oasis Booking"
                        }

                        Log.e("dkd","prof -"+it.data?.results?.image)
                        if(it.data?.results?.image  != null) {
                            iv_prof_img.setImageResource(0)
                           // Glide.with(iv_prof_img).load(it.data?.results?.image).into(iv_prof_img)
                            Picasso.with(context).load(it.data?.results?.image).into(iv_prof_img)

                        }

                        if(it.data?.results?.email  != null) {
                            tv_email.text=it.data?.results?.email
                        }
                    }
                } else {
                    Util.showErrorToast((activity as HomeActivity), it.message)
                }
            } else {
                Util.showErrorToast((activity as HomeActivity), Constant.ERROR_TRY_AGAIN)
            }
        })
    }

    private fun uploadProfileImg() {
        (activity as HomeActivity).showLoadingWithMessage( "Loading...")

        profileViewModel.hitUploadProfileImg(userId,(activity as HomeActivity).profilePhoto!!, userDetails[4]).observe(this, Observer {
            Util.showLog("ChangePass Response", "" + it)
            (activity as HomeActivity). hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                       // var message="Profile Image Uploaded Successfully."

                        Util.showToast((activity as HomeActivity), "Error Occured")
                    }else{
                        var message=jsonObj.getString("messages")
                        Util.showToast((activity as HomeActivity), message)
                    }
                } else {
                    Util.showErrorToast((activity as HomeActivity), it.message)
                }
            } else
            {
                Util.showErrorToast((activity as HomeActivity), Constant.ERROR_TRY_AGAIN)
            }
        })
    }

    private fun submitChangePass() {
        (activity as HomeActivity).showLoadingWithMessage( "Loading...")

        profileViewModel.changePassword(userId,userDetails[1], "12345","12345",userDetails[4]).observe(this, Observer {
            Util.showLog("ChangePass Response", "" + it)
            (activity as HomeActivity). hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("errors")
                        val jsonObj1= JSONObject(messageArr)
                        var message=""

                        if(jsonObj1.has("user_id") && jsonObj1.getString("user_id")!=null) {
                            message = jsonObj1.getString("user_id")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else  if(jsonObj1.has("old_password") && jsonObj1.getString("old_password")!=null) {
                            message = jsonObj1.getString("old_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }
                        if(jsonObj1.has("new_password") && jsonObj1.getString("new_password")!=null) {
                            message = jsonObj1.getString("new_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else  if(jsonObj1.has("confirm_password") && jsonObj1.getString("confirm_password")!=null) {
                            message = jsonObj1.getString("confirm_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else {
                            Util.showErrorToast((activity as HomeActivity),messageArr.substring(2,message.length-3))
                        }
                    }else{
                        Util.showToast((activity as HomeActivity),"Password Changed successfully");
                    }


                } else
                {
                    Util.showErrorToast((activity as HomeActivity), it.message)
                }
            } else
            {
                Util.showErrorToast((activity as HomeActivity), Constant.ERROR_TRY_AGAIN)
            }
        })
    }

    private fun changePassword() {
        submitChangePass()
    }

    private fun updateProfile() {
        (activity as HomeActivity).showLoadingWithMessage( "Loading...")

        profileViewModel.updateProfile(userId,userDetails[1], "12345","12345",userDetails[4]).observe(this, Observer {
            Util.showLog("ChangePass Response", "" + it)
            (activity as HomeActivity). hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("errors")
                        val jsonObj1= JSONObject(messageArr)
                        var message=""

                        if(jsonObj1.has("user_id") && jsonObj1.getString("user_id")!=null) {
                            message = jsonObj1.getString("user_id")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else  if(jsonObj1.has("old_password") && jsonObj1.getString("old_password")!=null) {
                            message = jsonObj1.getString("old_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }
                        if(jsonObj1.has("new_password") && jsonObj1.getString("new_password")!=null) {
                            message = jsonObj1.getString("new_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else  if(jsonObj1.has("confirm_password") && jsonObj1.getString("confirm_password")!=null) {
                            message = jsonObj1.getString("confirm_password")
                            Util.showErrorToast((activity as HomeActivity),message.substring(2,message.length-3))
                        }else {
                            Util.showErrorToast((activity as HomeActivity),messageArr.substring(2,message.length-3))
                        }
                    }else{
                        Util.showToast((activity as HomeActivity),"Password Changed successfully");
                    }


                } else
                {
                    Util.showErrorToast((activity as HomeActivity), it.message)
                }
            } else
            {
                Util.showErrorToast((activity as HomeActivity), Constant.ERROR_TRY_AGAIN)
            }
        })
    }

    fun callPhone(phone:String){
        var intent =  Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(phone));
        startActivity(intent);
    }

    fun openDialogContactUs(){
//        val dialog = Dialog(activity!!)
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
//        dialog.setContentView(R.layout_header.call_popup_dialog)
//        val v = activity!!.window.decorView
//        v.setBackgroundResource(android.R.color.transparent)
//        dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
//
//
//        var tv_phone_no=dialog.findViewById(R.id.tv_phone) as TextView
//        var popup_no_button=dialog.findViewById(R.id.popup_no_button) as TextView
//        var popup_call_button=dialog.findViewById(R.id.popup_call_button) as TextView
//
//        popup_no_button.setOnClickListener {
//            dialog.dismiss()
//        }
//
//        popup_call_button.setOnClickListener {
//            dialog.dismiss()
//            var phoneNo="tel:"+tv_phone_no.text.toString().trim();
//            Log.e("dkd","dkd phoneNo="+phoneNo)
//           // callPhone(phoneNo);
//        }
//
//        dialog.show()

        ActionDialog(activity as HomeActivity, "Call Support", "9876543210", object : ActionDialog.OnDialogClick
        {
            override fun reject(inputText: String, type: Int)
            {
            }

            override fun accept(inputText: String, type: Int)
            {
                try {

                }catch (e:Exception){
                    e.printStackTrace()
                }

            }
        }, "Call", "Cancel").show()

    }

    fun getOutputMediaFileUri(type: Int): Uri {
        return Uri.fromFile(getOutputMediaFile(type))
    }

    private fun getOutputMediaFile(type: Int): File? {
        mediaStorageDir =  File( Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Oasis");

        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {

                return null
            }
        }

        val mediaFile: File
        if (type == MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE) {
            mediaFile =  File(mediaStorageDir,  System.currentTimeMillis().toString() + ".jpg");
        } else {
            return null
        }

        return mediaFile
    }

    fun compressImage(imageUri: String?) {

        val filePath = getRealPathFromURI(imageUri!!)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var bmp = BitmapFactory.decodeFile(filePath, options)
        var actualHeight = options.outHeight
        var actualWidth = options.outWidth
        val maxHeight = 1224.0f
        val maxWidth = 815.0f
        var imgRatio = (actualWidth / actualHeight).toFloat()
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
        options.inPurgeable = true
        options.inInputShareable = true
        options.inTempStorage = ByteArray(16 * 1024)
        try {
            bmp = BitmapFactory.decodeFile(filePath, options)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        try {
            bitmap = Bitmap.createBitmap(actualWidth, actualHeight, Bitmap.Config.ARGB_8888)
        } catch (exception: OutOfMemoryError) {
            exception.printStackTrace()
        }
        val ratioX = actualWidth / options.outWidth.toFloat()
        val ratioY = actualHeight / options.outHeight.toFloat()
        val middleX = actualWidth / 2.0f
        val middleY = actualHeight / 2.0f

        val scaleMatrix = Matrix()
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY)

        val canvas = Canvas(bitmap)
        canvas.setMatrix(scaleMatrix)
        canvas.drawBitmap(bmp, middleX - bmp.width / 2, middleY - bmp.height / 2, Paint(Paint.FILTER_BITMAP_FLAG))

        //      check the rotation of the image and display it properly
        val exif: ExifInterface
        try {
            exif = ExifInterface(filePath)

            val orientation = exif.getAttributeInt(
                ExifInterface.TAG_ORIENTATION, 0)
            Log.d("EXIF", "Exif: $orientation")
            val matrix = Matrix()
            if (orientation == 6) {
                matrix.postRotate(90f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 3) {
                matrix.postRotate(180f)
                Log.d("EXIF", "Exif: $orientation")
            } else if (orientation == 8) {
                matrix.postRotate(270f)
                Log.d("EXIF", "Exif: $orientation")
            }
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix,
                true)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val out: FileOutputStream? = null
        try {
            val cameraImages = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, cameraImages)
            (activity as HomeActivity).profilePhoto=bitmap
            iv_prof_img.setImageBitmap(bitmap)
            uploadProfileImg()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getRealPathFromURI(contentURI: String): String? {
        val contentUri = Uri.parse(contentURI)
        val cursor = activity?.getContentResolver()?.query(contentUri, null, null, null, null)
        if (cursor == null) {
            return contentUri.path
        } else {
            cursor!!.moveToFirst()
            val index = cursor!!.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            return cursor!!.getString(index)
        }
    }

    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
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