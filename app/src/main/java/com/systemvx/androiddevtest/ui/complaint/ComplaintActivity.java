package com.systemvx.androiddevtest.ui.complaint;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity;
import com.systemvx.androiddevtest.utils.HttpUtil;

import java.util.HashMap;

public class ComplaintActivity extends Activity {
    public static final String ARG_ORDER_ID = "id";

    private EditText mFeedBackEditText;
    private RadioGroup radiogroup;
    private String text;
    RadioButton radio1 = findViewById(R.id.radiobutton1);
    RadioButton radio2 = findViewById(R.id.radiobutton2);
    RadioButton radio3 = findViewById(R.id.radiobutton3);
    RadioButton radio4 = findViewById(R.id.radiobutton4);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);
        mFeedBackEditText =  findViewById(R.id.feed_back_edit);
        Button mSendFeedBackButton = findViewById(R.id.feed_back_btn);
        radiogroup = findViewById(R.id.radio_group);
        Button mCancelButton=findViewById(R.id.complaintCancel);
        mCancelButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, OrderDetailActivity.class);
            startActivity(intent);
        });


        mSendFeedBackButton.setOnClickListener(v -> {
            String content = mFeedBackEditText.getText().toString();

            if (kindValidate()) {
                if (!TextUtils.isEmpty(content)) {
                    try {
                        addComplaint(text,content);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "请输入内容！",
                            Toast.LENGTH_SHORT).show();
                }
            }


        }

        );
    }

    private boolean kindValidate() {
        int selected=radiogroup.getCheckedRadioButtonId();
        if(selected==R.id.radiobutton1)
        {
            text=radio1.getText().toString();
            return true;
        }
        else if(selected==R.id.radiobutton2) {
            text=radio2.getText().toString();
            return true;
        }
        else if (selected==R.id.radiobutton3){
            text=radio3.getText().toString();
            return true;
        }
        else if (selected==R.id.radiobutton4){
            text=radio4.getText().toString();
            return true;
        }
        else {

            return false;
        }
    }

    private void addComplaint(String text, String content) {
        //Map封装
        HashMap<String, String> map = new HashMap<>();
        map.put("complaint_reason", text);
        map.put("complaint_content", content);
        String url = HttpUtil.BASE_URL + "complaint";
        new HttpUtil().postRequest(url, map);
    }

}