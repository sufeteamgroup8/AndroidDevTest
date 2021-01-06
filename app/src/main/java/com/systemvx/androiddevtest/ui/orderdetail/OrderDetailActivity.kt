package com.systemvx.androiddevtest.ui.orderdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ToastUtils
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivityOrderDetailBinding
import com.systemvx.androiddevtest.ui.comment.CommentSendActivity
import com.systemvx.androiddevtest.ui.complaint.complaintActivity
import com.systemvx.androiddevtest.ui.util.RoundProgressDialog


class OrderDetailActivity : AppCompatActivity(), View.OnClickListener {
    /**
     *
     * */
    companion object {
        const val ARG_ORDER_ID = "orderID"
    }

    private lateinit var viewModel: OrderDetailViewModel
    private var orderState: Int = 0
    private var tvOrderStateButton: TextView? = null
    private var tvOrderStateButton1: TextView? = null

    private lateinit var mBinding: ActivityOrderDetailBinding

    private lateinit var netLoadProgress: RoundProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        //getViewModel
        viewModel = ViewModelProvider(this).get(OrderDetailViewModel::class.java)
        //get the ID of the Showing order
        val id = intent.getIntExtra(ARG_ORDER_ID, -1)

        findViewById<TextView>(R.id.orderDetail_price).text = "$id"
        tvOrderStateButton = findViewById<TextView>(R.id.tv_order_type_button);
        tvOrderStateButton1 = findViewById<TextView>(R.id.tv_order_type_button1);
        //val orderState = OrderDataSource().getOrderState("2")    todo
        tvOrderStateButton?.setOnClickListener(this)
        tvOrderStateButton1?.setOnClickListener(this)
        when (orderState) {
            0 -> {
                tvOrderStateButton?.text = "接单"
                tvOrderStateButton1?.visibility = View.GONE
            }
            1 -> {
                tvOrderStateButton?.text = "投诉"
                tvOrderStateButton1?.visibility = View.VISIBLE
                tvOrderStateButton1?.text = "更改状态"
            }
            2 -> {
                tvOrderStateButton?.text = "投诉"
                tvOrderStateButton1?.visibility = View.VISIBLE
                tvOrderStateButton1?.text = "评价"
            }
            3 -> {
                tvOrderStateButton?.text = ""
                tvOrderStateButton?.visibility = View.GONE
                tvOrderStateButton1?.visibility = View.GONE
                tvOrderStateButton1?.text = ""
            }
        }
        //pass viewModel for data transfer
        mBinding.viewModel = this.viewModel
        //requestOrderData
        if (id != -1) {
            //showProgressBar
            netLoadProgress.show()
            viewModel.fetchOrderData(id)
        }
        viewModel.dataResult.observe(this, Observer {
            //cancelProgressBar
            netLoadProgress.dismiss()
        })
    }


    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_order_type_button -> {
                if (tvOrderStateButton?.text == "接单") {
                    AlertDialog.Builder(OrderDetailActivity@ this).setMessage("是否接取该订单").setNegativeButton("接单") { p0, _ ->
                        p0?.dismiss()
                        ToastUtils.showShort("接单成功")
                    }
                } else {
                    //投诉
                    startActivity(Intent(OrderDetailActivity@ this, complaintActivity::class.java))
                }
            }
            R.id.tv_order_type_button1 -> {
                if (tvOrderStateButton1?.text == "更改状态") {
                    AlertDialog.Builder(OrderDetailActivity@ this).setMessage("是否修改该订单状态").setNegativeButton("修改") { p0, _ ->
                        p0?.dismiss()
                        ToastUtils.showShort("修改成功")
                    }
                } else {
                    //评价
                    startActivity(Intent(OrderDetailActivity@ this, CommentSendActivity::class.java))
                }
            }


        }
    }
}