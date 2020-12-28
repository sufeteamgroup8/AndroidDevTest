package com.systemvx.appservice.dataclass

import com.systemvx.androiddevtest.data.model.Account
import java.sql.Date

data class RegisterData(
        val id: Int? = null,
        val studentNo: String = "",
        val state: Int = 0,
        val time: Date? = null,
        val account: Account,
)
