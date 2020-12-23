package com.systemvx.androiddevtest.ui.main.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.OrderBriefing

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
        //TODO 接入查询业务
        //目前随机生成点占位数据
        val temp = ArrayList<OrderBriefing>()
        temp.add(OrderBriefing.randomGarbage())
        temp.add(OrderBriefing.randomGarbage())
        mData.value = temp
    }


}