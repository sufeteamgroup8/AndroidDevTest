package com.systemvx.androiddevtest.data.model

import java.io.Serializable
import java.sql.Date

data class CoinTrans(
        val account: Account,
        val in_out: Boolean,//false转出，true转进
        val isRelateToSys: Boolean,//是否是充值提现操作引发的
        val relatedOrders: Orders?,
        val id: Int,
        val amount: Double,
        val transTime: Date,
) : Serializable
