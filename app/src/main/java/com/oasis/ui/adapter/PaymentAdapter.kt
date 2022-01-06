package com.oasis.com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oasis.R
import com.oasis.com.oasis.ui.adapter.ImgGalleryAdapterNew
import com.oasis.ui.adapter.BusListAdapter
import com.oasis.ui.adapter.ItemClickListener
import com.oasis.ui.listener.BusItemClickListener
import com.oasis.ui.models.home.BusAmenity
import com.oasis.ui.models.home.BusInfoModel

class PaymentAdapter(activity: Activity, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<PaymentAdapter.MyVewHolder>() {
    private val context: Context
    var payments = arrayOf(
        R.drawable.debit,
        R.drawable.debit,
        R.drawable.orange_money_logo,
        R.drawable.orange_money_logo
    )

    var paymentsName = arrayOf(
        "Debit card",
        "Credit card",
        "Orange Money",
        "MTM Money"

        )

    var amenityMap = HashMap<String,Int>()


    init {
        this.context = activity
        amenityMap.put("Debit card",R.drawable.debit)
        amenityMap.put("Credit card",R.drawable.credit)
        amenityMap.put("Orange Money",R.drawable.orange_money_logo)
        amenityMap.put("MTM Money",R.drawable.orange_money_logo)

    }

    companion object {
        var clickListener: ItemClickListener? = null
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PaymentAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_pay, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            clickListener = itemClickListener
            holder.iv_payments.setImageResource(payments.get(position))
            holder.tv_payments.setText(paymentsName.get(position))
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return payments.size
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var iv_payments: ImageView
        var tv_payments: TextView
        init {
            iv_payments = itemView.findViewById(R.id.iv_payments) as ImageView
            tv_payments = itemView.findViewById(R.id.tv_payments) as TextView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            if (clickListener != null) {
               clickListener!!.onClick(v!!, getAdapterPosition());
            }
        }
    }

}
