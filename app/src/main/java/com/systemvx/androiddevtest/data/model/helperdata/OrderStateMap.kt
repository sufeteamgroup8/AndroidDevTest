package com.systemvx.androiddevtest.data.model.helperdata

import java.io.Serializable

/**
 * 映射订单的各种状态编号至文字.
 *
 */
data class OrderStateMap(
        val id: Int,
        val text: String,
) : Serializable
