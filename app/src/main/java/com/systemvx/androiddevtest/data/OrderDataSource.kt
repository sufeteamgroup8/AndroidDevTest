package com.systemvx.androiddevtest.data

import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.data.model.BasicDataSource
import com.systemvx.androiddevtest.data.model.OrderDetail
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class OrderDataSource : BasicDataSource() {

    fun updateDraft(
            orderID: Int,
            title: String,
            mainText: String,
            taskType: Int,
            price: Double,
            deadline: Date,
            addressID: Int,
    ): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["title"] = title
        params["mainText"] = mainText
        params["taskType"] = taskType.toString()
        params["price"] = price.toString()
        params["deadline"] = deadline.toString()
        params["addressID"] = addressID.toString()
        return getDataSingle("/order/redraft", params, String::class.java)
    }

    fun newOrder(
            publisherID: Int,
            title: String,
            mainText: String,
            taskType: Int,
            price: Double,
            deadline: Date,
            addressID: Int,
            state: Int,
    ): Result<String> {
        val params = HashMap<String, String>()
        params["publisherID"] = publisherID.toString()
        params["title"] = title
        params["mainText"] = mainText
        params["taskType"] = taskType.toString()
        params["price"] = price.toString()
        params["deadline"] = deadline.toString()
        params["addressID"] = addressID.toString()
        params["state"] = state.toString()
        return getDataSingle("/order/neworder", params, String::class.java)
    }

    fun searchOrder(
            accountID: Int?,
            searchStr: String?,
            priceMin: Double?,
            priceMax: Double?,
            timeBefore: Date?,
            hardMatch: Boolean = true,
    ): Result<ArrayList<OrderBriefing>> {
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
                params["accountID"] = accountID.toString()
                params["searchStr"] = searchStr.toString()
                params["priceMin"] = priceMin.toString()
                params["priceMax"] = priceMax.toString()
                params["timeBefore"] = timeBefore.toString()
                params["hardMatch"] = hardMatch.toString()
                try {
                    return when (val response = getDataList("/order/search", params, OrderDetail::class.java)) {
                        is Result.Success -> {
                            val result = ArrayList<OrderBriefing>()
                            for (detail in response.data) {
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
                        is Result.Error -> {
                            response
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
                params["userID"] = accountID.toString()
                try {
                    return when (val response = getDataList("/order/myorder", params, OrderDetail::class.java)) {
                        is Result.Success -> {
                            val result = ArrayList<OrderBriefing>()
                            for (detail in response.data) {
                                val cl = OrderBriefing(
                                        id = detail.id,
                                        title = detail.title,
                                        briefing = detail.mainText.take(40),
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
                        is Result.Error -> {
                            response
                        }
                    }
                } catch (e: Exception) {
                    return Result.Error(Exception("internal error"))
                }
            }
        }
    }

    fun changeOrderToState(orderID: Int, state: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["sta_int"] = state.toString()
        return getDataSingle("/order/updateOrderState", params, String::class.java)
    }

    fun completeOrder(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["accountID"] = accountID.toString()
        return getDataSingle("/order/confirmFinish", params, String::class.java)
    }

    /**
     * 更新任务状态
     */
    fun pushForwardTask(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["receiverID"] = accountID.toString()
        return getDataSingle("/order/updateTaskState", params, String::class.java)
    }

    /**
     * 放弃接单
     */
    fun abortOrder(orderID: Int, receiverID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["receiverID"] = receiverID.toString()
        return getDataSingle("/order/abort", params, String::class.java)
    }

    fun acceptOrder(orderID: Int, accountID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["accountID"] = accountID.toString()
        return getDataSingle("/order/acceptOrder", params, String::class.java)
    }

    fun rePubOrder(
            orderID: Int,
            title: String,
            mainText: String,
            taskType: Int,
            price: Double,
            deadline: Date,
            addressID: Int,
            requireModify: Boolean,
    ): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["title"] = title
        params["mainText"] = mainText
        params["taskType"] = taskType.toString()
        params["price"] = price.toString()
        params["deadline"] = deadline.toString()
        params["addressID"] = addressID.toString()
        params["requireModify"] = requireModify.toString()
        return getDataSingle("/order/repub", params, String::class.java)
    }

    fun getOrderFullData(orderID: Int): Result<OrderDetail> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        return getDataSingle("/order/getdetail", params, OrderDetail::class.java)
    }

    fun requestEditExistingOrder(orderID: Int, publisherID: Int): Result<String> {
        val params = HashMap<String, String>()
        params["orderID"] = orderID.toString()
        params["publisherID"] = publisherID.toString()
        return getDataSingle("/order/reEdit", params, String::class.java)
    }

    fun getOrderByReceiver(userID: Int): Result<ArrayList<OrderBriefing>> {
        val params = HashMap<String, String>()
        params["accountID"] = userID.toString()
        try {
            return when (val response = getDataList("/order/myReceivedOrders", params, OrderDetail::class.java)) {
                is Result.Success -> {
                    val result = ArrayList<OrderBriefing>()
                    for (detail in response.data) {
                        val cl = OrderBriefing(
                                id = detail.id,
                                title = detail.title,
                                briefing = detail.mainText.take(40),
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
                is Result.Error -> {
                    response
                }
            }
        } catch (e: Exception) {
            return Result.Error(Exception("internal error"))
        }
    }

    companion object {
        const val TAG = "OrderDataSource"
    }
}