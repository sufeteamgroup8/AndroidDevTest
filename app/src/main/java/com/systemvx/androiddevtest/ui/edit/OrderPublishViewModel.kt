package com.systemvx.androiddevtest.ui.edit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.MappingRepository
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.data.model.helperdata.AddressMap
import java.util.*

class OrderPublishViewModel(val orderID: Int) : ViewModel() {

    var addressPos = -1

    var typePos = -1
    lateinit var orderDetail: OrderDetail

    lateinit var addressMap: ArrayList<AddressMap>

    lateinit var typeMAp: List<Pair<Int, String>>

    val detailResult = MutableLiveData<Boolean>()

    val pubResult = MutableLiveData<Boolean>()

    val paramResult = MutableLiveData<Boolean>()


    fun fetchMapping{
        Thread {
            try {
                val address = MappingRepository().fetchAddressMap()
                val type = MappingRepository().fetchTypeMap()
                addressMap = address
                typeMAp = type.toList()
                paramResult.postValue(true)
            } catch (e: Exception) {
                paramResult.postValue(false)
            }
        }.start()
    }

    fun publishOrder(title: String, mainText: String, price: Double, deadline: Date) {
        Thread {
            try {
                val result =
                        when (orderID) {
                            -1 -> OrderDataSource().newOrder(
                                    publisherID = LoginRepository.user!!.id,
                                    title = title,
                                    mainText = mainText,
                                    taskType = typeMAp[typePos].first,
                                    price = price,
                                    deadline = deadline,
                                    addressID = addressMap[addressPos].id,
                                    state = 1
                            )
                            else -> OrderDataSource().rePubOrder(
                                    orderID = orderID,
                                    title = title,
                                    mainText = mainText,
                                    taskType = typeMAp[typePos].first,
                                    price = price,
                                    deadline = deadline,
                                    addressID = addressMap[addressPos].id,
                                    requireModify = true,
                            )
                        }
                when (result) {
                    is Result.Success -> pubResult.postValue(true)
                    else -> pubResult.postValue(false)
                }
            } catch (e: Exception) {
                pubResult.postValue(null)
            }
        }.start()
    }

    fun publishDraft() {

    }

    fun fetchOrderDetail() {

    }

    class OrderPublishViewModelFactory(val orderID: Int) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OrderPublishViewModel::class.java)) {
                return OrderPublishViewModel(orderID) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}