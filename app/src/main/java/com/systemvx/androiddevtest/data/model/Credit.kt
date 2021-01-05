package com.systemvx.androiddevtest.data.model

import java.io.Serializable
import java.util.*

data class Credit(
        val id: Int?,
        val amount: Int = 0,
        val source: String?,
        val relatedAccount: Account,
        val relatedOrder: Orders?,
        val time: Date,
) : Serializable
