package com.oasis.ui.screens.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.ui.BaseActivity
import com.oasis.ui.listener.BusItemClickListener
import com.oasis.ui.utils.Util.Util
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import android.view.animation.TranslateAnimation
import com.oasis.ui.adapter.*
import com.oasis.ui.models.home.*


class AvailiableBusActivity : BaseActivity(), BusItemClickListener {

    lateinit var bus_list_view: RecyclerView
    lateinit var rec_view__amenities: RecyclerView
    lateinit var rec_view_ratings: RecyclerView
    lateinit var mAdapter: BusListAdapter
    lateinit var tv_route:TextView
    lateinit var iv_hide_ratings:ImageView
    lateinit var iv_hide_amenities:ImageView
    lateinit var rel_lay_parent:RelativeLayout
    lateinit var rel_lay_ratings:RelativeLayout
    lateinit var rel_lay_amenities:RelativeLayout
    lateinit var lin_lay__amenities:LinearLayout
    lateinit var lin_lay_ratings:LinearLayout
    //lateinit var grid_view_amenity:GridView
    var busResponse= SearchBusRes()
    private lateinit var homeViewModel: HomeViewModel
    var selectedDateStr1=""
    var selectedTimeStr=""
    var isDialogOpened=false
    companion object {
        var availiableBusActivity= AvailiableBusActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_available_bus_list)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="Availiable Bus"
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)

        initViews()

        busResponse = intent.getSerializableExtra("bus_list") as SearchBusRes
        selectedDateStr1=intent.getStringExtra("selectedDateStr1")
        selectedTimeStr=intent.getStringExtra("selectedTimeStr")
        tv_route.text=intent.getStringExtra("start_city")+" To "+intent.getStringExtra("end_city")
        var busList=busResponse.busList

        setBusAdapter(busList as ArrayList<BusList>)
        hideClick()

        val back = findViewById(R.id.lin_lay_back) as LinearLayout
        back.setOnClickListener {

            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
    }

    fun initViews(){
        availiableBusActivity=this
        bus_list_view = findViewById(R.id.rec_view_bus_list)
        bus_list_view.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        bus_list_view.setLayoutManager(mLayoutManager)

        rel_lay_ratings=findViewById(R.id.rel_lay_ratings)
        rel_lay_amenities=findViewById(R.id.rel_lay_amenities)
        rel_lay_parent=findViewById(R.id.rel_lay_parent)
        lin_lay__amenities=findViewById(R.id.lin_lay__amenities)
        lin_lay_ratings=findViewById(R.id.lin_lay_ratings)
        //grid_view_amenity=findViewById(R.id.gv_amenities)

        rec_view__amenities = findViewById(R.id.rec_view__amenities)
        rec_view__amenities.setHasFixedSize(true)
        val mLayoutManager1 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec_view__amenities.setLayoutManager(mLayoutManager1)

        rec_view_ratings = findViewById(R.id.rec_view_ratings)
        rec_view_ratings.setHasFixedSize(true)
        val mLayoutManager2 = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec_view_ratings.setLayoutManager(mLayoutManager2)

        tv_route=findViewById(R.id.tv_route) as TextView
        iv_hide_ratings=findViewById(R.id.iv_hide_ratings)
        iv_hide_amenities=findViewById(R.id.iv_hide_amenities)

        val back = findViewById(R.id.lin_lay_back) as LinearLayout

        back.setOnClickListener {
            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
    }

    fun setBusAdapter(busList:ArrayList<BusList>){
        mAdapter = BusListAdapter(this!!, busList,this, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

             //   if(busList.get(position).availableSeats>0) {
                    if (!isDialogOpened) {
                        val busDetailReq = BusDetailReq(
                            busList.get(position).id.toString(),
                            busList.get(position).sourceId,
                            busList.get(position).destinationId,
                            selectedDateStr1,
                            selectedTimeStr
//                            getCurrentDate(),
//                            getCurrentTime()
                        )
                        getBusDetail(busDetailReq)
                  //  }
                }else{
                    Util.showErrorToast(this@AvailiableBusActivity,"All seats are booked.")
                }
            }
        })
        bus_list_view.adapter=mAdapter
    }

    fun getBusDetail( busDetailReq: BusDetailReq){
        showLoadingWithMessage( "Loading...")
        homeViewModel.getBusDetail(busDetailReq).observe(this, Observer {
            Util.showLog("Response", "" + it)
            hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    Log.e("dkd","dkd it-"+it.data!!)
                    Log.e("dkd","dkd it-"+it.data!!)
                    var error=it.data!!.error

                    if(error){
                        Util.showErrorToast(this,"Error occured!");
                    }else{
                        if((it.data!!.availableSeats)>0) {
                            val intent = Intent(this@AvailiableBusActivity, SeatsActivity::class.java)
                            intent.putExtra("bus_route", tv_route.text.toString().trim())
                            intent.putExtra("bus_info", it.data)
                            intent.putExtra("selectedDateStr1", selectedDateStr1)
                            startActivity(intent)
                            overridePendingTransition(R.anim.slide_in_up, R.anim.stay)

                        }else{
                            Util.showErrorToast(this, "All seats are booked")
                        }
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
        val formatter =  SimpleDateFormat("dd-MM-yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
        val date = Date(System.currentTimeMillis())
        Log.e("dkd","dkd  date-"+formatter.format(date))
        return formatter.format(date)
    }

    override fun onRatingClick(pos:Int) {
        if(busResponse.busList.get(pos).busRatings.size>0) {
            if (!isDialogOpened) {
                isDialogOpened = true
                ratings(pos)
            }
        }else{
            Util.showErrorToast(this,"No Rating available")
        }
    }

    override fun onAmenityClick(pos:Int) {
        if(busResponse.busList.get(pos).busAmenities.size>0) {
            if (!isDialogOpened) {
                isDialogOpened = true
                amenitiesGrid(pos)
            }
        }
    }


    fun hideClick(){
        iv_hide_ratings.setOnClickListener {

            isDialogOpened=false
            val height=rel_lay_parent.getHeight().toFloat()

            val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                height
            )
            animate.duration = 500
            animate.fillAfter = true
            rel_lay_ratings.startAnimation(animate)
            rel_lay_amenities.setVisibility(View.GONE)
            rel_lay_ratings.setVisibility(View.GONE)
            iv_hide_amenities.setVisibility(View.GONE)
            iv_hide_ratings.setVisibility(View.GONE)
            lin_lay__amenities.setVisibility(View.GONE)
            lin_lay_ratings.setVisibility(View.GONE)

        }

        iv_hide_amenities.setOnClickListener {

            isDialogOpened=false
            val height=rel_lay_parent.getHeight().toFloat()

            val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                height
            )
            animate.duration = 500
            animate.fillAfter = true
            rel_lay_amenities.startAnimation(animate)
            rel_lay_amenities.setVisibility(View.GONE)
            rel_lay_ratings.setVisibility(View.GONE)
            iv_hide_amenities.setVisibility(View.GONE)
            iv_hide_ratings.setVisibility(View.GONE)
            lin_lay__amenities.setVisibility(View.GONE)
            lin_lay_ratings.setVisibility(View.GONE)
        }
    }

    fun amenitiesGrid(pos:Int){
        rel_lay_ratings.visibility=View.GONE
        rel_lay_amenities.visibility=View.VISIBLE
        iv_hide_amenities.visibility=View.VISIBLE
        lin_lay__amenities.setVisibility(View.VISIBLE)

        val animate = TranslateAnimation(
            0f,
            0f,
            rel_lay_amenities.getHeight().toFloat(),
            0f
        )
        animate.duration = 500
        animate.fillAfter = true
        rel_lay_amenities.startAnimation(animate)
       // grid_view_amenity.setAdapter( AmenitiesGridImageAdapter(this));
        rec_view__amenities.setAdapter( AmenitiesAdapter(this,busResponse.busList.get(pos).busAmenities));
    }

    fun ratings(pos:Int){
        rel_lay_amenities.visibility=View.GONE
        rel_lay_ratings.setVisibility(View.VISIBLE);
        iv_hide_ratings.setVisibility(View.VISIBLE);
        lin_lay_ratings.setVisibility(View.VISIBLE)

        val animate = TranslateAnimation(
            0f,
            0f,
            rel_lay_ratings.getHeight().toFloat(),
            0f
        )
        animate.duration = 500
        animate.fillAfter = true
        rel_lay_ratings.startAnimation(animate)
        rec_view_ratings.setAdapter( RatingAdapter(this,busResponse.busList.get(pos).busRatings));

    }
}
