package com.systemvx.androiddevtest.ui.complaint

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.data.ComplaintDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail

class ComplaintSendViewModel(private val orderDetail: OrderDetail) : ViewModel() {

    var message: String = ""

    val netResult = MutableLiveData<Boolean>()

    var reasonText = ""

    fun submitComplaint(message: String) {
        Thread {
            val finalMessage = message
            try {
                val userID = LoginRepository.user!!.id
                val pubID = orderDetail.order.publisher.id
                val recvID = orderDetail.order.receiver!!.id
                val result = if (recvID == userID) {
                    ComplaintDataSource().sendComplaint(recvID, pubID, orderDetail.order.id, finalMessage)
                } else {
                    ComplaintDataSource().sendComplaint(pubID, recvID, orderDetail.order.id, finalMessage)
                }
                when (result) {
                    is Result.Success -> {
                        netResult.postValue(true)
                    }
                    else -> {
                        netResult.postValue(false)
                    }
                }
            } catch (e: Exception) {
                netResult.postValue(false)
            }
        }.start()
    }

    class CommentSendViewModelFactory(val data: OrderDetail) : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ComplaintSendViewModel::class.java)) {
                return ComplaintSendViewModel(data) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}