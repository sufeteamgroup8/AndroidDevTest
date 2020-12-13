package com.systemvx.androiddevtest

import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*
import java.util.HashMap

class test {

    @Test
    fun testDate() {
        val a = Date()
        print(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a))
    }

    @Test
    fun testHttpGET() {
        var response = HttpUtil().getRequest("http://localhost:8080/testget?param1=something")
        Assert.assertEquals("restful success, data=something",response)
        val data = HashMap<String,String>()
        data["param1"] = "something"
        response = HttpUtil().postRequest("http://localhost:8080/testpost", data)
        Assert.assertEquals("restful success, data=something",response)
    }


}