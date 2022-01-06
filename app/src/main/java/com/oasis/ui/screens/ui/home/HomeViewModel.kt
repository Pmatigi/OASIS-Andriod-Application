package com.oasis.ui.screens.ui.home
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.DataRepository.DataRepository
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.booking.BookingReq
import com.oasis.ui.models.booking.OrangeMoneyPaymentReq
import com.oasis.ui.models.booking.PaymentConfirmReq
import com.oasis.ui.models.home.*

class HomeViewModel (application: Application) : AndroidViewModel(application)
{
    private val dataRepo: DataRepository = DataRepository(application, ApiInterface.getApiService())
    fun searchBus(source_id: String, destination_id: String, selected_date_str: String, selected_time_str: String): LiveData<ResDataWrapper<SearchBusRes>>
    {
        return dataRepo.searchBus(source_id, destination_id,selected_date_str,selected_time_str)
    }

    fun getCity(): LiveData<ResDataWrapper<CityResponse>>
    {
        return dataRepo.getCity()
    }

    fun getBusDetail(busDetailReq: BusDetailReq): LiveData<ResDataWrapper<BusDetailRes>>
    {
        return dataRepo.getBusDetail(busDetailReq)
    }

    fun doBooking(bookingReq: BookingReq): LiveData<ResDataWrapper<BookingRes>>
    {
        return dataRepo.doBooking(bookingReq)
    }

    fun doPayment(makePaymentReq: MakePaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {
        return dataRepo.doPayment(makePaymentReq)
    }

 fun doPaymentOrangeMoney(orangeMoneyPaymentReq: OrangeMoneyPaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {
        return dataRepo.doPaymentOrangeMoney(orangeMoneyPaymentReq)
    }

    fun doPaymentConfirm(paymentConfirmReq: PaymentConfirmReq): LiveData<ResDataWrapper<PaymentRes>>
    {
        return dataRepo.doPaymentConfirm(paymentConfirmReq)
    }

    fun doBraintreePayment(makePaymentReq: MakeBraintreePaymentReq): LiveData<ResDataWrapper<PaymentRes>>
    {
        return dataRepo.doBraintreePayment(makePaymentReq)
    }
}