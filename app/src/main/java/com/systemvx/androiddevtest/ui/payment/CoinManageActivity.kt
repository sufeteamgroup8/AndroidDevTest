package com.systemvx.androiddevtest.ui.payment

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.databinding.ActivityCoinManageBinding
import java.text.DecimalFormat

class CoinManageActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityCoinManageBinding

    private lateinit var mViewModel: CoinManageViewModel

    private lateinit var mAdapter: PayAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_coin_manage)
        mViewModel = ViewModelProvider(this).get(CoinManageViewModel::class.java)


        mAdapter = PayAdapter(this)

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
        mBinding.tvBack.setOnClickListener { finish() }

        val act = this
        mViewModel.getCoinTrans()
        with(mBinding.rvCoin) {
            layoutManager = LinearLayoutManager(act)
            addItemDecoration(DividerItemDecoration(act, DividerItemDecoration.VERTICAL))
            adapter = mAdapter
        }

        mBinding.deposit.text = DecimalFormat("#.##").format(LoginRepository.user?.coin) ?: ""

        mViewModel.getCoinTrans()
        mViewModel.netResult.observe(this, {
            if (it) {
                mAdapter.updateData(mViewModel.coinData)
            } else {
                Toast.makeText(this, "无法获取金币详单\n请检查网络", Toast.LENGTH_LONG).show()
            }
        })
    }
}