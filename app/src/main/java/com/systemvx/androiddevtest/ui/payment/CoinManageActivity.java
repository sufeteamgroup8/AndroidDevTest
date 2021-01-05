package com.systemvx.androiddevtest.ui.payment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.systemvx.androiddevtest.R;

public class CoinManageActivity extends AppCompatActivity {
    private Button payBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coin_manage);

        payBtn = findViewById(R.id.pay_btn);
        payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoinManageActivity.this, PayAndWithdrawActivity.class);
                startActivity(intent);
                //跳转到充值提现页面
            }
        });
    }
}