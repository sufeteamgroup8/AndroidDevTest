package com.systemvx.androiddevtest.ui.payment


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.PaymentBriefing
import com.systemvx.androiddevtest.databinding.PaymentItemBinding


class payAdapter(
        val context: Context,

        ) :
        RecyclerView.Adapter<payAdapter.Companion.payBriefingViewHolder>() {

    private val items: ArrayList<PaymentBriefing> = ArrayList()


    fun updateData(data: ArrayList<PaymentBriefing>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): payBriefingViewHolder {
        val binding: PaymentItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.payment_item,
                        parent, false)
        return payBriefingViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: payBriefingViewHolder, position: Int) {
        with(holder.mBinding) {
            model = items[position]
            root.tag = items[position].price
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    companion object {
        class payBriefingViewHolder(view: View, binding: PaymentItemBinding) : RecyclerView.ViewHolder(view) {
            val mBinding: PaymentItemBinding = binding
        }


    }


}