package com.oasis.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.oasis.R;

public class AmenitiesGridImageAdapter extends BaseAdapter {
    private Context mContext;

    // Keep all Images in array
    public Integer[] mThumbIds = {
            R.drawable.ac_icon,R.drawable.water__icon,R.drawable.wifi_icon,R.drawable.slippers_icon,
            R.drawable.curtain_icon,R.drawable.electric_outlet_icon,R.drawable.lavatory_icon,R.drawable.towels_icon,
            R.drawable.snacks_icon,R.drawable.tv_icon
    };

    // Constructor
    public AmenitiesGridImageAdapter(Context c){
        mContext = c;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
        return imageView;
    }

}
