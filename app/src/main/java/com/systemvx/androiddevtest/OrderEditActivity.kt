package com.systemvx.androiddevtest

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.systemvx.devapp.business.OrderManagement
import com.systemvx.devapp.data.OrderEditData
import com.systemvx.devapp.databinding.ActivityOrderEditBinding
import java.text.SimpleDateFormat

class OrderEditActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityOrderEditBinding
    private var mInputData = OrderEditData()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_order_edit)
        mBinding.inputData = mInputData
        mBinding.buttonCancel.setOnClickListener(View.OnClickListener {
            this.finish()
        })
        mBinding.buttonSubmit.setOnClickListener(View.OnClickListener {
            Thread(Runnable {
                OrderManagement().createNewOrderWithDetail(
                mInputData.title,
                mInputData.maintext,
                mInputData.price.toDouble(),
                SimpleDateFormat("yyyyMMdd").parse(mInputData.deadline),
                0,//mInputData.address.toInt()
                1)
                runOnUiThread(Runnable{
                    Toast.makeText(this,"创建成功",Toast.LENGTH_LONG).show()
                    var intent = Intent(this,OrderBrowsingActivity::class.java)
                    intent.putExtra("orderID",3)
                    startActivity(intent)
                })
            }).start()

        })

    }
}