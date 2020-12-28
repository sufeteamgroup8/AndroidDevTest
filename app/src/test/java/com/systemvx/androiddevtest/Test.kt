package com.systemvx.androiddevtest

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.data.model.Account
import com.systemvx.androiddevtest.utils.HttpUtil
import org.junit.Assert
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.*

class Test {

    @Test
    fun testDate() {
        val a = Date()
        print(SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(a))
    }

    @Test
    fun testHttpGET() {
        var response = HttpUtil().getRequest("http://localhost:8080/testget?param1=something")
        Assert.assertEquals("restful success, data=something", response)

    }

    @Test
    fun testOrderBriefing() {
        val result = OrderBriefing.randomGarbage()
        println(result)
    }

    @Test
    fun testJSONReturn() {
        try {
            HttpUtil.useAbsoluteURL = true
            val data = HashMap<String, String>()
            data["param1"] = "something"
            val response = HttpUtil().postRequest("http://localhost:8080/testjson", data)
            val json = JSON.parseObject(response)
            val test = json.getString("error")
            val dao = JSON.parseObject(JSON.toJSONString(json["payload"]), Account::class.java)
            println(test)
            println(dao)
        } catch (e: Exception) {
            e.printStackTrace()
            throw e
        }
    }

    @Test
    fun testJSonParse() {
        val data = "{\"password\":\"\",\"signature\":\"\",\"name\":\"something\",\"nickname\":\"\",\"credit\":700,\"phoneNo\":\"\",\"coin\":0.0}"

        var daoStr = JSON.toJSONString(Account(45, "aaaaaa", "bbbbbbbbb"))
        println(JSON.parseObject(daoStr, Account::class.java))
    }

}