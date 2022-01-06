package com.oasis.ui.screens.ui.booking

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.AppController
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.BookingListAdapterUpcoming
import com.oasis.ui.models.booking.CurrentBooking
import com.oasis.ui.models.booking.MyBookingInfoRequest
import com.oasis.ui.models.booking.MyBookingInfoRes
import com.oasis.ui.models.booking.PastBooking
import com.oasis.ui.models.home.BusInfoModel
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.screens.ui.maps.MapsActivity
import com.oasis.ui.utils.Util.Util
import kotlinx.android.synthetic.main.fragment_booking.*
import java.util.*
import kotlin.collections.ArrayList

class BookingFragment : Fragment() {

    private lateinit var bookingViewModel: BookingViewModel
    private lateinit var bookingListAdapterUpcoming:BookingListAdapterUpcoming
    private lateinit var myBookingInfoRes:MyBookingInfoRes
    private lateinit var tv_no_data:TextView
    var userId=""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bookingViewModel =
            ViewModelProviders.of(this).get(BookingViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_booking, container, false)
        val tv_title:TextView=root.findViewById(R.id.tv_title)

        val lin_lay_back:LinearLayout=root.findViewById(R.id.lin_lay_back)
        lin_lay_back.visibility=View.GONE

        tv_title.text="Booking"
        val rec_view_booking_list_upcoming : RecyclerView= root.findViewById(R.id.rec_view_booking_list_upcoming)
        tv_no_data =root.findViewById(R.id.tv_no_data)

        val mLayoutManager1 = LinearLayoutManager(activity)
        rec_view_booking_list_upcoming.setHasFixedSize(true)
        rec_view_booking_list_upcoming.setLayoutManager(mLayoutManager1)
        val dataVault = DataVaultHelper()
        val userDetails = dataVault.getVault(activity!!.applicationContext, DataVaultHelper.APP_VAULTNAME)
        if (userDetails!!.isNotEmpty()) {
            if (userDetails[0] != null) {
                userId=userDetails[3]
            }
        }

        Log.e("dkd","dkd userId-"+userId)

//        getBookings(userId)
        return root
    }

    override fun onResume() {
        super.onResume()
        getBookings(userId)

    }

    fun getBookings(customerId:String ){
        val myBookingInfoRequest = MyBookingInfoRequest(
            customerId)

        (activity as HomeActivity).showLoading()
        bookingViewModel.getBooking(myBookingInfoRequest).observe(this, Observer {
            Util.showLog("BookingResponse", "" + it)
            (activity as HomeActivity).hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    Log.e("dkd","dkd it-"+it.data!!)
                    Log.e("dkd","dkd it-"+it.data!!.error)
                    var error=it.data!!.error

                    if(error){
                        Util.showErrorToast(activity!!.applicationContext,"Error occured!");
                    }else{
                        myBookingInfoRes= it.data!!

                        val currentBooking=myBookingInfoRes.bookings.currentBooking as ArrayList<CurrentBooking>?

                         bookingListAdapterUpcoming = BookingListAdapterUpcoming(activity!!, currentBooking, object : BookingListAdapterUpcoming.ItemClickListener {

                             override fun onClick(view: View, position: Int) {
                                 val intent = Intent(activity as HomeActivity, BookingDetailsActivity::class.java)
                                 intent.putExtra("booking", currentBooking?.get(position) )
                                 intent.putExtra("bookingType", "current")
                                 startActivity(intent)
                                 activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                             }
                         })
                        rec_view_booking_list_upcoming.adapter=bookingListAdapterUpcoming
                        rec_view_booking_list_upcoming.addItemDecoration( DividerItemDecoration(rec_view_booking_list_upcoming.getContext(), DividerItemDecoration.VERTICAL));

                        if(myBookingInfoRes.bookings.currentBooking.size<=0){
                            tv_no_data.visibility=View.VISIBLE
                        }else{
                            tv_no_data.visibility=View.GONE
                        }
                    }
                } else {
                    Util.showErrorToast(activity!!.applicationContext, it.message)
                }
            } else
            {
                Util.showErrorToast(activity!!.applicationContext, Constant.ERROR_TRY_AGAIN)
            }
        })
    }

}