package com.systemvx.androiddevtest.data.dao

import com.systemvx.androiddevtest.data.CreditData
import java.sql.ResultSet

interface ICreditDAO {
    fun createCredit(data:CreditData)

    fun searchCreditByAccount(accountID:Int): ArrayList<CreditData>

    fun searchCreditbyOrder(orderID:Int): ArrayList<CreditData>

    fun showAllCredit(): ArrayList<CreditData>


}