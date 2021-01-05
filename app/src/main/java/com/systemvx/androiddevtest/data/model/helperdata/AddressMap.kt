package com.systemvx.androiddevtest.data.model.helperdata

import java.io.Serializable

data class AddressMap(
        val id: Int,
        val text: String,

        val rootAddress: AddressMap?,


        ) : Serializable
