package com.oasis.ui.screens.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.BookingListAdapterComp
import com.oasis.ui.BaseActivity
import com.oasis.ui.models.booking.CurrentBooking
import com.oasis.ui.models.booking.MyBookingInfoRequest
import com.oasis.ui.models.booking.MyBookingInfoRes
import com.oasis.ui.utils.Util.Util
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper

class MyBookingsActivity : BaseActivity() {
    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var bookingListAdapterComp: BookingListAdapterComp
    var myBookingInfoRes: MyBookingInfoRes?=null
    lateinit var tv_current: TextView
    lateinit var tv_comp: TextView
    lateinit var tv_cancel: TextView
    lateinit var tv_no_data: TextView
    lateinit var rec_view_booking_list_comp: RecyclerView
    var bookings=ArrayList<CurrentBooking>()
    var userId=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_bookings)
        //expListView=findViewById(R.id.exp_lv_my_bookings)
        bookingViewModel =
            ViewModelProviders.of(this).get(BookingViewModel::class.java)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="My Bookings"

        rec_view_booking_list_comp =findViewById(R.id.rec_view_booking_list_comp)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rec_view_booking_list_comp.layoutManager=mLayoutManager
        rec_view_booking_list_comp.addItemDecoration( DividerItemDecoration(rec_view_booking_list_comp.getContext(), DividerItemDecoration.VERTICAL));
        tv_current =findViewById(R.id.tv_current)
        tv_comp =findViewById(R.id.tv_comp)
        tv_cancel =findViewById(R.id.tv_cancel)
        tv_no_data =findViewById(R.id.tv_no_data)

        val dataVault = DataVaultHelper()
        val userDetails = dataVault.getVault(this, DataVaultHelper.APP_VAULTNAME)
        if (userDetails!!.isNotEmpty()) {
            if (userDetails[0] != null) {
                userId=userDetails[3]
            }
        }

        val back = findViewById(R.id.lin_lay_back) as LinearLayout
        back.setOnClickListener {

            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
//    getBookings(userId)
    }

    override fun onResume() {
        super.onResume()
        tv_current.setBackgroundResource(R.drawable.rounded_white)
        tv_cancel.setBackgroundResource(0)
        tv_comp.setBackgroundResource(0)
        getBookings(userId)
    }

    fun getBookings(customerId:String ){
        val myBookingInfoRequest = MyBookingInfoRequest(
            customerId)

        showLoading()
        bookingViewModel.getBooking(myBookingInfoRequest).observe(this, Observer {
            Util.showLog("BookingResponse", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {

                    if(it.data!!.error){
                        Util.showErrorToast(this,"Error occured!");
                    }else{
                        myBookingInfoRes= it.data!!
                        bookings= myBookingInfoRes?.bookings?.currentBooking as ArrayList<CurrentBooking>
                        bookingListAdapterComp = BookingListAdapterComp(this!!,bookings as ArrayList<CurrentBooking>?
                            , object : BookingListAdapterComp.ItemClickListener {

                                override fun onClick(view: View, position: Int) {
                                    Log.e("dkd","dkd pos-"+position)
                                    val intent = Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java)
                                    intent.putExtra("booking", bookings.get(position))
                                    intent.putExtra("bookingType", "current")
                                    startActivity(intent)
                                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                                }
                            })
                        rec_view_booking_list_comp.adapter=bookingListAdapterComp
                        if(myBookingInfoRes?.bookings?.currentBooking?.size==0){
                            tv_no_data.visibility= View.VISIBLE
                        }else{
                            tv_no_data.visibility= View.GONE
                        }
                        bookingType()
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

    fun bookingType(){
        tv_current.setOnClickListener {
            if(myBookingInfoRes!!.bookings.currentBooking.size==0){
                tv_no_data.visibility= View.VISIBLE
            }else{
                tv_no_data.visibility= View.GONE
            }
            tv_current.setBackgroundResource(R.drawable.rounded_white)
            tv_cancel.setBackgroundResource(0)
            tv_comp.setBackgroundResource(0)
            bookings= myBookingInfoRes!!.bookings.currentBooking as ArrayList<CurrentBooking>
            bookingListAdapterComp = BookingListAdapterComp(this!!,bookings as ArrayList<CurrentBooking>?, object : BookingListAdapterComp.ItemClickListener {

                override fun onClick(view: View, position: Int) {
                    val intent = Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java)
                    intent.putExtra("bookingType", "current")
                    intent.putExtra("booking", bookings.get(position))
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                }
            })
            rec_view_booking_list_comp.adapter=bookingListAdapterComp
        }

        tv_cancel.setOnClickListener {
            if(myBookingInfoRes!!.bookings.cancelledBooking.size==0){
                tv_no_data.visibility= View.VISIBLE
            }else{
                tv_no_data.visibility= View.GONE
            }
            tv_current.setBackgroundResource(0)
            tv_cancel.setBackgroundResource(R.drawable.rounded_white)
            tv_comp.setBackgroundResource(0)
            bookings= myBookingInfoRes!!.bookings.cancelledBooking as ArrayList<CurrentBooking>
            bookingListAdapterComp = BookingListAdapterComp(this!!,bookings as ArrayList<CurrentBooking>?, object : BookingListAdapterComp.ItemClickListener {

                override fun onClick(view: View, position: Int) {
                    Log.e("dkd","dkd pos-"+position)
                    val intent = Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java)
                    intent.putExtra("booking", bookings.get(position))
                    intent.putExtra("bookingType", "cancel")
                    startActivity(intent)
                    overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                }
            })
            rec_view_booking_list_comp.adapter=bookingListAdapterComp
        }

        tv_comp.setOnClickListener {
            if(myBookingInfoRes!!.bookings.pastBooking.size==0){
                tv_no_data.visibility= View.VISIBLE
            }else{
                tv_no_data.visibility= View.GONE
            }
            tv_current.setBackgroundResource(0)
            tv_cancel.setBackgroundResource(0)
            tv_comp.setBackgroundResource(R.drawable.rounded_white)
            bookings= myBookingInfoRes!!.bookings.pastBooking as ArrayList<CurrentBooking>
            bookingListAdapterComp = BookingListAdapterComp(this!!,bookings as ArrayList<CurrentBooking>?
                , object : BookingListAdapterComp.ItemClickListener {

                    override fun onClick(view: View, position: Int) {
                        Log.e("dkd","dkd pos-"+position)
                        val intent = Intent(this@MyBookingsActivity, BookingDetailsActivity::class.java)
                        intent.putExtra("booking", bookings.get(position))
                        intent.putExtra("bookingType", "past")
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                    }
                })
            rec_view_booking_list_comp.adapter=bookingListAdapterComp

        }
    }




    /*
  * Preparing the list data
  */
//    fun prepareListData(myBookingInfoRes:MyBookingInfoRes) {
//        listDataHeader =  ArrayList<String>();
//        listDataChild =  HashMap<String, List<CurrentBooking>>();
//
//        // Adding child data
//        listDataHeader.add("Current Booking");
//        listDataHeader.add("Cancel Booking");
//        listDataHeader.add("Past Booking");
//
//        listDataChild.put(listDataHeader.get(0), myBookingInfoRes.bookings.currentBooking); // Header, Child data
//        listDataChild.put(listDataHeader.get(1), myBookingInfoRes.bookings.cancelledBooking);
//        listDataChild.put(listDataHeader.get(2), myBookingInfoRes.bookings.pastBooking);
//
//        listAdapter = ExpandableListAdapter(this, listDataHeader, listDataChild)
//
//        // setting list adapter
//        expListView.setAdapter(listAdapter)
//    }

}
