package com.systemvx.androiddevtest.ui.complaint;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.ui.complaint.view.LogisticsData;
import com.systemvx.androiddevtest.ui.complaint.view.NodeProgressAdapter;
import com.systemvx.androiddevtest.ui.complaint.view.NodeProgressView;

import java.util.ArrayList;
import java.util.List;


public class complaintProgress extends AppCompatActivity {
    List<LogisticsData> logisticsDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint_progress);

        logisticsDatas = new ArrayList<>();
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】已签收,签收人是【王漾】,签收网点是【忻州原平】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【相城中转仓】装车,正发往【无锡分拨中心】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊市中转部】,上一站是【】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【潍坊市中转部】装车,正发往【潍坊奎文代派】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件到达【潍坊】,上一站是【潍坊市中转部】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("快件在【武汉分拨中心】装车,正发往【晋江分拨中心】"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));
        logisticsDatas.add(new LogisticsData().setTime("2016-6-28 15:13:02").setContext("【北京鸿运良乡站】的【010058.269】正在派件"));

        NodeProgressView nodeProgressView = (NodeProgressView) findViewById(R.id.npv_NodeProgressView);
        nodeProgressView.setNodeProgressAdapter(new NodeProgressAdapter() {

            @Override
            public int getCount() {
                return logisticsDatas.size();
            }

            @Override
            public List<LogisticsData> getData() {
                return logisticsDatas;
            }
        });
    }
}