package com.systemvx.androiddevtest.ui.util

import android.content.Context
import androidx.appcompat.app.AlertDialog
import com.systemvx.androiddevtest.R

class RoundProgressDialog(context: Context) : AlertDialog(context) {

    companion object {
        fun getInstance(context: Context): RoundProgressDialog {
            val builder = Builder(context)
            builder.setView(R.layout.dialog_round_progressbar)
                    .setCancelable(false)

            return builder.create() as RoundProgressDialog
        }
    }
}