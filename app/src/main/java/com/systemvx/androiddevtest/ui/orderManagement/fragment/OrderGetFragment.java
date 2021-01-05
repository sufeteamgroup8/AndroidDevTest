package com.systemvx.androiddevtest.ui.orderManagement.fragment;



import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.ui.orderManagement.adapter.FragmentAdapter;
import com.systemvx.androiddevtest.ui.orderbrowsing.OrderBrowsingAdapter;


public class OrderGetFragment extends Fragment{
    private  RecyclerView rv;

    private  FragmentAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View orderView = inflater.inflate(R.layout.frag_order_get, container,false);
        return orderView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }
}

