package com.systemvx.androiddevtest.data.model

data class Account(
        val id: Int,

        val name: String = "",
        var password: String = "",
        var phoneNo: String = "",

        var nickname: String = "",
        var coin: Double? = 0.0,
        var credit: Int? = 700,
        var signature: String = "",
)