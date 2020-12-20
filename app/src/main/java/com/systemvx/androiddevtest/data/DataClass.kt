package com.systemvx.androiddevtest.data

import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class User(
        val id: Int,
        val name: String = "",
        val nickname: String = "",
        val signature: String = "",
)

data class OrderBriefing(
        val id: Int,
        val title: String,
        val price: Double,
        val briefing: String,
        val address: String,
        val deadline: Date,
        val type: String
) {
    val priceStr: String
        get() = "￥${DecimalFormat("#.00").format(price)}"

    val deadlineStr: String
        get() = SimpleDateFormat("yy-M-dd HH:mm").format(deadline).toString()

    companion object {
        fun randomGarbage(): OrderBriefing {
            val rand = Random()
            return OrderBriefing(rand.nextInt(100),
                    randomString(10),
                    rand.nextDouble() * 20,
                    randomString(50),
                    randomString(5),
                    Date(rand.nextLong()),
                    randomString(5)
            )
        }

        private fun randomString(length: Int): String {
            var result = ""
            val rand = Random()
            for (index in 1..length) {
                result += (rand.nextInt(26) + 97).toChar()
            }
            return result
        }
    }
}