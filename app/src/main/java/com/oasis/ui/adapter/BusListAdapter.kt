package com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.PassengerListAdapter
import com.oasis.ui.listener.BusItemClickListener
import com.oasis.ui.models.home.BusInfoModel
import com.oasis.ui.models.home.BusList
import com.squareup.picasso.Picasso

class BusListAdapter(activity: Activity, busList: ArrayList<BusList>?, val busItemClickListener1: BusItemClickListener, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<BusListAdapter.MyVewHolder>() {
    private val context: Context
    var busList: ArrayList<BusList>?
    companion object {
        var clickListener: ItemClickListener? = null
        var busItemClickListener: BusItemClickListener? = null
    }

    init {
        this.context = activity
        this.busList=busList;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BusListAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_bus, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            clickListener = itemClickListener
            busItemClickListener =busItemClickListener1
            if(busList?.get(position)?.busImages!!.size > 0) {
                Picasso.with(context).load(busList?.get(position)?.busImages?.get(0)?.path)
                    .into(holder.iv_bus_logo)
            }
            holder.tv_price.text="Rs: "+busList?.get(position)?.totalFare
            holder.tv_bus_no.text="Bus no: "+busList?.get(position)?.busNumber
            holder.tv_rating.text=((busList?.get(position)?.busAverageRatings.toString())+"/5")
            Log.e("dkd","dkd isAC-"+busList?.get(position)?.isAc.equals("1") )
            if(busList?.get(position)?.isAc!!.trim().equals("1")) {
                holder.tv_bus_name.text = busList?.get(position)?.busName + " (AC)"
            }else{
                holder.tv_bus_name.text = busList?.get(position)?.busName + " (Non AC)"
            }

            if((busList?.get(position)?.availableSeats!!) > 0) {
                holder.tv_bus_time.text = busList?.get(position)?.startTime + " - " + busList?.get(position)?.reachedTime + " | Availiable Seats:  " + busList?.get(position
                    )?.availableSeats
            }else{
                holder.tv_bus_time.text = busList?.get(position)?.startTime + " - " + busList?.get(position)?.reachedTime + " | Availiable Seats:   0"
            }

//            holder.rel_lay_rating.setOnClickListener {
//                busItemClickListener!!.onRatingClick(position)
//            }
            holder.lin_lay_rating1.setOnClickListener {
                busItemClickListener!!.onRatingClick(position)
            }

            holder.lin_lay_amenity.setOnClickListener {
                busItemClickListener!!.onAmenityClick(position)

            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return busList!!.size;
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener{
        var tv_bus_name: TextView
        var tv_bus_no: TextView
        var tv_rating: TextView
        var tv_bus_time: TextView
        var iv_amenities: ImageView
        var tv_price: TextView
        var iv_bus_logo: ImageView
       // var rel_lay_rating: RelativeLayout
        var lin_lay_rating1: LinearLayout
        var lin_lay_amenity: LinearLayout

        init {
            tv_bus_no = itemView.findViewById(R.id.tv_bus_no) as TextView
            tv_bus_name = itemView.findViewById(R.id.tv_bus_name) as TextView
            tv_rating = itemView.findViewById(R.id.tv_rating) as TextView
            iv_amenities = itemView.findViewById(R.id.iv_amenities)
            tv_bus_time = itemView.findViewById(R.id.tv_bus_time)
            tv_price = itemView.findViewById(R.id.tv_price)
         //   rel_lay_rating = itemView.findViewById(R.id.rel_lay_rating)
            lin_lay_rating1 = itemView.findViewById(R.id.lin_lay_rating1)
            lin_lay_amenity = itemView.findViewById(R.id.lin_lay_amenity)
            iv_bus_logo = itemView.findViewById(R.id.iv_bus_logo)
        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
        ////Log.e("dkd","dkd MyVewHolder")
            if (clickListener != null) {
              //  if(busList?.get(adapterPosition)?.availableSeats)
                clickListener!!.onClick(v!!, getAdapterPosition());
            }
        }
    }
}

interface ItemClickListener  {
    fun onClick(view: View, position: Int)
}