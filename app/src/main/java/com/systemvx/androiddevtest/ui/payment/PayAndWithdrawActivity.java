package com.systemvx.androiddevtest.ui.payment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.systemvx.androiddevtest.R;

public class PayAndWithdrawActivity extends AppCompatActivity implements View.OnClickListener {
    private Handler handler = new Handler();
    private ProgressBar pbBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_withdraw);
        findViewById(R.id.toLogin1).setOnClickListener(this);
        findViewById(R.id.toLogin2).setOnClickListener(this);
        pbBar = findViewById(R.id.pb_bar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toLogin1:
                pbBar.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    ToastUtils.showShort("充值成功");
                    pbBar.setVisibility(View.GONE);
                }, 1500);

                break;
            case R.id.toLogin2:
                pbBar.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    pbBar.setVisibility(View.GONE);
                    ToastUtils.showShort("提现成功");
                }, 1500);

                break;
        }
    }
}