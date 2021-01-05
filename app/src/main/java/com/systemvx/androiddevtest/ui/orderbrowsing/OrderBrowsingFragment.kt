package com.systemvx.androiddevtest.ui.orderbrowsing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.FragmentOrderBrowsingBinding

/**
 * A simple [Fragment] subclass.
 * Use the [OrderBrowsingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OrderBrowsingFragment(val viewModel: OrderBrowsingViewModel) : Fragment(), View.OnClickListener {
    // TODO: Rename and change types of parameters
    private var fragType: String? = null

    private lateinit var rv: RecyclerView

    private lateinit var mAdapter: OrderBrowsingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            fragType = it.getString(FRAG_TYPE)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View {
        mAdapter = OrderBrowsingAdapter(fragType!!, this.requireContext(), this)
        val bind = DataBindingUtil.inflate<FragmentOrderBrowsingBinding>(inflater, R.layout.fragment_order_browsing, container, false)
        rv = bind.mainRv
        // Inflate the layout for this fragment
        return bind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = mAdapter
        viewModel.fullListData.observe(this.viewLifecycleOwner, Observer { mAdapter.updateData(it) })
    }

    companion object {
        private const val FRAG_TYPE = "fragType"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment OrderBowsingFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(viewModel: OrderBrowsingViewModel, type: String) =
                OrderBrowsingFragment(viewModel).apply {
                    arguments = Bundle().apply {
                        putString(FRAG_TYPE, type)
                    }
                }
    }

    override fun onClick(v: View?) {
        when (v?.tag) {
            //TODO
        }
    }
}