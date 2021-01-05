package com.systemvx.androiddevtest.data.model.helperdata

import java.io.Serializable

data class TaskStateMap(

        val serial: Int,
        val type: TaskTypeMap,
        val text: String,

        val id: Int? = null,
) : Serializable
