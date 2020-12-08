package com.systemvx.androiddevtest.data.daoImpl

import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.DetailData
import com.systemvx.androiddevtest.data.FullOrderData
import com.systemvx.androiddevtest.data.OrderData
import com.systemvx.androiddevtest.data.dao.IOrderDAO
import java.sql.Types
import java.text.SimpleDateFormat

class OrderDAOImpl : BaseDAO<FullOrderData>(), IOrderDAO {

    override fun createOrder(order: OrderData): Int {
        val sql = "INSERT INTO `order`(publisher)VALUES(?)"
        val pstmt = conn.prepareStatement(sql)
        if (order.publisher.id == 0) {
            throw Exception("发布者不存在或非法")
        } else {
            pstmt.setInt(1, order.publisher.id)
            return pstmt.executeUpdate()
        }
    }

    override fun changeOrderState(order: OrderData) {
        changeOrderState(order.id, order.orderState)
    }

    override fun changeOrderState(id: Int, state: Int): Int {
        if (id == 0)
            throw Exception("无order id")
        val sql = "UPDATE $tableOrder SET order_state = ? WHERE order_id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, state)
        pstmt.setInt(2, id)
        return pstmt.executeUpdate()
    }

    override fun findWaitingOrder(): ArrayList<FullOrderData> {
        var result: ArrayList<FullOrderData> = ArrayList()
        val sql = "select * FROM order_complete " +
                "WHERE order_receiver is null and order_state >1 "
        val set = conn.prepareStatement(sql).executeQuery()
        while (set.next()) {
            var tempData = FullOrderData(OrderData(
                    set.getInt("order_id"),
                    set.getInt("order_state"),
                    set.getInt("order_task_state"),
                    AccountData(
                            set.getString("publisher_name"),
                            set.getString("publisher_nickname"),
                            set.getString("publisher_passwd"),
                            set.getString("publisher_signature"),
                            set.getString("publisher_phone"),
                            set.getInt("publisher_confidential"),
                            set.getDouble("publisher_coin"),
                            null,
                            set.getInt("order_publisher"),
                            set.getInt("publisher_sex")
                    ),
                    AccountData(
                            set.getString(" receiver_name"),
                            set.getString(" receiver_nickname"),
                            set.getString(" receiver_passwd"),
                            set.getString(" receiver_signature"),
                            set.getString(" receiver_phone"),
                            set.getInt(" receiver_confidential"),
                            set.getDouble(" receiver_coin"),
                            null,
                            set.getInt("order_ receiver"),
                            set.getInt(" receiver_sex")
                    )),
                    DetailData(
                            set.getInt("order_version"),
                            set.getString("order_title"),
                            set.getString("order_details"),
                            set.getDouble("order_price"),
                            set.getInt("order_type"),
                            set.getDate("order_pub_time"),
                            set.getDate("order_deadline"),
                            set.getInt("order_address"),
                            "address dummy",
                            true
                    ))
            result.add(tempData)
        }
        return result
    }

    override fun findOrderByID(id: Int): FullOrderData {
        val sql = "select * FROM order_complete " +
                "WHERE order_id = ? "
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, id)
        val set = pstmt.executeQuery()
        set.next()
        return FullOrderData(OrderData(
                set.getInt("order_id"),
                set.getInt("order_state"),
                set.getInt("order_task_state"),
                AccountData(
                        set.getString("publisher_name"),
                        set.getString("publisher_nickname"),
                        set.getString("publisher_passwd"),
                        set.getString("publisher_signature"),
                        set.getString("publisher_phone"),
                        set.getInt("publisher_confidential"),
                        set.getDouble("publisher_coin"),
                        null,
                        set.getInt("order_publisher"),
                        set.getInt("publisher_sex")
                ),
                AccountData(
                        set.getString(" receiver_name"),
                        set.getString(" receiver_nickname"),
                        set.getString(" receiver_passwd"),
                        set.getString(" receiver_signature"),
                        set.getString(" receiver_phone"),
                        set.getInt(" receiver_confidential"),
                        set.getDouble(" receiver_coin"),
                        null,
                        set.getInt("order_ receiver"),
                        set.getInt(" receiver_sex")
                )),
                DetailData(
                        set.getInt("order_version"),
                        set.getString("order_title"),
                        set.getString("order_details"),
                        set.getDouble("order_price"),
                        set.getInt("order_type"),
                        set.getDate("order_pub_time"),
                        set.getDate("order_deadline"),
                        set.getInt("order_address"),
                        "address dummy",
                        true
                ))
    }

    fun findOrder(settings:FullOrderData,ambigious:Boolean = true){

        conn.createStatement().executeQuery("")//TODO
    }

    override fun updateOrderTaskState(data: OrderData) {
        if (data.id == 0)
            throw Exception("无order id")
        val sql = "UPDATE $tableOrder SET order_state = ? WHERE order_id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, data.taskState)
        pstmt.setInt(2, data.id)
        pstmt.executeUpdate()
    }

    override fun updateOrderReceiver(data: OrderData, receiverID: Int) {
        if (data.id == 0)
            throw Exception("无order id")
        val sql = "UPDATE $tableOrder SET order_state = ? WHERE order_id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, data.taskState)
        pstmt.setInt(2, receiverID)
        pstmt.executeUpdate()
    }

    override fun updateOrderDetail(data: DetailData, orderID: Int) {
        createNewDetail(data, orderID)
    }

    override fun createNewDetail(data: DetailData, orderID: Int) {
        val sql = "INSERT INTO $tableDetail(" +
                "order_id" +
                "order_version," +
                "order_title," +
                "order_details," +
                "order_price," +
                "order_type," +
                "order_deadline," +
                "order_address," +
                "order_is_final)VALUES(" +
                "?,?,?,?,?,?,?,?,?) "
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1,orderID)
        pstmt.setInt(2,data.version!!)//TODO 版本号实现
        pstmt.setObject(3,data.title)
        pstmt.setObject(4,data.maintext)
        pstmt.setObject(5,data.price)
        pstmt.setInt(6,data.type)
        pstmt.setObject(7,SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(data.endTime))
        pstmt.setObject(8,data.address)
        pstmt.setBoolean(9,data.isFinalVersion)
        pstmt.executeUpdate()
    }

    companion object {
        const val tableOrder = "`order`"
        const val tableDetail = "`order_detail`"
    }
}