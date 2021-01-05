package com.systemvx.androiddevtest.ui.credit

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.CreditShowCase
import com.systemvx.androiddevtest.databinding.ItemCreditDetailBinding
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity
import com.systemvx.androiddevtest.ui.util.GeneralBindingViewHolder

class CreditInfoAdapter(val context: Context) : RecyclerView.Adapter<GeneralBindingViewHolder<ItemCreditDetailBinding>>() {
    private var list = ArrayList<CreditShowCase>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GeneralBindingViewHolder<ItemCreditDetailBinding> {
        val binding: ItemCreditDetailBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.item_credit_detail,
                parent, false)
        return GeneralBindingViewHolder(binding.root, binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: GeneralBindingViewHolder<ItemCreditDetailBinding>, position: Int) {
        with(holder.mBinding) {
            this.model = list[position]

            if (list[position].amount >= 0) {
                this.creditAmount.setTextColor(context.getColor(R.color.green))
                creditAmount.text = "+ ${list[position].amount}"
            } else {
                this.creditAmount.setTextColor(context.getColor(R.color.colo_red))
                creditAmount.text = "${list[position].amount}"
            }
            if (list[position].orderID != null) {
                this.root.setOnClickListener {
                    val intent = Intent(context, OrderDetailActivity::class.java)
                    intent.putExtra(OrderDetailActivity.ARG_ORDER_ID, list[position].orderID)
                    context.startActivity(intent)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateData(creditList: ArrayList<CreditShowCase>) {
        list = creditList
        notifyDataSetChanged()
    }
}