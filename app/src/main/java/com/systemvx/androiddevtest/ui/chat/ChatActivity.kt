package com.systemvx.androiddevtest.ui.chat

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.AccountDataSource
import com.systemvx.androiddevtest.data.ChatDataSource
import com.systemvx.androiddevtest.data.ChatShowCase
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.ActivityChatBinding
import com.systemvx.androiddevtest.ui.login.LoginActivity
import java.util.*

class ChatActivity : AppCompatActivity() {
    companion object {
        const val ARG_CHATTER_ID = "id"
    }

    private lateinit var mBinding: ActivityChatBinding

    private val model: ChatViewModel = ChatViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        model.chatterID = intent.getIntExtra(ARG_CHATTER_ID, -1)

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_chat)

        LoginRepository(AccountDataSource()).login("", "")
        if (!LoginRepository.isLoggedIn) {
            startActivity(Intent(this, LoginActivity::class.java))
            this.finish()
        }


        model.dataResult.observe(this, Observer {
            when (it) {
                true -> {
                    mBinding.chatShowBox.layoutManager =
                            LinearLayoutManager(this)
                                    .apply { stackFromEnd = true;reverseLayout = true }
                    mBinding.chatShowBox.adapter = ChatMessageAdapter(this, model.chatHistory)

                }
                false -> {
                    mBinding.errorText.visibility = View.VISIBLE
                }
            }

        })


        mBinding.chatShowBox.layoutManager = LinearLayoutManager(this)
        mBinding.chatShowBox.adapter = ChatMessageAdapter(this, model.chatHistory)
        mBinding.backBtn.setOnClickListener(View.OnClickListener { this.finish() })
        mBinding.btnSend.setOnClickListener(View.OnClickListener { this.sendMessage() })


        model.findChatData(model.chatterID)
    }

    override fun onResume() {
        super.onResume()
        ChatDataSource().setToRead(model.chatHistory)
    }


    private fun sendMessage() {
        if (!mBinding.textSend.text.toString().isNullOrBlank()) {
            val message = ChatShowCase.Message(
                    null,
                    LoginRepository.user!!.nickname!!,
                    mBinding.textSend.text.toString(),
                    Date(),
                    true
            )
            (mBinding.chatShowBox.adapter as ChatMessageAdapter).addMessage(message)
            mBinding.chatShowBox.smoothScrollToPosition(0)
            model.sendMessage(mBinding.textSend.text.toString())
            mBinding.textSend.text.clear()
        }
    }
}