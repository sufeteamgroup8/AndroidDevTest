package com.systemvx.androiddevtest.data.daoImpl

import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.CoinData
import com.systemvx.androiddevtest.data.CreditData
import com.systemvx.androiddevtest.data.dao.ICreditDAO
import java.sql.ResultSet

class CreditDAOImpl() : BaseDAO<CreditData>(), ICreditDAO {
    override fun createCredit(data: CreditData) {
        val sql = "INSERT INTO `credit`(credit_value,credit_source,order_id,account_id)" +
                "VALUES(?,?,?,?)"
        val pstmt = conn.prepareStatement(sql)
        pstmt.setInt(1, data.amount)
        pstmt.setString(2, data.source)
        if (data.relatedOrder != null)
            pstmt.setInt(3, data.relatedOrder!!.id)
        else
            pstmt.setObject(3, null)
        pstmt.setInt(4, data.relatedAccount.id)
        pstmt.executeUpdate()
    }

    override fun searchCreditByAccount(accountID: Int): ArrayList<CreditData> {
        val sql = "SELECT * FROM `credit` WHERE account_id = ?"
        val pstmt = conn.prepareStatement(sql)

        pstmt.setInt(1, accountID)
        val result = pstmt.executeQuery()
        return packData(result)
    }

    override fun searchCreditbyOrder(orderID: Int): ArrayList<CreditData> {
        val sql = "SELECT * FROM `credit` WHERE order_id = ?"
        val pstmt = conn.prepareStatement(sql)

        pstmt.setInt(1, orderID)
        val result = pstmt.executeQuery()
        return packData(result)
    }

    override fun showAllCredit(): ArrayList<CreditData> {
        val sql = "SELECT * FROM `credit`"
        val pstmt = conn.prepareStatement(sql)
        val result = pstmt.executeQuery()
        return packData(result)
    }

    private fun packData(resultSet: ResultSet): ArrayList<CreditData> {
        val out = ArrayList<CreditData>()
        while (resultSet.next()) {
            val temp = CreditData(
                    resultSet.getInt("credit_id"),
                    resultSet.getInt("credit_value"),
                    resultSet.getDate("credit_time"),
                    resultSet.getString("credit_source"),
                    null,
                    AccountData(id = resultSet.getInt("account_id"))
            )
            out.add(temp)
        }
        return out
    }
}