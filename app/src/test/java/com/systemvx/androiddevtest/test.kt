package com.systemvx.androiddevtest

import com.systemvx.androiddevtest.data.AccountData
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class test {

    @Test
    fun testDate(){
        val a = Date()
        print(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a))
    }
}