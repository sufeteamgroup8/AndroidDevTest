package com.systemvx.androiddevtest.ui.orderdetail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.MappingRepository
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.databinding.ActivityOrderDetailBinding
import com.systemvx.androiddevtest.ui.util.RoundProgressDialog

class OrderDetailActivity : AppCompatActivity() {
    companion object {
        const val ARG_ORDER_ID = "orderID"
    }

    private lateinit var viewModel: OrderDetailViewModel

    private lateinit var mBinding: ActivityOrderDetailBinding

    private lateinit var actionFragment: Fragment

    private lateinit var netLoadProgress: RoundProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getBinding
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_detail)
        //getViewModel
        viewModel = ViewModelProvider(this).get(OrderDetailViewModel::class.java)

        //pass viewModel for data transfer
        mBinding.viewModel = this.viewModel

        //get the ID of the Showing order
        val id = intent.getIntExtra(ARG_ORDER_ID, -1)

        netLoadProgress = RoundProgressDialog.getInstance(this)

        //requestOrderData
        if (id != -1) {
            //showProgressBar
            netLoadProgress.show()
            viewModel.fetchOrderData(id)
        }


        viewModel.orderdetail.observe(this, {
            netLoadProgress.dismiss()
            if (LoginRepository.isLoggedIn) {
                val userID = LoginRepository.user?.id ?: -1
                val pubID = it.order.publisher.id
                val recID = it.order.receiver?.id

                actionFragment = when (userID) {
                    pubID -> DetailActionBarPublisher(viewModel)
                    recID -> DetailActionBarReceiver(viewModel)
                    else -> DetailActionBarGuest(viewModel)
                }
                supportFragmentManager.beginTransaction()
                        .replace(mBinding.orderDetailActionContainer.id, actionFragment)
                        .commit()

                upDateDisplay(it)
            }
        })
        viewModel.dataResult.observe(this, Observer {
            //cancelProgressBar
            netLoadProgress.dismiss()
            Toast.makeText(this, "获取订单信息失败", Toast.LENGTH_SHORT).show()

        })

        mBinding.tvBack.setOnClickListener { finish() }

    }

    private fun upDateDisplay(detail: OrderDetail) {
        with(mBinding) {
            this.mainText.text = detail.mainText
            this.title.text = detail.title
            orderDetailPrice.text = viewModel!!.getPriceStr()
            this.orderDetailPublisher.text = "发布者:  ${detail.order.publisher.nickname}"
            this.orderDetailAddressFull.text = viewModel!!.getAddressFull()
            if (detail.order.receiver != null) {
                wrapperReceivedSection.visibility = viewModel!!.isOrderReceived()
                val receiver = detail.order.receiver
                this.receiver.text = "承接人 : " + receiver.nickname
                this.receiveTime.text = "承接时间:" + viewModel!!.getDateStr("yy-MM-dd HH:mm:ss", detail.order.receiveTime)
                this.taskState.text = "委托进度:" + MappingRepository().getTaskStateStr(detail.missionType.id, detail.order.taskState)
            }

        }

    }
}