package com.systemvx.androiddevtest.ui.main.notifications

import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.ChatDataRepository
import com.systemvx.androiddevtest.data.ChatterInfo
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result

class NotificationsViewModel : ViewModel() {
    fun findChatter(): ArrayList<ChatterInfo> {
        val result: Result<ArrayList<ChatterInfo>> = if (!ProjectSettings.netWorkDebug) {
            ChatDataRepository.findChatters(LoginRepository.user!!.id)
        } else {
            ChatDataRepository.findChattersFake()
        }
        return when (result) {
            is Result.Success -> {
                result.data
            }
            is Result.Error -> {
                ArrayList<ChatterInfo>()
            }
        }
    }

}