package com.systemvx.androiddevtest.ui.complaint;


import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import android.widget.TextView;
import android.widget.Toast;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.utils.HttpUtil;

import java.util.HashMap;

public class complaintActivity extends Activity {
    private EditText mFeedBackEditText;
    private Button mSendFeedBackButton;
    private RadioGroup radiogroup;
    private RadioButton radio1,radio2,radio3,radio4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        mFeedBackEditText = (EditText) findViewById(R.id.feed_back_edit);
        mSendFeedBackButton = (Button) findViewById(R.id.feed_back_btn);
        radiogroup = (RadioGroup)findViewById(R.id.radio_group);
        radio1 = (RadioButton) findViewById(R.id.radiobutton1);
        radio2 = (RadioButton) findViewById(R.id.radiobutton2);
        radio3 = (RadioButton) findViewById(R.id.radiobutton3);
        radio4 = (RadioButton) findViewById(R.id.radiobutton4);
        String text="投诉原因：";
        boolean validate=true;
        int selected=radiogroup.getCheckedRadioButtonId();
        switch (selected)
        {
            case R.id.radiobutton1: text+=radio1.getText().toString();
            case R.id.radiobutton2: text+=radio2.getText().toString();
            case R.id.radiobutton3: text+=radio3.getText().toString();
            case R.id.radiobutton4: text+=radio4.getText().toString();
            default:validate=false;

        }


        boolean finalValidate = validate;
        String finalText = text;
        mSendFeedBackButton.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View v) {
                String content = mFeedBackEditText.getText().toString();

                if (finalValidate) {
                    if (!TextUtils.isEmpty(content)) {
                        try {
                            String result=addComplaint(finalText,content);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "请输入内容！",
                                Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
    }

    private String addComplaint(String text, String content) throws Exception {
        //Map封装
        HashMap<String, String> map = new HashMap<>();
        map.put("complaint_reason", text);
        map.put("complaint_content", content);
        String url = HttpUtil.BASE_URL + "complaint";
        return new HttpUtil().postRequest(url, map);
    }

}