package com.systemvx.androiddevtest.ui.credit

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.data.CreditDataSource
import com.systemvx.androiddevtest.data.CreditShowCase
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result

class CreditViewModel : ViewModel() {

    val netResult = MutableLiveData<Boolean>()

    var creditList = ArrayList<CreditShowCase>()

    fun getCredit(): String = (LoginRepository.user?.credit ?: "???").toString()

    fun getJudgeStr(): String {
        val mCredit = LoginRepository.user?.credit
        return when {
            mCredit == null -> "您尚未登录!"
            mCredit >= 800 -> "信用优秀,堪称模范!"
            mCredit >= 700 -> "信用良好,继续保持!"
            mCredit >= 650 -> "信用尚可,请努力遵守约定!"
            mCredit < 650 -> "信用欠佳,要加油了!"
            else -> "这个信用...奇了怪了...."
        }
    }

    fun fetchCreditInfo(): Boolean {
        if (LoginRepository.isLoggedIn) {
            Thread(Runnable {
                when (val result = CreditDataSource().getCreditInfo(LoginRepository.user!!.id)) {
                    is Result.Success -> {
                        val list = ArrayList<CreditShowCase>()
                        for (i in result.data) {
                            list.add(
                                    CreditShowCase(
                                            orderID = i.relatedOrder?.id,
                                            time = i.time,
                                            reason = i.source ?: "系统信用调整",
                                            amount = i.amount
                                    )
                            )
                        }
                        creditList = list
                        netResult.postValue(true)
                    }
                    is Result.Error -> {
                        netResult.postValue(false)
                    }
                }
            }).start()
            return true
        } else {
            return false
        }
    }
}