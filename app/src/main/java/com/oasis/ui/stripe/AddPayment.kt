package com.oasis.ui.stripe

import Constant
import Constant.Companion.PAYMENT_CONFIRM_MSG
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.R
import com.oasis.ui.BaseActivity
import com.oasis.ui.models.booking.OrangeMoneyPaymentReq
import com.oasis.ui.models.booking.PaymentConfirmReq
import com.oasis.ui.models.home.MakePaymentReq
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screen_authentication.LoginScreen
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.screens.ui.home.AvailiableBusActivity
import com.oasis.ui.screens.ui.home.HomeViewModel
import com.oasis.ui.screens.ui.home.WebViewActivity
import com.oasis.ui.utils.Util.Util
import com.ril.utils.ActionDialog
import com.stripe.android.ApiResultCallback
import com.stripe.android.Stripe
import com.stripe.android.model.Card
import com.stripe.android.model.Token
import com.stripe.android.view.CardInputWidget


class AddPayment : AppCompatActivity() {
    var card_input_widget: CardInputWidget? = null
    private lateinit var homeViewModel: HomeViewModel
    lateinit var booking_id: String
    lateinit var ticket_link: String
    var payment_position=0
    var total_price=0
    lateinit var save: Button
    lateinit var tv_amount: TextView
    lateinit var et_mobile_no: EditText
    lateinit var lin_lay_card: LinearLayout
    lateinit var lin_lay_orange_money: LinearLayout
    lateinit var lin_lay_back:LinearLayout

    companion object {
        var addPayment= AddPayment()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_payment)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="Payment"
        card_input_widget = findViewById(R.id.card_input_widget)
        lin_lay_back =findViewById(R.id.lin_lay_back)
        lin_lay_back.setOnClickListener { finish() }

        lin_lay_card = findViewById(R.id.lin_lay_card)
        lin_lay_orange_money = findViewById(R.id.lin_lay_orange_money)
        save = findViewById(R.id.save_payment)
        tv_amount = findViewById(R.id.tv_amount)
        et_mobile_no = findViewById(R.id.et_mobile_no)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        booking_id=intent.getStringExtra("booking_id")
        total_price=intent.getIntExtra("total_price",0)
        payment_position=intent.getIntExtra("payment_position",0)
        if(payment_position==0 || payment_position==1){
            lin_lay_card.visibility=View.VISIBLE
        }else{
            lin_lay_orange_money.visibility=View.VISIBLE
        }
        tv_amount.text="Amount:"+total_price
        addPayment=this
        save.setOnClickListener {
            Log.e("dkd","save clicked")
                if (Util.checkNetworkStatus(this)) {
                    Util.hideKeyboard(this)
                    showLoadingWithMessage("Processing Payment...")
                    if(payment_position==0 || payment_position==1) {
                        saveCard()
                    }else{
                        if(!et_mobile_no.text.trim().equals("")) {
                            if(payment_position==2) {
                                val orangeMoneyPaymentReq1 = OrangeMoneyPaymentReq("om",booking_id,et_mobile_no.text.toString().trim())
                                orangeMoneyPayment(orangeMoneyPaymentReq1)
                            }else{
                                val orangeMoneyPaymentReq2 = OrangeMoneyPaymentReq("momo",booking_id,et_mobile_no.text.toString().trim())
                                orangeMoneyPayment(orangeMoneyPaymentReq2)
                            }
                        }else{
                            Util.showToast(this, "Please Enter Mobile Number")
                        }
                    }
                } else {
                    Util.showToast(this, Constant.NO_INTERNET)
                }

        }

    }

    private fun saveCard() {
        Log.e("dkd","save clicked111")

        val card = card_input_widget!!.card
        Log.e("dkd","save clicked111 card-"+card)

        if (card == null) {
            Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
            hideLoading()
        } else {
            if (!card.validateCard()) {
                // Do not continue token creation.
                Toast.makeText(applicationContext, "Invalid card", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("dkd","save clicked111 CreateToken-"+card)

                createCardToken(card)
            }
        }
    }

    fun createCardToken(card: Card) {
        val stripe = Stripe(applicationContext, getString(R.string.publishablekey))
        stripe.createCardToken(card, callback = object : ApiResultCallback<Token> {
            override fun onSuccess(token: Token) {
                Log.e("StripeExample", "Success-"+token)
                Log.e("StripeExample", "Success-"+token.bankAccount)
                Log.e("StripeExample", "Success-"+token.id)
                val makePaymentReq=MakePaymentReq(token.id,booking_id)
                sendToken(makePaymentReq);
            }

            override fun onError(e: Exception) {
                Log.e("StripeExample", "Error while creating card token", e)
            }
        })
    }

    fun orangeMoneyPayment( orangeMoneyPaymentReq: OrangeMoneyPaymentReq){

        homeViewModel.doPaymentOrangeMoney(orangeMoneyPaymentReq).observe(this, Observer {
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
                        //Util.showErrorToast(this,"Payment Failed,please try again");
                       showPaymentConfirmDialog()
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

    fun oMPaymentConfirmation( paymentConfirmReq: PaymentConfirmReq){

        homeViewModel.doPaymentConfirm(paymentConfirmReq).observe(this, Observer {
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

    private fun showPaymentConfirmDialog()
    {
        ActionDialog(this, "Success", PAYMENT_CONFIRM_MSG, object : ActionDialog.OnDialogClick
        {
            override fun reject(inputText: String, type: Int)
            {
            }

            override fun accept(inputText: String, type: Int)
            {
                try {
                    showLoadingWithMessage("Confirming Payment")
                    val paymentConfirmReq=PaymentConfirmReq(booking_id)
                  oMPaymentConfirmation(paymentConfirmReq)
                }catch (e: java.lang.Exception){
                    e.printStackTrace()
                }

            }
        }, "Ok","Cancel").show()

    }


    fun sendToken( paymentReq: MakePaymentReq){

        homeViewModel.doPayment(paymentReq).observe(this, Observer {
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


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
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
                StripePaymentActivity.paymentActivity.finish()
                finish()
            }
            dialog.setCancelable(false)
            dialog.show()
    }

    var progressBarLayout: View? = null

    private fun getProgresBarView(): View {

        val progressBarLayout = layoutInflater.inflate(R.layout.progressbar, null)
        val rl = RelativeLayout(this)
        rl.gravity = Gravity.CENTER
        rl.addView(progressBarLayout)
        val layout = this.findViewById<View>(android.R.id.content).rootView as ViewGroup
        layout.isClickable=false
        layout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })
        val lin_lay_loading = progressBarLayout?.findViewById<View>(R.id.lin_lay_loading) as LinearLayout

        lin_lay_loading.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })

        layout.addView(rl, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        return rl
    }

    fun showLoadingWithMessage(title: String) {

        if (progressBarLayout == null)
            progressBarLayout = getProgresBarView()

        val tv_title = progressBarLayout?.findViewById<View>(R.id.tv_title) as TextView
        tv_title.visibility= View.VISIBLE
        tv_title.text = title
    }

    fun hideLoading() {
        if (progressBarLayout != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressBarLayout?.setVisibility(View.GONE)
            progressBarLayout = null
        }
    }
}

