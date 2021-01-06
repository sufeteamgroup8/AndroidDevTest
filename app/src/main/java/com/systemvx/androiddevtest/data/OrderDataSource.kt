package com.systemvx.androiddevtest.data

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.model.BasicDataSource
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.utils.HttpUtil
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OrderDataSource : BasicDataSource() {


    fun newOrder(
            publisherID: Int,
            title: String,
            mainText: String,
            taskType: Int,
            price: Double,
            deadline: java.sql.Date,
            addressID: Int,
    ): Result<String> {
        val params = HashMap<String, String>()
        params["publisherID"] = publisherID.toString()
        params["title"] = title
        params["mainText"] = mainText
        params["taskType"] = taskType.toString()
        params["price"] = price.toString()
        params["deadline"] = deadline.toString()
        params["addressID"] = addressID.toString()
        return getDataSingle("/order/neworder", params, String::class.java)
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
                    val response = JSON.parseObject(HttpUtil().postRequest("TODO()", params))
                    return when (response.getBoolean("success")) {
                        true -> {
                            val result = ArrayList<OrderBriefing>()
                            val listJson = JSON.parseArray(response.get("payload").toString(), OrderDetail::class.java)
                            for (detail in listJson) {
                                val cl = OrderBriefing(
                                        id = detail.id,
                                        title = detail.title,
                                        briefing = detail.mainText.substring(0, 40),
                                        price = detail.price,
                                        deadline = detail.deadline,
                                        address = detail.address.toString(),//TODO
                                        type = detail.missionType.text,
                                        state = detail.order.state.id
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
                            val listJson = JSON.parseArray(response.get("payload").toString(), OrderDetail::class.java)
                            for (detail in listJson) {
                                val cl = OrderBriefing(
                                        id = detail.id,
                                        title = detail.title,
                                        briefing = detail.mainText.substring(0, 40),
                                        price = detail.price,
                                        deadline = detail.deadline,
                                        address = detail.address.toString(),//TODO
                                        type = detail.missionType.text,
                                        state = detail.order.state.id
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


    fun getOrderState(id: String): Result<ArrayList<OrderStateBean>> {
        val params = HashMap<String, String>()
        try {
            val response = JSON.parse(HttpUtil().postRequest("TODO()", params)) as JSONObject
            return when (response.getBoolean("success")) {
                true -> {
                    val result = ArrayList<OrderStateBean>()
                    val listJson = response.getJSONArray("orderState")
                    for (state in listJson) {
                        val tem = state as JSONObject
                        val orderStateBean = OrderStateBean()
                        orderStateBean.id = tem.getString("id")
                        orderStateBean.orderstate = tem.getString("orderstate")
                    }
                    Result.Success(result)
                }
                false -> {
                    Result.Error(Exception(response.getString("error")))
                }
            }
        } catch (e: java.lang.Exception) {
            return Result.Error(Exception("internal error"))
        }

    }

    fun changeOrderToState(orderID: Int, state: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

    fun completeOrder(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["accountID"] = accountID.toString()
        return getDataSingle("/order/confirmFinish", params, String::class.java)
    }

    fun pushForwardTask(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["receiverID"] = accountID.toString()
        return getDataSingle("/order/updateTaskState", params, String::class.java)
    }

    fun abortOrder(orderID: Int): Result<Boolean> {
        TODO("Not yet implemented")
    }

    fun acceptOrder(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["accountID"] = accountID.toString()
        return getDataSingle("/order/acceptOrder", params, String::class.java)
    }

    fun getOrderFullData(orderID: Int): Result<OrderDetail> {
        val params = HashMap<String, String>()
        params["OrderID"] = orderID.toString()
        return getDataSingle("/order/getDetail", params, OrderDetail::class.java)
    }

    companion object {
        const val TAG = "OrderDataSource"
    }
}