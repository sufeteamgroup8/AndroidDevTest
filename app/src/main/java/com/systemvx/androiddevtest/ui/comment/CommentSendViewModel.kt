package com.systemvx.androiddevtest.ui.comment

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.CommentDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

class CommentSendViewModel(
        val orderData: OrderDetail,
) : ViewModel() {
    var stars: Float = 3.toFloat()

    var commentText = ""

    val message = MutableLiveData<Toast>()

    val netResult = MutableLiveData<Boolean>()

    //显示辅助函数
    fun getIDStr() = String.format("%08d", orderData.order.id)

    fun getEndTimeStr(): String = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA).format(orderData.order.closedTime)

    fun getPriceStr(): String = "￥ " + DecimalFormat("#.00").format(orderData.price)

    fun getContact(): String {
        return if (LoginRepository.user!!.id == orderData.order.publisher.id) {
            orderData.order.receiverPhone
        } else {
            orderData.order.publisherPhone
        } ?: "暂无联系方式"
    }

    fun sendComment(): Boolean {
        val stars = stars.toInt()
        if (stars < 1 || stars > 5)
            return false
        else {
            Thread(Runnable {
                try {
                    val sender: Int
                    val receiver: Int
                    if (LoginRepository.user!!.id == orderData.order.publisher.id) {
                        sender = orderData.order.publisher.id
                        receiver = orderData.order.receiver!!.id
                    } else {
                        receiver = orderData.order.publisher.id
                        sender = orderData.order.receiver!!.id
                    }

                    when (CommentDataSource().sendComment(sender, receiver, orderData.order.id, commentText, stars)) {
                        is Result.Success -> {
                            netResult.postValue(true)
                        }
                        else -> {
                            netResult.postValue(false)
                        }
                    }
                } catch (e: NullPointerException) {
                    netResult.postValue(false)
                }
            }).start()
            return true
        }
    }
}