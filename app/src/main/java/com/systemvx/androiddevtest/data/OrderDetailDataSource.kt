package com.systemvx.androiddevtest.data

import android.util.Log
import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil

class OrderDetailDataSource {
    @Suppress("PrivatePropertyName")
    private val TAG = "OrderDetailDataSource"

    fun getOrderFullData(orderID: Int): Result<OrderDetail> {
        val params = HashMap<String, String>()
        return try {
            params["OrderID"] = orderID.toString()
            val result = JSON.parseObject(HttpUtil().postRequest("/Orders/getDetail", params))
            if (result.getBoolean("success")) {
                val detail = JSON.parseObject(result["payload"].toString(), OrderDetail::class.java)
                Result.Success(detail)
            } else {
                Result.Error(CustomRestfulError())
            }

        } catch (e: Exception) {
            Log.d(TAG, "getOrderFullData: Error")
            Result.Error(CustomRestfulError("parseError"))
        }
    }

}
