package com.systemvx.androiddevtest;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.systemvx.androiddevtest.utils.HttpUtil;

import java.util.HashMap;


public class CommentActivity extends AppCompatActivity {
    EditText editText;
    RatingBar ratingBar;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment);
        editText=findViewById(R.id.editTextTextPersonName);
        ratingBar=findViewById(R.id.ratingBar);
        button=findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment="评价内容："+editText.getText().toString();
                Float num= ratingBar.getRating();
                String comment_num=num.toString();
                try {
                    String result=addComment(comment,comment_num);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
    private String addComment(String comment, String comment_num) throws Exception {
        //Map封装
        HashMap<String, String> map = new HashMap<>();
        map.put("comment", comment);
        map.put("comment_num", comment_num);
        String url = HttpUtil.BASE_URL + "comment";
        return new HttpUtil().postRequest(url, map);
    }

}
