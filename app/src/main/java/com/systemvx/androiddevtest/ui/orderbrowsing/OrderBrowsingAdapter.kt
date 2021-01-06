package com.systemvx.androiddevtest.ui.orderbrowsing

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

class OrderBrowsingAdapter(private val mType: String, val context: Context) : RecyclerView.Adapter<OrderBrowsingAdapter.OrderBrowsingVHolder>() {

    private val mDataList = ArrayList<OrderBriefing>()

    class OrderBrowsingVHolder(view: View, val viewType: Int, val binding: ItemOrderBriefingBinding) : RecyclerView.ViewHolder(view)

    private fun checkData(order: OrderBriefing): Boolean {
        return when (mType) {
            "all" -> true
            "debate" -> order.state == 5
            "ended" -> order.state in setOf(3, 4, 6)

            "draft" -> order.state == 0
            "published" -> order.state == 1
            "active" -> order.state == 2

            "received" -> order.state <= 2
            else -> true
        }
    }

    fun updateData(data: ArrayList<OrderBriefing>) {
        mDataList.clear()
        for (order in data) {
            if (checkData(order))
                mDataList.add(order)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBrowsingVHolder {
        val binding = DataBindingUtil.inflate<ItemOrderBriefingBinding>(
                LayoutInflater.from(context),
                R.layout.item_order_briefing,
                parent, false)
        return OrderBrowsingVHolder(binding.root, viewType, binding)

    }

    override fun onBindViewHolder(holder: OrderBrowsingVHolder, position: Int) {
        holder.binding.model = mDataList[position]
        holder.binding.root.setOnClickListener {
            val intent = Intent(context, OrderDetailActivity::class.java)
                    .putExtra(OrderDetailActivity.ARG_ORDER_ID, mDataList[position].id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}