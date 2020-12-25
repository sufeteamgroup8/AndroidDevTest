package com.systemvx.androiddevtest.ui.main.personalcenter

import android.content.Context
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import java.text.DecimalFormat

class PersonalCenterViewModel(val context: Context) : ViewModel() {
    companion object {
        const val CREDIT_LOW_BOUNDARY = 670
        const val CREDIT_HIGH_BOUNDARY = 800
    }


    val userName: String
        get() {
            var result = ""
            if (LoginRepository.isLoggedIn) {
                LoginRepository.user?.name?.let { result = it }
            }
            return result
        }

    val userNickname: String
        get() {
            var result = ""
            return if (LoginRepository.isLoggedIn) {
                LoginRepository.user?.nickname?.let { result = it }
                result
            } else {
                "尚未登录"
            }
        }

    val userCoinStr: String
        get() {
            val result = context.getString(R.string.coin_hint)
            return if (LoginRepository.isLoggedIn) {
                result + DecimalFormat("0.00").format(LoginRepository.user!!.coin)
            } else {
                result
            }
        }

    val userCreditStr: String
        get() {
            return if (!LoginRepository.isLoggedIn) {
                ""
            } else {
                val credit = LoginRepository.user!!.credit
                when {
                    credit < CREDIT_LOW_BOUNDARY -> "信用较差\n$credit"
                    credit >= CREDIT_HIGH_BOUNDARY -> "信用优秀\n$credit"
                    else -> "信用良好\n$credit"
                }
            }
        }

    val userSignature: String
        get() {
            return if (LoginRepository.isLoggedIn) {
                val sign = LoginRepository.user!!.signature
                if (sign.isNullOrBlank()) {
                    "这个家伙很神秘,什么都没有写"
                } else {
                    sign
                }
            } else {
                ""
            }
        }

    fun getCreditColor(): Int {
        return if (!LoginRepository.isLoggedIn) {
            context.getColor(R.color.colo_blue)
        } else {
            val credit = LoginRepository.user!!.credit
            when {
                credit < CREDIT_LOW_BOUNDARY -> context.getColor(R.color.colo_blue)
                else -> context.getColor(R.color.colo_blue)
            }
        }
    }

}