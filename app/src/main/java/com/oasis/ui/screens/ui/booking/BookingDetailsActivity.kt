package com.oasis.ui.screens.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.BookingListAdapterComp
import com.oasis.ui.BaseActivity
import com.oasis.ui.models.booking.BookingStatusReq
import com.oasis.ui.models.booking.CancelBookingReq
import com.oasis.ui.models.booking.CurrentBooking
import com.oasis.ui.models.booking.MyBookingInfoRequest
import com.oasis.ui.models.home.SearchBusRes
import com.oasis.ui.screens.ui.home.WebViewActivity
import com.oasis.ui.utils.Util.Util

import kotlinx.android.synthetic.main.activity_booking_details.*
import org.json.JSONObject

class BookingDetailsActivity : BaseActivity() {

    lateinit var iv_view_pdf:ImageView
    lateinit var tv_start:TextView
    lateinit var tv_end:TextView
    lateinit var tv_booking_date:TextView
    lateinit var tv_bus_name:TextView
    lateinit var tv_price:TextView
    lateinit var tv_start_time:TextView
    lateinit var tv_pnr:TextView
    lateinit var tv_seat_no:TextView
    lateinit var tv_passenger_name:TextView
    lateinit var tv_stop_point:TextView
    lateinit var tv_boarding_point:TextView
    lateinit var tv_ticket_no:TextView
    lateinit var tv_cancel_booking:TextView
    lateinit var lin_lay_back:LinearLayout
    private lateinit var bookingViewModel: BookingViewModel
    var bookingType=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_booking_details)
        bookingViewModel =
            ViewModelProviders.of(this).get(BookingViewModel::class.java)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="Booking Details"

        val booking = intent.getSerializableExtra("booking") as CurrentBooking
        bookingType = intent.getStringExtra("bookingType")

        lin_lay_back =findViewById(R.id.lin_lay_back)
        lin_lay_back.setOnClickListener { finish() }

        iv_view_pdf =findViewById(R.id.iv_view_pdf)
        tv_start =findViewById(R.id.tv_start)
        tv_cancel_booking =findViewById(R.id.tv_cancel_booking)
        tv_ticket_no =findViewById(R.id.tv_ticket_no)
        tv_end =findViewById(R.id.tv_end)
        tv_booking_date =findViewById(R.id.tv_booking_date)
        tv_bus_name =findViewById(R.id.tv_bus_name)
        tv_price =findViewById(R.id.tv_price)
        tv_start_time =findViewById(R.id.tv_start_time)
        tv_pnr =findViewById(R.id.tv_pnr)
        tv_seat_no =findViewById(R.id.tv_seat_no)
        tv_passenger_name =findViewById(R.id.tv_passenger_name)
        tv_stop_point =findViewById(R.id.tv_dropping_point)
        tv_boarding_point =findViewById(R.id.tv_boarding_point)
        tv_ticket_no.text=booking.ticketId
        tv_start.text=booking.startingPoint
        tv_end.text=booking.destinationPoint
        tv_booking_date.text=Util.getFullDateFromDateTimeInMonthName(booking.bookingDateTime)
        tv_pnr.text=booking.pnrNo
        tv_price.text="Rs. "+booking.totalFareAccepted
        tv_boarding_point.text=booking.boardingPoint
        tv_stop_point.text=booking.dropPoint
        tv_start_time.text=Util.getTimeFromDateTime(booking.bookingDateTime)
        if(booking.passengerName !=null) {
            tv_passenger_name.text= booking.passengerName as CharSequence?
        }

        if(!bookingType.equals("current")){
            tv_cancel_booking.visibility=View.GONE
        }

        var seatsStr=""
        for(item in booking.seatNumber){
            if(seatsStr.equals("")){
                seatsStr=seatsStr+item
            }else{
                seatsStr=seatsStr+","+item
            }
        }

        if(!seatsStr.equals("")) {
            tv_seat_no.text = seatsStr
        }

        tv_cancel_booking.setOnClickListener {
            cancelBooking(booking.bookingId.toString())
        }

        iv_view_pdf.setOnClickListener {
            val intent = Intent(this, WebViewActivity::class.java)
            intent.putExtra("ticket_link",booking.ticket_pdf)
            overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
            startActivity(intent)
        }
    }

    fun cancelBooking(bookingId:String ){
        val cancelBookingReq = CancelBookingReq(bookingId)
        showLoading()
        bookingViewModel.cancelBooking(cancelBookingReq).observe(this, Observer {
            Util.showLog("cancelBookingResponse", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("message")
                        Util.showToast(this,jsonObj.getString("message"));
                    }else{
                        Util.showToast(this,jsonObj.getString("message"));
                        finish()
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

    fun getBookingStatus(bookingId:String ){
        val bookingStatusReq = BookingStatusReq(bookingId)
        showLoading()
        bookingViewModel.getBookingStaus(bookingStatusReq).observe(this, Observer {
            Util.showLog("BookingStatusReq", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    val jsonObj= JSONObject(it.data)
                    var error=jsonObj.getBoolean("error")

                    if(error){
                        var messageArr=jsonObj.getString("message")
                        Util.showToast(this,jsonObj.getString("message"));
                    }else{
                        Util.showToast(this,jsonObj.getString("message"));
                        finish()
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
}
