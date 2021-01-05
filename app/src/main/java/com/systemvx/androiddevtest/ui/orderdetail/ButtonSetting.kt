package com.systemvx.androiddevtest.ui.orderdetail

import android.view.View

data class ButtonSetting(
        val leftStr: String = "",
        val leftTag: String?,
        val midStr: String = "",
        val midTag: String?,
        val rightStr: String = "",
        val rightTag: String?,
) {
    fun getLeftVis(): Int = if (!leftStr.isNullOrBlank()) View.VISIBLE else View.INVISIBLE
    fun getMidVis(): Int = if (!midStr.isNullOrBlank()) View.VISIBLE else View.INVISIBLE
    fun getRightVis(): Int = if (!rightStr.isNullOrBlank()) View.VISIBLE else View.INVISIBLE
}

data class OutSetting(
        val success: Boolean,
        val tag: String,
)
