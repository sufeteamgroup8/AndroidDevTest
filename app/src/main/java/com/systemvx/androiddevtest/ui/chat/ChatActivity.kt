package com.systemvx.androiddevtest.ui.chat

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.ChatShowCase
import com.systemvx.androiddevtest.data.LoginDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.Result
import com.systemvx.androiddevtest.databinding.ActivityChatBinding
import java.util.*

class ChatActivity : AppCompatActivity() {
    companion object {
        const val ARG_CHATTER_ID = "id"
    }

    private lateinit var mBinding: ActivityChatBinding

    private val model: ChatViewModel = ChatViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.chatterID = intent.getIntExtra(ARG_CHATTER_ID, 0)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)


        model.dataResult.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    mBinding.chatShowBox.layoutManager = LinearLayoutManager(this)
                    mBinding.chatShowBox.adapter = ChatMessageAdapter(this, model.chatHistory)

                }
                is Result.Error -> {
                    mBinding.errorText.visibility = View.VISIBLE
                }
            }

        })


        mBinding.chatShowBox.layoutManager = LinearLayoutManager(this)
        mBinding.chatShowBox.adapter = ChatMessageAdapter(this, model.chatHistory)
        mBinding.backBtn.setOnClickListener(View.OnClickListener { this.finish() })
        mBinding.btnSend.setOnClickListener(View.OnClickListener { this.sendMessage() })


        //测试用
        LoginRepository(LoginDataSource()).login("", "")
        model.chatterNickname.postValue("F.A.T.H.E.R")

        model.findChatData(model.chatterID)
    }


    fun sendMessage() {
        if (!mBinding.textSend.text.toString().isNullOrBlank()) {
            val message = ChatShowCase.Message(
                    LoginRepository.user!!.nickname!!,
                    mBinding.textSend.text.toString(),
                    Date(),
                    true
            )
            (mBinding.chatShowBox.adapter as ChatMessageAdapter).addMessage(message)
            mBinding.chatShowBox.smoothScrollToPosition((mBinding.chatShowBox.adapter as ChatMessageAdapter).itemCount)

        }
    }
}