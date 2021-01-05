package com.systemvx.androiddevtest.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.CoinDataSource
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.CoinTrans

class CoinManageViewModel : ViewModel() {
    val netResult = MutableLiveData<Boolean>()

    var coinData = ArrayList<CoinTrans>()

    fun getCoinTrans(accountID: Int) {
        Thread {
            when (val result = CoinDataSource().requestCoinData(accountID)) {
                is Result.Success -> {
                    coinData = result.data
                    netResult.postValue(true)
                }
                else -> {
                    netResult.postValue(false)
                }
            }
        }.start()
    }
}