package com.systemvx.androiddevtest.data

import com.systemvx.androiddevtest.data.model.BasicDataSource
import com.systemvx.androiddevtest.data.model.CoinTrans

class CoinDataSource : BasicDataSource() {
    fun viewCoinTrans(accountID: Int): Result<List<CoinTrans>> {
        val params = HashMap<String, String>()
        params["accountID"] = accountID.toString()
        return getDataList("/coin/view", params, CoinTrans::class.java)
    }

    fun makePayment(accountID: Int, amount: Double): Result<String> {
        TODO()
    }

    fun makeWithdraw(accountID: Int, amount: Double): Result<String> {
        TODO()
    }

}
