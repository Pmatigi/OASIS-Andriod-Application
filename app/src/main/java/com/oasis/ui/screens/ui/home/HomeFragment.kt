package com.oasis.ui.screens.ui.home

import Constant
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.preference.PreferenceActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.braintreepayments.api.dropin.DropInRequest
import com.braintreepayments.api.dropin.DropInResult
import com.oasis.R
import com.oasis.ui.horizontalpicker.DatePickerListener
import com.oasis.ui.horizontalpicker.HorizontalPicker
import com.oasis.ui.models.home.City
import com.oasis.ui.screens.HomeActivity
import com.oasis.ui.utils.Util.Util
import cz.msebera.android.httpclient.Header
import kotlinx.android.synthetic.main.fragment_home.*
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment(),DatePickerListener {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var tv_start_city:TextView
    private lateinit var tv_end_city:TextView
    private  var startCity= City()
    private  var endCity= City()
    lateinit var picker:HorizontalPicker

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val tv_current_date: TextView = root.findViewById(R.id.tv_current_date)
        val btnSearch = root.findViewById(R.id.btnSearch) as Button
        tv_start_city = root.findViewById(R.id.tv_start_city) as TextView
        tv_end_city = root.findViewById(R.id.tv_end_city) as TextView
        val lin_lay_start_city = root.findViewById(R.id.lin_lay_start_city) as LinearLayout
        val lin_lay_end_city = root.findViewById(R.id.lin_lay_end_city) as LinearLayout
        val iv_city_reverse = root.findViewById(R.id.iv_city_reverse) as ImageView
        picker = root.findViewById(R.id.datePicker) as HorizontalPicker

        btnSearch.setOnClickListener {
            if (tv_start_city.text.trim().equals("Select city")) {
                Util.showErrorToast(activity!!.applicationContext,"Please select start city")

            } else if (tv_end_city.text.trim().equals("Select city")) {
                Util.showErrorToast(activity!!.applicationContext,"Please select destination city")

            }else {
                searchBus(startCity.id.toString(), endCity.id.toString())
            }
        }

        iv_city_reverse.setOnClickListener {
            Util.rotateImage(iv_city_reverse)
            val tempCity=startCity;
            startCity=endCity
            endCity=tempCity
            if(startCity.name!=null) {
                tv_start_city.text = startCity.name
                tv_start_city.setTextColor(resources.getColor(R.color.blue))

            }else{
                tv_start_city.text ="Select city"
                tv_start_city.setTextColor(resources.getColor(R.color.grey))

            }

            if(endCity.name!=null) {
                tv_end_city.text = endCity.name
                tv_end_city.setTextColor(resources.getColor(R.color.blue))

            }else{
                tv_end_city.text ="Select city"
                tv_end_city.setTextColor(resources.getColor(R.color.grey))

            }
        }


        lin_lay_start_city.setOnClickListener {
            getCity(1)
        }

        lin_lay_end_city.setOnClickListener {
            getCity(2)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        picker.setListener(this)
            .setDays(73)
            .setOffset(0)
            .setDateSelectedColor(Color.DKGRAY)
            .setDateSelectedTextColor(Color.WHITE)
            .setMonthAndYearTextColor(Color.DKGRAY)
            .setTodayButtonTextColor(getResources().getColor(R.color.colorPrimary))
            .setTodayDateTextColor(getResources().getColor(R.color.colorPrimary))
            .setTodayDateBackgroundColor(Color.GRAY)
            .setUnselectedDayTextColor(Color.DKGRAY)
            .setDayOfWeekTextColor(Color.DKGRAY)
            .setUnselectedDayTextColor(getResources().getColor(R.color.colorPrimary))
            .showTodayButton(false)
            .init()
        picker.setBackgroundColor(Color.LTGRAY)
        picker.setDate(DateTime())

        setCurrentDate(tv_current_date)
       // payNow()
    }

    fun getCity( reqCode:Int){
        (activity as HomeActivity).showLoadingWithMessage("Searching")
        homeViewModel.getCity().observe(this, Observer {
            Util.showLog("LoginResponse", "" + it)
            (activity as HomeActivity).hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull())
                {
                    Log.e("dkd","dkd it-"+it.data!!.error)
                    var error=it.data!!.error

                    if(error){
                        Util.showErrorToast(activity!!.applicationContext,"Error occured!");
                    }else{
                        val intent= Intent(activity,CitylistActivity::class.java)
                        intent.putExtra("city_list", it.data)
                        intent.putExtra("requestCode", reqCode);
                        startActivityForResult(intent,reqCode)
                    }
                } else {
                    Util.showErrorToast(activity!!.applicationContext, it.message)
                }
            } else
            {
                Util.showErrorToast(activity!!.applicationContext, Constant.ERROR_TRY_AGAIN)
            }
        })
    }


    fun searchBus(sourceId:String,destinationId:String){
        (activity as HomeActivity).showLoadingWithMessage( "Loading...")
        homeViewModel.searchBus(sourceId, destinationId,selectedDateStr1,selectedTimeStr).observe(this, Observer {
            Util.showLog("LoginResponse", "" + it)
            (activity as HomeActivity).hideLoading()
            if (it != null)
            {
                if (it.isSuccessFull()) {
                      Log.e("dkd","dkd bus list-"+it.data)
                    Log.e("dkd","dkd bus list-"+it.data?.busList)
                    if(it.data?.busList!=null) {
                        val intent = Intent(activity, AvailiableBusActivity::class.java)
                        intent.putExtra("bus_list", it.data)
                        intent.putExtra("start_city", startCity.name)
                        intent.putExtra("end_city", endCity.name)
                        intent.putExtra("selectedDateStr1", selectedDateStr1)
                        intent.putExtra("selectedTimeStr", selectedTimeStr)
                        startActivity(intent)
                        activity?.overridePendingTransition(R.anim.slide_in_up, R.anim.stay)

                    }else{
                        Util.showErrorToast(activity!!.applicationContext,"No Bus found for this route")
                    }
                } else
                {
                    Util.showErrorToast(activity!!.applicationContext, it.message)
                }
            } else
            {
                Util.showErrorToast(activity!!.applicationContext, Constant.ERROR_TRY_AGAIN)
            }
        })
    }


   // lateinit var selectedDateStr:String
    lateinit var selectedTimeStr:String
    lateinit var selectedDateStr1:String

    fun setCurrentDate(tv_date: TextView){

        var calendar: Calendar = Calendar.getInstance();
        var mdformat: SimpleDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy");
//        var mdformat1: SimpleDateFormat = SimpleDateFormat("EEEE, dd MMMM yyyy");
        var mdformat2: SimpleDateFormat = SimpleDateFormat("dd-MM-yyyy");
        val formatted = mdformat.format(calendar.time)
    //    selectedDateStr = mdformat1.format(calendar.time)
        selectedDateStr1 = mdformat2.format(calendar.time)
        var mdformatTime: SimpleDateFormat = SimpleDateFormat("HH:mm");
        selectedTimeStr= mdformatTime.format(calendar.time)
        Log.e("dkd","dkd selectedTimeStr-"+selectedTimeStr)

        tv_date.text=formatted.toString()

    }


    override fun onDateSelected(dateSelected: DateTime?) {

        Log.e("dkd","dkd dete dateSelected="+dateSelected)
        val dateFormat =  SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
         val date = dateFormat.parse(dateSelected.toString());//You will get date object relative to server/client timezone wherever it is parsed
    //     val formatter =  SimpleDateFormat("MM-dd-yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
         val formatter2 =  SimpleDateFormat("dd-MM-yyyy"); //If you need time just put specific format for time like 'HH:mm:ss'
  //      selectedDateStr = formatter.format(date);
        selectedDateStr1 = formatter2.format(date);
        var mdformatTime: SimpleDateFormat = SimpleDateFormat("HH:mm");

        var calendar: Calendar = Calendar.getInstance();
        selectedTimeStr= mdformatTime.format(calendar.time)
        Log.e("dkd","dkd selectedTimeStr-"+selectedTimeStr)
        val formatter1 = SimpleDateFormat("EEEE, dd MMMM yyyy")
        val dateStr1 = formatter1.format(date)
        tv_current_date.setText(dateStr1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == 1) {
                var tempCity = data!!.getSerializableExtra("city") as City

                if ((endCity.name == null) || ((endCity.name != null) && (!(endCity.name.toString()
                        .trim().equals(tempCity.name.toString().trim()))))
                ) {
                    startCity = data!!.getSerializableExtra("city") as City
                    Log.e("dkd", "dkd city=" + startCity.name)
                    tv_start_city.text = startCity.name
                    tv_start_city.setTextColor(resources.getColor(R.color.blue))
                } else {
                    Util.showErrorToast(
                        activity!!.applicationContext,
                        "Start City and End City can not be same."
                    )
                }
            }
            if (requestCode == 2) {
                var tempCity = data!!.getSerializableExtra("city") as City
                Log.e("dkd", "dkd city=" + tempCity.name)
                if ((startCity.name == null) || ((startCity.name != null) && (!(startCity.name.toString()
                        .trim().equals(tempCity.name.toString().trim()))))
                ) {
                    endCity = data!!.getSerializableExtra("city") as City
                    tv_end_city.text = endCity.name
                    tv_end_city.setTextColor(resources.getColor(R.color.blue))
                } else {
                    Util.showErrorToast(
                        activity!!.applicationContext,
                        "Start City and End City can not be same."
                    )
                }
            }
            if (requestCode == 3) {
                when (resultCode) {
                    Activity.RESULT_OK -> {
                        val result = data!!.getParcelableExtra<DropInResult>(
                            DropInResult.EXTRA_DROP_IN_RESULT
                        )
                        val paymentMethodNonce = result.paymentMethodNonce!!.nonce
                        Log.v("PayNonce", "PayNonce==" + paymentMethodNonce)
                        Toast.makeText(activity, "Payment Success!", Toast.LENGTH_SHORT).show()
                        // Send paymentMethodNonce to your server here..
                    }


                    Activity.RESULT_CANCELED -> Log.v("Cancelled", "Cancelled!!")

                }
            }
        }
    }
        val REQUEST_CODE_PAYPAL = 3
        var token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJjMDhiZmQzOGFlYmZkYTcyYjY4MWY0NWFhNTNkZjMyMGQ0Nzg4Zjk0OGUxNTdlNGJlZDY2YjZjNzE4NWJlZWYzfGNyZWF0ZWRfYXQ9MjAxNy0xMi0yMFQxMzowMjozNy4yNjIxODM5MzcrMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0="

//        fun getTokenFromServer() {
//            val androidClient = AsyncHttpClient()
//            androidClient.get(API_URL, object : TextHttpResponseHandler() {
//                override fun onSuccess(
//                    statusCode: Int,
//                    headers: Array<out Header>?,
//                    responseString: String?
//                ) {
//                    Log.d("Success!!", "Client Token== " + responseString)
//                }
//
//                override fun onFailure(
//                    statusCode: Int, headers: Array<Header>, responseString:
//                    String, throwable: Throwable
//                ) {
//                    Log.v("Failure!", responseString)
//                }
//            })
//        }

        fun payNow() {
            val dropInRequest = DropInRequest().clientToken(token)
                .amount("$1.00") // Here you can pass Amount to be paid.
            dropInRequest.disableGooglePayment().disableVenmo()
            startActivityForResult(dropInRequest.getIntent(activity), REQUEST_CODE_PAYPAL)
        }

    }
