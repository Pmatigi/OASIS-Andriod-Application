package com.oasis.ui.screens.ui.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.BookingListAdapterComp
import com.oasis.com.oasis.ui.adapter.BookingListAdapterUpcoming
import com.oasis.ui.models.booking.MyBookingInfoRes
import com.oasis.ui.models.home.BusInfoModel

class CompleteBookingActivity : AppCompatActivity() {
    lateinit var booking_list_view: RecyclerView
    //lateinit var mAdapter: BookingListAdapterUpcoming

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_complete_booking)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="My Bookings"
        booking_list_view = findViewById(R.id.rec_view_booking_list)
        booking_list_view.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        booking_list_view.setLayoutManager(mLayoutManager)
        val back = findViewById(R.id.lin_lay_back) as LinearLayout
        back.setOnClickListener {

            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
    }
}
