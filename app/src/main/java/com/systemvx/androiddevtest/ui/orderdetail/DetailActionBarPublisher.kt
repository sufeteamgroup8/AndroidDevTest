package com.systemvx.androiddevtest.ui.orderdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentDetailActionBarBinding

class DetailActionBarPublisher(val viewModel: OrderDetailViewModel) : Fragment() {
    private lateinit var mBind: FragmentDetailActionBarBinding

    private lateinit var orderSetting: ArrayList<ButtonSetting>


    fun init() {
        orderSetting = ArrayList()
        orderSetting.add(0, ButtonSetting(
                "", View.INVISIBLE, "",
                "", View.INVISIBLE, "",
                "", View.INVISIBLE, ""))
        orderSetting.add(1, ButtonSetting(
                "修改", View.VISIBLE, "edit",
                "", View.INVISIBLE, "",
                "取消", View.VISIBLE, "cancel",
        ))
        orderSetting.add(2)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        mBind = DataBindingUtil.inflate(inflater,
                R.layout.fragment_detail_action_bar,
                container, false)


        return mBind.root
    }

    private fun setUpButtons() {

    }

    inner class DetailActionListener : View.OnClickListener {
        override fun onClick(v: View?) {
            when (v) {
                mBind.btnLeft {

                }
            }
        }
    }
}