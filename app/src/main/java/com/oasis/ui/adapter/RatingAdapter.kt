package com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
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
import com.oasis.ui.listener.BusItemClickListener
import com.oasis.ui.models.home.BusInfoModel
import com.oasis.ui.models.home.BusRating

class RatingAdapter(activity: Activity,busRating:List<BusRating>) : RecyclerView.Adapter<RatingAdapter.MyVewHolder>() {
    private val context: Context
    lateinit var busRating:List<BusRating>
    var rating_comments = arrayOf(
        "It is good I will give 4.3",
        "It is ok I will give 4",
        "It is good I will give 4.3",
        "It is ok I will give 4",
        "It is good I will give 4.3",
        "It is ok I will give 4",
        "It is good I will give 4.3",
        "It is ok I will give 4",
        "It is good I will give 4.3",
        "It is ok I will give 4"

        )
    init {
        this.context = activity
        this.busRating = busRating
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RatingAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_rating, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            holder.tv_amenities.setText(busRating.get(position).review)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return busRating.size
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var tv_amenities: TextView
        init {
            tv_amenities = itemView.findViewById(R.id.tv_amenities) as TextView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {

        }
    }
}