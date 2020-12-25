package com.systemvx.androiddevtest.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentSResultListBinding
import com.systemvx.androiddevtest.ui.main.dashboard.OrderListAdapter

/**
 * A fragment representing a list of Items.
 */
class SResultListFragment(private val optionViewModel: SOptionViewModel) : Fragment() {

    private var columnCount = 1

    private lateinit var mBinding: FragmentSResultListBinding

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_s_result_list, container, false)

        optionViewModel.searchResult.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            when (it) {
                true -> {
                    // Set the adapter
                    with(mBinding.list) {
                        layoutManager = when {
                            columnCount <= 1 -> LinearLayoutManager(context)
                            else -> GridLayoutManager(context, columnCount)
                        }
                        adapter = OrderListAdapter(this.context, optionViewModel.resultList)
                    }
                }
                else -> {
                }
            }
        })
        return mBinding.root
    }
}