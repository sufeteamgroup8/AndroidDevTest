package com.systemvx.androiddevtest.ui.orderbrowsing

import androidx.lifecycle.MutableLiveData
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result

class OrderBrowsingViewModel {

    val fullListData = MutableLiveData<ArrayList<OrderBriefing>>()

    fun updateData() {
        val result = OrderDataSource().getOrderByPublisher(LoginRepository.user!!.id)
        if (result is Result.Success) {
            fullListData.value = result.data
        }
    }
}