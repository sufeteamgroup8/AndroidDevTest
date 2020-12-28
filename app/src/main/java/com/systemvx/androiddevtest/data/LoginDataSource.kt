package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.utils.HttpUtil

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
                    Thread.sleep(3 * 1000)
                    val fakeUser = User(9999, "alyce",
                            "ALYCE",
                            "Hello world by alice",
                            700,
                            123.5
                    )
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
                try {
                    val response = JSON.parse(HttpUtil().postRequest("${HttpUtil.BASE_URL}/account/login", data)) as JSONObject
                    //拆包重组成前端显示用格式
                    return when (response.getBoolean("success")) {
                        true -> {
                            val userJson = response.getJSONObject("payload")

                            var nickname = ""
                            try {
                                nickname = userJson.getString("nickname")
                            } catch (e: Exception) {
                            }
                            var signature = ""
                            try {
                                signature = userJson.getString("signature")
                            } catch (e: Exception) {
                            }
                            val user = User(
                                    userJson.getIntValue("id"),
                                    userJson.getString("name"),
                                    nickname,
                                    signature,
                                    userJson.getIntValue("credit"),
                                    userJson.getDouble("coin")
                            )
                            Result.Success(user)
                        }
                        false -> {
                            Result.Error(Exception(response.getString("error")))
                        }
                    }
                } catch (e: Exception) {
                    return Result.Error(Exception("connection Error"))
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