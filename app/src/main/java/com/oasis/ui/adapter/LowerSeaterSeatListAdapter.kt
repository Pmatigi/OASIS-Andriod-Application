package com.oasis.ui.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.oasis.R
import com.oasis.ui.listener.SeatClickListener
import com.oasis.ui.models.home.LowerSeat
import com.oasis.ui.screens.ui.home.SeatsActivity
import com.oasis.ui.utils.Util.Util

class LowerSeaterSeatListAdapter(activity: Activity, seatsCount:Int, busSeats:List<LowerSeat>, val seatClickListener: SeatClickListener, val itemClickListener: ItemClickListener) : RecyclerView.Adapter<LowerSeaterSeatListAdapter.MyVewHolder>() {
    private val context: Context
    var busSeats:List<LowerSeat>?
    var seatsCount=0

    companion object {
        var clickListener: ItemClickListener? = null
        var seat1ClickListener: SeatClickListener? = null
    }

    init {
        this.context = activity
        this.busSeats=busSeats;
        this.seatsCount=seatsCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowerSeaterSeatListAdapter.MyVewHolder {
        val faqItem = LayoutInflater.from(context).inflate(R.layout.list_item_seat_lower, parent, false)

        return MyVewHolder(faqItem)
    }

    override fun onBindViewHolder(holder: MyVewHolder, position: Int) {
        (holder as MyVewHolder).bind()
        try {
            clickListener = itemClickListener
            seat1ClickListener=seatClickListener

                holder.rel_layout_lower.visibility=View.VISIBLE

                if(((4*position+1)-1)>=(busSeats!!.size)) {

                    holder.iv_lower_l1.visibility=View.GONE
                }else {
                    holder.iv_lower_l1.visibility=View.VISIBLE
                    if((busSeats?.size!! > ((4*position+1)-1)) && busSeats?.get((4*position+1)-1)?.isBooked==1) {
                        holder.iv_lower_l1.setImageResource(R.drawable.booked)
                    } else if (SeatsActivity.selectedPosListSeater.contains(4 * position + 1)) {
                        holder.iv_lower_l1.setImageResource(R.drawable.booked)
                    }else{
                        holder.iv_lower_l1.setImageResource(R.drawable.available)
                    }
                }

                if(((4*position+2)-1)>=(busSeats!!.size)) {
                    holder.iv_lower_l2.visibility=View.INVISIBLE
                }else {
                    holder.iv_lower_l2.visibility=View.VISIBLE
                    if((busSeats?.size!! > ((4*position+2)-1)) && busSeats?.get((4*position+2)-1)?.isBooked==1) {
                        holder.iv_lower_l2.setImageResource(R.drawable.booked)
                    } else if (SeatsActivity.selectedPosListSeater.contains(4 * position + 2)) {
                        holder.iv_lower_l2.setImageResource(R.drawable.booked)
                    }else{
                        holder.iv_lower_l2.setImageResource(R.drawable.available)
                    }
                }

                if(((4*position+3)-1)>=(busSeats!!.size)) {
                    holder.iv_lower_l3.visibility=View.INVISIBLE
                }else {
                    holder.iv_lower_l3.visibility=View.VISIBLE
                    if((busSeats?.size!! > ((4*position+3)-1)) && busSeats?.get((4*position+3)-1)?.isBooked==1) {
                        holder.iv_lower_l3.setImageResource(R.drawable.booked)
                    } else if (SeatsActivity.selectedPosListSeater.contains(4 * position + 3)) {
                        holder.iv_lower_l3.setImageResource(R.drawable.booked)
                    }else{
                        holder.iv_lower_l3.setImageResource(R.drawable.available)
                    }
                }

                if(((4*position+4)-1)>=(busSeats!!.size)) {
                    holder.iv_lower_l4.visibility=View.INVISIBLE
                }else {
                    holder.iv_lower_l4.visibility=View.VISIBLE
                    if((busSeats?.size!! > ((4*position+4)-1)) && busSeats?.get((4*position+4)-1)?.isBooked==1) {
                        holder.iv_lower_l4.setImageResource(R.drawable.booked)
                    } else if (SeatsActivity.selectedPosListSeater.contains(4 * position + 4)) {
                        holder.iv_lower_l4.setImageResource(R.drawable.booked)
                    }else{
                        holder.iv_lower_l4.setImageResource(R.drawable.available)
                    }
                }

            holder.iv_lower_l1.setOnClickListener{
                if(!((busSeats?.size!! > ((4*position+1)-1)) && busSeats?.get((4*position+1)-1)?.isBooked==1)) {
                    if (SeatsActivity.selectedPosListSeater.contains((4 * position + 1))) {
                        SeatsActivity.selectedPosListSeater.remove((4 * position + 1))
                        SeatsActivity.selectedSeatListSeater.remove(busSeats!!.get(4 * position).seatNo)
                        holder.iv_lower_l1.setImageResource(R.drawable.available)
                    } else {
                        SeatsActivity.selectedPosListSeater.add(4 * position + 1)
                        SeatsActivity.selectedSeatListSeater.add(busSeats!!.get(4 * position).seatNo)
                        holder.iv_lower_l1.setImageResource(R.drawable.booked)
                    }
                    seat1ClickListener!!.onClick()
                }else{
                    Util.showErrorToast(context,"Already booked")
                }
            }

            holder.iv_lower_l2.setOnClickListener{
                if(!((busSeats?.size!! > ((4*position+2)-1)) && busSeats?.get((4*position+2)-1)?.isBooked==1)) {

                    if (SeatsActivity.selectedPosListSeater.contains((4 * position + 2))) {
                        SeatsActivity.selectedPosListSeater.remove((4 * position + 2))
                        SeatsActivity.selectedSeatListSeater.remove(busSeats!!.get(4 * position+1).seatNo)

                        holder.iv_lower_l2.setImageResource(R.drawable.available)
                    } else {
                        SeatsActivity.selectedPosListSeater.add(4 * position + 2)
                        SeatsActivity.selectedSeatListSeater.add(busSeats!!.get(4 * position+1).seatNo)

                        holder.iv_lower_l2.setImageResource(R.drawable.booked)
                    }
                    seat1ClickListener!!.onClick()
                }else{
                    Util.showErrorToast(context,"Already booked")
                }
            }

            holder.iv_lower_l3.setOnClickListener{
                val pos1=(4*position+3)-1

                if(!((busSeats?.size!! > (pos1)) && busSeats?.get(pos1)?.isBooked==1)) {

                    if (SeatsActivity.selectedPosListSeater.contains(4 * position + 3)) {
                        SeatsActivity.selectedPosListSeater.remove(4 * position + 3)
                        SeatsActivity.selectedSeatListSeater.remove(busSeats!!.get(4 * position+2).seatNo)

                        holder.iv_lower_l3.setImageResource(R.drawable.available)
                    } else {
                        SeatsActivity.selectedPosListSeater.add(4 * position + 3)
                        SeatsActivity.selectedSeatListSeater.add(busSeats!!.get(4 * position+2).seatNo)

                        holder.iv_lower_l3.setImageResource(R.drawable.booked)
                    }
                    seat1ClickListener!!.onClick()
                }else{
                    Util.showErrorToast(context,"Already booked")
                }
            }

            holder.iv_lower_l4.setOnClickListener{
                val pos=(4*position+4)-1
                if(!((busSeats?.size!! > (pos)) && (busSeats?.get(pos)?.isBooked==1))) {

                    if (SeatsActivity.selectedPosListSeater.contains(4 * position + 4)) {
                        SeatsActivity.selectedPosListSeater.remove(4 * position + 4)
                        SeatsActivity.selectedSeatListSeater.remove(busSeats!!.get(4 * position+3).seatNo)

                        holder.iv_lower_l4.setImageResource(R.drawable.available)
                    } else {
                        SeatsActivity.selectedPosListSeater.add(4 * position + 4)
                        SeatsActivity.selectedSeatListSeater.add(busSeats!!.get(4 * position+3).seatNo)

                        holder.iv_lower_l4.setImageResource(R.drawable.booked)
                    }
                    seat1ClickListener!!.onClick()
                }else{
                    Util.showErrorToast(context,"Already booked")
                }
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun getItemCount(): Int {

        val reminder=(seatsCount)%4
        var count=(seatsCount)/4

        if(reminder>0){
            count=count+1
        }
        return count
    }

    inner class MyVewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) ,View.OnClickListener{
        var rel_layout_lower: RelativeLayout
        var iv_lower_l1:ImageView
        var iv_lower_l2:ImageView
        var iv_lower_l3:ImageView
        var iv_lower_l4:ImageView

        init {
            rel_layout_lower = itemView.findViewById(R.id.rel_layout_lower) as RelativeLayout
            iv_lower_l1 = itemView.findViewById(R.id.iv_lower_l1) as ImageView
            iv_lower_l2 = itemView.findViewById(R.id.iv_lower_l2) as ImageView
            iv_lower_l3 = itemView.findViewById(R.id.iv_lower_l3) as ImageView
            iv_lower_l4 = itemView.findViewById(R.id.iv_lower_l4) as ImageView

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
