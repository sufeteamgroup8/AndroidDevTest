package com.systemvx.androiddevtest.data

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
        val briefing: String,
        val address: String,
        val deadline: Date,
        val type: String
) {
    companion object {
        fun randomGarbage(): OrderBriefing {
            val rand = Random()
            return OrderBriefing(
                    rand.nextInt(100),
                    randomString(10),
                    randomString(50),
                    randomString(5),
                    Date(),
                    randomString(5)
            )
        }

        fun randomString(length: Int): String {
            var result = ""
            val rand = Random()
            for (index in 1..length) {
                result += (rand.nextInt(26) + 97).toChar()
            }
            return result
        }
    }
}