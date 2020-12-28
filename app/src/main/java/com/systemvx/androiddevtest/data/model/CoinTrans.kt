package com.systemvx.androiddevtest.data.model

import java.sql.Date

data class CoinTrans(
        val account: Account,
        val isSendToSys: Boolean,
        val relatedOrders: Orders? = null,
        val id: Int? = null,
        val amount: Double = 0.0,

        val transTime: Date?,


        )
