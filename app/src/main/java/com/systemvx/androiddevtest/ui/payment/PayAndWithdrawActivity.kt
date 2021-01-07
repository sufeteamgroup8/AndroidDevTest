package com.systemvx.androiddevtest.ui.payment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.systemvx.androiddevtest.R
import com.systemvx.androiddevtest.data.CoinDataSource
import com.systemvx.androiddevtest.data.LoginRepository
import com.systemvx.androiddevtest.ui.setting.SettingActivity

class PayAndWithdrawActivity : AppCompatActivity(), View.OnClickListener {
    private val handler = Handler()
    private var pbBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pay_and_withdraw)
        findViewById<View>(R.id.toLogin1).setOnClickListener(this)
        findViewById<View>(R.id.toLogin2).setOnClickListener(this)
        findViewById<View>(R.id.setting).setOnClickListener(this)
        findViewById<View>(R.id.tv_back).setOnClickListener(this)
        pbBar = findViewById(R.id.pb_bar)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.toLogin1 -> {
                CoinDataSource().makePayment(LoginRepository.user!!.id, 50.0)
                pbBar!!.visibility = View.VISIBLE
                handler.postDelayed({
                    Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show()
                    pbBar!!.visibility = View.GONE
                }, 1500)
            }
            R.id.toLogin2 -> {
                CoinDataSource().makeWithdraw(LoginRepository.user!!.id, 10.0)
                pbBar!!.visibility = View.VISIBLE
                handler.postDelayed({
                    pbBar!!.visibility = View.GONE
                    Toast.makeText(this, "提现成功", Toast.LENGTH_SHORT).show()
                }, 1500)
            }
            R.id.setting -> startActivity(Intent(this, SettingActivity::class.java))
            R.id.tv_back -> finish()
        }
    }
}