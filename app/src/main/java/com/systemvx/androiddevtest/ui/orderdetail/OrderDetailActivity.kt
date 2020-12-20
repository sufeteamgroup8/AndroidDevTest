package com.systemvx.androiddevtest.ui.orderdetail

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.systemvx.androiddevtest.R

class OrderDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        val id = intent.getIntExtra("order_id", 0)

        findViewById<TextView>(R.id.orderDetail_price).text = "$id"
    }
}