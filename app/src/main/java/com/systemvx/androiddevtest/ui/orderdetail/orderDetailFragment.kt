package com.systemvx.androiddevtest.ui.orderdetail

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.systemvx.androiddevtest.CommentActivity
import com.systemvx.androiddevtest.OrderComplaintActivity
import com.systemvx.androiddevtest.OrderPublishActivity
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository.Companion.user
import com.systemvx.androiddevtest.databinding.FragmentOrderDetailBinding
import com.systemvx.androiddevtest.ui.chat.ChatActivity
import com.systemvx.androiddevtest.utils.DialogUtil

class orderDetailFragment
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */(private val mViewModel: OrderDetailViewModel) : Fragment() {
    private lateinit var mBinding: FragmentOrderDetailBinding

    // TODO: Customize parameters
    private var mColumnCount = 1
    private fun textChange(order_type: Int, receiver_id: Int, publisher_id: Int) {
        val userid = user!!.id
        if (userid == receiver_id) {
            when (order_type) {
                0 ->                 //订单已接单
                {
                    mBinding!!.bn3.text = "确认完成"
                    mBinding!!.bn2.text = "取消订单"
                    mBinding!!.bn1.text = "聊一聊"
                }
                1 ->                 //订单未接单
                {
                    mBinding!!.bn3.text = "抢单"
                    mBinding!!.bn2.visibility = View.INVISIBLE
                    mBinding!!.bn1.visibility = View.INVISIBLE
                }
                2 ->                 //订单已完成
                {
                    mBinding!!.bn3.text = "评价"
                    mBinding!!.bn2.text = "投诉"
                    mBinding!!.bn1.text = "再接一单"
                }
            }
        } else if (userid == publisher_id) {
            when (order_type) {
                0 ->                     //订单已接单
                {
                    mBinding!!.bn3.text = "确认完成"
                    mBinding!!.bn2.visibility = View.INVISIBLE
                    mBinding!!.bn1.text = "聊一聊"
                }
                1 ->                     //订单未接单
                {
                    mBinding!!.bn3.text = "修改订单"
                    mBinding!!.bn2.text = "取消订单"
                    mBinding!!.bn1.visibility = View.INVISIBLE
                }
                2 ->                     //订单已完成
                {
                    mBinding!!.bn3.text = "评价"
                    mBinding!!.bn2.text = "投诉"
                    mBinding!!.bn1.text = "再发一单"
                }
            }
        } else {
            when (order_type) {
                0, 2 ->                     //订单已完成
                    //订单已接单
                {
                    mBinding!!.bn2.visibility = View.INVISIBLE
                    mBinding!!.bn3.visibility = View.INVISIBLE
                    mBinding!!.bn1.visibility = View.INVISIBLE
                }
                1 ->                     //订单未接单
                {
                    mBinding!!.bn3.text = "抢单"
                    mBinding!!.bn2.visibility = View.INVISIBLE
                    mBinding!!.bn1.visibility = View.INVISIBLE
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val detail = mViewModel.orderdetail.value
        val publisher = detail!!.order.publisher.id
        val receiver = detail.order.receiver!!.id
        val orderType = detail.order.taskState
        textChange(orderType, receiver, publisher)
        if (arguments != null) {
            mColumnCount = requireArguments().getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        val txt1 = mBinding.bn1.text.toString()
        val txt2 = mBinding.bn2.text.toString()
        val txt3 = mBinding.bn3.text.toString()
        mBinding.bn1.setOnClickListener{
            it as Button
            when(it.text){
                "再发一单"->{
                     var intent=Intent(this.context,OrderPublishActivity::class.java)
                    DialogUtil.showDialog(requireContext(),"再发一单",intent)
                }
                "再接一单"->{

                    DialogUtil.showDialog(requireContext(),"再接一单",true)
                }
                "聊一聊"->{
                    var intent=Intent(this.context,ChatActivity::class.java)
                    DialogUtil.showDialog(requireContext(),"聊一聊",intent)
                }


            }
        }
        mBinding.bn2.setOnClickListener{
            it as Button
            when(it.text){
                "取消"->{
                    DialogUtil.showDialog(requireContext(),"取消",true)
                }
                "投诉"->{
                    var intent=Intent(this.context,OrderComplaintActivity::class.java)
                    DialogUtil.showDialog(requireContext(),"投诉",intent)
                }


            }
        }
        mBinding.bn3.setOnClickListener{
            it as Button
            when(it.text){
                "确认完成"->{
                    DialogUtil.showDialog(requireContext(),"确认完成",true)
                }
                "抢单"->{
                    DialogUtil.showDialog(requireContext(),"抢单",false)
                }
                "评价"->{
                    var intent=Intent(this.context,CommentActivity::class.java)
                    DialogUtil.showDialog(requireContext(),"评价",intent)
                }
                "修改订单"->{
                    var intent=Intent(this.context,OrderPublishActivity::class.java)
                    DialogUtil.showDialog(requireContext(),"修改订单",intent)
                }

            }
        }
        return mBinding.getRoot()
    }

    companion object {
        // TODO: Customize parameter argument names
        private const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int, viewModel: OrderDetailViewModel): orderDetailFragment {
            val fragment = orderDetailFragment(viewModel)
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }

}