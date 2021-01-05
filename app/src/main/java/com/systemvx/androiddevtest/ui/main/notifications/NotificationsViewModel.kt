package com.systemvx.androiddevtest.ui.main.notifications

import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.ChatDataSource
import com.systemvx.androiddevtest.data.ChatterInfo
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result

class NotificationsViewModel : ViewModel() {
    fun findChatter(): ArrayList<ChatterInfo> {
        val result: Result<ArrayList<ChatterInfo>> = if (!ProjectSettings.netWorkDebug) {
            ChatDataSource.findChatters(LoginRepository.user!!.id)
        } else {
            ChatDataSource.findChattersFake()
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