package com.oasis.ui.horizontalpicker;


import android.app.AlarmManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.oasis.R;

import org.joda.time.DateTime;

import java.util.ArrayList;


public class HorizontalPickerAdapter extends RecyclerView.Adapter<HorizontalPickerAdapter.ViewHolder> {

    private static final long DAY_MILLIS = AlarmManager.INTERVAL_DAY;
    private final int mBackgroundColor;
    private final int mDateSelectedTextColor;
    private final int mDateSelectedColor;
    private final int mTodayDateTextColor;
    private final int mTodayDateBackgroundColor;
    private final int mDayOfWeekTextColor;
    private final int mUnselectedDayTextColor;
    private int itemWidth;
    private final OnItemClickedListener listener;
    private ArrayList<Day> items;
    private  Day selectedItem;

    public HorizontalPickerAdapter(int itemWidth, OnItemClickedListener listener, Context context, int daysToCreate, int offset, int mBackgroundColor, int mDateSelectedColor, int mDateSelectedTextColor, int mTodayDateTextColor, int mTodayDateBackgroundColor, int mDayOfWeekTextColor, int mUnselectedDayTextColor) {
        items=new ArrayList<>();
        this.itemWidth=itemWidth;
        this.listener=listener;
        generateDays(daysToCreate*5,new DateTime().minusDays(offset).getMillis(),false);
        this.mBackgroundColor=mBackgroundColor;
        this.mDateSelectedTextColor=mDateSelectedTextColor;
        this.mDateSelectedColor=mDateSelectedColor;
        this.mTodayDateTextColor=mTodayDateTextColor;
        this.mTodayDateBackgroundColor=mTodayDateBackgroundColor;
        this.mDayOfWeekTextColor=mDayOfWeekTextColor;
        this.mUnselectedDayTextColor=mUnselectedDayTextColor;
    }

    public  void generateDays(int n, long initialDate, boolean cleanArray) {
        if(cleanArray)
            items.clear();
        int i=0;
        while(i<n)
        {
            DateTime actualDate = new DateTime(initialDate + (DAY_MILLIS * i++));
            items.add(new Day(actualDate));
        }
        selectedItem=items.get(0);
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_day,parent,false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        Day item1=getItem(position*5+0);
        holder.tvDay1.setText(item1.getDay());
        holder.tvWeekDay1.setText(item1.getWeekDay());
        holder.tvMonth1.setText(item1.getMonth("").substring(0,3));

        Day item2=getItem(position*5+1);
        holder.tvDay2.setText(item2.getDay());
        holder.tvWeekDay2.setText(item2.getWeekDay());
        holder.tvMonth2.setText(item2.getMonth("").substring(0,3));

        Day item3=getItem(position*5+2);
        holder.tvDay3.setText(item3.getDay());
        holder.tvWeekDay3.setText(item3.getWeekDay());
        holder.tvMonth3.setText(item3.getMonth("").substring(0,3));

        Day item4=getItem(position*5+3);
        holder.tvDay4.setText(item4.getDay());
        holder.tvWeekDay4.setText(item4.getWeekDay());
        holder.tvMonth4.setText(item4.getMonth("").substring(0,3));

        Day item5=getItem(position*5+4);
        holder.tvDay5.setText(item5.getDay());
        holder.tvWeekDay5.setText(item5.getWeekDay());
        holder.tvMonth5.setText(item5.getMonth("").substring(0,3));

        holder.linearLayout1.setBackgroundResource(R.drawable.background_left_hover);
        holder.linearLayout2.setBackgroundResource(R.drawable.background_left_hover);
        holder.linearLayout3.setBackgroundResource(R.drawable.background_middle_hover);
        holder.linearLayout4.setBackgroundResource(R.drawable.background_right_hover);
        holder.linearLayout5.setBackgroundResource(R.drawable.background_right_hover);

        if(selectedItem!=null && selectedItem.getDay().equalsIgnoreCase(item1.getDay())
                && selectedItem.getMonth().equalsIgnoreCase(item1.getMonth())
                && selectedItem.getWeekDay().equalsIgnoreCase(item1.getWeekDay())){
            holder.linearLayout1.setBackgroundResource(R.drawable.background_day_selected);

        }else if(selectedItem!=null && selectedItem.getDay().equalsIgnoreCase(item2.getDay())
                && selectedItem.getMonth().equalsIgnoreCase(item2.getMonth())
                && selectedItem.getWeekDay().equalsIgnoreCase(item2.getWeekDay())){
            holder.linearLayout2.setBackgroundResource(R.drawable.background_day_selected);

        }else if(selectedItem!=null && selectedItem.getDay().equalsIgnoreCase(item3.getDay())
                && selectedItem.getMonth().equalsIgnoreCase(item3.getMonth())
                && selectedItem.getWeekDay().equalsIgnoreCase(item3.getWeekDay())){
            holder.linearLayout3.setBackgroundResource(R.drawable.background_day_selected);

        }else if(selectedItem!=null && selectedItem.getDay().equalsIgnoreCase(item4.getDay())
                && selectedItem.getMonth().equalsIgnoreCase(item4.getMonth())
                && selectedItem.getWeekDay().equalsIgnoreCase(item4.getWeekDay())){
            holder.linearLayout4.setBackgroundResource(R.drawable.background_day_selected);

        }else if(selectedItem!=null && selectedItem.getDay().equalsIgnoreCase(item5.getDay())
                && selectedItem.getMonth().equalsIgnoreCase(item5.getMonth())
                && selectedItem.getWeekDay().equalsIgnoreCase(item5.getWeekDay())){
            holder.linearLayout5.setBackgroundResource(R.drawable.background_day_selected);

        }

        holder.linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout1.setBackgroundResource(R.drawable.background_day_selected);
                holder.linearLayout2.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout3.setBackgroundResource(R.drawable.background_middle_hover);
                holder.linearLayout4.setBackgroundResource(R.drawable.background_right_hover);
                holder.linearLayout5.setBackgroundResource(R.drawable.background_right_hover);
                item1.setSelected(true);
                item2.setSelected(false);
                item3.setSelected(false);
                item4.setSelected(false);
                item5.setSelected(false);
                selectedItem=item1;
                notifyDataSetChanged();
                listener.onClickDateItem(item1.getDate());

            }
        });
        holder.linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout1.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout2.setBackgroundResource(R.drawable.background_day_selected);
                holder.linearLayout3.setBackgroundResource(R.drawable.background_middle_hover);
                holder.linearLayout4.setBackgroundResource(R.drawable.background_right_hover);
                holder.linearLayout5.setBackgroundResource(R.drawable.background_right_hover);
                item1.setSelected(false);
                item2.setSelected(true);
                item3.setSelected(false);
                item4.setSelected(false);
                item5.setSelected(false);
                selectedItem=item2;

                notifyDataSetChanged();
                listener.onClickDateItem(item2.getDate());

            }
        });
        holder.linearLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout1.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout2.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout3.setBackgroundResource(R.drawable.background_day_selected);
                holder.linearLayout4.setBackgroundResource(R.drawable.background_right_hover);
                holder.linearLayout5.setBackgroundResource(R.drawable.background_right_hover);
                item1.setSelected(false);
                item2.setSelected(false);
                item3.setSelected(true);
                item4.setSelected(false);
                item5.setSelected(false);
                selectedItem=item3;

                notifyDataSetChanged();
                listener.onClickDateItem(item3.getDate());

            }
        });
        holder.linearLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout1.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout2.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout3.setBackgroundResource(R.drawable.background_middle_hover);
                holder.linearLayout4.setBackgroundResource(R.drawable.background_day_selected);
                holder.linearLayout5.setBackgroundResource(R.drawable.background_right_hover);
                item1.setSelected(false);
                item2.setSelected(false);
                item3.setSelected(false);
                item4.setSelected(true);
                item5.setSelected(false);
                selectedItem=item4;
                notifyDataSetChanged();
                listener.onClickDateItem(item4.getDate());

            }
        });
        holder.linearLayout5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.linearLayout1.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout2.setBackgroundResource(R.drawable.background_left_hover);
                holder.linearLayout3.setBackgroundResource(R.drawable.background_middle_hover);
                holder.linearLayout4.setBackgroundResource(R.drawable.background_right_hover);
                holder.linearLayout5.setBackgroundResource(R.drawable.background_day_selected);
                item1.setSelected(false);
                item2.setSelected(false);
                item3.setSelected(false);
                item4.setSelected(false);
                item5.setSelected(true);
                selectedItem=item5;
                notifyDataSetChanged();
                listener.onClickDateItem(item5.getDate());
            }
        });
    }

    public Day getItem(int position) {
        if(position<items.size()) {
            return items.get(position);
        }
        return items.get(items.size()-1);
    }

    @Override
    public int getItemCount() {
        return (items.size()/5);
    }

    public class ViewHolder extends RecyclerView.ViewHolder/* implements View.OnClickListener */{
        TextView tvDay1,tvWeekDay1,tvDay2,tvWeekDay2,tvDay3,tvWeekDay3,
                tvDay4,tvWeekDay4,tvDay5,tvWeekDay5,tvMonth1,tvMonth2,tvMonth3,tvMonth4,tvMonth5;
        LinearLayout base,linearLayout1,linearLayout2,linearLayout3,linearLayout4,linearLayout5;
        ImageView iv1,iv2,iv3,iv4,iv5;

        public ViewHolder(View itemView) {
            super(itemView);
            base=(LinearLayout)itemView.findViewById(R.id.base);
            linearLayout1=(LinearLayout)itemView.findViewById(R.id.lin_lay1);
            linearLayout2=(LinearLayout)itemView.findViewById(R.id.lin_lay2);
            linearLayout3=(LinearLayout)itemView.findViewById(R.id.lin_lay3);
            linearLayout4=(LinearLayout)itemView.findViewById(R.id.lin_lay4);
            linearLayout5=(LinearLayout)itemView.findViewById(R.id.lin_lay5);
            tvDay1= (TextView) itemView.findViewById(R.id.tvDay1);
            tvWeekDay1= (TextView) itemView.findViewById(R.id.tvWeekDay1);
            tvDay2= (TextView) itemView.findViewById(R.id.tvDay2);
            tvWeekDay2= (TextView) itemView.findViewById(R.id.tvWeekDay2);
            tvDay3= (TextView) itemView.findViewById(R.id.tvDay3);
            tvWeekDay3= (TextView) itemView.findViewById(R.id.tvWeekDay3);
            tvDay4= (TextView) itemView.findViewById(R.id.tvDay4);
            tvWeekDay4= (TextView) itemView.findViewById(R.id.tvWeekDay4);
            tvDay5= (TextView) itemView.findViewById(R.id.tvDay5);
            tvWeekDay5= (TextView) itemView.findViewById(R.id.tvWeekDay5);
            tvMonth1= (TextView) itemView.findViewById(R.id.tvMonth1);
            tvMonth2= (TextView) itemView.findViewById(R.id.tvMonth2);
            tvMonth3= (TextView) itemView.findViewById(R.id.tvMonth3);
            tvMonth4= (TextView) itemView.findViewById(R.id.tvMonth4);
            tvMonth5= (TextView) itemView.findViewById(R.id.tvMonth5);
            iv1=(ImageView)itemView.findViewById(R.id.iv1);
            iv2=(ImageView)itemView.findViewById(R.id.iv2);
            iv3=(ImageView)itemView.findViewById(R.id.iv3);
            iv4=(ImageView)itemView.findViewById(R.id.iv4);
            iv5=(ImageView)itemView.findViewById(R.id.iv5);
        }
    }
}