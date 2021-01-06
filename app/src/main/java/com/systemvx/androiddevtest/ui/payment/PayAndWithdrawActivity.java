package com.systemvx.androiddevtest.ui.payment;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.systemvx.androiddevtest.R;

public class PayAndWithdrawActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_withdraw);
        findViewById(R.id.toLogin1).setOnClickListener(this);
        findViewById(R.id.toLogin2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.toLogin1:
                ToastUtils.showShort("充值成功");
                break;
            case R.id.toLogin2:
                ToastUtils.showShort("提现成功");
                break;
        }
    }
}