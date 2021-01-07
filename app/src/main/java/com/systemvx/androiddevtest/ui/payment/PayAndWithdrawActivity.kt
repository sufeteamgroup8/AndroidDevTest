package com.systemvx.androiddevtest.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.ui.setting.SettingActivity;

public class PayAndWithdrawActivity extends AppCompatActivity implements View.OnClickListener {
    private final Handler handler = new Handler();
    private ProgressBar pbBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_and_withdraw);
        findViewById(R.id.toLogin1).setOnClickListener(this);
        findViewById(R.id.toLogin2).setOnClickListener(this);
        findViewById(R.id.setting).setOnClickListener(this);
        findViewById(R.id.tv_back).setOnClickListener(this);
        pbBar = findViewById(R.id.pb_bar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.toLogin1:
                pbBar.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    Toast.makeText(this, "充值成功", Toast.LENGTH_SHORT).show();
                    pbBar.setVisibility(View.GONE);
                }, 1500);
                break;
            case R.id.toLogin2:
                pbBar.setVisibility(View.VISIBLE);
                handler.postDelayed(() -> {
                    pbBar.setVisibility(View.GONE);
                    Toast.makeText(this, "提现成功", Toast.LENGTH_SHORT).show();
                }, 1500);
                break;
            case R.id.setting:
                startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.tv_back:
                finish();
                break;
        }
    }
}