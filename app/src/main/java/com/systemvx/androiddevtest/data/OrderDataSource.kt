package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.utils.HttpUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class OrderDataSource {

    fun getAllOrderTypes(): HashMap<String, Int> {
        TODO("Not implemented")
    }

    fun searchOrder(searchStr: String?, priceMin: Double?, priceMax: Double?, timeBefore: Date?, hardMatch: Boolean = true): Result<ArrayList<OrderBriefing>> {
        when (ProjectSettings.netWorkDebug) {
            true -> {
                //目前随机生成点占位数据
                val temp = ArrayList<OrderBriefing>()
                for (i in 1..10) {
                    temp.add(OrderBriefing.randomGarbage())
                }
                return Result.Success(temp)
            }
            false -> {
                val params = HashMap<String, String>()
                params["title"] = searchStr ?: ""
                params["minPrice"] = priceMin?.toString() ?: 0.0.toString()
                params["maxPrice"] = priceMax?.toString() ?: 9999.0.toString()
                params["endTime"] = when (timeBefore) {
                    null -> ""
                    else -> SimpleDateFormat("yyyy-MM-dd", Locale.CHINA).format(timeBefore)
                }
                try {
                    val response = JSON.parse(HttpUtil().postRequest("TODO()", params)) as JSONObject
                    return when (response.getBoolean("success")) {
                        true -> {
                            val result = ArrayList<OrderBriefing>()
                            val listJson = response.getJSONArray("payload")
                            for (order in listJson) {
                                val temp = order as JSONObject
                                val cl = OrderBriefing(
                                        id = temp.getIntValue("id"),
                                        title = temp.getString("title"),
                                        briefing = temp.getString("maintext").substring(0, 40),
                                        price = temp.getDoubleValue("price"),
                                        deadline = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(temp.getString("deadline")),
                                        address = this.getAddressStr(temp.getIntValue("address")),
                                        type = this.getTypeStr(temp.getIntValue("type")),
                                        state = temp.getIntValue("state")
                                )
                                result.add(cl)
                            }
                            Result.Success(result)
                        }
                        false -> {
                            Result.Error(Exception(response.getString("error")))
                        }
                    }
                } catch (e: Exception) {
                    return Result.Error(Exception("internal error"))
                }
            }
        }
    }


    fun getOrderByPublisher(accountID: Int): Result<ArrayList<OrderBriefing>> {
        when (ProjectSettings.netWorkDebug) {
            true -> {
                //目前随机生成点占位数据
                val temp = ArrayList<OrderBriefing>()
                for (i in 1..20) {
                    temp.add(OrderBriefing.randomGarbage())
                }
                return Result.Success(temp)
            }
            false -> {
                val params = HashMap<String, String>()
                params["id"] = accountID.toString()
                try {
                    val response = JSON.parse(HttpUtil().postRequest("TODO()", params)) as JSONObject
                    return when (response.getBoolean("success")) {
                        true -> {
                            val result = ArrayList<OrderBriefing>()
                            val listJson = response.getJSONArray("payload")
                            for (order in listJson) {
                                val temp = order as JSONObject
                                val cl = OrderBriefing(
                                        id = temp.getIntValue("id"),
                                        title = temp.getString("title"),
                                        briefing = temp.getString("maintext").substring(0, 40),
                                        price = temp.getDoubleValue("price"),
                                        deadline = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA).parse(temp.getString("deadline")),
                                        address = this.getAddressStr(temp.getIntValue("address")),
                                        type = this.getTypeStr(temp.getIntValue("type")),
                                        state = temp.getIntValue("state")
                                )
                                result.add(cl)
                            }
                            Result.Success(result)
                        }
                        false -> {
                            Result.Error(Exception(response.getString("error")))
                        }
                    }
                } catch (e: Exception) {
                    return Result.Error(Exception("internal error"))
                }
            }
        }
    }
    private fun getTypeStr(typeCode: Int): String {
        TODO()
    }

    private fun getAddressStr(addressCode: Int): String {
        TODO("Not yet implemented")
    }
}