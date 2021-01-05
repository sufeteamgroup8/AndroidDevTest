package com.systemvx.androiddevtest.ui.chat

import androidx.lifecycle.MutableLiveData
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.ChatDataSource
import com.systemvx.androiddevtest.data.ChatShowCase
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result
import java.util.*
import kotlin.collections.ArrayList

class ChatViewModel {

    var chatterID = 0

    var chatterNickname: MutableLiveData<String> = MutableLiveData("")

    /**
     * 聊天记录列表:顺序应为从新到旧
     */
    var chatHistory = ArrayList<ChatShowCase.Message>()

    /**
     * 网络请求结果
     */
    var dataResult: MutableLiveData<Boolean> = MutableLiveData()

    var sendResult: MutableLiveData<Boolean> = MutableLiveData()


    /**
     * 获取与特定账号的聊天数据.
     *
     * @param chatterID 聊天对方账户ID
     * @param lookUpDays 查询X天内的记录 (0则表示所有记录)
     */
    private fun findChatData(chatterID: Int, lookUpDays: Int) {
        Thread {
            val result = if (ProjectSettings.netWorkDebug) {
                ChatDataSource.findChatDataRand()
            } else {
                if (lookUpDays == 0) {
                    ChatDataSource.findChatData(chatterID, null)
                } else {
                    val calendar = Calendar.getInstance()
                    calendar.add(Calendar.DAY_OF_MONTH, -lookUpDays)
                    calendar.set(Calendar.SECOND, 0)
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    val timeLeftBound: Date = calendar.time
                    ChatDataSource.findChatData(chatterID, timeLeftBound)
                }
            }
            if (result is Result.Success) {
                chatHistory = result.data
                dataResult.postValue(true)
            } else {
                dataResult.postValue(false)
            }
        }.start()
    }

    /**
     * 获取与特定账号的聊天数据.
     *
     * @param chatterID 聊天对方账户ID
     */
    fun findChatData(chatterID: Int) = findChatData(chatterID, 0)

    fun sendMessage(message: String) {
        Thread {
            when (ChatDataSource.sendMessage(LoginRepository.user!!.id, chatterID, message)) {
                is Result.Success -> sendResult.postValue(true)
                is Result.Error -> sendResult.postValue(false)
            }
        }.start()
    }
}