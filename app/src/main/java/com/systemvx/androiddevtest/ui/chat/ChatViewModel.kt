package com.systemvx.androiddevtest.ui.chat

import androidx.lifecycle.MutableLiveData
import com.systemvx.androiddevtest.data.ChatDataRepository
import com.systemvx.androiddevtest.data.ChatShowCase
import java.util.*

class ChatViewModel {


    /**
     * 聊天记录列表:顺序应为从旧到新
     */
    val chatHistory: MutableLiveData<ArrayList<ChatShowCase.Message>> = MutableLiveData()

    /**
     * 网络请求结果
     */
    val dataResult: MutableLiveData<Result<Any>> = MutableLiveData()


    /**
     * 获取与特定账号的聊天数据.
     *
     * @param chatterID 聊天对方账户ID
     * @param lookUpDays 查询X天内的记录 (0则表示所有记录)
     */
    fun findChatData(chatterID: Int, lookUpDays: Int) {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -lookUpDays)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        val timeLeftBound: Date = calendar.time
        ChatDataRepository.findChatData(chatterID, timeLeftBound)
    }

    /**
     * 获取与特定账号的聊天数据.
     *
     * @param chatterID 聊天对方账户ID
     */
    fun findChatData(chatterID: Int) = findChatData(chatterID, 0)
}