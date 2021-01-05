package com.systemvx.androiddevtest.data.model

import com.systemvx.androiddevtest.data.model.helperdata.TaskTypeMap
import java.io.Serializable
import java.sql.Date

data class OrderDetail(
        val id: Int,

        val order: Orders,

        val missionType: TaskTypeMap = TaskTypeMap(-1, ""),
        val isFinal: Boolean = false,
        val title: String,
        val price: Double,
        val address: Int,
        val mainText: String,

        val pubTime: Date,
        val deadline: Date,
) : Serializable
