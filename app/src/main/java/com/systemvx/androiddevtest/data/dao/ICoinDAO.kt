package com.systemvx.androiddevtest.data.dao

import android.service.autofill.UserData
import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.CoinData
import com.systemvx.androiddevtest.data.OrderData
import java.util.Date

interface ICoinDAO {

    fun makeUser2UserCoinTrans(transaction: CoinData)

    fun makeUser2SysCoinTrans(transaction: CoinData)

    fun findTransRelatedToAccount(account: AccountData, endTime: Date = Date(), lookupMonth: Int = 2): List<CoinData>

    fun findTransRelatedToOrder(order: OrderData): List<CoinData>

    fun showTrans(startTime: Date = Date(0), endTime: Date = Date()): List<CoinData>
}