package com.systemvx.androiddevtest.data

import android.media.Image
import com.systemvx.androiddevtest.utils.UtilStaticFunc.Companion.randomString
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*

data class UserStorage(
        val id: Int,
        val name: String? = "",
        val nickname: String? = "",
        val signature: String? = "",
        val credit: Int = 0,
        val coin: Double = 0.0,
)

data class OrderBriefing(
        val id: Int,
        val title: String,
        val price: Double,
        val briefing: String,
        val address: String,
        val deadline: Date,
        val type: String,
) {
    val priceStr: String
        get() = "￥${DecimalFormat("0.00").format(price)}"

    val deadlineStr: String
        get() = SimpleDateFormat("yy-M-dd HH:mm", Locale.CHINA).format(deadline).toString()

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
    }
}

interface ChatShowCase {
    data class Message(
            val senderNickName: String,
            val messageText: String,
            val sendTime: Date,
            val isSend: Boolean,
            val senderPortrait: Image? = null,
            val additionalInfo: String? = null,
    ) : ChatShowCase

    data class TimeNote(
            val timeStr: String,
    ) : ChatShowCase
}

