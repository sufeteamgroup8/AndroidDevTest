package com.systemvx.androiddevtest.ui.orderdetail

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.MappingRepository
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailViewModel : ViewModel() {

    val orderdetail = MutableLiveData<OrderDetail>()

    fun getPriceStr() = orderdetail.value?.let { return@let "￥ " + DecimalFormat("#.00").format(it.price) }
            ?: ""
    fun getAddressFull() = MappingRepository().getAddressChain(orderdetail.value?.address ?: 0)

    fun getCountDownTime(): String {
        val deadTime = orderdetail.value?.deadline?.time
        if (deadTime != null) {
            // time difference in minutes
            val gap = (Date().time - deadTime) / (60 * 1000)
            return when {
                // more than 1 day
                gap > 60 * 24 -> "剩余 ${gap / (60 * 24)} 天"
                // more than 1 hour
                gap > 60 -> "剩余 ${gap / 60} 小时"
                // more than 15 min
                gap > 15 -> "剩余 $gap 分钟 "

                gap <= 0 -> "已过期"

                else -> "即将过期"
            }
        } else {
            return ""
        }


    }

    fun getDateStr(pattern: String, date: Date?): String {
        if (date == null) {
            return ""
        }
        return SimpleDateFormat(pattern, Locale.getDefault()).format(date)

    }

    fun isOrderReceived(): Int {
        return when (orderdetail.value?.order?.receiver) {
            null -> View.GONE
            else -> View.VISIBLE
        }
    }


    val dataResult = MutableLiveData<Boolean>()

    fun fetchOrderData(orderID: Int): Boolean {
        return when (val result = OrderDataSource().getOrderFullData(orderID)) {
            is Result.Success -> {
                orderdetail.postValue(result.data)
                dataResult.postValue(true)
                true
            }
            is Result.Error -> {
                dataResult.postValue(false)
                false
            }
        }
    }


}