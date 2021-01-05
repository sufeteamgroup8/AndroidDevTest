package com.systemvx.androiddevtest.ui.util

import android.app.Dialog
import android.content.Context

class RoundProgressDialog(context: Context) : Dialog(context) {
    init {
        this.setCancelable(false)
    }
}