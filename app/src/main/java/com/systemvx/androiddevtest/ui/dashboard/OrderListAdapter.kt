package com.systemvx.androiddevtest.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.databinding.OrderBriefingItemBinding


class OrderListAdapter(val context: Context?) : RecyclerView.Adapter<OrderListAdapter.Companion.OrderBriefingViewHolder>() {


    private val items: List<OrderBriefing> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBriefingViewHolder {
        val binding: OrderBriefingItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.order_briefing_item,
                        parent, false)
        return OrderBriefingViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: OrderBriefingViewHolder, position: Int) {
        val binding: OrderBriefingItemBinding? = DataBindingUtil.getBinding(holder.itemView)
        binding?.model = items[position]
        binding?.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    companion object {
        class OrderBriefingViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }
    }


}