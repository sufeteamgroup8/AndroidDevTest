package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.Chat
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil
import com.systemvx.androiddevtest.utils.UtilStaticFunc
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatDataSource {


    companion object {
        fun findChatData(companionID: Int, timeLeftBound: Date?): Result<ArrayList<ChatShowCase.Message>> {
            val params = HashMap<String, String>()
            params["hostID"] = LoginRepository.user?.id.toString()
            params["targetID"] = companionID.toString()
            params["TimeAfter"] =
                    if (timeLeftBound == null)
                        ""
                    else {
                        SimpleDateFormat("yyyy-mm-dd", Locale.CHINA).format(timeLeftBound)
                    }
            try {
                val response = JSON.parseObject(HttpUtil().postRequest("/chat/find", params))
                if (response.getBoolean("success")) {
                    //TODO 打包数据
                    val list = ArrayList<ChatShowCase.Message>()
                    val dataPack = response.getJSONArray("payload")
                    for (dataItem in dataPack) {
                        val temp = JSON.parseObject(dataItem.toString(), Chat::class.java)

                    }
                    TODO()
                } else {
                    return Result.Error(CustomRestfulError(response.getString("error")))
                }
            } catch (e: Exception) {
                return Result.Error(CustomRestfulError())
            }

        }

        /**
         * 随便编点数据
         */
        fun findChatDataRand(): Result<ArrayList<ChatShowCase.Message>> {
            val messages = ArrayList<ChatShowCase.Message>()
            //固定部分
            val sender = "sender alyce"
            //发送时间的随机生成器
            val rand = Random(11111)
            //从2天前开始
            val calendar = Calendar.getInstance(Locale.CHINA)
            calendar.add(Calendar.DAY_OF_MONTH, -2)
            messages.add(ChatShowCase.Message(null, sender, UtilStaticFunc.randomString(40), calendar.time, false))
            for (i in 2..10) {
                //随机过去n分钟 (0-6小时内)
                calendar.add(-Calendar.MINUTE, rand.nextInt(360) - 1)
                //决定是谁发的信息
                if (rand.nextBoolean()) {
                    //发来的信息
                    messages.add(ChatShowCase.Message(
                            null,
                            sender,
                            UtilStaticFunc.randomString(40),
                            calendar.time,
                            false
                    ))
                } else {
                    //自己发送的信息
                    messages.add(ChatShowCase.Message(
                            null,
                            LoginRepository.user!!.nickname ?: "",
                            UtilStaticFunc.randomString(40),
                            calendar.time,
                            true
                    ))
                }

                if (i == 5) {
                    //空白间隙
                    calendar.add(Calendar.DAY_OF_MONTH, -4)
                }
            }
            return Result.Success(messages)
        }

        fun findChatters(accountID: Int): Result<ArrayList<ChatterInfo>> {
            val params = HashMap<String, String>()
            params["hostID"] = LoginRepository.user?.id.toString()
            try {
                val response = JSON.parseObject(HttpUtil().postRequest("/chat/find", params))
                if (response.getBoolean("success")) {
                    //TODO 打包数据
                    val list = ArrayList<ChatShowCase.Message>()
                    val dataPack = response.getJSONArray("payload")
                    for (dataItem in dataPack) {
                        val temp = JSON.parseObject(dataItem.toString(), Chat::class.java)
                    }
                    TODO()
                } else {
                    return Result.Error(CustomRestfulError(response.getString("error")))
                }
            } catch (e: Exception) {
                return Result.Error(CustomRestfulError())
            }
        }

        fun findChattersFake(): Result<ArrayList<ChatterInfo>> {
            val data = ArrayList<ChatterInfo>()
            for (i in 1..10) {
                data.add(ChatterInfo(
                        Random().nextInt(40),
                        UtilStaticFunc.randomString(10),
                        Random().nextBoolean()
                ))
            }
            return Result.Success(data)
        }

        /**
         * @throws Exception
         */
        fun sendMessage(id: Int, chatterID: Int, message: String): Result<Boolean> {
            try {
                val params = HashMap<String, String>()
                params["senderID"] = id.toString()
                params["receiverID"] = chatterID.toString()
                params["message"] = message
                val result = JSON.parseObject(HttpUtil().postRequest("/Chats/new", params))
                return if (result.getBoolean("success")) {
                    Result.Success(true)
                } else {
                    Result.Error(Exception(result.getString("error")))
                }
            } catch (e: java.lang.Exception) {
                return Result.Error(CustomRestfulError())
            }
        }


        fun setToRead(messages: ArrayList<ChatShowCase.Message>) {
            for (message in messages) {
                if (message.id != null) {
                    val params = HashMap<String, String>()
                    params["chatID"] = message.id.toString()
                    params["isRead"] = true.toString()
                    HttpUtil().postRequest("/Chats/isRead", params)
                }
            }
        }
    }


}