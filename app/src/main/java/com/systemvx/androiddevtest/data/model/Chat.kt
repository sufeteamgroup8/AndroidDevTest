package com.systemvx.androiddevtest.data.model

import java.sql.Date

data class Chat(
        val sender: Account,
        val receiver: Account,

        val id: Int?,
        val message: String = "",
        var isRead: Boolean = false,

        val sendTime: Date? = null,
)
