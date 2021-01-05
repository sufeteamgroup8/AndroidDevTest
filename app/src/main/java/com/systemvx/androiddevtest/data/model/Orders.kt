package com.systemvx.androiddevtest.data.model

import com.systemvx.androiddevtest.data.model.helperdata.OrderStateMap
import java.io.Serializable
import java.sql.Date

data class Orders(
        val id: Int,
        val state: OrderStateMap = OrderStateMap(-1, ""),
        val taskState: Int = 0,
        val receiveTime: Date? = null,
        val publisher: Account,
        val receiver: Account? = null,

        //快照数据
        val finalPublishTime: Date? = null,
        val closedTime: Date? = null,
        val receiverPhone: String? = null,
        val publisherPhone: String? = null,


        ) : Serializable
