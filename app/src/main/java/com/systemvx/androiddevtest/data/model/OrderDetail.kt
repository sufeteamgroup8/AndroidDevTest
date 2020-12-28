package com.systemvx.androiddevtest.data.model

import com.systemvx.androiddevtest.data.model.helperdata.TaskTypeMap
import java.sql.Date

data class OrderDetail(
        val id: Int? = null,

        val order: Orders,

        val missionType: TaskTypeMap = TaskTypeMap(null, null),
        val isFinal: Boolean = false,
        val version: Int?,
        val title: String?,
        val price: Double? = null,
        val address: Int?,
        val mainText: String?,

        val pubTime: Date? = null,
        val deadline: Date? = null,
)