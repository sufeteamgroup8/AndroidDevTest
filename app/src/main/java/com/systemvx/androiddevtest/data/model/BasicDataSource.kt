package com.systemvx.androiddevtest.data.model

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil
import java.util.*


open class BasicDataSource {

    protected fun <T> getDataSingle(url: String, params: HashMap<String, String>, clazz: Class<T> =): Result<T> {
        return try {
            val response = JSON.parseObject(HttpUtil().postRequest("/account/updateInfo", params))
            when (response.getBoolean("success")) {
                true -> Result.Success(JSON.parseObject(response["payload"].toString(), clazz))
                false -> Result.Error(Exception(response.getString("error")))
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }

    protected fun <T> getDataList(url: String, params: HashMap<String, String>, clazz: Class<T>): Result<List<T>> {
        return try {
            val response = JSON.parseObject(HttpUtil().postRequest("/account/updateInfo", params))
            when (response.getBoolean("success")) {
                true -> Result.Success(JSON.parseArray(response["payload"].toString(), clazz))
                false -> Result.Error(Exception(response.getString("error")))
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }
}
