package com.systemvx.androiddevtest.ui.orderdetail

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.OrderDataSource
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.databinding.FragmentDetailActionBarBinding

class DetailActionBarGuest(val viewModel: OrderDetailViewModel) : Fragment() {
    private lateinit var mBind: FragmentDetailActionBarBinding

    private lateinit var orderSetting: ArrayList<ButtonSetting>

    private val out = MutableLiveData<OutSetting>()
    private fun init() {
        orderSetting = ArrayList()
        orderSetting.add(0, ButtonSetting(
                "", "",
                "", "",
                "", ""))
        orderSetting.add(1, ButtonSetting(
                "接单", "accept",
                "", "",
                "", "",
        ))
        orderSetting.add(2, ButtonSetting(
                "", "",
                "", "",
                "", ""
        ))
        orderSetting.add(3, ButtonSetting(
                "", "",
                "", "r",
                "", ""
        ))
        orderSetting.add(4, ButtonSetting(
                "", "",
                "", "",
                "", ""
        ))

        orderSetting.add(5, ButtonSetting(
                "", "",
                "", "",
                "", ""
        ))
    }

    private fun setUpButtons() {
        val listener = DetailActionListener(requireContext())
        val buttonType: Int = when (viewModel.orderdetail.value?.order?.state?.id) {
            0 -> 0 //未发布
            1 -> 1 //已发布
            2 -> 2 //已接单
            3 -> 3 //已完成
            4 -> 4 //正常关闭
            5 -> 4 //申诉中
            6 -> 5 //非正常关闭
            else -> 0
        }
        with(orderSetting[buttonType]) {
            mBind.btnLeft.text = leftStr
            mBind.btnLeft.tag = leftTag
            mBind.btnLeft.visibility = getLeftVis()
            mBind.btnMid.text = midStr
            mBind.btnMid.tag = midTag
            mBind.btnMid.visibility = getMidVis()
            mBind.btnRight.text = rightStr
            mBind.btnRight.tag = rightTag
            mBind.btnRight.visibility = getRightVis()
        }
        mBind.btnLeft.setOnClickListener(listener)
        mBind.btnMid.setOnClickListener(listener)
        mBind.btnRight.setOnClickListener(listener)

    }

    @SuppressLint("ShowToast")
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail_action_bar,
                container, false)

        init()
        setUpButtons()
        out.observe(this.viewLifecycleOwner, {
            if (it.success) {
                when (it.tag) {
                    "accept" -> {
                        val intent = Intent(context, OrderDetail::class.java)
                        intent.putExtra("", viewModel.orderdetail.value!!.order.id)
                        Toast.makeText(context, "接单成功!", Toast.LENGTH_LONG).show()
                        startActivity(intent)
                        this.requireActivity().finish()
                    }
                }
            } else {
                Toast.makeText(context, "操作失败,请稍后重试", Toast.LENGTH_LONG).show()
            }
        })
        return mBind.root
    }


    inner class DetailActionListener(val context: Context) : View.OnClickListener {
        override fun onClick(v: View?) {
            val orderID = viewModel.orderdetail.value!!.order.id
            when (v!!.tag) {
                "accept" -> acceptOrder(orderID)
            }
        }
    }


    fun acceptOrder(orderID: Int) {
        Thread {
            val response = OrderDataSource().acceptOrder(orderID, LoginRepository.user!!.id)
            if (response is Result.Success) {
                out.postValue(OutSetting(true, "edit"))
            } else {
                out.postValue(OutSetting(false, ""))
            }
        }.start()
    }

}