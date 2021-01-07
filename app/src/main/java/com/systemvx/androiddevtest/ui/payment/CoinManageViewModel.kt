package com.systemvx.androiddevtest.ui.payment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.CoinDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.PaymentBriefing
import com.systemvx.androiddevtest.data.Result

class CoinManageViewModel : ViewModel() {
    val netResult = MutableLiveData<Boolean>()

    var coinData = ArrayList<PaymentBriefing>()

    fun getCoinTrans() {
        Thread {
            when (val result = CoinDataSource().viewCoinTrans(LoginRepository.user!!.id)) {
                is Result.Success -> {
                    val output = ArrayList<PaymentBriefing>()
                    for (i in result.data) {
                        output.add(PaymentBriefing(
                                if (i.in_out) -i.amount else i.amount,
                                type = i.relatedOrders?.let { return@let "订单号" + it.id.toString() }
                                        ?: "",
                                i.transTime
                        ))
                    }
                    coinData = output
                    netResult.postValue(true)
                }
                else -> {
                    netResult.postValue(false)
                }
            }
        }.start()
    }
}