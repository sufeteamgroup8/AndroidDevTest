package com.systemvx.androiddevtest.data

import android.graphics.Picture
import java.util.*

data class AccountData(
        var name: String = "",
        var nickname: String? = null,
        var password: String? = null,
        var signature: String? = null,
        var phone: String? = null,
        var credit: Int? = null,
        var coin: Double? = null,
        var portrait: Picture? = null,
        val id: Int = 0,
        var sex: Int? = null) {
    fun clearData() {
        name = ""
        nickname = null
        password = null
        signature = null
        phone = null
        credit = null
        coin = null
        sex = null
        portrait = null
    }
}

data class OrderData(
        var id: Int = 0,
        var orderState: Int = 0,
        var taskState: Int = 0,
        var publisher: AccountData,
        var receiver: AccountData?
)

data class DetailData(
        var version: Int? = null,
        var title: String,
        var maintext: String,
        var price: Double,
        var type: Int,
        var pubTime: Date,
        var endTime: Date,
        var address: Int,
        var addressStr: String,
        var isFinalVersion: Boolean
)


data class FullOrderData(
        val head: OrderData,
        val body: DetailData?
)

data class ChatData(
        val id: Int? = null,
        var time: Date,
        var sender: AccountData,
        var receiver: AccountData,
        var message: String,
        var isRead: Boolean
)

data class CreditData(
        var id: Int? = null,
        var amount: Int,
        var time: Date,
        var source: String,
        var relatedOrder: OrderData? = null,
        var relatedAccount: AccountData
)

data class CoinData(
        var id: Int? = null,
        var amount: Double,
        var time: Date,
        var relatedOrder: OrderData? = null,
        var sender: AccountData?,
        var receiver: AccountData?
)

data class CommentData(
        val id: Int? = null,
        var sender: AccountData,
        var receiver: AccountData,
        var message: String,
        var star: Int
)

data class ComplaintData(
        val id: Int? = null,
        var time: Date,
        var type: Int,
        var typeStr: String,
        var detailtext: String,
        var star: Int,
        var fromAccount: AccountData,
        var toAccount: AccountData,
        var relatedOrder: OrderData
)

data class MoneyTransactionData(
        val id: Int? = null,
        var aomunt: Double,
        var relatedAccount: AccountData,
        var transMethod: Int,
        val transMethodStr: String,
        val is_charge: Boolean
)