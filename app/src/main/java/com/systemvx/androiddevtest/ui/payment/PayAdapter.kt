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


class PayAdapter(
        val context: Context,

        ) :
        RecyclerView.Adapter<PayAdapter.Companion.PayBriefingViewHolder>() {

    private val items: ArrayList<PaymentBriefing> = ArrayList()


    fun updateData(data: ArrayList<PaymentBriefing>) {
        this.items.clear()
        this.items.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PayBriefingViewHolder {
        val binding: PaymentItemBinding =
                DataBindingUtil.inflate(LayoutInflater.from(context),
                        R.layout.payment_item,
                        parent, false)
        return PayBriefingViewHolder(binding.root, binding)

    }

    override fun onBindViewHolder(holder: PayBriefingViewHolder, position: Int) {
        with(holder.mBinding) {
            model = items[position]
            if (items[position].price >= 0) {
                coinAmount.setTextColor(context.getColor(R.color.green))
                coinAmount.text = "+ ${items[position].price}"
            } else {
                coinAmount.setTextColor(context.getColor(R.color.colo_red))
                coinAmount.text = "${items[position].price}"
            }
            executePendingBindings()
        }
    }

    override fun getItemCount(): Int {
        return this.items.size
    }

    companion object {
        class PayBriefingViewHolder(view: View, binding: PaymentItemBinding) : RecyclerView.ViewHolder(view) {
            val mBinding: PaymentItemBinding = binding
        }
    }


}