package com.systemvx.androiddevtest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.appcompat.app.AppCompatActivity;

import com.systemvx.androiddevtest.ui.complaint.complaintActivity;
import com.systemvx.androiddevtest.ui.orderdetail.OrderDetailActivity;
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
        editText=findViewById(R.id.comment_ctx);
        ratingBar=findViewById(R.id.ratingBar);
        Button mCancelButton=findViewById(R.id.commentCancel);
        mCancelButton.setOnClickListener(v -> {
            Intent intent =new Intent(CommentActivity.this, OrderDetailActivity.class);
            startActivity(intent);
        });
        button=findViewById(R.id.commit_btn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String comment="评价内容："+editText.getText().toString();
                Float num= ratingBar.getRating();
                String comment_num=num.toString();
                try {
                    addComment(comment,comment_num);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


    }
    private void addComment(String comment, String comment_num)  {
        //Map封装
        HashMap<String, String> map = new HashMap<>();
        map.put("comment", comment);
        map.put("comment_num", comment_num);
        String url = HttpUtil.BASE_URL + "comment";
        new HttpUtil().postRequest(url, map);
    }

}
