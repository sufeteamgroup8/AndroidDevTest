package com.systemvx.androiddevtest.utils

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
    }
}