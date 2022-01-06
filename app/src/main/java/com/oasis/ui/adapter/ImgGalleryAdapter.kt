package com.oasis.com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oasis.R
import com.oasis.ui.adapter.ItemClickListener
import com.oasis.ui.models.home.BusImage
import com.squareup.picasso.Picasso

class ImgGalleryAdapter(activity: Activity, busImges:List<BusImage>, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ImgGalleryAdapter.MyVewHolder>() {
    private val context: Context
    var busImges:List<BusImage>?
    init {
        this.context = activity
        this.busImges=busImges;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgGalleryAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_img_gallery, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            //clickListener = itemClickListener
            Log.e("dkd","dkd imgpath-"+busImges?.get(position)?.path)
           // Glide.with(holder.iv_img_gallery).load(busImges?.get(position)?.path).into(holder.iv_img_gallery)
            Picasso.with(context).load(busImges?.get(position)?.path).into(holder.iv_img_gallery)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return busImges!!.size;
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var iv_img_gallery: ImageView
        init {
            iv_img_gallery = itemView.findViewById(R.id.iv_img_gallery) as ImageView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            ////Log.e("dkd","dkd MyVewHolder")

        }
    }
}