package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil

class CommentDataSource {

    fun sendComment(senderID: Int, receiverID: Int, orderID: Int, message: String, stars: Int): Result<Boolean> {
        val params = HashMap<String, String>()
        params["senderID"] = senderID.toString()
        params["receiverID"] = receiverID.toString()
        params["orderID"] = orderID.toString()
        params["message"] = message
        params["stars"] = stars.toString()
        return try {
            val result = JSON.parseObject(HttpUtil().postRequest("/Comments/new", params))
            if (result.getBoolean("success")) {
                Result.Success(true)
            } else {
                Result.Error(CustomRestfulError(result.getString("error")))
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }
}