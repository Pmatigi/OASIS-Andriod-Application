package com.ril.utils

import android.app.Dialog
import android.content.Context
import android.view.View
import android.view.Window
import android.widget.TextView
import com.oasis.R


class CustomDialog(context: Context) : Dialog(context) {

    // Button yes_btn, no_btn;
    private var tvMsg: TextView

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.custom_dialog) // a simple layout_header with a TextView and Two Buttons
        val v = window!!.decorView
        v.setBackgroundResource(android.R.color.transparent)
        tvMsg = v.findViewById<View>(R.id.tvMsg) as TextView
        setCancelable(false)
    }

    fun quitDialog() {
        if (isShowing) dismiss()
    }

    fun setTitle(title: String) {
        tvMsg.text = ""
        tvMsg.text = title
    }
}