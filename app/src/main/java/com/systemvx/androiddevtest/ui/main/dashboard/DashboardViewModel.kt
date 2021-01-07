package com.systemvx.androiddevtest.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result
import java.util.*

class DashboardViewModel : ViewModel() {

    val mData: MutableLiveData<ArrayList<OrderBriefing>> = MutableLiveData()


    val data: LiveData<ArrayList<OrderBriefing>>
        get() = mData


    fun updateData() {
        val result = OrderDataSource().searchOrder(LoginRepository.user?.id
                ?: 0, "", 0.0, -2.0, Date())
        if (result is Result.Success) {
            mData.postValue(result.data)
        }


    }


}