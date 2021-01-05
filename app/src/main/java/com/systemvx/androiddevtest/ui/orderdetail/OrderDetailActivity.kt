package com.systemvx.androiddevtest.ui.orderdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.systemvx.androiddevtest.OrderCommentActivity
import com.systemvx.androiddevtest.OrderComplaintActivity
import com.systemvx.androiddevtest.R

class OrderDetailActivity : AppCompatActivity(), View.OnClickListener {
    /**
     *
     * */
    private var orderType:Int  = 0
    private var tvOrderStateButton:TextView? = null
    private var tvOrderStateButton1:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_detail)
        val id = intent.getIntExtra("order_id", 0)

        findViewById<TextView>(R.id.orderDetail_price).text = "$id"
         tvOrderStateButton = findViewById<TextView>(R.id.tv_order_type_button);
         tvOrderStateButton1 = findViewById<TextView>(R.id.tv_order_type_button1);
        //val orderState = OrderDataSource().getOrderState("2")    todo
        tvOrderStateButton?.setOnClickListener(this)
        tvOrderStateButton1?.setOnClickListener(this)
        when (orderType){
            0 -> {tvOrderStateButton?.text = "接单"
            tvOrderStateButton1?.visibility = View.GONE}
            1-> {tvOrderStateButton?.text = "投诉"
                tvOrderStateButton1?.visibility = View.VISIBLE
                tvOrderStateButton1?.text = "更改状态"
            }
            2-> {tvOrderStateButton?.text = "投诉"
                tvOrderStateButton1?.visibility = View.VISIBLE
                tvOrderStateButton1?.text = "评价"
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_order_type_button->{
                if (tvOrderStateButton?.text=="接单"){

                }else{
                    //投诉
                    startActivity(Intent(OrderDetailActivity@this,OrderComplaintActivity::class.java))
                }
            }
            R.id.tv_order_type_button1->{
                if (tvOrderStateButton1?.text=="更改状态"){

                }else{
                    //评价
                    startActivity(Intent(OrderDetailActivity@this,OrderCommentActivity::class.java))
                }
            }

        }
    }
}