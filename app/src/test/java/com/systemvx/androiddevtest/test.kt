package com.systemvx.androiddevtest

import com.systemvx.androiddevtest.data.AccountData
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class test {

    @Test
    fun testDate() {
        val a = Date()
        print(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a))
    }

    @Test
    fun testHttpGET() {
        val response = HttpUtil().getRequest("http://localhost:8080/func1?param1=something")
        println(response)
    }
}