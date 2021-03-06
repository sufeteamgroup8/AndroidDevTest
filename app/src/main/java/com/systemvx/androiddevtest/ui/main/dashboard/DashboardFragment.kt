package com.systemvx.androiddevtest.ui.main.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentDashboardBinding
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity
import com.systemvx.androiddevtest.ui.search.SearchActivity

class DashboardFragment : Fragment() {

    //界面的绑定实体,包含所有界面元素的引用
    private lateinit var mBinding: FragmentDashboardBinding

    //订单列表的显示适配器,负责生成并管理所有列表里的项
    private lateinit var mAdapter: OrderListAdapter

    //数据交互实体
    private lateinit var dashboardViewModel: DashboardViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?, savedInstanceState: Bundle?,
    ): View {
        //获取布局
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)
        //绑定数据类
        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        //观测data的变动,data改变时执行:

        mAdapter = OrderListAdapter(requireContext())
        dashboardViewModel.mData.observe(this.requireActivity(), Observer {
            // 新的data注入
            mAdapter.updateData(it)
        })
        dashboardViewModel.updateData()
        mBinding.dashboardOrderContainer.setOnClickListener {
            startActivity(Intent(context, OrderDetailActivity::class.java))
        }
        mBinding.btnSearch.setOnClickListener {
            requireContext().startActivity(Intent(this.requireContext(), SearchActivity::class.java))
        }
        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mBinding.dashboardOrderContainer.layoutManager = LinearLayoutManager(this.context)
        mBinding.dashboardOrderContainer.adapter = mAdapter
    }


}