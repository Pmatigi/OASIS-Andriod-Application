package com.oasis.ui.stripe

import Constant
import android.annotation.TargetApi
import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.braintreepayments.api.dropin.DropInActivity
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.TextHttpResponseHandler
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.PaymentAdapter
import com.oasis.ui.BaseActivity
import com.oasis.ui.adapter.ItemClickListener
import com.oasis.ui.models.home.MakeBraintreePaymentReq
import com.oasis.ui.screens.ui.home.AvailiableBusActivity
import com.oasis.ui.screens.ui.home.HomeViewModel
import com.oasis.ui.screens.ui.home.WebViewActivity
import com.oasis.ui.utils.Util.Util
import cz.msebera.android.httpclient.Header


class StripePaymentActivity : BaseActivity(){

    lateinit var rec_view_payment: RecyclerView
    lateinit var paymentAdapter: PaymentAdapter
    lateinit var booking_id: String
    var total_price=0;
    private lateinit var homeViewModel: HomeViewModel
    lateinit var ticket_link: String
    val REQUEST_CODE_BRAINTREE = 3

    companion object {
        var paymentActivity= StripePaymentActivity()
    }
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
            homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)

            val tv_title: TextView =findViewById(R.id.tv_title)
        paymentActivity=this
        tv_title.text="Payment"
        booking_id=intent.getStringExtra("booking_id")
        total_price=intent.getIntExtra("total_price",0)
        val back = findViewById(R.id.lin_lay_back) as LinearLayout
        back.setOnClickListener {

            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }

        rec_view_payment = findViewById(R.id.rec_view_payment)
        rec_view_payment.setHasFixedSize(true)
        val mLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rec_view_payment.setLayoutManager(mLayoutManager1)
        rec_view_payment.addItemDecoration( DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        setAdapter()

      //  getTokenFromServer()
    }

    fun setAdapter(){
        paymentAdapter = PaymentAdapter(this!!,object : ItemClickListener {
            override fun onClick(view: View, position: Int) {
//                if(position==2){
//                    processPaymentPayPal()
//                }else if(position==3){
//                    processPaymentPayPal()
//                }else {
//                    val intent = Intent(applicationContext, AddPayment::class.java)
//                    intent.putExtra("booking_id", booking_id)
//                    intent.putExtra("total_price", total_price)
//                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
//                    startActivity(intent)
//                }

                    val intent = Intent(applicationContext, AddPayment::class.java)
                    intent.putExtra("booking_id", booking_id)
                    intent.putExtra("total_price", total_price)
                    intent.putExtra("payment_position", position)
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                    startActivity(intent)
            }
        })
        rec_view_payment.adapter=paymentAdapter
    }


    fun processPaymentPayPal() {
        val dropInRequest = DropInRequest()
            .tokenizationKey("sandbox_fwxyfft3_4jtxwkgrm7fmzkhy")
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE_BRAINTREE)
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @RequiresApi(Build.VERSION_CODES.KITKAT)
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.e("dkd","CAMERA_CAPTURE onActivityResult")

        if (requestCode == REQUEST_CODE_BRAINTREE) {
            Log.e("dkd","dkd braintree onActivityResult resultCode-"+resultCode)

            if (resultCode == Activity.RESULT_OK) {
                val result = data!!.getParcelableExtra<DropInResult>(
                    DropInResult.EXTRA_DROP_IN_RESULT
                )
                val paymentMethodNonce = result.paymentMethodNonce!!.nonce
                Log.v("PayNonce", "PayNonce==" + paymentMethodNonce)
                sendBrainTreePayment(paymentMethodNonce, booking_id)
            } else if (resultCode == Activity.RESULT_FIRST_USER) {
                val result = data!!.getParcelableExtra<DropInResult>(
                    DropInResult.EXTRA_DROP_IN_RESULT
                )
                Log.e("dkd","dkd payment result-"+result)
                val error =
                    data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                Log.e("dkd","dkd payment error-"+error)
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
            }else if (resultCode != Activity.RESULT_CANCELED) {
                val error =
                    data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                Log.e("dkd","dkd payment error-"+error)
                Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()

            } else{
                if(data!=null) {
                    val error =
                        data!!.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                    Log.e("dkd", "dkd payment error-" + error)
                    Toast.makeText(this, "Payment Failed!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }



    fun sendBrainTreePayment(braintree_token:String,booking_id:String){
        val makeBraintreePaymentReq =MakeBraintreePaymentReq(braintree_token,booking_id);
        showLoadingWithMessage( "Loading...")
        homeViewModel.doBraintreePayment(makeBraintreePaymentReq).observe(this, Observer {
            Util.showLog("Response", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    Log.e("dkd","dkd it-"+it.data!!)
                    Log.e("dkd","dkd it-"+it.data!!.error)
                    var error=it.data!!.error

                    if(error){
                        Util.showErrorToast(this,"Payment Failed,please try again");
                    }else{
                        hideLoading()
                        // Toast.makeText(this,it.data!!.message, Toast.LENGTH_LONG).show()
                        ticket_link=it.data!!.ticket_link
                        showSuccess("Payment Success","View Ticket")
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

    fun showSuccess(title:String,message:String){
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.error_popup_dialog)
        val v = this!!.window.decorView
        v.setBackgroundResource(android.R.color.transparent)
        dialog.getWindow()?.setBackgroundDrawable( ColorDrawable(Color.TRANSPARENT));
        val dialogTitle = dialog.findViewById(R.id.dialog_title) as TextView
        dialogTitle.text = title
        val popup_description = dialog.findViewById(R.id.popup_description) as TextView
        popup_description.text = message
        val popup_okButton = dialog.findViewById(R.id.popup_ok_button) as TextView
        popup_okButton.text = "OK"
        popup_okButton.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("ticket_link",ticket_link)
            overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
            startActivity(intent)
            dialog.dismiss()
            AvailiableBusActivity.availiableBusActivity.finish()
            finish()
        }
        dialog.setCancelable(false)
        dialog.show()
    }


    val API_URL = "YOUR_API_URL" // Replace your url here
    fun getTokenFromServer() {
        val androidClient = AsyncHttpClient()
        androidClient.get(API_URL, object : TextHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseString: String?
            ) {
                Log.d("Success!!", "Client Token== " + responseString)
            }

            override fun onFailure(
                statusCode: Int, headers: Array<Header>, responseString:
                String, throwable: Throwable
            ) {
                Log.v("Failure!", responseString)
            }
        })
    }

}
