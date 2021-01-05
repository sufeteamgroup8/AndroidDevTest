package com.systemvx.androiddevtest.data.model.helperdata

import java.io.Serializable

/**
 * 映射订单的类型编号至文字描述
 *
 */
data class TaskTypeMap(
        val id: Int,
        val text: String,
) : Serializable

