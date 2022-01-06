package com.ril.utils

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.TextView
import com.oasis.R

class ActionDialog(context: Context, private val title: String, private val msg: String, private val myListener: OnDialogClick) : Dialog(context)
{
    private var accText: String = "Ok"
    private var canText: String = "No"
    private var hideNO: Boolean = false

    constructor(context: Context, title: String, msg: String, myListener: OnDialogClick, acceptText: String, cancelText: String) : this(context, title, msg, myListener)
    {
        accText = acceptText
        canText = cancelText
    }

    constructor(context: Context, title: String, msg: String, myListener: OnDialogClick, acceptText: String, cancelText: String, hNo: Boolean) : this(context, title, msg, myListener)
    {
        accText = acceptText
        canText = cancelText
        hideNO = hNo
    }

    interface OnDialogClick
    {
        fun accept(inputText: String, type: Int)
        fun reject(inputText: String, type: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        window!!.setBackgroundDrawable(ColorDrawable(0))
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.action_dialog_view)
        setCancelable(false)

        val tvHeaderTxt = findViewById<TextView>(R.id.tvHeaderTxt)
        val tvMsg = findViewById<TextView>(R.id.tvmsg)
        val btnReject = findViewById<TextView>(R.id.btnReject)
        val btnAccept = findViewById<TextView>(R.id.btnAccept)

        tvHeaderTxt.text = title
        tvMsg.text = msg

        btnAccept.text = accText
        btnReject?.text = canText

        if (hideNO) btnReject?.visibility = View.GONE

        btnAccept?.setOnClickListener {
            myListener.accept("Accept", 1)
            dismiss()
        }

        btnReject!!.setOnClickListener {
            myListener.reject("Reject", 0)
            dismiss()
        }
    }
}
