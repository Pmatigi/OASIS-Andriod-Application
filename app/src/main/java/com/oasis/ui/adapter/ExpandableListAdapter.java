package com.oasis.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.oasis.R;
import com.oasis.ui.models.booking.CurrentBooking;
import com.oasis.ui.utils.Util.Util;

import java.util.HashMap;
import java.util.List;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private HashMap<String, List<CurrentBooking>> _listDataChild;

    public ExpandableListAdapter(Context context, List<String> listDataHeader,
                                 HashMap<String, List<CurrentBooking>> listChildData) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this._listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View itemView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (itemView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = infalInflater.inflate(R.layout.list_item_bookings, null);
        }
        TextView tv_date = itemView.findViewById(R.id.tv_date);
        TextView tv_day = itemView.findViewById(R.id.tv_day);
        TextView tv_month = itemView.findViewById(R.id.tv_month);
        TextView tv_tour = itemView.findViewById(R.id.tv_tour);
        TextView tv_bus_name = itemView.findViewById(R.id.tv_bus_name);
        TextView tv_boarding_point = itemView.findViewById(R.id.tv_boarding_point) ;

//        tv_date.setText(Util.getDayNumber(_listDataChild.get(groupPosition).get(childPosition).getDateOfJourney()));
//        tv_day.setText(Util.getDayName(_listDataChild.get(groupPosition).get(childPosition).getDateOfJourney()));
//       tv_month.setText(Util.getMonthNameYear(_listDataChild.get(groupPosition).get(childPosition).getDateOfJourney()));
        return itemView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

//     getDayName(dateStr:String):String{
//        val format1= SimpleDateFormat("yyyy-MM-dd");
//        val dt1=format1.parse(dateStr);
//        val format2= SimpleDateFormat("EEEE");
//        val finalDay=format2.format(dt1);
//        return finalDay
//    }
//
//    fun getDayNumber(dateStr:String):String{
//        val format1= SimpleDateFormat("yyyy-MM-dd");
//        val dt1=format1.parse(dateStr);
//        val format2= SimpleDateFormat("dd");
//        val finalDay=format2.format(dt1);
//        return finalDay
//    }
//
//    fun getMonthNameYear(dateStr:String):String{
//        val format1= SimpleDateFormat("yyyy-MM-dd");
//        val dt1=format1.parse(dateStr);
//        val format2= SimpleDateFormat("MMM");
//        val finalDay=format2.format(dt1);
//        val format3= SimpleDateFormat("yyyy");
//        val finalDay1=format3.format(dt1);
//        val day=finalDay+" "+finalDay1
//        return day
//    }
}