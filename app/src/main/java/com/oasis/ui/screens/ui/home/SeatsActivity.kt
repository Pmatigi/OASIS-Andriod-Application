package com.oasis.ui.screens.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.ui.listener.SeatClickListener
import com.oasis.ui.adapter.ItemClickListener
import com.oasis.ui.adapter.LowerSeaterSeatListAdapter
import android.text.method.ScrollingMovementMethod
import android.view.animation.TranslateAnimation
import android.widget.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.oasis.com.oasis.ui.adapter.BoardingListAdapter
import com.oasis.com.oasis.ui.adapter.DroppingListAdapter
import com.oasis.com.oasis.ui.adapter.ImgGalleryAdapter
import com.oasis.ui.BaseActivity
import com.oasis.ui.adapter.LowerSleeperSeatListAdapter
import com.oasis.ui.adapter.UpperSleeperSeatListAdapter
import com.oasis.ui.models.home.*
import com.oasis.ui.utils.Util.Util
import kotlinx.android.synthetic.main.activity_seats.*


class SeatsActivity : BaseActivity(), SeatClickListener {

    lateinit var rec_view_normal_seat_list: RecyclerView
    lateinit var rec_view_img_gallery: RecyclerView
    lateinit var rec_view_boarding_point: RecyclerView
    lateinit var mAdapterLower: LowerSeaterSeatListAdapter
    lateinit var mAdapterLowerSleeper: LowerSleeperSeatListAdapter
    lateinit var mAdapterUpper: UpperSleeperSeatListAdapter
    lateinit var boardingAdapter: BoardingListAdapter
    lateinit var droppingAdapter: DroppingListAdapter
    lateinit var tv_bus_name:TextView
    lateinit var tv_booking_date:TextView
    lateinit var tv_seats:TextView
    lateinit var bus_route:TextView
    lateinit var tv_price_complete:TextView
    lateinit var tv_bus_type:TextView
    lateinit var tv_view_photo:TextView
    lateinit var tv_boarding_point:TextView
    lateinit var tv_dropping_point:TextView
    lateinit var tv_select:TextView
    lateinit var iv_hide_img_gallery:ImageView
    lateinit var iv_hide_img_boarding:ImageView
    lateinit var btnProceed:Button
    lateinit var btn_procede_assenger_detail: Button
    lateinit var rel_lay_boarding_dropping:RelativeLayout
    lateinit var lin_lay__img_gallery:LinearLayout
    lateinit var back:LinearLayout
    lateinit var lin_lay_bus_type:LinearLayout
    lateinit var tv_lower_seat:TextView
    lateinit var tv_upper_seat:TextView
    lateinit var lin_lay_seat_type:LinearLayout
    lateinit var tv_seating_seat:TextView
    lateinit var tv_sleeper_seat:TextView
    lateinit var rel_lay_seat_avail_upper:RelativeLayout
    lateinit var rel_lay_seat_avail_lower:RelativeLayout

    var selected_bus_type=1
    var selected_bus_type_lower=1
    var selected_bus_type_upper=2

    var bus_type=1
    var seat_type=1
    var selectedBoardingPos=-1
    var selectedDroppingPos=-1
    var bus_info= BusDetailRes()
    var boardingPointStr=""
    var droppingPointStr=""
    var sittingSeatsLower=ArrayList<LowerSeat>()
    var sleeperSeatsLower=ArrayList<LowerSeat>()
    var sleeperSeatsUpper=ArrayList<UpperSeat>()

    var total_price=0

    companion object {
        val selectedPosListSeater = ArrayList<Int>()
        val selectedPosListSleeper = ArrayList<Int>()
        val selectedPosListUpperSleeper = ArrayList<Int>()
        val selectedSeatListSeater = ArrayList<String>()
        val selectedSeatListSleeper = ArrayList<String>()
        val selectedSeatListUpperSleeper = ArrayList<String>()
        var seatsActivity= SeatsActivity()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seats)
        initViews()
        clickView()
        setData()
    }

    fun initViews(){
        seatsActivity=this
        rec_view_normal_seat_list = findViewById(R.id.rec_view_normal_seat_list)
        rec_view_boarding_point = findViewById(R.id.rec_view_boarding_point)
        rec_view_img_gallery = findViewById(R.id.rec_view_img_gallery)
        iv_hide_img_gallery = findViewById(R.id.iv_hide_img_gallery)
        tv_bus_name = findViewById(R.id.tv_bus_name)
        tv_lower_seat = findViewById(R.id.tv_lower_seat)
        tv_upper_seat = findViewById(R.id.tv_upper_seat)
        tv_seating_seat = findViewById(R.id.tv_seating_seat)
        tv_sleeper_seat = findViewById(R.id.tv_sleeper_seat)
        tv_seats = findViewById(R.id.tv_seats)
        tv_price_complete = findViewById(R.id.tv_price_complete)
        tv_bus_type = findViewById(R.id.tv_bus_type)
        btnProceed = findViewById(R.id.btnProceed)
        btn_procede_assenger_detail = findViewById(R.id.btn_procede_assenger_detail)
        rel_lay_boarding_dropping = findViewById(R.id.rel_lay_boarding_dropping)
        bus_route = findViewById(R.id.bus_route)
        tv_boarding_point = findViewById(R.id.tv_boarding_point)
        tv_dropping_point = findViewById(R.id.tv_dropping_point)
        tv_select = findViewById(R.id.tv_select)

        tv_booking_date = findViewById(R.id.tv_booking_date)
        rel_lay_seat_avail_upper = findViewById(R.id.rel_lay_seat_avail_upper)
        rel_lay_seat_avail_lower = findViewById(R.id.rel_lay_seat_avail_lower)
        tv_view_photo = findViewById(R.id.tv_view_photo)
        lin_lay__img_gallery=findViewById(R.id.lin_lay__img_gallery)

        iv_hide_img_boarding=findViewById(R.id.iv_hide_img_boarding) as ImageView
        back = findViewById(R.id.lin_lay_back) as LinearLayout
        lin_lay_bus_type = findViewById(R.id.lin_lay_bus_type) as LinearLayout
        lin_lay_seat_type = findViewById(R.id.lin_lay_seat_type) as LinearLayout
        tv_seats.movementMethod=ScrollingMovementMethod()
    }

    private fun setData() {
        selectedPosListSeater.clear()
        selectedSeatListSeater.clear()
        selectedPosListSleeper.clear()
        selectedSeatListSleeper.clear()
        selectedPosListUpperSleeper.clear()

        tv_bus_name.text=bus_info.busName
        bus_info=intent.getSerializableExtra("bus_info") as BusDetailRes

        tv_booking_date.text="Booking date: "+intent.getStringExtra("selectedDateStr1")
        bus_route.text=intent.getStringExtra("bus_route")
        bus_type=Integer.parseInt(bus_info.busTypeId)
        seat_type=Integer.parseInt(bus_info.busSeatType)
        tv_price.text="Rs: "+bus_info.totalFare
        tv_bus_type.text=bus_info.busTypeTitle

        if(bus_type==1) {
            lin_lay_bus_type.visibility=View.GONE
            parseLowerSitterSleeper()
        }else if(bus_type==2){
            parseLowerSitterSleeper()
            parseUpperSleeper()
            lin_lay_bus_type.visibility=View.VISIBLE
        }
        setSeatTypeVisibility()
    }
    
    fun setSeatTypeVisibility(){
        if(seat_type==1) {
            seaterClicked()
            lin_lay_seat_type.visibility=View.GONE
            lowerSeatSitter()
        }else if(seat_type==2){
            sleeperClicked()
            lin_lay_seat_type.visibility=View.GONE
            lowerSeatSleeper()
        }else if(seat_type==3){
            seaterClicked()
            lin_lay_seat_type.visibility=View.VISIBLE
            lowerSeatSitter()
        }
    }

    private fun clickView() {
        iv_hide_img_boarding.setOnClickListener {
            rel_lay_boarding_dropping.visibility=View.GONE

        }

        tv_view_photo.setOnClickListener {
            imageGallery(bus_info.busImages)
        }

        iv_hide_img_gallery.setOnClickListener {
            val height=rel_lay_image_gallery.getHeight().toFloat()

            val animate = TranslateAnimation(
                0f,
                0f,
                0f,
                height
            )
            animate.duration = 500
            animate.fillAfter = true
            rel_lay_image_gallery.startAnimation(animate)
            rel_lay_image_gallery.setVisibility(View.GONE)
            lin_lay__img_gallery.setVisibility(View.GONE)

        }

        btnProceed.setOnClickListener {
            if(selectedSeatListSeater.size>0 || selectedSeatListSleeper.size>0|| selectedSeatListUpperSleeper.size>0) {
                rel_lay_boarding_dropping.setVisibility(View.VISIBLE);
                boardingAdapter = BoardingListAdapter(this!!,selectedBoardingPos,
                    bus_info.boardingPoint as ArrayList<String>?,bus_info.busTypeTitle,bus_info.startTime+" - "+bus_info.reachedTime, object : BoardingListAdapter.ItemClickListener {

                        override fun onClick(view: View, position: Int) {
                            boardingPointStr=bus_info.boardingPoint.get(position)
                            selectedBoardingPos=position
                        }
                    })
                rec_view_boarding_point.adapter=boardingAdapter
            }else{
                Util.showErrorToast(this,"Please select seat")
            }
        }

        btn_procede_assenger_detail.setOnClickListener {
            if(droppingPointStr.trim().equals("") && boardingPointStr.trim().equals("")) {
                Util.showErrorToast(this,"Please select boarding/dropping point")
            }else if(boardingPointStr.trim().equals("")) {
                Util.showErrorToast(this,"Please select boarding point")
            }else if(droppingPointStr.trim().equals("")) {
                Util.showErrorToast(this,"Please select dropping point")
            }else{
                val intent = Intent(this, PassengerDetailsActivity::class.java)
                val total_seats=(selectedSeatListSeater.size+ selectedSeatListSleeper.size+ selectedSeatListUpperSleeper.size)
                intent.putExtra("passenger_count", total_seats)
                intent.putExtra("selectedSeatListSeater", selectedSeatListSeater)
                intent.putExtra("selectedSeatListSleeper", selectedSeatListSleeper)
                intent.putExtra("selectedSeatListUpperSleeper", selectedSeatListUpperSleeper)
                intent.putExtra("bus_info", bus_info)
                intent.putExtra("selectedPosListSeater", selectedPosListSeater)
                intent.putExtra("selectedPosListSleeper", selectedPosListSleeper)
                intent.putExtra("selectedPosListUpperSleeper", selectedPosListUpperSleeper)
                intent.putExtra("sittingSeatsLower", sittingSeatsLower)
                intent.putExtra("sleeperSeatsLower", sleeperSeatsLower)
                intent.putExtra("sleeperSeatsUpper", sleeperSeatsUpper)
                intent.putExtra("total_price", total_price)
                startActivity(intent)
                overridePendingTransition(R.anim.slide_in_up, R.anim.stay)
                rel_lay_boarding_dropping.visibility = View.GONE
            }
        }

        tv_lower_seat.setOnClickListener {
            setSeatTypeVisibility()
            selected_bus_type=selected_bus_type_lower
            tv_lower_seat.setBackgroundDrawable(resources.getDrawable(com.oasis.R.drawable.less_rounded_blue))
            tv_lower_seat.setTextColor(resources.getColor(R.color.white))
            tv_upper_seat.setBackgroundColor(0)
            tv_upper_seat.setTextColor(resources.getColor(R.color.black_colour_ios))
        }

        tv_upper_seat.setOnClickListener {
            rel_lay_seat_avail_lower.visibility = View.GONE
            rel_lay_seat_avail_upper.visibility = View.VISIBLE
            lin_lay_seat_type.visibility=View.GONE
            selected_bus_type=selected_bus_type_upper
            tv_upper_seat.setBackgroundDrawable(resources.getDrawable(R.drawable.less_rounded_blue_right))
            tv_upper_seat.setTextColor(resources.getColor(R.color.white))
            tv_lower_seat.setBackgroundColor(0)
            tv_lower_seat.setTextColor(resources.getColor(R.color.black_colour_ios))
            upperSeatSleeper()
        }

        tv_seating_seat.setOnClickListener {
            seaterClicked()
        }

        tv_sleeper_seat.setOnClickListener {
           sleeperClicked()
        }

        back.setOnClickListener {
            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
    }

    fun seaterClicked(){
        if(Integer.parseInt(bus_info.totalSleeper.trim())>0) {
            tv_seating_seat.setBackgroundDrawable(resources.getDrawable(com.oasis.R.drawable.less_rounded_blue))
            tv_seating_seat.setTextColor(resources.getColor(R.color.white))
            tv_sleeper_seat.setBackgroundColor(0)
            tv_sleeper_seat.setTextColor(resources.getColor(R.color.black_colour_ios))
            rel_lay_seat_avail_upper.visibility = View.GONE
            rel_lay_seat_avail_lower.visibility = View.VISIBLE
            lowerSeatSitter()
        }else{
            Util.showErrorToast(this,"No Sitting/Lower Seats!")
        }
    }

    fun sleeperClicked(){
        if(Integer.parseInt(bus_info.totalSleeper.trim())>0) {
            tv_sleeper_seat.setBackgroundDrawable(resources.getDrawable(R.drawable.less_rounded_blue_right))
            tv_sleeper_seat.setTextColor(resources.getColor(R.color.white))
            tv_seating_seat.setBackgroundColor(0)
            tv_seating_seat.setTextColor(resources.getColor(R.color.black_colour_ios))
            rel_lay_seat_avail_lower.visibility = View.GONE
            rel_lay_seat_avail_upper.visibility = View.VISIBLE
            lowerSeatSleeper()
        }else{
            Util.showErrorToast(this,"No Sleeper/Upper Seats!")
        }
    }

    fun lowerSeatSitter(){
        rec_view_normal_seat_list.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        rec_view_normal_seat_list.setLayoutManager(mLayoutManager)
        mAdapterLower = LowerSeaterSeatListAdapter(this!!,sittingSeatsLower.size,sittingSeatsLower,this, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }
        })
        rec_view_normal_seat_list.adapter=mAdapterLower
        initSeats()
    }

    fun lowerSeatSleeper(){
        rec_view_normal_seat_list.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        rec_view_normal_seat_list.setLayoutManager(mLayoutManager)
        mAdapterLowerSleeper = LowerSleeperSeatListAdapter(this!!,sleeperSeatsLower.size,sleeperSeatsLower,this, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }
        })
        rec_view_normal_seat_list.adapter=mAdapterLowerSleeper
        initSeats()
    }

    fun upperSeatSleeper(){
        rec_view_normal_seat_list.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        rec_view_normal_seat_list.setLayoutManager(mLayoutManager)

        mAdapterUpper = UpperSleeperSeatListAdapter(this!!,bus_info.upperSeats.size,bus_info.upperSeats,this, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }
        })
        rec_view_normal_seat_list.adapter=mAdapterUpper
        initSeats()
    }

    fun initSeats(){
        val lin_lay_droping:LinearLayout=findViewById(R.id.lin_lay_droping)
        val lin_lay_boarding:LinearLayout=findViewById(R.id.lin_lay_boarding)
        val tv_dropping_point:TextView=findViewById(R.id.tv_dropping_point)
        val tv_boarding_point:TextView=findViewById(R.id.tv_boarding_point)
        val view_blue_dropping:View=findViewById(R.id.view_blue_dropping)
        val view_grey_dropping:View=findViewById(R.id.view_grey_dropping)
        val view_blue_board:View=findViewById(R.id.view_blue_board)
        val view_grey_board:View=findViewById(R.id.view_grey_board)

        rec_view_boarding_point.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        rec_view_boarding_point.setLayoutManager(mLayoutManager)
        rec_view_boarding_point.addItemDecoration( DividerItemDecoration(rec_view_boarding_point.getContext(), DividerItemDecoration.VERTICAL));

        tv_boarding_point.setOnClickListener{
            boardingAdapter = BoardingListAdapter(this!!,selectedBoardingPos,
                bus_info.boardingPoint as ArrayList<String>?,bus_info.busTypeTitle,bus_info.startTime+" - "+bus_info.reachedTime, object : BoardingListAdapter.ItemClickListener {

                    override fun onClick(view: View, position: Int) {
                        boardingPointStr=bus_info.boardingPoint.get(position)
                        selectedBoardingPos=position
                    }
                })

            rec_view_boarding_point.adapter=boardingAdapter

            tv_boarding_point.setTextColor(resources.getColor(R.color.blue))
            tv_dropping_point.setTextColor(resources.getColor(R.color.black_colour_ios))
            view_blue_dropping.visibility=View.GONE
            view_grey_dropping.visibility=View.VISIBLE
            view_blue_board.visibility=View.VISIBLE
            view_grey_board.visibility=View.GONE
            tv_select.text="Need help selecting a boarding point"
        }

        tv_dropping_point.setOnClickListener{
            droppingAdapter = DroppingListAdapter(this!!,selectedDroppingPos,
                bus_info.dropPoint as ArrayList<String>?,bus_info.busTypeTitle,bus_info.startTime+" - "+bus_info.reachedTime, object : DroppingListAdapter.ItemClickListener {

                    override fun onClick(view: View, position: Int) {
                        droppingPointStr=bus_info.dropPoint.get(position)
                        selectedDroppingPos=position
                    }
                })

            rec_view_boarding_point.adapter=droppingAdapter

            tv_dropping_point.setTextColor(resources.getColor(R.color.blue))
            tv_boarding_point.setTextColor(resources.getColor(R.color.black_colour_ios))
            view_blue_dropping.visibility=View.VISIBLE
            view_grey_dropping.visibility=View.GONE
            view_blue_board.visibility=View.GONE
            view_grey_board.visibility=View.VISIBLE
            tv_select.text="Need help selecting a dropping point"

        }
    }

    fun imageGallery(busImges:List<BusImage>){
        rel_lay_image_gallery.setVisibility(View.VISIBLE);
        lin_lay__img_gallery.setVisibility(View.VISIBLE)

        val animate = TranslateAnimation(
            0f,
            0f,
            rel_lay_image_gallery.getHeight().toFloat(),
            0f
        )
        animate.duration = 500
        animate.fillAfter = true
        rel_lay_image_gallery.startAnimation(animate)

        rec_view_img_gallery.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rec_view_img_gallery.setLayoutManager(mLayoutManager)

        val imgAdapter = ImgGalleryAdapter(this!!, busImges, object : ItemClickListener {
            override fun onClick(view: View, position: Int) {

            }
        })
        rec_view_img_gallery.adapter=imgAdapter
    }

    override fun onClick() {
        var seatName="Selected Seats : "
        for(item in selectedPosListSeater){
            if(seatName.equals("Selected Seats : ")) {
                seatName = seatName + sittingSeatsLower.get(item-1).seatNo
            }else{
                seatName = seatName + ","+sittingSeatsLower.get(item-1).seatNo
            }
        }

        for(item in selectedPosListSleeper){
            if(seatName.equals("Selected Seats : ")) {
                seatName = seatName + sleeperSeatsLower.get(item-1).seatNo
            }else{
                seatName = seatName + ","+sleeperSeatsLower.get(item-1).seatNo
            }
        }

        for(item in selectedPosListUpperSleeper){
            if(seatName.equals("Selected Seats : ")) {
                seatName = seatName + sleeperSeatsUpper.get(item-1).seatNo
            }else{
                seatName = seatName + ","+sleeperSeatsUpper.get(item-1).seatNo
            }
        }
        tv_seats.text=seatName
        total_price=((selectedPosListSeater.size+ selectedPosListSleeper.size+ selectedPosListUpperSleeper.size)*(Integer.parseInt(bus_info.totalFare) ))
      //  tv_price_complete.text="(X2: Rs "+ (((selectedPosListSeater.size+ selectedPosListSleeper.size+ selectedPosListUpperSleeper.size)*(Integer.parseInt(bus_info.totalFare) ))).toString()+")"
        tv_price_complete.text="(X2: Rs "+ (total_price).toString()+")"
    }

    fun parseLowerSitterSleeper(){
        for(item in bus_info.lowerSeats){
           if(item.seatType.toString().trim().equals("sleeper")) {
                  sleeperSeatsLower.add(item)
                }else{
                  sittingSeatsLower.add(item)
                }
             }
    }

    fun parseUpperSleeper(){
        for(item in bus_info.upperSeats){
                sleeperSeatsUpper.add(item)
        }
    }
}
