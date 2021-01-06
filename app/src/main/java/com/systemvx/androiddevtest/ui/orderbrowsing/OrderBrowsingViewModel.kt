package com.systemvx.androiddevtest.ui.orderbrowsing

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result

class OrderBrowsingViewModel : ViewModel() {

    val fullListData = MutableLiveData<ArrayList<OrderBriefing>>()

    fun updateData() {
        Thread {
            val result = OrderDataSource().getOrderByPublisher(LoginRepository.user!!.id)
            if (result is Result.Success) {
                fullListData.postValue(result.data)
            } else {
                fullListData.postValue(null)
            }

        }.start()
    }
}