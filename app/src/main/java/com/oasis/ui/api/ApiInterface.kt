package com.oasis.ui.api

import com.google.gson.GsonBuilder
import com.oasis.ui.models.booking.*
import com.oasis.ui.models.home.*
import com.oasis.ui.models.login.ChangePasswordReq
import com.oasis.ui.models.login.ForgotPwdInfoRequest
import com.oasis.ui.models.login.LoginInfoRequest
import com.oasis.ui.models.login.SendOTPRequest
import com.oasis.ui.models.profile.*
import com.oasis.ui.models.register.RegisterInfoRequest
import com.ril.utils.ToStringConverterFactory
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import javax.security.cert.CertificateException

interface ApiInterface
{
    companion object
    {

        fun getApiService(): ApiInterface
        {
            var gson =  GsonBuilder().setLenient().create()
            return Retrofit.Builder().baseUrl(Constant.HOST).addConverterFactory(
                ToStringConverterFactory()
            ).addConverterFactory(GsonConverterFactory.create(gson)).client(unsafeOkHttpClient).build().create(ApiInterface::class.java)
        }

        private val unsafeOkHttpClient: OkHttpClient
            get()
            {
                try
                {
                    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager
                    {
                        override fun getAcceptedIssuers(): Array<X509Certificate>
                        {
                            return arrayOf()
                        }

                        @Throws(CertificateException::class)
                        override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) = Unit

                        @Throws(CertificateException::class)
                        override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) = Unit
                    })
                    val sslContext = SSLContext.getInstance("SSL")
                    sslContext.init(null, trustAllCerts, java.security.SecureRandom())
                    val sslSocketFactory = sslContext.socketFactory

                    val builder = OkHttpClient.Builder().readTimeout(180, TimeUnit.SECONDS).connectTimeout(180, TimeUnit.SECONDS)
                    builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                    builder.hostnameVerifier { hostname, session -> true }

                    return builder.build()
                } catch (e: Exception)
                {
                    throw RuntimeException(e)
                }
            }
    }

    @POST("login")
    fun loginUser(
        @Header("Authorization")
        auth: String,@Body loginInfoRequest: LoginInfoRequest
    ): Call<String>

 @POST("change-password")
    fun forgotPwd(
     @Header("Authorization")
     auth: String,
        @Body forgotPwdInfoRequest: ForgotPwdInfoRequest
    ): Call<String>

    @POST("send-otp")
    fun sendOTP(
        @Header("Authorization")
        auth: String,
        @Body sendOTPRequest: SendOTPRequest
    ): Call<String>

    @POST("register")
    fun registerUser(
        @Header("Authorization")
        auth: String,@Body registerInfoRequest: RegisterInfoRequest
    ): Call<String>

    @POST("search-bus")
    fun searchBus(@Body searchBusInfoRequest: SearchBusInfoRequest
    ): Call<SearchBusRes>

    @GET("get-city")
    fun getCity(): Call<CityResponse>

    @POST("bus-details")
    fun getBusDetail(@Body busDetailReq: BusDetailReq): Call<BusDetailRes>

    @POST("my-bookings")
    fun getBookings(@Body myBookingInfoRequest: MyBookingInfoRequest): Call<MyBookingInfoRes>

    @POST("change_password")
    fun changePass(
        @Header("Authorization")
        auth: String,@Body changePasswordReq: ChangePasswordReq
    ): Call<String>

    @POST("booking")
    fun doBooking(@Body bookingReq: BookingReq): Call<BookingRes>

    @POST("get-page")
    fun getAboutUs(@Body aboutUsReq: AboutUsReq): Call<AboutUsRes>

    @Multipart
    @POST("profile_image")
    fun profileImg(@Part("user_id") dummyextra: String,@Part("user_id") id: String,@Part prof: MultipartBody.Part,@Header("Authorization") auth: String
        /*,@Header("Content-Type") cType:String*/): Call<String>


    @POST("get_profile")
    fun getProfile(@Header("Authorization") auth: String,@Body getProfileReq: GetProfileReq
    ): Call<GetProfileRes>


    @POST("update_profile")
    fun updateProfile(
        @Header("Authorization")
        auth: String,@Body changePasswordReq: ChangePasswordReq
    ): Call<String>

    @POST("make-payment")
    fun doPayment(@Body bookingReq: MakePaymentReq): Call<PaymentRes>

    @POST("iwomi-payment-request")
    fun doPaymentOrangeMoney(@Body orangeMoneyPaymentReq: OrangeMoneyPaymentReq): Call<PaymentRes>

    @POST("iwomi-payment-status")
    fun doPaymentConfirm(@Body paymentConfirmReq: PaymentConfirmReq): Call<PaymentRes>

    @POST("make-braintree-payment")
    fun doBraintreePayment(@Body bookingReq: MakeBraintreePaymentReq): Call<PaymentRes>


    @POST("iwomi-cancel-booking")
    fun cancelBooking(@Body cancelBookingReq: CancelBookingReq): Call<String>

    @POST("iwomi-payment-status")
    fun getBookingStatus(@Body bookingStatusReq: BookingStatusReq): Call<String>
}