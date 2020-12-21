package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.systemvx.androiddevtest.HttpUtil
import com.systemvx.androiddevtest.ProjectSettings

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {


    fun login(username: String, password: String): Result<User> {
        when (ProjectSettings.netWorkDebug) {
            // TODO: handle loggedInUser authentication
            true -> {
                //返回一个假用户
                return try {
                    //模拟时延
                    Thread.sleep(2 * 1000)
                    val fakeUser = User(1, "alyce", "ALYCE", "Hello world by alice")
                    Result.Success(fakeUser)
                } catch (e: Throwable) {
                    Result.Error(Exception("Error logging in", e))
                }
            }
            false -> {
                // 数据打包
                val data = HashMap<String, String>()
                data["name"] = username
                data["passwd"] = password
                // 查询,获取结果字符串并转化为json
                val response = JSON.parse(HttpUtil().postRequest("${HttpUtil.BASE_URL}/account/login", data)) as JSONObject

                //拆包重组成前端显示用格式
                return when (response.getBoolean("success")) {
                    true -> {
                        val userJson = response.getJSONObject("payload")
                        val user = User(
                                userJson.getIntValue("id"),
                                userJson.getString("name"),
                                userJson.getString("nickname"),
                                userJson.getString("signature")
                        )
                        Result.Success(user)
                    }
                    false -> {
                        Result.Error(Exception(response.getString("error")))
                    }
                }
            }
        }
    }

    fun register(username: String, password: String, studentNo: String): Result<Boolean> {
        when (ProjectSettings.netWorkDebug) {
            true -> {
                return Result.Success(true)
            }
            false -> {
                val data = HashMap<String, String>()
                data["name"] = username
                data["passwd"] = password
                data["studentNo"] = studentNo
                val response = JSON.parse(HttpUtil().postRequest("${HttpUtil.BASE_URL}/account/register", data)) as JSONObject
                return when (response.getBoolean("success")) {
                    true -> Result.Success(true)
                    false -> Result.Error(java.lang.Exception(response.getString("error")))
                }
            }
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}