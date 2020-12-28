package com.systemvx.androiddevtest.data.model

data class Credit(
        val id: Int?,
        val amount: Int = 0,
        val source: String?,
        val relatedAccount: Account,
        val relatedOrder: Orders?,
)
