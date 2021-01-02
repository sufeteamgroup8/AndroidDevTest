package com.systemvx.androiddevtest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.systemvx.androiddevtest.adapter.OrderAddImageAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderCommentActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edEvaluate;
    private RatingBar rbStar;
    private Handler mHandler = new Handler();
    private RecyclerView rvImage;
    private OrderAddImageAdapter addImageAdapter;
    private List<String> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_order_comment);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.but_submit).setOnClickListener(this);
        edEvaluate = findViewById(R.id.ed_evaluate);
        rbStar = findViewById(R.id.rb_Star);

        RecyclerView rvImage = findViewById(R.id.rv_image);
        addImageAdapter = new OrderAddImageAdapter(this, list);
        rvImage.setLayoutManager(new GridLayoutManager(this,3));
        rvImage.setAdapter(addImageAdapter);
        addImageAdapter.setOnItemClickeLisener((view, position) -> {
            PictureSelector.create(this)
                    .openCamera(PictureMimeType.ofImage())
                    .imageFormat(PictureMimeType.PNG)// 拍照保存图片格式后缀,默认jpeg
                    .isCompress(true)// 是否压缩
                    .minimumCompressSize(100)// 小于100kb的图片不压缩
                    .forResult(PictureConfig.CHOOSE_REQUEST);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == PictureConfig.CHOOSE_REQUEST){
                List<LocalMedia> media = PictureSelector.obtainMultipleResult(data);
                String path = media.get(0).getPath();
                list.add(path);
                addImageAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.but_submit:
                String evaluate = edEvaluate.getText().toString().trim();
                float rating = rbStar.getRating();
                if (evaluate.isEmpty()) {
                    Toast.makeText(this, "请输入文字评价", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (evaluate.length() < 20) {
                    Toast.makeText(this, "评价文字不得小于20字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mSubmitEvaluate(evaluate, rating);
                break;
        }
    }

    private void mSubmitEvaluate(String evaluate, float rating) {
        mHandler.postDelayed(() -> {
            Toast.makeText(this, "提交评价成功", Toast.LENGTH_LONG).show();
            finish();
        }, 2000);
    }
}
