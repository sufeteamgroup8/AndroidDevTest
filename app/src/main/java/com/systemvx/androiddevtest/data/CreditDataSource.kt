package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.systemvx.androiddevtest.data.model.Credit
import com.systemvx.androiddevtest.utils.CustomRestfulError
import com.systemvx.androiddevtest.utils.HttpUtil

class CreditDataSource {
    fun getCreditInfo(accountID: Int): Result<ArrayList<Credit>> {
        val params = HashMap<String, String>()
        params["accountID"] = accountID.toString()
        return try {
            val result = JSON.parseObject(HttpUtil().postRequest("/credit/browse", params))
            if (result.getBoolean("success")) {
                val list: ArrayList<Credit> = JSON.parseArray(result["payload"].toString(), Credit::class.java) as ArrayList<Credit>
                Result.Success(list)
            } else {
                Result.Error(CustomRestfulError())
            }
        } catch (e: Exception) {
            Result.Error(CustomRestfulError())
        }
    }

}
