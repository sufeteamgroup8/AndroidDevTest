package com.systemvx.androiddevtest.ui.complaint;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.EditText;
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
import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.data.OrderTypeBean;

import java.util.ArrayList;
import java.util.List;

public class OrderComplaintActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edEvaluate;
    private final Handler mHandler = new Handler();
    private RecyclerView rvType;
    private List<OrderTypeBean> list;
    private List<String> photoList;
    private final String[] typeList = {"配送问题", "退单问题", "红包问题", "会员问题", "订单超时问题", "投诉餐厅/骑手", "订单超时没有赔付", "人工客服"};
    private OrderComplaintAdapter complaintAdapter;
    private OrderAddImageAdapter addImageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiti_order_complaint);
        init();
    }

    private void init() {
        list = new ArrayList<>();
        photoList = new ArrayList<>();
        findViewById(R.id.tv_back).setOnClickListener(this);
        findViewById(R.id.but_submit).setOnClickListener(this);
        RecyclerView rvImage = findViewById(R.id.rv_image);
        edEvaluate = findViewById(R.id.ed_complaint);
        rvType = findViewById(R.id.rv_type);

        for (String l : typeList) {
            list.add(new OrderTypeBean(l, false));
        }
        complaintAdapter = new OrderComplaintAdapter(this, list);
        addImageAdapter = new OrderAddImageAdapter(this, photoList);
        rvType.setLayoutManager(new LinearLayoutManager(this));
        rvImage.setLayoutManager(new GridLayoutManager(this, 3));
        rvType.setAdapter(complaintAdapter);
        rvImage.setAdapter(addImageAdapter);
        complaintAdapter.setOnItemClickLisener((view, position) -> {
            list.get(position).setCheck(!list.get(position).isCheck());
            complaintAdapter.notifyDataSetChanged();
        });
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
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureConfig.CHOOSE_REQUEST) {
                List<LocalMedia> media = PictureSelector.obtainMultipleResult(data);
                String path = media.get(0).getPath();
                photoList.add(path);
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
                StringBuilder sb = new StringBuilder();
                String evaluate = edEvaluate.getText().toString().trim();
                for (OrderTypeBean bean : list) {
                    if (bean.isCheck()) {
                        sb.append(bean.getText() + ",");
                    }
                }
                if (evaluate.isEmpty()) {
                    Toast.makeText(this, "请输入文字评价", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (evaluate.length() < 20) {
                    Toast.makeText(this, "评价文字不得小于20字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mSubmitEvaluate(evaluate, sb.toString());
                break;
        }
    }

    private void mSubmitEvaluate(String evaluate, String type) {
        mHandler.postDelayed(() -> {
            Toast.makeText(this, "提交评价成功", Toast.LENGTH_LONG).show();
            finish();
        }, 2000);
    }
}
