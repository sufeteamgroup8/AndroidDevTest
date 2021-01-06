package com.systemvx.androiddevtest.ui.complaint

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivityCommentSendBinding
import com.systemvx.androiddevtest.utils.HttpUtil
import java.util.*

class ComplaintSendActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityCommentSendBinding

    private lateinit var mViewModel: ComplaintSendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_send)

        mViewModel = ViewModelProvider(this).get(ComplaintSendViewModel::class.java)

        mBinding.tvBack.setOnClickListener { this.finish() }
        mBinding.butSubmit.setOnClickListener {
            if (kindValidate()) {
            }
        }
    }


    private fun kindValidate(): Boolean {
        mBinding.
    }

    private fun addComplaint(text: String?, content: String) {
        //Map封装
        val map = HashMap<String, String?>()
        map["complaint_reason"] = text
        map["complaint_content"] = content
        val url = HttpUtil.BASE_URL + "complaint"
        HttpUtil().postRequest(url, map)
    }

    companion object {
        const val ARG_ORDER_ID = "order_detail"
    }
}