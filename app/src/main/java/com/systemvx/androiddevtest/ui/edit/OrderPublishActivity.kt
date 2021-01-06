package com.systemvx.androiddevtest.ui.edit

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentOrderPublishBinding

class OrderPublishActivity : AppCompatActivity() {
    private lateinit var mBinding: FragmentOrderPublishBinding

    private lateinit var viewModel: OrderPublishViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.fragment_order_publish)
        //获取界面文本框
        val orderID = intent.getIntExtra(ARG_ORDER_ID, -1)
        viewModel =
                ViewModelProvider(
                        this,
                        OrderPublishViewModel.OrderPublishViewModelFactory(orderID = orderID)
                ).get(OrderPublishViewModel::class.java)


        //按钮事件
        mBinding.orderCancel.setOnClickListener {
            this.finish()
        }
        mBinding.orderPublish.setOnClickListener {
            viewModel.publishOrder()
        }
        mBinding.saveDraftBtn.setOnClickListener {
            viewModel.publishDraft()
        }


        //异步事件
        viewModel.pubResult.observe(this, {
            when (it) {
                true -> {
                }
                false -> {
                }
                null -> {
                }
            }
        })
        viewModel.pubResult.observe(this, {
            when (it) {
                true -> initSpinners()
                false -> {
                }
                null -> {
                }
            }
        })


        //获取数据
        viewModel.fetchMapping()
        if (viewModel.orderID != -1) {
            viewModel.fetchOrderDetail()
        }
    }

    private fun initSpinners() {
        val listAddress = ArrayList<String>()
        for (i in viewModel.addressMap) {
            listAddress.add(i.text)
        }
        val addressAdapter = ArrayAdapter<String>(this,
                R.layout.spinner_item, listAddress)
        mBinding.spinnerAddress.adapter = addressAdapter
        mBinding.spinnerAddress.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
        val listType = ArrayList<String>()
        for (i in viewModel.typeMAp) {
            listType.add(i.second)
        }
        val typeAdapter = ArrayAdapter<String>(this,
                R.layout.spinner_item, listAddress)
        mBinding.spinnerType.adapter = typeAdapter
        mBinding.spinnerType.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                TODO("Not yet implemented")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }


    private fun injectExistingOrder() {
        TODO()
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }
}