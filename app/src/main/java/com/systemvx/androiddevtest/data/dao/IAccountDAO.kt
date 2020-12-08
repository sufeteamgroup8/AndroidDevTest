package com.systemvx.androiddevtest.data.dao

import com.systemvx.androiddevtest.data.AccountData

interface IAccountDAO {
    /**
     *  插入新用户
     *  id自动生成，已填id将无效
     */
    fun createAccount(name: String,password:String,studentNo:String): Int

    /**
     * 修改指定用户
     * 以id为判断标准
     */
    fun updateAccount(account: AccountData): Int

    /**
     * 删除用户（慎用）
     * 以id为判断标准
     */
    fun deleteAccount(account: AccountData): Boolean

    fun updateAccountCoin(account: AccountData, newCoinValue: Double)
    fun updateAccountState(account: AccountData, newState: Int)
    fun updateAccountCredit(account: AccountData, newConfidential: Int)

    fun getAccountById(id: Int): AccountData
    fun getAccountByName(name: String): AccountData
    fun findAccounts(account: AccountData): List<AccountData>

}