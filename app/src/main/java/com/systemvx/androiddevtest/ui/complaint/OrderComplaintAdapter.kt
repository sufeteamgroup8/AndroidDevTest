package com.systemvx.androiddevtest.ui.complaint

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.OrderTypeBean

class OrderComplaintAdapter(private val context: Context, private val list: List<OrderTypeBean>) :
        RecyclerView.Adapter<OrderComplaintAdapter.MyViewHolder>() {
    var onItemClick: OnItemClick? = null

    interface OnItemClick {
        fun OnItemClick(view: View?, position: Int)
    }

    fun setOnItemClickLisener(clickLisener: OnItemClick?) {
        onItemClick = clickLisener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_compaint_type, null, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.itemView.setOnClickListener { view: View? -> onItemClick!!.OnItemClick(view, position) }
        if (list[position].isCheck) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200))
        } else {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}