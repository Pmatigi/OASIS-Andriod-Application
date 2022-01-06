package com.oasis.com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.ui.models.home.City


class CityListAdapter(activity: Activity, citylist: ArrayList<City>?, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<CityListAdapter.MyVewHolder>(), Filterable {
    private val context: Context
    var citylist: ArrayList<City>?
    var filtereCitylist: ArrayList<City>? = ArrayList()

    companion object {
        var clickListener: ItemClickListener? = null
    }

    init {
        this.context = activity
        this.citylist=citylist;
        filtereCitylist=citylist
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityListAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_city, parent, false)
        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            clickListener = itemClickListener
             holder.tv_city?.setText(filtereCitylist!!.get(position).name)

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {
        return filtereCitylist!!.size;
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): Filter.FilterResults {
                val charString = charSequence.toString()
                if (charString.isEmpty()) {
                    filtereCitylist = citylist
                } else {
                    val filteredList = ArrayList<City>()
                    for (row in citylist!!) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (row.name.toLowerCase().contains(charString.toLowerCase()) || row.name.contains(charSequence)
                        ) {
                            filteredList.add(row)
                        }
                    }

                    filtereCitylist = filteredList
                }

                val filterResults = Filter.FilterResults()
                filterResults.values = filtereCitylist
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: Filter.FilterResults
            ) {
                filtereCitylist = filterResults.values as ArrayList<City>

                // refresh the list with filtered data
                notifyDataSetChanged()
            }
        }
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{
        var tv_city: TextView
        init {
            tv_city = itemView.findViewById(R.id.tv_city_name) as TextView

        }
        fun bind() {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            v?.let {
                clickListener?.onClick(it,position,filtereCitylist!!.get(position))
            }
        }
    }
}

    interface ItemClickListener {
        fun onClick(view: View, position: Int,city:City)
    }