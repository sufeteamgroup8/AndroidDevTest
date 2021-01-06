package com.systemvx.androiddevtest.data

import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.ui.util.DummyDataSet

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 *
 * 该类用于在内存中保存登录状态信息以及账户基本详情
 */

class LoginRepository(val dataSource: AccountDataSource) {

    companion object {
        // in-memory cache of the User object
        //对外部只读,不可修改
        var user: UserStorage? = null
            private set
            get() = if (ProjectSettings.fakeData) DummyDataSet.fakeUser else user

        val isLoggedIn: Boolean
            get() = user != null
    }

    init {
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
        user = null
    }

    fun logout() {
        user = null
        dataSource.logout()
    }

    fun login(username: String, password: String): Boolean {
        // handle login
        val result = dataSource.login(username, password)

        return if (result is Result.Success) {
            user = result.data
            true
        } else {
            false
        }
    }

    fun encryptPassword(password: String): String {
        return password
    }


}