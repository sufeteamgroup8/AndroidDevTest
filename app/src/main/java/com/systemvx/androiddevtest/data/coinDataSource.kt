package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.CoinTrans
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil

class coinDataSource {
    fun viewCoin(UserId: Int): Result<ArrayList<CoinTrans>> {
        val params = HashMap<String, String>()

        params["accountID"] = UserId.toString()
        return try {
            val result = JSON.parseObject(HttpUtil().postRequest("/Coin/View", params))
            if (result.getBoolean("success")) {
                val list =JSON.parseArray(result["payload"].toString(),CoinTrans::class.java) as ArrayList
                Result.Success(list)
            } else {
                Result.Error(CustomRestfulError(result.getString("error")))
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }

    fun addCoin(


            userID: Int, orderID: Int,
            amount: Double,
            in_out: Boolean,
            sys: Boolean,
    ): Result<Boolean> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["amount"] = amount.toString()
        params["in_out"] = in_out.toString()
        params["sys"] = sys.toString()
        params["accountID"] = userID.toString()
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