package com.oasis.ui.screens.ui.booking

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.oasis.ui.api.ApiInterface
import com.oasis.ui.api.DataRepository.DataRepository
import com.oasis.ui.api.ResDataWrapper
import com.oasis.ui.models.booking.BookingStatusReq
import com.oasis.ui.models.booking.CancelBookingReq
import com.oasis.ui.models.booking.MyBookingInfoRequest
import com.oasis.ui.models.booking.MyBookingInfoRes
import com.oasis.ui.models.home.BusDetailReq
import com.oasis.ui.models.home.BusDetailRes
import com.oasis.ui.models.home.CityResponse
import com.oasis.ui.models.home.SearchBusRes

class BookingViewModel  (application: Application) : AndroidViewModel(application)
{
    private val dataRepo: DataRepository = DataRepository(application, ApiInterface.getApiService())

    fun getBooking(myBookingInfoRequest:MyBookingInfoRequest): LiveData<ResDataWrapper<MyBookingInfoRes>>
    {
        return dataRepo.getBookings(myBookingInfoRequest)
    }

    fun cancelBooking(cancelBookingReq:CancelBookingReq): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.cancelBooking(cancelBookingReq)
    }

    fun getBookingStaus(bookingStatusReq: BookingStatusReq): LiveData<ResDataWrapper<String>>
    {
        return dataRepo.getBookingStatus(bookingStatusReq)
    }

}