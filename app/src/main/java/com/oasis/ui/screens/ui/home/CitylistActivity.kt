package com.oasis.ui.screens.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.oasis.com.oasis.ui.adapter.CityListAdapter
import com.oasis.ui.models.home.City
import com.oasis.ui.models.home.CityResponse
import android.content.Intent
import com.oasis.R
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.oasis.com.oasis.ui.adapter.ItemClickListener
import com.oasis.ui.utils.Util.Util


class CitylistActivity : AppCompatActivity() {
    lateinit var city_list_view: RecyclerView
    var cityResponse= CityResponse()
    lateinit var mAdapter: CityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.oasis.R.layout.activity_citylist)
        Util.hideKeyboard(this)
        val tv_title: TextView =findViewById(R.id.tv_title)
        tv_title.text="Select City"
        cityResponse = intent.getSerializableExtra("city_list") as CityResponse

        city_list_view = findViewById(com.oasis.R.id.rec_view_city_list)
        city_list_view.setHasFixedSize(true)
        val mLayoutManager = LinearLayoutManager(this)
        city_list_view.setLayoutManager(mLayoutManager)
        var cityList=ArrayList<City>();
            cityList=cityResponse.city
        mAdapter = CityListAdapter(this!!, cityList, object : ItemClickListener {
            override fun onClick(view: View, position: Int,city:City) {
                val intent = Intent()
                //intent.putExtra("city", cityList.get(position))
                intent.putExtra("city", city)
                val resultCode=intent.getIntExtra("requestCode",0)
                setResult(resultCode, intent)
                overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
                finish()
            }
        })
        val back = findViewById(com.oasis.R.id.lin_lay_back) as LinearLayout
        val et_search_city = findViewById(R.id.et_search_city) as TextView

        et_search_city.addTextChangedListener(searchTextWatcher)

        back.setOnClickListener {
            overridePendingTransition(R.anim.stay, R.anim.slide_in_down);
            finish()
        }
        city_list_view.adapter=mAdapter
    }

    override fun onBackPressed() {
    }

    private val searchTextWatcher = object : TextWatcher {
        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            // ignore
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            // ignore
        }

        override fun afterTextChanged(s: Editable) {
            mAdapter.getFilter().filter(s.toString())
        }
    }
}
