package com.oasis.ui.horizontalpicker;

import org.joda.time.DateTime;

public interface HorizontalPickerListener {
    void onStopDraggingPicker();
    void onDraggingPicker();
    void onDateSelected(DateTime item);
}