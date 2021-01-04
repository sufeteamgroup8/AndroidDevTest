package com.systemvx.androiddevtest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.systemvx.androiddevtest.ui.main.home.HomeViewModel;

public class ConfidentialActivity extends AppCompatActivity {
    private Button button_1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confidential);

    }
}