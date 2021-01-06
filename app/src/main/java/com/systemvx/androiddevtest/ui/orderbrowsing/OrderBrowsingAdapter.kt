package com.systemvx.androiddevtest.ui.orderbrowsing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.OrderBriefing
import com.systemvx.androiddevtest.databinding.ItemOrderBriefingBinding
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity

class OrderBrowsingAdapter(private val mType: String, val context: Context, val listener: View.OnClickListener) : RecyclerView.Adapter<OrderBrowsingAdapter.OrderBrowsingVHolder>() {

    private val mDataList = ArrayList<OrderBriefing>()

    class OrderBrowsingVHolder(view: View, val viewType: Int, val binding: ItemOrderBriefingBinding) : RecyclerView.ViewHolder(view)

    override fun getItemViewType(position: Int): Int {
        return when (mType) {
            "all" -> TYPE_ALL
            "published" -> TYPE_PUBLISHED
            "received" -> TYPE_RECEIVED
            "active" -> TYPE_ACTIVE
            else -> TYPE_ENDED
        }
    }


    private fun checkData(order: OrderBriefing): Boolean {
        return when (mType) {
            "all" -> true
            "published" -> order.state == 1
            "received" -> order.state == 2
            "active" -> order.state == 3
            else -> order.state == 4
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


    companion object {
        const val TYPE_ALL = 0
        const val TYPE_PUBLISHED = 1
        const val TYPE_RECEIVED = 2
        const val TYPE_ACTIVE = 3
        const val TYPE_ENDED = 4
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderBrowsingVHolder {
        val binding = DataBindingUtil.inflate<ItemOrderBriefingBinding>(
                LayoutInflater.from(context),
                R.layout.item_order_briefing,
                parent, false)
        //setupButtons(binding, viewType)
        binding.root.setOnClickListener {
            context.startActivity(Intent(context, OrderDetailActivity::class.java))
        }
        return OrderBrowsingVHolder(binding.root, viewType, binding)

    }

    override fun onBindViewHolder(holder: OrderBrowsingVHolder, position: Int) {
        holder.binding.model = mDataList[position]
    }

    private fun setupButtons(binding: ItemOrderBriefingBinding, viewType: Int) {
        when (viewType) {
            TYPE_PUBLISHED -> {
                binding.actionButtonHolder.addView(addButton("btnEdit", "编辑"))
                binding.actionButtonHolder.addView(addButton("btnCancel", "撤回"))
            }
            TYPE_RECEIVED ->
                binding.actionButtonHolder.addView(addButton("btnConfirm", "确认完成"))
            TYPE_ENDED ->
                binding.actionButtonHolder.addView(addButton("btnComment", "评价对方"))
        }
    }

    private fun addButton(tag: String, text: String): Button {
        val btn = Button(context)
        btn.tag = tag
        btn.text = text
        btn.id
        btn.setOnClickListener(listener)
        return btn
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

}