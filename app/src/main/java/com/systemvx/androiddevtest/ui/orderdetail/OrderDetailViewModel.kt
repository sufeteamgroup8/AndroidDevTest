package com.systemvx.androiddevtest.ui.orderdetail

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.OrderDetailDataSource
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailViewModel(val context: Context) : ViewModel() {

    val orderdetail = MutableLiveData<OrderDetail>()

    fun getMainTextStr() = orderdetail.value?.let { return@let "\t" + it.mainText } ?: ""

    fun getPriceStr() = orderdetail.value?.let { return@let "￥ " + it.price } ?: ""

    fun getDeadLineStr() = orderdetail.value?.let { return@let SimpleDateFormat("yy-MM-dd HH:mm", Locale.getDefault()).format(it.deadline) }
    private val dataResult = MutableLiveData<Boolean>()

    fun fetchOrderData(orderID: Int): Boolean {
        return when (val result = OrderDetailDataSource().getOrderFullData(orderID)) {
            is Result.Success -> {
                orderdetail.postValue(result.data)
                dataResult.postValue(true)
                true
            }
            is Result.Error -> false
        }
    }


}