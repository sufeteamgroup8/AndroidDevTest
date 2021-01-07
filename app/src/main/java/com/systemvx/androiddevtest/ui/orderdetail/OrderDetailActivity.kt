package com.systemvx.androiddevtest.ui.orderdetail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
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


        viewModel.dataResult.observe(this, Observer {
            //cancelProgressBar
            netLoadProgress.dismiss()
            if (LoginRepository.isLoggedIn) {
                val userID = LoginRepository.user?.id ?: -1
                val pubID = viewModel.orderdetail.value?.order?.publisher?.id
                val recID = viewModel.orderdetail.value?.order?.receiver?.id

                actionFragment = when (userID) {
                    pubID -> DetailActionBarPublisher(viewModel)
                    recID -> DetailActionBarReceiver(viewModel)
                    else -> DetailActionBarGuest(viewModel)
                }
                supportFragmentManager.beginTransaction()
                        .replace(mBinding.orderDetailActionContainer.id, actionFragment)
                        .commit()
            }
        })

        mBinding.tvBack.setOnClickListener { finish() }

    }
}