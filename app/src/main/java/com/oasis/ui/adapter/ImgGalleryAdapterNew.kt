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

class ImgGalleryAdapterNew(activity: Activity, busImges:ArrayList<String>,val itemClickListener: ItemClickListener) : RecyclerView.Adapter<ImgGalleryAdapterNew.MyVewHolder>() {
    private val context: Context
    var busImges:ArrayList<String>
    init {
        this.context = activity
        this.busImges=busImges;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImgGalleryAdapterNew.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_img_gallery, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            //clickListener = itemClickListener
            Log.e("dkd","dkd imgpath-"+busImges?.get(position))
            Glide.with(holder.iv_img_gallery).load(busImges?.get(position)).into(holder.iv_img_gallery)

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