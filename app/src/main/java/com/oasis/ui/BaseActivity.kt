package com.oasis.ui

import android.os.Bundle

import android.view.*
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.oasis.AppController
import com.oasis.R

open class BaseActivity : AppCompatActivity() {

    lateinit var mApp: AppController
//    lateinit var mSharedPreferences: SharedPreferences
//    lateinit var mEditor: SharedPreferences.Editor
    lateinit var   inflater: LayoutInflater
    var progressBarLayout: View? = null
    var progressBarLayoutBar: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic)

        initMembers()
    }

    private fun initMembers() {
        mApp = application as AppController
        inflater = layoutInflater
    }

    private fun getProgresBarView(): View {

        val progressBarLayout = layoutInflater.inflate(R.layout.progressbar, null)
        val rl = RelativeLayout(this)
        rl.gravity = Gravity.CENTER
        rl.addView(progressBarLayout)
        val layout = this.findViewById<View>(android.R.id.content).rootView as ViewGroup
        layout.isClickable=false
        layout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })
        val lin_lay_loading = progressBarLayout?.findViewById<View>(R.id.lin_lay_loading) as LinearLayout

        lin_lay_loading.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })

        layout.addView(rl, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        return rl
    }

    fun showLoading() {

        if (progressBarLayout == null)
            progressBarLayout = getProgresBarView()

        val tv_title = progressBarLayout?.findViewById<View>(R.id.tv_title) as TextView
        tv_title.visibility=View.GONE
    }

    fun showLoadingWithMessage(title: String) {

        if (progressBarLayout == null)
            progressBarLayout = getProgresBarView()

        val tv_title = progressBarLayout?.findViewById<View>(R.id.tv_title) as TextView
        tv_title.visibility=View.VISIBLE
        tv_title.text = title
    }

    fun hideLoading() {
        if (progressBarLayout != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressBarLayout?.setVisibility(View.GONE)
            progressBarLayout = null
        }
    }

    private fun getProgresBarViewOnlyBar(): View {

        val progressBarLayoutBar = layoutInflater.inflate(R.layout.progressbar_only_image, null)
        val rl = RelativeLayout(this)
        rl.gravity = Gravity.CENTER
        rl.addView(progressBarLayoutBar)
        val layout = this.findViewById<View>(android.R.id.content).rootView as ViewGroup
        layout.addView(rl, RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        layout.isClickable=false
        layout.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })
        val lin_lay_loading_only_bar = progressBarLayoutBar?.findViewById<View>(R.id.lin_lay_loading_only_bar) as LinearLayout
        lin_lay_loading_only_bar.setOnTouchListener(object : View.OnTouchListener {
            override fun onTouch(v: View, m: MotionEvent): Boolean {
                // Perform tasks here
                return false
            }
        })

        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        return rl
    }

    fun showLoadingBar(title: String) {

        if (progressBarLayoutBar == null)
            progressBarLayoutBar = getProgresBarViewOnlyBar()

        val tv_title = progressBarLayoutBar?.findViewById<View>(R.id.tv_title) as TextView
        tv_title.text = title
    }

    fun hideLoadingBar() {
        if (progressBarLayoutBar != null) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
            progressBarLayoutBar?.setVisibility(View.GONE)
            progressBarLayoutBar = null
        }
    }

}
