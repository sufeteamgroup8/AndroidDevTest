package com.systemvx.androiddevtest.data.model

import java.io.Serializable
import java.sql.Date

data class Comment(
        val sender: Account,
        val receiver: Account,
        val relatedOrder: Orders,
        val id: Int? = null,
        val message: String = "",
        val stars: Int = 3,
        val sendTime: Date? = null,
) : Serializable
