package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.Chat
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatDataRepository {


    companion object {
        fun findChatData(companionID: Int, timeLeftBound: Date?): Result<ArrayList<ChatShowCase.Message>> {
            val params = HashMap<String, String>()
            params["hostID"] = LoginRepository.user?.id.toString()
            params["targetID"] = companionID.toString()
            params["TimeAfter"] =
                    if (timeLeftBound == null) "" else {
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
    }
}