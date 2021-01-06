package com.systemvx.androiddevtest.ui.comment

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.AccountDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.databinding.ActivityCommentSendBinding
import com.systemvx.androiddevtest.ui.util.DummyDataSet
import com.systemvx.androiddevtest.ui.util.RoundProgressDialog

class CommentSendActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCommentSendBinding

    private lateinit var viewModel: CommentSendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val orderData: OrderDetail = if (!ProjectSettings.fakeData) {
            intent.getSerializableExtra(ARG_ORDER_DATA) as OrderDetail
        } else {
            DummyDataSet.dummyDetail
        }

        LoginRepository(AccountDataSource()).login("", "")

        mBinding = DataBindingUtil
                .setContentView(this, R.layout.activity_comment_send)

        viewModel = ViewModelProvider(this, CommentSendViewModelFactory(data = orderData))
                .get(CommentSendViewModel::class.java)

        val progressDialog = RoundProgressDialog.getInstance(context = this)
        mBinding.viewModel = this.viewModel
        viewModel.netResult.observe(this, {
            progressDialog.dismiss()
            if (it) {
                Toast.makeText(this, "评分成功!", Toast.LENGTH_SHORT).show()
                this.finish()
            } else {
                Toast.makeText(this, "提交失败了呢..", Toast.LENGTH_SHORT).show()
            }
        })
        mBinding.butSubmit.setOnClickListener {
            progressDialog.show()
            if (!viewModel.sendComment()) {
                progressDialog.dismiss()
            }
        }
        mBinding.tvBack.setOnClickListener {
            this.finish()
        }

    }

    companion object {
        const val ARG_ORDER_DATA = "order_data"
    }
}