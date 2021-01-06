package com.systemvx.androiddevtest.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result

class DashboardViewModel : ViewModel() {

    private val mData: MutableLiveData<ArrayList<OrderBriefing>> = MutableLiveData()


    val data: LiveData<ArrayList<OrderBriefing>>
        get() = mData


    fun updateData() {
        val result = OrderDataSource().searchOrder(null, null, null, null, null)
        if (result is Result.Success) {
            mData.value = result.data
        }


    }


}