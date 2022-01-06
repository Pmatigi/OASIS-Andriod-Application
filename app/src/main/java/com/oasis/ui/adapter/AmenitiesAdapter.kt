package com.oasis.ui.adapter

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
import com.oasis.ui.listener.BusItemClickListener
import com.oasis.ui.models.home.BusAmenity
import com.oasis.ui.models.home.BusInfoModel

class AmenitiesAdapter(activity: Activity,busAmenity:List<BusAmenity>) : RecyclerView.Adapter<AmenitiesAdapter.MyVewHolder>() {
    private val context: Context
    lateinit var busAmenity:List<BusAmenity>
    var amenities = arrayOf(
        R.drawable.water__icon,
        R.drawable.curtain_icon,
        R.drawable.curtain_icon,
        R.drawable.electric_outlet_icon,
        R.drawable.tv_icon,
        R.drawable.wifi_icon,
        R.drawable.ac_icon,
        R.drawable.slippers_icon,
        R.drawable.towels_icon,
        R.drawable.snacks_icon
    )

//    var amenities_text = arrayOf(
//        "Water Bottol",
//        "Blankets",
//        "Pillow",
//        "Charging Point",
//        "Movie",
//        "Wifi",
//        "AC",
//        "Slippers",
//        "Towels",
//        "Snacks"
//    )

    var amenityMap = HashMap<String,Int>()


    init {
        this.context = activity
        this.busAmenity = busAmenity
        amenityMap.put("Water Bottol",R.drawable.water__icon)
        amenityMap.put("Blankets",R.drawable.curtain_icon)
        amenityMap.put("Pillow",R.drawable.curtain_icon)
        amenityMap.put("Charging Point",R.drawable.electric_outlet_icon)
        amenityMap.put("Movie",R.drawable.tv_icon)
        amenityMap.put("Wifi",R.drawable.wifi_icon)
        amenityMap.put("AC",R.drawable.ac_icon)
        amenityMap.put("Slippers",R.drawable.slippers_icon)
        amenityMap.put("Towels",R.drawable.towels_icon)
        amenityMap.put("Snacks",R.drawable.snacks_icon)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AmenitiesAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_amenities, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            holder.iv_amenities.setImageResource(amenityMap.get(busAmenity.get(position).name)!!)
            holder.tv_amenities.setText(busAmenity.get(position).name)
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return busAmenity.size
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var iv_amenities: ImageView
        var tv_amenities: TextView
        init {
            iv_amenities = itemView.findViewById(R.id.iv_amenities) as ImageView
            tv_amenities = itemView.findViewById(R.id.tv_amenities) as TextView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {

        }
    }
}