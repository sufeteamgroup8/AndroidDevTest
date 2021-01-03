package com.systemvx.androiddevtest.ui.orderManagement;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.systemvx.androiddevtest.R;

public class OrderManagementActivity extends Activity {

    private RelativeLayout rl_person_alllist;//全部订单
    private RelativeLayout rl_person_wait_pay;//待支付
    private RelativeLayout rl_person_send;//待发货
    private RelativeLayout rl_person_wait_get;//待收货
    private RelativeLayout rl_person_aftermarket;//售后
    private Intent intent;//需要启动的界面

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_management);
        initView();
        initData();
    }

    private void initData() {
        /**
         * 全部订单
         */
        rl_person_alllist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });

        /**
         * 待支付
         */
        rl_person_wait_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("page",1);
                startActivity(intent);
            }
        });

        /**
         * 待发货
         */
        rl_person_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("page",2);
                startActivity(intent);
            }
        });

        /**
         * 待收货
         */
        rl_person_wait_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("page",3);
                startActivity(intent);
            }
        });

        /**
         * 售后
         */
        rl_person_aftermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(),OrderActivity.class);
                intent.putExtra("page",4);
                startActivity(intent);
            }
        });
    }

    private void initView() {
        rl_person_alllist = (RelativeLayout) findViewById(R.id.rl_person_alllist);
        rl_person_wait_pay = (RelativeLayout) findViewById(R.id.rl_person_wait_pay);
        rl_person_send = (RelativeLayout) findViewById(R.id.rl_person_send);
        rl_person_wait_get = (RelativeLayout) findViewById(R.id.rl_person_wait_get);
        rl_person_aftermarket = (RelativeLayout) findViewById(R.id.rl_person_aftermarket);
    }
}