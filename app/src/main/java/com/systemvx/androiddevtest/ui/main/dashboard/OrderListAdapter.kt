package com.systemvx.androiddevtest.ui.main.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.databinding.ItemOrderBriefingBinding
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity


class OrderListAdapter(
        val context: Context,
        listener: OrderBriefingListener? = null,
) :
        RecyclerView.Adapter<OrderListAdapter.Companion.OrderBriefingViewHolder>() {

    private val items: ArrayList<OrderBriefing> = ArrayList()
    private val mListener: OrderBriefingListener = listener ?: OrderBriefingListener()

    fun updateData(data: ArrayList<OrderBriefing>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBriefingViewHolder {
        val binding: ItemOrderBriefingBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.item_order_briefing,
                        parent, false)
        return OrderBriefingViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: OrderBriefingViewHolder, position: Int) {
        with(holder.mBinding) {
            model = items[position]
            root.tag = items[position].id
            root.setOnClickListener(mListener)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    companion object {
        class OrderBriefingViewHolder(view: View, binding: ItemOrderBriefingBinding) : RecyclerView.ViewHolder(view) {
            val mBinding: ItemOrderBriefingBinding = binding
        }

        class OrderBriefingListener : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(v?.context, OrderDetailActivity::class.java)
                intent.putExtra(OrderDetailActivity.ARG_ORDER_ID, v?.tag as Int)
                v.context.startActivity(intent)
            }
        }
    }


}