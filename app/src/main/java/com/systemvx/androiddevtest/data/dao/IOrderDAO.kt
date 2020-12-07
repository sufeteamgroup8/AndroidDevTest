package com.systemvx.androiddevtest.data.dao

import com.systemvx.androiddevtest.data.DetailData
import com.systemvx.androiddevtest.data.FullOrderData
import com.systemvx.androiddevtest.data.OrderData

interface IOrderDAO {

    fun createOrder(ordr: OrderData): Int

    fun changeOrderState(order: OrderData)

    fun changeOrderState(id: Int, state: Int): Int

    fun findWaitingOrder(): List<FullOrderData>

    fun findOrderByID(id: Int): FullOrderData

    fun updateOrderTaskState(data: OrderData)

    fun updateOrderReceiver(data: OrderData, receiverID: Int)

    fun updateOrderDetail(data: DetailData)

    fun createNewDetail(data: DetailData, orderID: Int)


}