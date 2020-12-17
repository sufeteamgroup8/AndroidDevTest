package com.systemvx.androiddevtest.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {


    //界面的绑定实体,包含所有界面元素的引用
    private lateinit var mBinding: FragmentDashboardBinding

    //
    private lateinit var dashboardViewModel: DashboardViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_dashboard, container, false)

        dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)


        return mBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.dashboardOrderContainer.layoutManager = LinearLayoutManager(this.context)
        mBinding.dashboardOrderContainer.adapter = OrderListAdapter(this.context)
    }
}