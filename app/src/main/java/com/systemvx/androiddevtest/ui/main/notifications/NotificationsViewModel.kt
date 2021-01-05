package com.systemvx.androiddevtest.ui.main.notifications

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.ChatDataSource
import com.systemvx.androiddevtest.data.ChatterInfo
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result

class NotificationsViewModel : ViewModel() {

    var data = ArrayList<ChatterInfo>()

    val natResult = MutableLiveData<Boolean>()

    fun findChatter(): Boolean {
        Thread {
            val result: Result<ArrayList<ChatterInfo>> =
                    if (!ProjectSettings.netWorkDebug) {
                        ChatDataSource.findChatters(LoginRepository.user!!.id)
                    } else {
                        ChatDataSource.findChattersFake()
                    }
            when (result) {
                is Result.Success -> {
                    data = result.data
                    natResult.postValue(true)
                }
                is Result.Error -> {
                    natResult.postValue(false)
                }
            }
        }.start()
        return true
    }

}