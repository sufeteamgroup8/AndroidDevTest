package com.systemvx.androiddevtest.data.daoImpl

import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.CoinData
import com.systemvx.androiddevtest.data.OrderData
import com.systemvx.androiddevtest.data.dao.ICoinDAO
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CoinDaoImpl : BaseDAO<CoinData>(), ICoinDAO {

    override fun makeUser2UserCoinTrans(transaction: CoinData) {
        val sql = "INSERT INTO `coin`(" +
                "coin_amount," +
                "coin_account_id," +
                "coin_is_income," +
                "order_id)VALUES(?,?,?);" +
                "INSERT INTO `coin`( "
        "coin_amount," +
                "coin_account_id," +
                "coin_is_income," +
                "order_id)VALUES(?,?,?);"
        val pstmt = conn.prepareStatement(sql)
        if (transaction.amount >= 0) {
            pstmt.setBoolean(3, false)
            pstmt.setBoolean(6, true)
        } else {
            pstmt.setBoolean(3, true)
            pstmt.setBoolean(6, false)
        }
        pstmt.setDouble(1, -transaction.amount)
        pstmt.setDouble(4, transaction.amount)
        pstmt.setInt(2, transaction.sender!!.id)
        pstmt.setInt(5, transaction.receiver!!.id)
        pstmt.executeUpdate()


    }

    override fun makeUser2SysCoinTrans(transaction: CoinData) {
        val sql = "INSERT INTO `coin`(" +
                "coin_amount," +
                "coin_account_id," +
                "coin_is_income," +
                "order_id)VALUES(?,?,?);"
        val pstmt = conn.prepareStatement(sql)
        if (transaction.amount >= 0) {
            pstmt.setBoolean(3, false)
        } else {
            pstmt.setBoolean(3, true)
        }
        pstmt.setDouble(1, -transaction.amount)
        pstmt.setInt(2, transaction.sender!!.id)
        pstmt.executeUpdate()

    }

    override fun findTransRelatedToAccount(account: AccountData, endTime: Date, lookupMonth: Int): List<CoinData> {
        val sql = "SELECT * FROM `coin` WHERE coin_account_id = ? AND coin_time between ? and ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, account.id)
        val calendar = Calendar.getInstance()
        calendar.time = endTime
        calendar.add(Calendar.MONTH, -lookupMonth)
        val startTime = calendar.time
        pstmt.setString(2, SimpleDateFormat("`yyyy-MM-dd`").format(startTime))
        pstmt.setString(3, SimpleDateFormat("`yyyy-MM-dd`").format(endTime))
        val resultSet = pstmt.executeQuery()
        val result = ArrayList<CoinData>()
        while (resultSet.next()) {
            var temp = CoinData(
                    resultSet.getInt("coin_id"),
                    resultSet.getDouble("coin_amount"),
                    resultSet.getDate("coin_time"),
                    null,
                    AccountData(id = resultSet.getInt("coin_account_id")),
                    null
            )
            result.add(temp)
        }
        return result
    }

    override fun findTransRelatedToOrder(order: OrderData): List<CoinData> {
        val sql = "SELECT * FROM `coin` WHERE order_id = ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, order.id)
        val resultSet = pstmt.executeQuery()
        val result = ArrayList<CoinData>()
        while (resultSet.next()) {
            var temp = CoinData(
                    resultSet.getInt("coin_id"),
                    resultSet.getDouble("coin_amount"),
                    resultSet.getDate("coin_time"),
                    null,
                    AccountData(id = resultSet.getInt("coin_account_id")),
                    null
            )
            result.add(temp)
        }
        return result
    }

    override fun showTrans(startTime: Date, endTime: Date): List<CoinData> {
        val sql = "SELECT * FROM `coin` WHERE coin_time between ? and ?"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setString(1, SimpleDateFormat("`yyyy-MM-dd`").format(startTime))
        pstmt.setString(2, SimpleDateFormat("`yyyy-MM-dd`").format(endTime))
        val resultSet = pstmt.executeQuery()
        val result = ArrayList<CoinData>()
        while (resultSet.next()) {
            var temp = CoinData(
                    resultSet.getInt("coin_id"),
                    resultSet.getDouble("coin_amount"),
                    resultSet.getDate("coin_time"),
                    null,
                    AccountData(id = resultSet.getInt("coin_account_id")),
                    null
            )
            result.add(temp)
        }
        return result
    }
}