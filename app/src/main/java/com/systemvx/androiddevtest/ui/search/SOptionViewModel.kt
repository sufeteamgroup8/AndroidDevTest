package com.systemvx.androiddevtest.ui.search

import android.view.View
import android.widget.ToggleButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result

class SOptionViewModel : ViewModel() {
    fun findOrder(searchStr: String) {
        val typeCode = ArrayList<Int>()
        for (view in typeTagList) {
            view as ToggleButton
            if (view.isChecked) {
                typeCode.add(view.tag.toString().toInt())
            }
        }
        val result = OrderDataSource().searchOrder(
                LoginRepository.user?.id,
                searchStr,
                priceMin,
                null,
                null
        )
        if (result is Result.Success) {
            this.resultList = result.data
            searchResult.postValue(true)
        } else {
            searchResult.postValue(false)
        }
    }

    var priceMin: Double = 3.0

    var typeTagList: ArrayList<View> = ArrayList()

    var resultList: ArrayList<OrderBriefing> = ArrayList()

    var searchResult: MutableLiveData<Boolean> = MutableLiveData()


}