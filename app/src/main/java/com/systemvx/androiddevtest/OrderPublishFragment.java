package com.systemvx.androiddevtest;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class OrderPublishFragment extends Fragment {
    //定义文本框
    private EditText orderTittle;
    private EditText orderContext;
    private EditText orderPrice;
    private EditText orderDDL;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.order_publish, container, false);
        //获取界面文本框
        orderTittle = rootView.findViewById(R.id.orderTittle);
        orderContext = rootView.findViewById(R.id.orderContext);
        orderPrice = rootView.findViewById(R.id.orderPrice);
        orderDDL = rootView.findViewById(R.id.orderDDL);
        String url = HttpUtil.BASE_URL + "kinds";
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(new HttpUtil().getRequest(url));
        } catch (Exception el) {
            el.printStackTrace();
        }
        Button orderPublish = rootView.findViewById(R.id.orderPublish);
        Button orderCancel = rootView.findViewById(R.id.orderCancel);
        orderCancel.setOnClickListener(new HomeListener(getActivity()));
        orderPublish.setOnClickListener(view -> {
            if (validate()) {
                String Tittle = orderTittle.getText().toString();
                String Context = orderContext.getText().toString();
                String Time = orderDDL.getText().toString();
                String Price = orderPrice.getText().toString();
                try {
                    String result = addOrder(Tittle, Context, Time, Price);
                    DialogUtil.showDialog(getActivity(), result, true);
                } catch (Exception e) {
                    DialogUtil.showDialog(getActivity(), "Error", false);
                    e.printStackTrace();
                }
            }
        });
        return rootView;
    }

    private boolean validate() {
        String Tittle = orderTittle.getText().toString().trim();
        if (Tittle.equals("")) {
            DialogUtil.showDialog(getActivity(), "请填写标题", false);
            return false;
        }
        String Context = orderContext.getText().toString().trim();
        if (Context.equals("")) {
            DialogUtil.showDialog(getActivity(), "请填具体描述", false);
            return false;
        }
        String Price = orderPrice.getText().toString().trim();
        if (Price.equals("")) {
            DialogUtil.showDialog(getActivity(), "请填写价格", false);
            return false;
        }
        return true;
    }

    private String addOrder(String Tittle, String Context, String Time, String Price) throws Exception {
        //Map封装
        Map<String, String> map = new HashMap<>();
        map.put("orderTittle", Tittle);
        map.put("orderContext", Context);
        map.put("orderTime", Time);
        map.put("orderPrice", Price);
        String url = HttpUtil.BASE_URL + "order";
        return new HttpUtil().postRequest(url, map);
    }

    static class DialogUtil {
        static void showDialog(Context c,String s,Boolean b) {
            //TODO (do nothing yet)
        }
    }
}
