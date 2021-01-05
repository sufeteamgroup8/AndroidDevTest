package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil

class ComplaintDataSource {

    fun sendComplaint(senderID: Int, receiverID: Int, orderID: Int, message: String): Result<Boolean> {
        val params = HashMap<String, String>()
        params["senderID"] = senderID.toString()
        params["receiverID"] = receiverID.toString()
        params["orderID"] = orderID.toString()
        params["message"] = message
        return try {
            val result = JSON.parseObject(HttpUtil().postRequest("/Complaints/new", params))
            if (result.getBoolean("success")) {
                Result.Success(true)
            } else {
                Result.Error(CustomRestfulError(result.getString("error")))
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }
    fun viewComplaint( orderID: Int ): Result<Boolean> {
        val params = HashMap<String, String>()

        params["orderID"] = orderID.toString()
        return try {
            val result = JSON.parseObject(HttpUtil().postRequest("/Complaint/browseSender", params))
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