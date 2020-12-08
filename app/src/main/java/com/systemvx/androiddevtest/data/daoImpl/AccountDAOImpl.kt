package com.systemvx.androiddevtest.data.daoImpl

import com.systemvx.androiddevtest.data.AccountData
import com.systemvx.androiddevtest.data.dao.IAccountDAO
import java.lang.Exception
import java.sql.Statement

class AccountDAOImpl : BaseDAO<AccountData>(), IAccountDAO {
    val tableName = "`account`"

    override fun createAccount(name:String,password:String,stunedtNo:String): Int {
        if (name.isNullOrBlank() || password.isNullOrBlank())
            throw Exception("name 或 password 属性不符要求")
        val pstmt = conn.prepareStatement(
                "INSERT INTO $tableName" +
                        "(account_name,account_passwd,account_student_number)" +
                        "VALUES(?,?,?)")
        pstmt.setString(1, name)
        pstmt.setString(2, password)
        pstmt.setString(3, "2077123456")
        return pstmt.executeUpdate()
    }

    override fun updateAccount(account: AccountData): Int {
        var isChanged = false
        var sql = "UPDATE $tableName SET "
        if (!account.password.isNullOrBlank()) {
            sql += "account_passwd = '${account.password}',"
            isChanged = true
        }
        if (!account.nickname.isNullOrBlank()) {
            sql += "account_nickname = '${account.nickname}',"
            isChanged = true
        }
        if (account.sex != null) {
            sql += "account_sex = ${account.sex},"
            isChanged = true
        }
        if (!account.phone.isNullOrBlank()) {
            sql += "account_phone = '${account.phone}',"
            isChanged = true
        }
        if (account.portrait != null) {
            sql += "account_portrait = ${account.portrait.toString()},"
            isChanged = true
        }
        if (account.coin != null) {
            sql += "account_coin_balance = ${account.coin},"
            isChanged = true
        }
        if (account.credit != null) {
            sql += "account_credit = ${account.credit},"
            isChanged = true
        }
        if (!isChanged)
            throw Exception("无修改项")
        sql = sql.dropLast(1)
        sql += " WHERE account_id = ${account.id}"
        var stmt = conn.createStatement()
        return stmt.executeUpdate(sql)

    }

    override fun deleteAccount(account: AccountData): Boolean {
        var pstmt = conn.prepareStatement("DELETE FROM $tableName WHERE account_id=?")
        pstmt.setInt(1, account.id)
        return (pstmt.executeUpdate() > 0)
    }

    override fun updateAccountCoin(account: AccountData, newCoinValue: Double) {
        updateAccount(AccountData(name = account.name, id = account.id, coin = newCoinValue))
    }

    override fun updateAccountState(account: AccountData, newState: Int) {
        TODO("Not yet implemented")
    }

    override fun updateAccountCredit(account: AccountData, newConfidential: Int) {
        updateAccount(AccountData(name = account.name, id = account.id, credit = newConfidential))
    }

    override fun getAccountById(id: Int): AccountData {
        val pstmt = conn.prepareStatement("SELECT * FROM $tableName WHERE account_id = ?")
        pstmt.setInt(1, id)
        var result = pstmt.executeQuery()
        result.next()
        return AccountData(
                name = result.getString("account_name"),
                id = result.getInt("account_id"),
                nickname = result.getString("account_nickname"),
                sex = result.getInt("account_sex"),
                credit = result.getInt("account_credit"),
                coin = result.getDouble("account_coin_balance"),
                phone = result.getString("account_phone"),
                password = result.getString("account_passwd")
        )
    }

    override fun getAccountByName(name: String): AccountData {
        val pstmt = conn.prepareStatement("SELECT * FROM $tableName WHERE account_name = ?")
        pstmt.setString(1, name)
        var result = pstmt.executeQuery()
        result.next()
        return AccountData(
                name = result.getString("account_name"),
                id = result.getInt("account_id"),
                nickname = result.getString("account_nickname"),
                sex = result.getInt("account_sex"),
                credit = result.getInt("account_credit"),
                coin = result.getDouble("account_coin_balance"),
                phone = result.getString("account_phone"),
                password = result.getString("account_passwd")
        )
    }

    override fun findAccounts(account: AccountData): List<AccountData> {
        TODO("Not yet implemented")
    }

}