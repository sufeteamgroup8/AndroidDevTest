package com.systemvx.androiddevtest.ui.orderbrowsing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.AccountDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.ActivityOrderBrowsingBinding

class ReceivedBrowsingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityOrderBrowsingBinding

    private lateinit var viewModel: OrderBrowsingViewModel

    private val tabCounts = 4


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(OrderBrowsingViewModel::class.java)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_browsing)

        mBinding.viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = tabCounts

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> OrderBrowsingFragment.newInstance(viewModel, "all")
                    1 -> OrderBrowsingFragment.newInstance(viewModel, "received")
                    2 -> OrderBrowsingFragment.newInstance(viewModel, "ended")
                    else -> OrderBrowsingFragment.newInstance(viewModel, "debate")
                }
            }

        }
        TabLayoutMediator(mBinding.tabs, mBinding.viewpager,
                true, false
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "全部"
                1 -> "进行中"
                2 -> "已结束"
                else -> "申诉中"
            }
        }.attach()

        LoginRepository(AccountDataSource()).login(",", "")
        viewModel.updateRecvData()
    }
}