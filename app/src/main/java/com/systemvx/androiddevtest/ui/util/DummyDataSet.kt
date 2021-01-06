package com.systemvx.androiddevtest.ui.util

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.UserStorage
import com.systemvx.androiddevtest.data.model.OrderDetail

object DummyDataSet {
    val fakeUser = UserStorage(9999, "alyce",
            "ALYCE",
            "Hello world by alice",
            700,
            123.5
    )
    val dummyDetail: OrderDetail = JSON.parseObject("{\n" +
            "  \"id\": 1,\n" +
            "  \"order\": {\n" +
            "    \"id\": 1000,\n" +
            "    \"state\": {\n" +
            "      \"id\": 1,\n" +
            "      \"text\": \"已发布\"\n" +
            "    },\n" +
            "    \"taskState\": 0,\n" +
            "    \"receiveTime\": null,\n" +
            "    \"publisher\": {\n" +
            "      \"id\": 1000,\n" +
            "      \"name\": \"alyce\",\n" +
            "      \"password\": \"12345678\",\n" +
            "      \"phoneNo\": null,\n" +
            "      \"nickname\": \"ALYCE\",\n" +
            "      \"coin\": 100.0,\n" +
            "      \"credit\": 700,\n" +
            "      \"signature\": null,\n" +
            "      \"states\": 0\n" +
            "    },\n" +
            "    \"receiver\": null,\n" +
            "    \"finalPublishTime\": null,\n" +
            "    \"closedTime\": null,\n" +
            "    \"receiverPhone\": null,\n" +
            "    \"publisherPhone\": null\n" +
            "  },\n" +
            "  \"missionType\": {\n" +
            "    \"id\": 0,\n" +
            "    \"text\": \"其他\"\n" +
            "  },\n" +
            "  \"isFinal\": false,\n" +
            "  \"title\": \"title\",\n" +
            "  \"price\": 9.99,\n" +
            "  \"address\": 3,\n" +
            "  \"mainText\": \"maintext\",\n" +
            "  \"pubTime\": \"2021-01-05\",\n" +
            "  \"deadline\": \"2021-01-21\"\n" +
            "}", OrderDetail::class.java)
}