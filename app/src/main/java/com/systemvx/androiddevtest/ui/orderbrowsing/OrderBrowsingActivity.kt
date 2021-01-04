package com.systemvx.androiddevtest.ui.orderbrowsing

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.ActivityOrderBrowsingBinding

class OrderBrowsingActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityOrderBrowsingBinding

    private val viewModel = OrderBrowsingViewModel()

    private val tabCounts = 5


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_browsing)

        mBinding.viewpager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount() = 5

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> OrderBrowsingFragment.newInstance(viewModel, "all")
                    1 -> OrderBrowsingFragment.newInstance(viewModel, "published")
                    2 -> OrderBrowsingFragment.newInstance(viewModel, "received")
                    3 -> OrderBrowsingFragment.newInstance(viewModel, "active")
                    else -> OrderBrowsingFragment.newInstance(viewModel, "ended")
                }
            }

        }
        TabLayoutMediator(mBinding.tabs, mBinding.viewpager,
                true, false
        ) { tab, position ->
            tab.text = when (position) {
                0 -> "全部"
                1 -> "已发布"
                2 -> "已接受"
                3 -> "执行中"
                else -> "已结束"
            }
        }.attach()

        LoginRepository(LoginDataSource()).login(",", "")
        viewModel.updateData()
    }
}