package com.systemvx.androiddevtest.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result

class DashboardViewModel : ViewModel() {
    private val mText: MutableLiveData<String?> = MutableLiveData()

    private val mData: MutableLiveData<ArrayList<OrderBriefing>> = MutableLiveData()

    val text: LiveData<String?>
        get() = mText

    val data: LiveData<ArrayList<OrderBriefing>>
        get() = mData

    init {
        mText.value = "This is dashboard fragment"
    }

    fun updateData() {
        val result = OrderDataSource().searchOrder(null, null, null, null)
        if (result is Result.Success) {
            mData.value = result.data
        }


    }


}