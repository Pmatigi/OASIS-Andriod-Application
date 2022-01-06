package com.oasis.ui.api.DataRepository

import Constant.Companion.ERROR_TRY_AGAIN
import Constant.Companion.NO_INTERNET
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.booking.*
import com.oasis.ui.models.home.*
import com.oasis.ui.models.login.ChangePasswordReq
import com.oasis.ui.models.login.ForgotPwdInfoRequest
import com.oasis.ui.models.login.LoginInfoRequest
import com.oasis.ui.models.login.SendOTPRequest
import com.oasis.ui.models.profile.*
import com.oasis.ui.models.register.RegisterInfoRequest
import com.oasis.ui.utils.Util.Util
import com.oasis.ui.utils.UtilJava
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*
import java.net.URLEncoder
import java.text.SimpleDateFormat
import java.util.*


class DataRepository(private val context: Context, private val apiInterface: ApiInterface)
{
    fun login(username: String, password: String): LiveData<ResDataWrapper<String>>
    {
        val loginInfoRequest=
            LoginInfoRequest(username, password)
        val loginRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            loginRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.loginUser("",loginInfoRequest).enqueue(object : Callback<String>
        {
            override fun onFailure(call: Call<String>, t: Throwable)
            {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                loginRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    //Util.writeToFormFile("LOGIN API SUCCESS "+response.body())

                    Log.e("dkd","dkd response="+response.body())
                    loginRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))

                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE--"+errorResponse)

                        if (!errorResponse.isNull("error")) {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE error--"+errorResponse.getJSONObject("error").get("message").toString())

                            loginRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE message--"+errorResponse.get("message").toString())

                            loginRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        loginRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.localizedMessage)
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.printStackTrace())
                    }
                }
            }
        })

        return loginRes
    }

    fun register(username: String , password: String, confirmPassword: String
    ,email:String, phone: String, cin: String): LiveData<ResDataWrapper<String>>
    {

        val registerInfoRequest=
            RegisterInfoRequest(username, password
                ,confirmPassword,email,phone,cin)

        val registerRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            registerRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.registerUser("",registerInfoRequest).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                //Util.writeToFormFile("API ERROR REGISTER API onFailure-"+t.localizedMessage)
                registerRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    //Util.writeToFormFile("REGISTER API SUCCESS "+response.body())

                    registerRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        //Util.writeToFormFile("API ERROR REGISTER API ERROR RESPONSE--"+errorResponse)

                        if (!errorResponse.isNull("error"))
                        {
                            //Util.writeToFormFile("API ERROR REGISTER API ERROR RESPONSE error--"+errorResponse.getJSONObject("error").get("message").toString())

                            registerRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            //Util.writeToFormFile("API ERROR REGISTER API ERROR RESPONSE message--"+errorResponse.get("message").toString())
                            registerRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        //Util.writeToFormFile("API ERROR REGISTER EXCEPTION-"+e.localizedMessage)
                        //Util.writeToFormFile("API ERROR REGISTER EXCEPTION-"+e.printStackTrace())
                        registerRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd registerRes-"+registerRes)

        return registerRes
    }

    fun searchBus(sourceId: String,destinationId: String ,selectedDateStr: String,selectedTimeStr: String ): LiveData<ResDataWrapper<SearchBusRes>>
    {
        var calendar: Calendar = Calendar.getInstance();

        var mdformatTime: SimpleDateFormat = SimpleDateFormat("HH:mm");
        val selectedTimeStr1= mdformatTime.format(calendar.time)
        val searchBusInfoRequest=
            SearchBusInfoRequest(sourceId, destinationId, selectedDateStr,selectedTimeStr1)

        val searchBusRes: MutableLiveData<ResDataWrapper<SearchBusRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            searchBusRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.searchBus(searchBusInfoRequest ).enqueue(object : Callback<SearchBusRes> {
            override fun onFailure(call: Call<SearchBusRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                searchBusRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<SearchBusRes>, response: Response<SearchBusRes>) {
                if (response.isSuccessful)
                {

                    Log.e("dkd","dkd response="+response.body())
                    searchBusRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            searchBusRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            searchBusRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        searchBusRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd String-"+String)

        return searchBusRes
    }


    fun getCity(): LiveData<ResDataWrapper<CityResponse>>
    {

        val getCityRes: MutableLiveData<ResDataWrapper<CityResponse>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            getCityRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.getCity().enqueue(object : Callback<CityResponse> {
            override fun onFailure(call: Call<CityResponse>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                getCityRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<CityResponse>, response: Response<CityResponse>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    getCityRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            getCityRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            getCityRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        getCityRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getCityRes-"+getCityRes)

        return getCityRes
    }

    fun getBusDetail(busDetailReq: BusDetailReq): LiveData<ResDataWrapper<BusDetailRes>>
    {

        val getBusDetail: MutableLiveData<ResDataWrapper<BusDetailRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            getBusDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.getBusDetail(busDetailReq).enqueue(object : Callback<BusDetailRes> {
            override fun onFailure(call: Call<BusDetailRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                getBusDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<BusDetailRes>, response: Response<BusDetailRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    getBusDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            getBusDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            getBusDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        getBusDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+getBusDetail)

        return getBusDetail
    }

    fun getBookings(myBookingInfoRequest: MyBookingInfoRequest): LiveData<ResDataWrapper<MyBookingInfoRes>>
    {

        val getBookingRes: MutableLiveData<ResDataWrapper<MyBookingInfoRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            getBookingRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.getBookings(myBookingInfoRequest).enqueue(object : Callback<MyBookingInfoRes> {
            override fun onFailure(call: Call<MyBookingInfoRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                getBookingRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<MyBookingInfoRes>, response: Response<MyBookingInfoRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    getBookingRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            getBookingRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            getBookingRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        getBookingRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBookingRes-"+getBookingRes)

        return getBookingRes
    }

    fun cancelBooking(cancelBookingReq: CancelBookingReq): LiveData<ResDataWrapper<String>>
    {
        val cancelBookingRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            cancelBookingRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.cancelBooking(cancelBookingReq).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                cancelBookingRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    cancelBookingRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            cancelBookingRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            cancelBookingRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        cancelBookingRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd cancelBookingRes-"+cancelBookingRes)

        return cancelBookingRes
    }

    fun getBookingStatus(bookingStatusReq: BookingStatusReq): LiveData<ResDataWrapper<String>>
    {
        val bookingStatusRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            bookingStatusRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.getBookingStatus(bookingStatusReq).enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                bookingStatusRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    bookingStatusRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            bookingStatusRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            bookingStatusRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        bookingStatusRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd bookingStatusRes-"+bookingStatusRes)

        return bookingStatusRes
    }



    fun uploadProfileImg(userId: String, bitmap: Bitmap, jwt_auth_token: String): LiveData<ResDataWrapper<String>>
    {
        val profileImgRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            profileImgRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else {
            try {
                val file = File(context.cacheDir, "profile_picture")
                val os = BufferedOutputStream(FileOutputStream(file))
                bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os)
                try {
                    os.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                Log.e("dkd", "file=" + file.absolutePath)
                var bitmap1 = BitmapFactory.decodeFile(file.absolutePath)

                bitmap1 = Util.getCompressedBitmap(file.absolutePath)

                val fileReqBody = RequestBody.create(MediaType.parse("image/*"), file)

                val requestImage = MultipartBody.Part.createFormData("profile_picture", file.name+".png", fileReqBody)

               // val userReqBody = RequestBody.create(MediaType.parse("multipart/form-data"), userId)

                val cType="application/x-www-form-urlencoded"

                apiInterface.profileImg(userId,userId,requestImage,jwt_auth_token/*,cType*/)
                    .enqueue(object : Callback<String> {
                        override fun onFailure(call: Call<String>, t: Throwable) {
                            Log.e("dkd", "dkd server error-" + t.localizedMessage)
                            profileImgRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
                        }

                        override fun onResponse(call: Call<String>, response: Response<String>) {
                            if (response.isSuccessful) {
                                try {
                                    Log.e("dkd", "dkd response=" + response.body())
                                    profileImgRes.postValue(
                                        ResDataWrapper.success(
                                            response.code(),
                                            "Success",
                                            response.body()
                                        )
                                    )
                                }catch (e:Exception){e.printStackTrace()}

                            } else {
                                try {
                                    val errorResponse = JSONObject(response.errorBody()!!.string())
                                    if (!errorResponse.isNull("error")) {
                                        profileImgRes.postValue(
                                            ResDataWrapper.error(
                                                response.code(),
                                                errorResponse.getJSONObject("error").get("message").toString()
                                            )
                                        )
                                    } else {
                                        profileImgRes.postValue(
                                            ResDataWrapper.error(
                                                response.code(),
                                                errorResponse.get("message").toString()
                                            )
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    profileImgRes.postValue(
                                        ResDataWrapper.error(
                                            response.code(),
                                            ERROR_TRY_AGAIN
                                        )
                                    )
                                }
                            }
                        }
                    })
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
        return profileImgRes
    }


    fun getProfile(userId: String,token: String): LiveData<ResDataWrapper<GetProfileRes>>
    {
        val getProfRes: MutableLiveData<ResDataWrapper<GetProfileRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            getProfRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else {
            try {
                var getProfileReq= GetProfileReq(userId)
            Log.e("dkd","dkd getProfileReq-"+getProfileReq);
                apiInterface.getProfile(token,getProfileReq)
                    .enqueue(object : Callback<GetProfileRes> {

                        override fun onFailure(call: Call<GetProfileRes>, t: Throwable) {
                            Log.e("dkd", "dkd getProfile server error-" + t.localizedMessage)
                            getProfRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
                        }

                        override fun onResponse(call: Call<GetProfileRes>, response: Response<GetProfileRes>) {
                            if (response.isSuccessful) {

                                Log.e("dkd", "dkd getProfile response=" + response.body())
                                getProfRes.postValue(
                                    ResDataWrapper.success(
                                        response.code(),
                                        "Success",
                                        response.body()
                                    )
                                )

                            } else {
                                try {
                                    val errorResponse = JSONObject(response.errorBody()!!.string())
                                    if (!errorResponse.isNull("error")) {
                                        getProfRes.postValue(
                                            ResDataWrapper.error(
                                                response.code(),
                                                errorResponse.getJSONObject("error").get("message").toString()
                                            )
                                        )
                                    } else {
                                        getProfRes.postValue(
                                            ResDataWrapper.error(
                                                response.code(),
                                                errorResponse.get("message").toString()
                                            )
                                        )
                                    }
                                } catch (e: Exception) {
                                    e.printStackTrace()
                                    getProfRes.postValue(
                                        ResDataWrapper.error(
                                            response.code(),
                                            ERROR_TRY_AGAIN
                                        )
                                    )
                                }
                            }
                        }
                    })
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }

        }
        return getProfRes
    }


    fun doBooking(bookingReq: BookingReq): LiveData<ResDataWrapper<BookingRes>>
    {

        val doBookingDetail: MutableLiveData<ResDataWrapper<BookingRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            doBookingDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.doBooking(bookingReq).enqueue(object : Callback<BookingRes> {
            override fun onFailure(call: Call<BookingRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                doBookingDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<BookingRes>, response: Response<BookingRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    doBookingDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        doBookingDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+doBookingDetail)

        return doBookingDetail
    }


    fun getAboutUs(aboutUsReq: AboutUsReq): LiveData<ResDataWrapper<AboutUsRes>>
    {

        val aboutUs: MutableLiveData<ResDataWrapper<AboutUsRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            aboutUs.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.getAboutUs(aboutUsReq).enqueue(object : Callback<AboutUsRes> {


            override fun onFailure(call: Call<AboutUsRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                aboutUs.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<AboutUsRes>, response: Response<AboutUsRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    aboutUs.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            aboutUs.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            aboutUs.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        aboutUs.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd aboutUs-"+aboutUs)

        return aboutUs
    }


    fun doPaymentOrangeMoney(orangeMoneyPaymentReq: OrangeMoneyPaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {

        val doBookingDetail: MutableLiveData<ResDataWrapper<PaymentRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            doBookingDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.doPaymentOrangeMoney(orangeMoneyPaymentReq).enqueue(object : Callback<PaymentRes> {
            override fun onFailure(call: Call<PaymentRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                doBookingDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<PaymentRes>, response: Response<PaymentRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    doBookingDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        doBookingDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+doBookingDetail)

        return doBookingDetail
    }

    fun doPaymentConfirm(paymentConfirmReq: PaymentConfirmReq): LiveData<ResDataWrapper<PaymentRes>>
    {

        val doBookingDetail: MutableLiveData<ResDataWrapper<PaymentRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            doBookingDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.doPaymentConfirm(paymentConfirmReq).enqueue(object : Callback<PaymentRes> {
            override fun onFailure(call: Call<PaymentRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                doBookingDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<PaymentRes>, response: Response<PaymentRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    doBookingDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        doBookingDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+doBookingDetail)

        return doBookingDetail
    }

    fun doPayment(bookingReq: MakePaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {

        val doBookingDetail: MutableLiveData<ResDataWrapper<PaymentRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            doBookingDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.doPayment(bookingReq).enqueue(object : Callback<PaymentRes> {
            override fun onFailure(call: Call<PaymentRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                doBookingDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<PaymentRes>, response: Response<PaymentRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    doBookingDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        doBookingDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+doBookingDetail)

        return doBookingDetail
    }


    fun doBraintreePayment(bookingReq: MakeBraintreePaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {

        val doBookingDetail: MutableLiveData<ResDataWrapper<PaymentRes>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context)) {
            doBookingDetail.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.doBraintreePayment(bookingReq).enqueue(object : Callback<PaymentRes> {
            override fun onFailure(call: Call<PaymentRes>, t: Throwable) {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                doBookingDetail.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }
            override fun onResponse(call: Call<PaymentRes>, response: Response<PaymentRes>) {
                if (response.isSuccessful)
                {
                    Log.e("dkd","dkd response="+response.body())
                    doBookingDetail.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))
                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error"))
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            doBookingDetail.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        doBookingDetail.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }

        })
        Log.e("dkd","dkd getBusDetail-"+doBookingDetail)

        return doBookingDetail
    }

    fun changePassword(user_id: String, old_password: String,
                       new_password: String,
                       confirm_password: String,
                       token: String): LiveData<ResDataWrapper<String>>
    {
        val changePasswordReq=
            ChangePasswordReq(user_id, old_password,new_password,confirm_password)
        val changePassRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            changePassRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.changePass(token,changePasswordReq).enqueue(object : Callback<String>
        {
            override fun onFailure(call: Call<String>, t: Throwable)
            {
                //   //Util.writeToFormFile("API ERROR LOGIN API onFailure--"+t.localizedMessage)
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                changePassRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    //Util.writeToFormFile("LOGIN API SUCCESS "+response.body())

                    Log.e("dkd","dkd response="+response.body())
                    changePassRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))

                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error")) {
                            changePassRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            changePassRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        changePassRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }
        })

        return changePassRes
    }

    fun updateProfile(user_id: String, old_password: String,
                      new_password: String,
                      confirm_password: String,
                      token: String): LiveData<ResDataWrapper<String>>
    {
        val changePasswordReq=
            ChangePasswordReq(user_id, old_password,new_password,confirm_password)
        val profileImgRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            profileImgRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.updateProfile(token,changePasswordReq).enqueue(object : Callback<String>
        {
            override fun onFailure(call: Call<String>, t: Throwable)
            {
                //   //Util.writeToFormFile("API ERROR LOGIN API onFailure--"+t.localizedMessage)
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                profileImgRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    //Util.writeToFormFile("LOGIN API SUCCESS "+response.body())

                    Log.e("dkd","dkd response="+response.body())
                    profileImgRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))

                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        if (!errorResponse.isNull("error")) {
                            profileImgRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            profileImgRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        profileImgRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                    }
                }
            }
        })

        return profileImgRes
    }

    fun sendOTP(email: String): LiveData<ResDataWrapper<String>>
    {
        val sendOTPRequest=
            SendOTPRequest(email)
        val sendOTPRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            sendOTPRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.sendOTP("",sendOTPRequest).enqueue(object : Callback<String>
        {
            override fun onFailure(call: Call<String>, t: Throwable)
            {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                sendOTPRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    //Util.writeToFormFile("LOGIN API SUCCESS "+response.body())

                    Log.e("dkd","dkd response="+response.body())
                    sendOTPRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))

                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE--"+errorResponse)

                        if (!errorResponse.isNull("error")) {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE error--"+errorResponse.getJSONObject("error").get("message").toString())

                            sendOTPRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE message--"+errorResponse.get("message").toString())

                            sendOTPRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        sendOTPRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.localizedMessage)
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.printStackTrace())
                    }
                }
            }
        })

        return sendOTPRes
    }

    fun forgotPwd(email: String,otp: String,password: String,newPassword: String): LiveData<ResDataWrapper<String>>
    {
        val forgotPwdInfoRequest=
            ForgotPwdInfoRequest(email,otp,password,newPassword)
        val forgotPwdRes: MutableLiveData<ResDataWrapper<String>> = MutableLiveData()

        if (!Util.checkNetworkStatus(context))
        {
            forgotPwdRes.postValue(ResDataWrapper.error(500, NO_INTERNET))

        } else apiInterface.forgotPwd("",forgotPwdInfoRequest).enqueue(object : Callback<String>
        {
            override fun onFailure(call: Call<String>, t: Throwable)
            {
                Log.e("dkd","dkd server error-"+t.localizedMessage)
                forgotPwdRes.postValue(ResDataWrapper.error(500, ERROR_TRY_AGAIN))
            }

            override fun onResponse(call: Call<String>, response: Response<String>)
            {
                if (response.isSuccessful)
                {
                    //Util.writeToFormFile("LOGIN API SUCCESS "+response.body())

                    Log.e("dkd","dkd response="+response.body())
                    forgotPwdRes.postValue(ResDataWrapper.success(response.code(), "Success", response.body()))

                } else
                {
                    try
                    {
                        val errorResponse = JSONObject(response.errorBody()!!.string())
                        //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE--"+errorResponse)

                        if (!errorResponse.isNull("error")) {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE error--"+errorResponse.getJSONObject("error").get("message").toString())

                            forgotPwdRes.postValue(ResDataWrapper.error(response.code(), errorResponse.getJSONObject("error").get("message").toString()))
                        } else
                        {
                            //Util.writeToFormFile("API ERROR LOGIN API ERROR RESPONSE message--"+errorResponse.get("message").toString())

                            forgotPwdRes.postValue(ResDataWrapper.error(response.code(), errorResponse.get("message").toString()))
                        }
                    } catch (e: Exception)
                    {
                        e.printStackTrace()
                        forgotPwdRes.postValue(ResDataWrapper.error(response.code(), ERROR_TRY_AGAIN))
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.localizedMessage)
                        //Util.writeToFormFile("API ERROR LOGIN EXCEPTION-"+e.printStackTrace())
                    }
                }
            }
        })

        return forgotPwdRes
    }

}