package com.systemvx.androiddevtest.data.daoImpl

import android.util.Log
import com.systemvx.androiddevtest.SQLAgent
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException

open class BaseDAO<T> {
    lateinit var conn: Connection

    init {
        connectToDB()
    }

    private fun connectToDB(): Unit {
        try {
            Class.forName(JDBC_Driver_name);
            val url = "$JDBC_SQL_Prefix$host/$port/$currentSchema$Time_Zone"
            conn = DriverManager.getConnection(url, user, passWord)
            if (debug) {
                Log.d(TAG, "connectToDB($url)success.\n")
            }
        } catch (e: SQLException) {
            print(e.stackTrace)
            throw e //TODO
        }
    }

    companion object {
        var debug = false
        private const val host = "10.0.2.3"
        private const val port = 3306
        private const val currentSchema = "modeling"
        private const val user = "admin"
        private const val passWord = "123456"
        private const val TAG = "SQLAgent"
        private const val JDBC_SQL_Prefix = "jdbc:mysql://"
        private const val JDBC_Driver_name = "com.mysql.cj.jdbc.Driver"
        private const val Time_Zone = "?serverTimezone=Asia/Shanghai"
    }
}