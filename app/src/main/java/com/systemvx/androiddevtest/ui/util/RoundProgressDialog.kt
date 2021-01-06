package com.systemvx.androiddevtest.ui.util

import android.app.Dialog
import android.content.Context
import com.systemvx.androiddevtest.R

class RoundProgressDialog(context: Context) : Dialog(context) {

    companion object {
        fun getInstance(context: Context): RoundProgressDialog {

            val dialog = RoundProgressDialog(context)
            dialog.setContentView(R.layout.dialog_round_progressbar)
            dialog.setCancelable(false)
            dialog.window?.setBackgroundDrawableResource(R.color.transparent)
            return dialog
        }
    }
}