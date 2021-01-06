package com.systemvx.androiddevtest.data

import com.systemvx.androiddevtest.data.model.Account
import com.systemvx.androiddevtest.data.model.BasicDataSource
import com.systemvx.androiddevtest.data.model.Chat
import com.systemvx.androiddevtest.utils.HttpUtil
import com.systemvx.androiddevtest.utils.UtilStaticFunc
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class ChatDataSource : BasicDataSource() {

    fun findChatData(companionID: Int, timeLeftBound: Date?): Result<ArrayList<ChatShowCase.Message>> {
        val params = HashMap<String, String>()
        params["hostID"] = LoginRepository.user?.id.toString()
        params["subjectID"] = companionID.toString()
        params["timeLimit"] =
                if (timeLeftBound == null) {
                    "null"
                } else {
                    SimpleDateFormat("yyyy-mm-dd", Locale.CHINA).format(timeLeftBound)
                }

        when (val response = getDataList("/chats/browse", params, Chat::class.java)) {
            is Result.Success -> {
                //重构
                val list = ArrayList<ChatShowCase.Message>()
                val userID = LoginRepository.user!!.id

                for (chat: Chat in response.data) {
                    val myNickname =
                            if (userID == chat.sender.id)
                                LoginRepository.user!!.nickname!!
                            else
                                chat.sender.nickname
                    list.add(
                            ChatShowCase.Message(
                                    chat.id,
                                    myNickname,
                                    chat.message,
                                    chat.sendTime,
                                    (userID == chat.sender.id),
                                    null,
                                    null
                            ))
                }
                return Result.Success(list)
            }
            is Result.Error -> {
                return response
            }
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
        params["accountID"] = LoginRepository.user?.id.toString()
        when (val response = getDataList("/chats/chatters", params, Account::class.java)) {
            is Result.Success -> {
                val list = ArrayList<ChatterInfo>()
                //重构
                for (account: Account in response.data) {
                    list.add(ChatterInfo(
                            account.id,
                            account.nickname,
                            false,
                            ""
                    ))
                }
                return Result.Success(list)
            }
            is Result.Error -> {
                return response
            }
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

    fun sendMessage(id: Int, chatterID: Int, message: String): Result<String> {
        val params = HashMap<String, String>()
        params["senderID"] = id.toString()
        params["receiverID"] = chatterID.toString()
        params["message"] = message
        return getDataSingle("/chats/new", params, String::class.java)
    }


    fun setToRead(messages: ArrayList<ChatShowCase.Message>) {
        for (message in messages) {
            if (message.id != null) {
                val params = HashMap<String, String>()
                params["chatID"] = message.id.toString()
                HttpUtil().postRequest("/chats/isRead", params)
            }
        }
    }


}