package com.oasis.ui.screens.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.PassengerClickListener
import com.oasis.com.oasis.ui.adapter.PassengerListAdapter
import com.oasis.ui.BaseActivity
import com.oasis.ui.listener.PassengerDelListener
import com.oasis.ui.models.booking.BookingReq
import com.oasis.ui.models.booking.Passenger
import com.oasis.ui.models.home.*
import com.oasis.ui.preferneces.DataVaultHelper.DataVaultHelper
import com.oasis.ui.stripe.StripePaymentActivity
import com.oasis.ui.utils.Util.Util
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class PassengerDetailsActivity : BaseActivity(), AdapterView.OnItemSelectedListener,PassengerDelListener {

    lateinit var rel_lay_upper:RelativeLayout
  //  lateinit var rel_lay_add_passenger:RelativeLayout
    lateinit var lin_lay_passenger:LinearLayout
    lateinit var  mAdapter:PassengerListAdapter
    lateinit var tv_procede_booking:TextView
    val selectedPassengerList = ArrayList<Passenger>()
    var list_of_items_gender = arrayOf("Select Gender","Male", "Female", "Other")
    lateinit var spinner_gender:Spinner
    var bus_info= BusDetailRes()
    private lateinit var homeViewModel: HomeViewModel
    var countSeats=0
    lateinit var etEmail:EditText
    lateinit var etAge:EditText
    var totalSeats=ArrayList<String>()

    var selectedSeatListSeater = ArrayList<String>()
    var selectedSeatListSleeper = ArrayList<String>()
    var selectedSeatListUpperSleeper = ArrayList<String>()
    var selectedPosListSeater = ArrayList<Int>()
    var selectedPosListSleeper = ArrayList<Int>()
    var selectedPosListUpperSleeper = ArrayList<Int>()
    var sittingSeatsLower=ArrayList<LowerSeat>()
    var sleeperSeatsLower=ArrayList<LowerSeat>()
    var sleeperSeatsUpper=ArrayList<UpperSeat>()
    var total_price =0
    var seatsCount =0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passenger_detail)
        val tv_title: TextView =findViewById(R.id.tv_title)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        tv_procede_booking =findViewById(R.id.tv_procede_booking)

        Util.hideKeyboard(this)

        val tv_name: TextView =findViewById(R.id.tv_name)
        val tv_phone: TextView =findViewById(R.id.tv_phone)
        etEmail =findViewById(R.id.etEmail) as EditText
        etAge =findViewById(R.id.etAge) as EditText

        val total_price=intent.getIntExtra("total_price",0)
        val passenger_count=intent.getIntExtra("passenger_count",0)
        bus_info=intent.getSerializableExtra("bus_info")as BusDetailRes
        selectedPosListSeater=intent.getSerializableExtra("selectedPosListSeater") as ArrayList<Int>
        selectedPosListSleeper =intent.getSerializableExtra("selectedPosListSleeper")as ArrayList<Int>
        selectedPosListUpperSleeper =intent.getSerializableExtra("selectedPosListUpperSleeper")as ArrayList<Int>
        selectedSeatListSeater=intent.getSerializableExtra("selectedSeatListSeater") as ArrayList<String>
        selectedSeatListSleeper=intent.getSerializableExtra("selectedSeatListSleeper") as ArrayList<String>
        selectedSeatListUpperSleeper=intent.getSerializableExtra("selectedSeatListUpperSleeper") as ArrayList<String>
        sittingSeatsLower=intent.getSerializableExtra("sittingSeatsLower") as ArrayList<LowerSeat>
        sleeperSeatsLower=intent.getSerializableExtra("sleeperSeatsLower") as ArrayList<LowerSeat>
        sleeperSeatsUpper=intent.getSerializableExtra("sleeperSeatsUpper") as ArrayList<UpperSeat>

        totalSeats=selectedSeatListSeater
        totalSeats.addAll(selectedSeatListSleeper)
        totalSeats.addAll(selectedSeatListUpperSleeper)

        rel_lay_upper = findViewById(R.id.rel_lay_upper) as RelativeLayout
        //rel_lay_add_passenger = findViewById(R.id.rel_lay_add_passenger) as RelativeLayout
        lin_lay_passenger = findViewById(R.id.lin_lay_passenger) as LinearLayout
        val btnDone = findViewById(R.id.btnDone) as Button
        val rec_view_passenger: RecyclerView =findViewById(R.id.rec_view_passenger)
        tv_title.text="Passenger Details"

        spinner_gender =findViewById(R.id.spinner_gender)

        spinner_gender!!.setOnItemSelectedListener(this)

        val back = findViewById(R.id.lin_lay_back) as LinearLayout
        back.setOnClickListener {
            Util.hideKeyboard(this)
            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }

        val aa = ArrayAdapter(this, android.R.layout.simple_spinner_item, list_of_items_gender)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner_gender!!.setAdapter(aa)

        rec_view_passenger.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        rec_view_passenger.setLayoutManager(mLayoutManager)

        btnDone.setOnClickListener {
            var firstNo=""
            var firstNoInt=0
            if(tv_phone.text.toString().trim().length>0) {
                 firstNo = tv_phone.text.toString().trim().substring(0, 1);
                firstNoInt=(Integer.parseInt(firstNo))
            }
            Log.e("dkd","dkd firstNo- "+firstNo)
            if(tv_name.text.toString().trim().equals("")){
                Util.showErrorToast(this,"Please Enter Name")
            }else if(tv_phone.text.toString().trim().equals("")){
                Util.showErrorToast(this,"Please Enter Phone number")
            }else if(tv_phone.text.toString().trim().length<10){
                    Util.showErrorToast(this, "Please Enter Valid Phone number")
            }else if(firstNoInt<6) {
                    Util.showErrorToast(this, "Please Enter Valid Phone number")
            }else if(spinner_gender.selectedItem.toString().trim().equals("Select Gender")){
                Util.showErrorToast(this,"Please select gender")
             }else {
                if(countSeats< totalSeats.size) {

                    selectedPassengerList.add(
                        Passenger(
                            (countSeats+1).toString(),
                            tv_name.text.toString(),
                            totalSeats.get(countSeats),
                            tv_phone.text.toString(),
                            spinner_gender.selectedItem.toString(),
                            etAge.text.toString(),
                            etEmail.text.toString()
                            )
                    )
                    countSeats++
                }

                tv_name.text=""
                tv_phone.text=""
                etAge.setText("")
                etEmail.setText("")
                spinner_gender.setSelection(0)
                tv_name.requestFocus()
                mAdapter = PassengerListAdapter(
                    this!!,
                    selectedPassengerList,
                    this,
                    object : PassengerClickListener {
                        override fun onClick(view: View, position: Int) {

                        }
                    })
                rec_view_passenger.adapter = mAdapter

                mAdapter.notifyDataSetChanged()

                if (passenger_count > selectedPassengerList.size) {
                    lin_lay_passenger.visibility = View.VISIBLE
                } else {
                    tv_procede_booking.visibility=View.VISIBLE
                    lin_lay_passenger.visibility = View.GONE
                }

                Util.hideKeyboard(this)
            }
        }

        back.setOnClickListener {

            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }

        tv_procede_booking.setOnClickListener {
            Util.hideKeyboard(this)
            showLoadingWithMessage( "Loading...")
            booking()
        }
    }

    fun booking(){

        val dataVault = DataVaultHelper()
        var userDetails = dataVault.getVault(this, DataVaultHelper.APP_VAULTNAME)!!
        var userId=""
        if (userDetails!!.isNotEmpty()) {
            Log.e("dkd","dkd --"+userDetails[0])
            if (userDetails[0] != null) {
                userId=userDetails[3]
            }
        }
        val bookingReq=BookingReq()
        bookingReq.busId=bus_info.id.toString()
        bookingReq.customerId=userId
        bookingReq.dateOfJourney=getCurrentDate()
        bookingReq.timeOfJourney=getCurrentTime()
        bookingReq.fromId=bus_info.sourceId
        bookingReq.toId=bus_info.destinationId

        var passengerList=ArrayList<PassengersDetail>()
        for ( item in selectedPassengerList){
            val passengersDetail=PassengersDetail(item.name,item.phone
            ,"adars@gmail.com",item.gender,"30")

            passengerList.add(passengersDetail)
        }
        bookingReq.passengersDetail=passengerList

        var selectedSeatsNo=ArrayList<String>()
        for ( item in selectedPosListSeater){
            selectedSeatsNo.add(sittingSeatsLower.get(item-1).seatNo)
        }
        for ( item in selectedPosListSleeper){
            selectedSeatsNo.add(sleeperSeatsLower.get(item-1).seatNo)
        }
        for ( item in selectedPosListUpperSleeper){
            selectedSeatsNo.add(sleeperSeatsUpper.get(item-1).seatNo)
        }

        bookingReq.seatNo=selectedSeatsNo
         seatsCount=selectedSeatsNo.size
        bookingReq.totalFare=((bus_info!!.totalFare!!.toInt())*seatsCount).toString()

        doBooking(bookingReq)
    }

    fun doBooking( bookingReq: BookingReq){

        homeViewModel.doBooking(bookingReq).observe(this, Observer {
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
                        Util.showErrorToast(this,"Error occured!");
                    }else{

                        SeatsActivity.seatsActivity.finish()
                        this.finish()
                        
                        val intent = Intent(this, StripePaymentActivity::class.java)
                        intent.putExtra("booking_id",it.data?.bookingDetails!!.get(0).bookingSeats.get(0).bookingId)
                        intent.putExtra("total_price",(bus_info!!.totalFare!!.toInt())*seatsCount)
                        overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                        startActivity(intent)
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

    fun getCurrentTime():String{
        val formatter = SimpleDateFormat("HH:mm")
        val date = Date(System.currentTimeMillis())
        Log.e("dkd","dkd  date-"+formatter.format(date))
        return formatter.format(date)
    }

    fun getCurrentDate():String{
        val formatter =  SimpleDateFormat("yyyy-MM-dd"); //If you need time just put specific format for time like 'HH:mm:ss'
        val date = Date(System.currentTimeMillis())
        Log.e("dkd","dkd  date-"+formatter.format(date))
        return formatter.format(date)
    }

    override fun onDeleteClick(position: Int) {
        countSeats=countSeats-1
        if(selectedPassengerList.size>position) {
            selectedPassengerList.removeAt(position)
            mAdapter.notifyDataSetChanged()
            lin_lay_passenger.visibility=View.VISIBLE
            tv_procede_booking.visibility=View.GONE
            //tv_add_passenger.visibility=View.VISIBLE
        }
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

    }

    private fun isValidEmail(email: String): Boolean {
        val pattern = Patterns.EMAIL_ADDRESS
        return pattern.matcher(email).matches()
    }

    val PHONE = Regex(
        "[6-9][0-9]{9}")

    private fun isValidPhone(mobile: String): Boolean {
        return mobile.matches(PHONE)
    }
}
