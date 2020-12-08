package com.systemvx.androiddevtest

import android.util.Log
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.SQLException
import java.util.*

class SQLAgent {
    private var user: String? = null
    private var passWord: String? = null
    private var host: String? = null
    private var currentSchema: String? = null
    private var port = 3306
    private var connection: Connection? = null
    private var debug = false

    constructor() {}
    constructor(
            host: String,
            username: String?,
            password: String?,
            dbName: String,
            port_num: Int = 3306
    ) {
        try {
            connectToDB(host, dbName, username, password, port_num)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun toggleDebug(debug: Boolean) {
        this.debug = debug
    }

    @Throws(ClassNotFoundException::class)
    fun connectToDB(
            host: String,
            dbName: String,
            username: String?,
            password: String?,

            port_num: Int = 3306
    ): Boolean {
        this.host = host
        port = port_num
        passWord = password
        user = username
        currentSchema = dbName
        return try {
            Class.forName(JDBC_Driver_name)
            val url =
                    "$JDBC_SQL_Prefix$host:$port/$currentSchema$Time_Zone"
            connection = DriverManager.getConnection(url, user, passWord)
            if (debug) {
                Log.d(TAG, "connectToDB($url)success.\n")
            }
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun chooseSchema(schemaName: String?): Boolean {
        return true
    }

    @Throws(SQLException::class)
    fun rawQuery(sqlStatement: String?): ResultSet {
        val stmt = connection!!.createStatement()
        return stmt.executeQuery(sqlStatement)
    }

    @Throws(SQLException::class)
    fun execute(sqlStatement: String?): Int {
        val stmt = connection!!.createStatement()
        return stmt.executeUpdate(sqlStatement)
    }

    fun refreshConnection(): Boolean {
        return try {
            Class.forName(JDBC_Driver_name);
            val url = "$JDBC_SQL_Prefix$host/$port/$currentSchema$Time_Zone"
            connection = DriverManager.getConnection(url, user, passWord)
            if (debug) {
                Log.d(TAG, "connectToDB($url)success.\n")
            }
            true
        } catch (e: SQLException) {
            e.printStackTrace()
            false
        }
    }

    fun getconn(): Connection {
        return connection!!
    }
    companion object {
        private const val TAG = "SQLAgent"
        private const val JDBC_SQL_Prefix = "jdbc:mysql://"
        private const val JDBC_Driver_name = "com.mysql.cj.jdbc.Driver"
        private const val Time_Zone = "?serverTimezone=Asia/Shanghai"
    }
}