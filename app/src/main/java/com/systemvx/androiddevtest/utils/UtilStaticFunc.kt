package com.systemvx.androiddevtest.utils

import android.content.Context
import android.widget.Toast
import java.util.*

class UtilStaticFunc {
    companion object {
        fun randomString(length: Int): String {
            var result = ""
            val rand = Random()
            for (index in 1..length) {
                result += (rand.nextInt(26) + 97).toChar()
            }
            return result
        }

        fun netWorkErrorToast(context: Context) {
            Toast.makeText(context, "无法获取网络数据.请检查网络连接", Toast.LENGTH_LONG).show()
        }
    }
}