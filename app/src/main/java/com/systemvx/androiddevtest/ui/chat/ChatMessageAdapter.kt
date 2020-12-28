package com.systemvx.androiddevtest.ui.chat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.Chat
import com.systemvx.androiddevtest.databinding.ItemChatMessageTextReceiveBinding
import com.systemvx.androiddevtest.databinding.ItemChatMessageTextSendBinding
import com.systemvx.androiddevtest.databinding.ItemChatTimestampBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ChatMessageAdapter(val context: Context, chatRecords: ArrayList<ChatShowCase.Message>) :
        RecyclerView.Adapter<ChatMessageAdapter.Companion.ChatMessageViewHolder>() {

    private var displayList: ArrayList<Chat> = ArrayList()

    //准备显示内容
    init {
        if (displayList.isNotEmpty()) {
            displayList.add(chatRecords[0])
            for (i in 0 until chatRecords.size - 1) {
                //两条消息间间隔过长,
                //则加入时间戳
                if (compareRecordTime(chatRecords[i], chatRecords[i + 1]))
                    displayList.add(Chat.ChatTimeNote(
                            SimpleDateFormat("MM-d hh-mm", Locale.CHINA).format(chatRecords[i + 1].sendTime))
                    )
                //加入消息本体
                displayList.add(chatRecords[i + 1])
            }
        }


    }


    private fun compareRecordTime(soonerMessage: ChatShowCase.Message, laterMessage: ChatShowCase.Message): Boolean {
        val interval = 1000 * 60 * 60 * 4//ms-s-min-hour-6hours
        return (laterMessage.sendTime.time - soonerMessage.sendTime.time) > interval
    }


    override fun getItemViewType(position: Int): Int {
        return when (displayList[position].javaClass) {
            ChatShowCase.TimeNote::class.java -> TIME_INDICATOR
            ChatShowCase.Message::class.java -> {
                val temp = displayList[position] as ChatShowCase.Message
                if (temp.isSend) {
                    MESSAGE_TEXT_SEND
                } else {
                    MESSAGE_TEXT_RECEIVE
                }
            }
            else -> OTHERS
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatMessageViewHolder {
        when (viewType) {
            MESSAGE_TEXT_RECEIVE -> {
                val binding: ItemChatMessageTextReceiveBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.item_chat_message_text_receive,
                        parent, false)
                return ChatMessageViewHolder(binding.root, viewType)
            }
            MESSAGE_TEXT_SEND -> {
                val binding: ItemChatMessageTextSendBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.item_chat_message_text_receive,
                        parent, false)
                return ChatMessageViewHolder(binding.root, viewType)
            }
            TIME_INDICATOR -> {
                val binding: ItemChatTimestampBinding = DataBindingUtil.inflate(
                        LayoutInflater.from(context),
                        R.layout.item_chat_message_text_receive,
                        parent, false)
                return ChatMessageViewHolder(binding.root, viewType)
            }
            else -> throw Exception("invalid view type")
        }
    }

    override fun onBindViewHolder(holder: ChatMessageViewHolder, position: Int) {
        when (holder.type) {
            MESSAGE_TEXT_RECEIVE -> {
                val binding: ItemChatMessageTextReceiveBinding = DataBindingUtil.getBinding(holder.itemView)!!
                with(displayList[position] as ChatShowCase.Message) {
                    binding.name = senderNickName
                    binding.text = messageText
                }

            }
            MESSAGE_TEXT_SEND -> {
                val binding: ItemChatMessageTextSendBinding = DataBindingUtil.getBinding(holder.itemView)!!
                with(displayList[position] as ChatShowCase.Message) {
                    binding.name = senderNickName
                    binding.text = messageText
                }
            }
            TIME_INDICATOR -> {
                val binding: ItemChatTimestampBinding = DataBindingUtil.getBinding(holder.itemView)!!
                with(displayList[position] as Chat.ChatTimeNote) {
                    binding.timeStr = timeStr
                }
            }

        }
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    companion object {

        const val TIME_INDICATOR = 0
        const val MESSAGE_TEXT_SEND = 1
        const val MESSAGE_TEXT_RECEIVE = 2
        const val OTHERS = 3

        class ChatMessageViewHolder(itemView: View, val type: Int) : RecyclerView.ViewHolder(itemView)

    }
}