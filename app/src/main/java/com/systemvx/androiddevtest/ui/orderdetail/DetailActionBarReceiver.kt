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
import com.systemvx.androiddevtest.databinding.FragmentDetailActionBarBinding
import com.systemvx.androiddevtest.ui.chat.ChatActivity
import com.systemvx.androiddevtest.ui.comment.CommentSendActivity
import com.systemvx.androiddevtest.ui.complaint.ComplaintActivity
import com.systemvx.androiddevtest.ui.complaint.ComplaintProgress
import java.io.Serializable

class DetailActionBarReceiver(val viewModel: OrderDetailViewModel) : Fragment() {
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
                "", "",
                "", "",
                "", "",
        ))
        orderSetting.add(2, ButtonSetting(
                "聊一聊", "chat",
                "放弃订单", "abort",
                "下个阶段", "next"
        ))
        orderSetting.add(3, ButtonSetting(
                "评价", "comment",
                "申诉", "report",
                "", ""
        ))
        orderSetting.add(4, ButtonSetting(
                "查看评价", "review",
                "申诉", "report",
                "", ""
        ))

        orderSetting.add(5, ButtonSetting(
                "查看申诉状态", "R_state",
                "", "",
                "", ""
        ))
    }

    private fun setUpButtons() {
        val listener = DetailActionListener(requireContext())
        val buttonType: Int = when (viewModel.orderdetail.value!!.order.state.id) {
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
                    "abort" -> {
                        Toast.makeText(context, "已放弃订单", Toast.LENGTH_SHORT).show()
                        this.requireActivity().finish()
                    }
                    "next" -> {
                        this.viewModel.fetchOrderData(viewModel.orderdetail.value!!.order.id)
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
                "next" -> pushForwardTask(orderID)
                "abort" -> abortOrder(orderID)
                "report" -> {
                    val intent = Intent(context, ComplaintActivity::class.java)
                    intent.putExtra(ComplaintActivity.ARG_ORDER_ID, orderID)
                    context.startActivity(intent)
                }
                "chat" -> {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra(ChatActivity.ARG_CHATTER_ID, viewModel.orderdetail.value!!.order.publisher.id)
                    context.startActivity(intent)
                }
                "comment" -> {
                    val intent = Intent(context, CommentSendActivity::class.java)
                    intent.putExtra(CommentSendActivity.ARG_ORDER_DATA, viewModel.orderdetail as Serializable)
                    context.startActivity(intent)
                }
                "review" -> {
                    TODO()
                }
                "R_state" -> {
                    val intent = Intent(context, ComplaintProgress::class.java)
                    intent.putExtra(CommentSendActivity.ARG_ORDER_DATA, viewModel.orderdetail as Serializable)
                    context.startActivity(intent)//TODO
                }
            }
        }
    }


    fun pushForwardTask(orderID: Int) {
        Thread {
            val response = OrderDataSource().pushForwardTask(orderID, LoginRepository.user!!.id)
            if (response is Result.Success<*>) {
                out.postValue(OutSetting(true, "next"))
            } else {
                out.postValue(OutSetting(false, ""))
            }
        }.start()
    }

    fun abortOrder(orderID: Int) {
        Thread {
            val response = OrderDataSource().abortOrder(orderID, LoginRepository.user!!.id)
            if (response is Result.Success<*>) {
                out.postValue(OutSetting(true, "abort"))
            } else {
                out.postValue(OutSetting(false, ""))
            }
        }.start()
    }
}