package com.systemvx.androiddevtest.ui.complaint

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.ProjectSettings
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.model.OrderDetail
import com.systemvx.androiddevtest.databinding.ActivityComplaintSendBinding
import com.systemvx.androiddevtest.ui.util.DummyDataSet
import com.systemvx.androiddevtest.ui.util.RoundProgressDialog

class ComplaintSendActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityComplaintSendBinding

    private lateinit var mViewModel: ComplaintSendViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_complaint_send)

        val orderDetail = if (ProjectSettings.fakeData) {
            DummyDataSet.dummyDetail
        } else {
            intent.getSerializableExtra(ARG_ORDER_DETAIL) as OrderDetail
        }

        mViewModel = ViewModelProvider(this,
                ComplaintSendViewModel.CommentSendViewModelFactory(orderDetail))
                .get(ComplaintSendViewModel::class.java)

        val process = RoundProgressDialog.getInstance(this)
        mBinding.complaintCancel.setOnClickListener { this.finish() }
        mBinding.feedBackBtn.setOnClickListener {
            if (mBinding.feedBackEdit.text.toString().isBlank()) {
                Toast.makeText(this, "请填写投诉原因", Toast.LENGTH_LONG).show()
            } else {
                process.show()
                mViewModel.submitComplaint(mBinding.feedBackEdit.text.toString())
            }
        }

        mBinding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            mViewModel.reasonText = when (checkedId) {
                mBinding.radiobutton1.id -> mBinding.radiobutton1.text.toString()
                mBinding.radiobutton2.id -> mBinding.radiobutton2.text.toString()
                mBinding.radiobutton3.id -> mBinding.radiobutton3.text.toString()
                mBinding.radiobutton4.id -> mBinding.radiobutton4.text.toString()
                else -> ""
            }
        }

        mViewModel.netResult.observe(this, {
            process.dismiss()
            if (it) {
                Toast.makeText(this, "发送成功", Toast.LENGTH_LONG).show()
                this.finish()
            } else {
                Toast.makeText(this, "发送失败", Toast.LENGTH_LONG).show()
            }
        })
    }

    companion object {
        const val ARG_ORDER_DETAIL = "order_detail"
    }
}