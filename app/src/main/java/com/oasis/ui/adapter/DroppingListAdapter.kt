package com.oasis.com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R


class DroppingListAdapter(activity: Activity,selectedDroppingPos:Int, boardinglist: ArrayList<String>?, busType:String, tour_time:String, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<DroppingListAdapter.MyVewHolder>() {
    private val context: Context
    var boardinglist1: ArrayList<String>?
    var busType1: String?
    var tour_time1: String?
    var clickListener: ItemClickListener? = null
    var selectedPosition = -1

    init {
        this.context = activity
        this.boardinglist1=boardinglist;
        this.busType1=busType;
        this.tour_time1=tour_time;
        this.clickListener=itemClickListener;
        this.selectedPosition=selectedDroppingPos;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DroppingListAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_boarding, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            clickListener = itemClickListener
             holder.tv_boarding_point_name?.setText(boardinglist1!!.get(position))
             holder.tv_bus_type?.setText(busType1)
             holder.tv_bus_time?.setText(tour_time1)
            if(selectedPosition==position) {
                holder.iv_tick.visibility=View.VISIBLE
            }else{
                holder.iv_tick.visibility=View.GONE
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return boardinglist1!!.size;
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var tv_boarding_point_name: TextView
        var tv_bus_type: TextView
        var tv_bus_time: TextView
        var iv_tick: ImageView

        init {
            tv_boarding_point_name = itemView.findViewById(R.id.tv_boarding_point_name) as TextView
            tv_bus_type = itemView.findViewById(R.id.tv_bus_type) as TextView
            tv_bus_time = itemView.findViewById(R.id.tv_bus_time) as TextView
            iv_tick = itemView.findViewById(R.id.iv_tick) as ImageView
        }
        fun bind() {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            v?.let {
                clickListener?.onClick(it,position)
                selectedPosition=position
                notifyDataSetChanged();
            }
        }
    }

    interface ItemClickListener {
        fun onClick(view: View, position: Int)
    }
}
