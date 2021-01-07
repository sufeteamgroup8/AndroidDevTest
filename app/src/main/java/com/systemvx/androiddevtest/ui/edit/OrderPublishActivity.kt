package com.systemvx.androiddevtest.ui.edit

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentOrderPublishBinding
import java.text.DecimalFormat
import java.text.SimpleDateFormat

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
            pubOrder()
        }
        mBinding.saveDraftBtn.setOnClickListener {
            pubDraft()
        }


        //异步事件
        viewModel.paramResult.observe(this, {
            when (it) {
                true -> {
                    initSpinners()
                }
                else -> {
                    Toast.makeText(this, "请检查网络连接", Toast.LENGTH_SHORT).show()
                }
            }
        })
        viewModel.pubResult.observe(this, {
            when (it) {
                true -> {
                    Toast.makeText(this, "发布成功", Toast.LENGTH_LONG).show()
                    this.finish()
                }
                false -> {
                    Toast.makeText(this, "发布失败", Toast.LENGTH_LONG).show()
                }
                null -> {
                    Toast.makeText(this, "数据不全", Toast.LENGTH_LONG).show()
                }
            }
        })
        viewModel.detailResult.observe(this, {
            when (it) {
                true -> injectExistingOrder()
                else -> {

                }
            }
        })


        //获取数据
        viewModel.fetchMapping()
        if (viewModel.orderID != -1) {
            viewModel.fetchOrderDetail()
        }
    }


    //ok
    private fun initSpinners() {
        val addressAdapter = ArrayAdapter<String>(this,
                R.layout.spinner_item, viewModel.getListAddress())
        mBinding.spinnerAddress.adapter = addressAdapter
        mBinding.spinnerAddress.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.addressPos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.addressPos = -1
            }

        }

        val typeAdapter = ArrayAdapter<String>(this,
                R.layout.spinner_item, viewModel.getListType())
        mBinding.spinnerType.adapter = typeAdapter
        mBinding.spinnerType.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.typePos = position
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                viewModel.typePos = -1
            }

        }
    }


    //ok
    private fun injectExistingOrder() {
        mBinding.orderTitle.setText(
                viewModel.orderDetail.title)
        mBinding.orderContext.setText(
                viewModel.orderDetail.mainText)
        mBinding.priceEdt.setText(
                DecimalFormat("#.00").format(viewModel.orderDetail.price))
        mBinding.spinnerAddress.setSelection(viewModel.orderDetail.address)
        mBinding.spinnerType.setSelection(viewModel.getListType().indexOf(viewModel.orderDetail.missionType.text))
        //TODO()
    }

    private fun pubOrder() {
        viewModel.publishOrder(
                title = mBinding.orderTitle.text.toString(),
                mainText = mBinding.orderContext.text.toString(),
                price = DecimalFormat("#.00").format(mBinding.priceEdt.text.toString().toDouble()).toDouble(),
                deadline = SimpleDateFormat("yyyyMMddHHmm").parse(mBinding.timeEdt.text.toString()), //TODO()
        )
    }

    private fun pubDraft() {
        viewModel.publishDraft(
                title = mBinding.orderTitle.text.toString(),
                mainText = mBinding.orderContext.text.toString(),
                price = DecimalFormat("#.00").format(mBinding.priceEdt.text.toString().toDouble()).toDouble(),
                deadline = SimpleDateFormat("yyyyMMddHHmm").parse(mBinding.timeEdt.text.toString()), //TODO()
        )
    }

    companion object {
        const val ARG_ORDER_ID = "order_id"
    }
}