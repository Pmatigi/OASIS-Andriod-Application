package com.oasis.ui.horizontalpicker;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import androidx.annotation.ColorInt;
import androidx.annotation.Nullable;


import com.oasis.R;

import org.joda.time.DateTime;


public class HorizontalPicker extends LinearLayout implements HorizontalPickerListener {

    private static final int NO_SETTED = -1;
    private DatePickerListener listener;
    private HorizontalPickerRecyclerView rvDays;
    private int days;
    private int offset;
    private int mDateSelectedColor = -1;
    private int mDateSelectedTextColor = -1;
    private int mMonthAndYearTextColor = -1;
    private int mTodayButtonTextColor = -1;
    private boolean showTodayButton = true;
    private String mMonthPattern = "";
    private int mTodayDateTextColor = -1;
    private int mTodayDateBackgroundColor = -1;
    private int mDayOfWeekTextColor = -1;
    private int mUnselectedDayTextColor = -1;

    public HorizontalPicker(Context context) {
        super(context);
        internInit();
    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        internInit();

    }

    public HorizontalPicker(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        internInit();
    }

    private void internInit() {
        this.days = NO_SETTED;
        this.offset = NO_SETTED;
    }

    public HorizontalPicker setListener(DatePickerListener listener) {
        this.listener = listener;
        return this;
    }

    public void setDate(final DateTime date) {
        rvDays.post(new Runnable() {
            @Override
            public void run() {
                rvDays.setDate(date);
            }
        });
    }

    public void init() {
        inflate(getContext(), R.layout.horizontal_picker, this);
        rvDays = (HorizontalPickerRecyclerView) findViewById(R.id.rvDays);
        //dkd
        int DEFAULT_DAYS_TO_PLUS = 120;
        int finalDays = days == NO_SETTED ? DEFAULT_DAYS_TO_PLUS : days;
        int DEFAULT_INITIAL_OFFSET = 1;
        int finalOffset = offset == NO_SETTED ? DEFAULT_INITIAL_OFFSET : offset;
        rvDays.setListener(this);
        int mBackgroundColor = getBackgroundColor();
        setBackgroundColor(mBackgroundColor != Color.TRANSPARENT ? mBackgroundColor : Color.WHITE);
        mDateSelectedColor = mDateSelectedColor == -1 ? getColor(R.color.colorPrimary) : mDateSelectedColor;
        mDateSelectedTextColor = mDateSelectedTextColor == -1 ? Color.WHITE : mDateSelectedTextColor;
        mTodayDateTextColor = mTodayDateTextColor == -1 ? getColor(R.color.colorPrimary) : mTodayDateTextColor;
        mDayOfWeekTextColor = mDayOfWeekTextColor == -1 ? getColor(R.color.colorPrimary) : mDayOfWeekTextColor;
        mUnselectedDayTextColor = mUnselectedDayTextColor == -1 ? getColor(R.color.colorPrimary) : mUnselectedDayTextColor;
        rvDays.init(
                getContext(),
                finalDays,
                finalOffset,
                mBackgroundColor,
                mDateSelectedColor,
                mDateSelectedTextColor,
                mTodayDateTextColor,
                mTodayDateBackgroundColor,
                mDayOfWeekTextColor,
                mUnselectedDayTextColor);
    }

    private int getColor(int colorId) {
        return getResources().getColor(colorId);
    }

    public int getBackgroundColor() {
        int color = Color.TRANSPARENT;
        Drawable background = getBackground();
        if (background instanceof ColorDrawable)
            color = ((ColorDrawable) background).getColor();
        return color;
    }

    @Override
    public boolean post(Runnable action) {
        return rvDays.post(action);
    }

    @Override
    public void onStopDraggingPicker() {

    }

    @Override
    public void onDraggingPicker() {

    }

    @Override
    public void onDateSelected(DateTime item) {
        listener.onDateSelected(item);
    }

    public HorizontalPicker setDays(int days) {
        this.days = days;
        return this;
    }

    public int getDays() {
        return days;
    }

    public HorizontalPicker setOffset(int offset) {
        this.offset = offset;
        return this;
    }

    public int getOffset() {
        return offset;
    }

    public HorizontalPicker setDateSelectedColor(@ColorInt int color) {
        mDateSelectedColor = color;
        return this;
    }

    public HorizontalPicker setDateSelectedTextColor(@ColorInt int color) {
        mDateSelectedTextColor = color;
        return this;
    }

    public HorizontalPicker setMonthAndYearTextColor(@ColorInt int color) {
        mMonthAndYearTextColor = color;
        return this;
    }

    public HorizontalPicker setTodayButtonTextColor(@ColorInt int color) {
        mTodayButtonTextColor = color;
        return this;
    }

    public HorizontalPicker showTodayButton(boolean show) {
        showTodayButton = show;
        return this;
    }

    public HorizontalPicker setTodayDateTextColor(int color) {
        mTodayDateTextColor = color;
        return this;
    }

    public HorizontalPicker setTodayDateBackgroundColor(@ColorInt int color) {
        mTodayDateBackgroundColor = color;
        return this;
    }

    public HorizontalPicker setDayOfWeekTextColor(@ColorInt int color) {
        mDayOfWeekTextColor = color;
        return this;
    }

    public HorizontalPicker setUnselectedDayTextColor(@ColorInt int color) {
        mUnselectedDayTextColor = color;
        return this;
    }

    public HorizontalPicker setMonthPattern(String pattern) {
        mMonthPattern = pattern;
        return this;
    }
}