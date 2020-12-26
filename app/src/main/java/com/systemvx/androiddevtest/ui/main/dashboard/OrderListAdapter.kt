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
import com.systemvx.androiddevtest.databinding.OrderBriefingItemBinding
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity


class OrderListAdapter(
        val context: Context?,
        private val items: List<OrderBriefing> = ArrayList(),
        listener: OrderBriefingListener? = null,
) :
        RecyclerView.Adapter<OrderListAdapter.Companion.OrderBriefingViewHolder>() {
    private val mListener: OrderBriefingListener = listener ?: OrderBriefingListener()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBriefingViewHolder {
        val binding: OrderBriefingItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.order_briefing_item,
                        parent, false)
        return OrderBriefingViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: OrderBriefingViewHolder, position: Int) {
        with(holder.mBinding) {
            model = items[position]
            root.tag = model.id
            root.setOnClickListener(mListener)
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    companion object {
        class OrderBriefingViewHolder(view: View, binding: OrderBriefingItemBinding) : RecyclerView.ViewHolder(view) {
            val mBinding: OrderBriefingItemBinding = binding
        }

        class OrderBriefingListener : View.OnClickListener {
            override fun onClick(v: View?) {
                val intent = Intent(v?.context, OrderDetailActivity::class.java)
                intent.putExtra("order_id", v?.tag as Int)
                v.context.startActivity(intent)
            }
        }
    }


}