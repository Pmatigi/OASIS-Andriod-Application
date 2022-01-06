package com.oasis.com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.ui.models.booking.CurrentBooking
import com.oasis.ui.models.home.BusInfoModel
import com.oasis.ui.utils.Util.Util

class BookingListAdapterUpcoming(activity: Activity, bookingList: ArrayList<CurrentBooking>?,val itemClickListener:ItemClickListener) : RecyclerView.Adapter<BookingListAdapterUpcoming.MyVewHolder>() {
    private val context: Context
    var bookingList: ArrayList<CurrentBooking>?
    var clickListener: ItemClickListener? = null

    init {
        this.context = activity
        this.bookingList=bookingList;
        this.clickListener=itemClickListener;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingListAdapterUpcoming.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_bookings, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
                clickListener = itemClickListener
            holder.tv_date?.text= Util.getDateFromDateTime(bookingList?.get(position)?.bookingDateTime)
            holder.tv_day?.text= Util.getDayNameFromDateTime(bookingList?.get(position)?.bookingDateTime!!)
            holder.tv_month?.text= Util.getMonthNameYearFromDateTime(bookingList?.get(position)?.bookingDateTime!!)
            holder.tv_tour?.text= bookingList?.get(position)?.startingPoint!! +" - "+bookingList?.get(position)?.destinationPoint!!
            holder.tv_boarding_point?.text= "Boarding point : "+bookingList?.get(position)?.boardingPoint

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return bookingList!!.size;
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var tv_date: TextView
        var tv_day: TextView
        var tv_month: TextView
        var tv_tour: TextView
        var tv_bus_name: TextView
        var tv_boarding_point: TextView

        init {
            tv_date = itemView.findViewById(R.id.tv_date) as TextView
            tv_day = itemView.findViewById(R.id.tv_day) as TextView
            tv_month = itemView.findViewById(R.id.tv_month) as TextView
            tv_tour = itemView.findViewById(R.id.tv_tour) as TextView
            tv_bus_name = itemView.findViewById(R.id.tv_bus_name) as TextView
            tv_boarding_point = itemView.findViewById(R.id.tv_boarding_point) as TextView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            v?.let { clickListener?.onClick(it,position) }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
}