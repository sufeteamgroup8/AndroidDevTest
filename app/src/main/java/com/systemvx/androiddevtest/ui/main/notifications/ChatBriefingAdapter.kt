package com.systemvx.androiddevtest.ui.main.notifications

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.ChatterInfo
import com.systemvx.androiddevtest.databinding.ItemChatHeaderBinding
import com.systemvx.androiddevtest.ui.chat.ChatActivity

class ChatBriefingAdapter(val context: Context) :
        RecyclerView.Adapter<ChatBriefingAdapter.Companion.ChatBriefingViewHolder>() {
    private val dataList = ArrayList<ChatterInfo>()

    fun updateWithNewData(info: ArrayList<ChatterInfo>) {
        dataList.clear()
        dataList.addAll(info)
        notifyDataSetChanged()
    }

    fun insertData(info: ChatterInfo, position: Int) {
        if (position < dataList.size) {
            dataList.add(position, info)
        } else {
            dataList.add(info)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatBriefingViewHolder {
        val mBinding = DataBindingUtil.inflate<ItemChatHeaderBinding>(LayoutInflater.from(context),
                R.layout.item_chat_header,
                parent, false)

        return ChatBriefingViewHolder(mBinding.root, mBinding)
    }

    override fun onBindViewHolder(holder: ChatBriefingViewHolder, position: Int) {
        with(holder.mBinding) {
            root.setOnClickListener(View.OnClickListener {
                val intent = Intent(context, ChatActivity::class.java)
                intent.putExtra(ChatActivity.ARG_CHATTER_ID, dataList[position].id)
                context.startActivity(intent)
            })
            chatNickname.text = dataList[position].nickname
            if (dataList[position].unRead) {
                redDot.visibility = View.VISIBLE
            } else {
                redDot.visibility = View.INVISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    companion object {
        class ChatBriefingViewHolder(view: View, val mBinding: ItemChatHeaderBinding) : RecyclerView.ViewHolder(view)
    }
}