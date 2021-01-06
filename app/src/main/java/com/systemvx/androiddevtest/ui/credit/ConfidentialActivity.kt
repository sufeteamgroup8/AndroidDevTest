package com.systemvx.androiddevtest.ui.credit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivityConfidentialBinding

class ConfidentialActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityConfidentialBinding

    private lateinit var viewModel: CreditViewModel

    private lateinit var mAdapter: CreditInfoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_confidential)

        viewModel = ViewModelProvider(this).get(CreditViewModel::class.java)

        mBinding.model = viewModel

        mBinding.tvBack.setOnClickListener { finish() }
        mAdapter = CreditInfoAdapter(context = this)
        mBinding.rvCredit.layoutManager = LinearLayoutManager(this)
        mBinding.rvCredit.adapter = mAdapter

        viewModel.netResult.observe(this, {
            if (it) {
                mAdapter.updateData(viewModel.creditList)
            }
        })

        viewModel.fetchCreditInfo()


    }


}