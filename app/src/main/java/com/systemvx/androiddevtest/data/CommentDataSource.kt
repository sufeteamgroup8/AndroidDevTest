package com.systemvx.androiddevtest.data

import com.systemvx.androiddevtest.data.model.BasicDataSource

class CommentDataSource : BasicDataSource() {

    fun sendComment(
            senderID: Int,
            receiverID: Int,
            orderID: Int,
            message: String,
            stars: Int,
    ): Result<String> {
        val params = HashMap<String, String>()
        params["senderID"] = senderID.toString()
        params["receiverID"] = receiverID.toString()
        params["orderID"] = orderID.toString()
        params["message"] = message
        params["stars"] = stars.toString()
        return getDataSingle("/comments/new", params, String::class.java)
    }
}