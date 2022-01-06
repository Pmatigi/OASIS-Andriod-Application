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
import com.oasis.ui.listener.PassengerDelListener
import com.oasis.ui.models.booking.Passenger


class PassengerListAdapter(activity: Activity, passengerList: ArrayList<Passenger>?, val passengerDelListener: PassengerDelListener, val itemClickListener: PassengerClickListener) : RecyclerView.Adapter<PassengerListAdapter.MyVewHolder>() {
    private val context: Context
    var passengerList: ArrayList<Passenger>?

    companion object {
        var clickListener: PassengerClickListener? = null
        var passengerDelListener1: PassengerDelListener? = null

    }

    init {
        this.context = activity
        this.passengerList=passengerList;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PassengerListAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_passenger, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            passengerDelListener1 =passengerDelListener

            clickListener = itemClickListener
             holder.tv_name?.setText(passengerList!!.get(position).name+" ("+passengerList!!.get(position).gender+")")
             holder.tv_phone?.setText(passengerList!!.get(position).phone)
             holder.tv_seat?.setText("Seat No : "+passengerList!!.get(position).seat)
             holder.iv_del?.setOnClickListener {
                 passengerDelListener1!!.onDeleteClick(position)

             }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return passengerList!!.size;
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var tv_name: TextView
        var tv_phone: TextView
        var tv_seat: TextView
        var iv_del: ImageView
        init {
            tv_name = itemView.findViewById(R.id.tv_name) as TextView
            tv_seat = itemView.findViewById(R.id.tv_seat) as TextView
            tv_phone = itemView.findViewById(R.id.tv_phone) as TextView
            iv_del = itemView.findViewById(R.id.iv_delete) as ImageView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            v?.let { clickListener?.onClick(it,position) }
        }
    }
}

interface PassengerClickListener {
    fun onClick(view: View, position: Int)
}

