package com.systemvx.androiddevtest.ui.payment

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.databinding.ActivityCoinManageBinding

class CoinManageActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCoinManageBinding

    private lateinit var mViewModel: CoinManageViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coin_manage)
        mViewModel = ViewModelProvider(this).get(CoinManageViewModel::class.java)
        mBinding.btnPay.setOnClickListener {
            val intent = Intent(this@CoinManageActivity, PayAndWithdrawActivity::class.java)
            intent.putExtra("isPay", true)
            startActivity(intent)
            //跳转到充值提现页面
        }
        mBinding.btnWithdraw.setOnClickListener {
            val intent = Intent(this@CoinManageActivity, PayAndWithdrawActivity::class.java)
            intent.putExtra("isPay", false)
            startActivity(intent)
            //跳转到充值提现页面
        }
    }
}