package com.systemvx.androiddevtest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.systemvx.androiddevtest.R;
import com.systemvx.androiddevtest.bean.OrderTypeBean;

import java.util.List;

public class OrderComplaintAdapter extends RecyclerView.Adapter {
    private List<OrderTypeBean> list;
    private Context context;
    public OnItemClick onItemClick;

    public interface OnItemClick{
        void OnItemClick(View view,int position);
    }
    public OrderComplaintAdapter(Context context,List<OrderTypeBean> list) {
        this.list = list;
        this.context = context;
    }
    public void setOnItemClickLisener(OnItemClick clickLisener){
        onItemClick = clickLisener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compaint_type, null, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.itemView.setOnClickListener(view -> onItemClick.OnItemClick(view,position));

        if (list.get(position).isCheck()){
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.purple_200));
        }else {
            viewHolder.itemView.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    class MyViewHolder extends RecyclerView.ViewHolder{

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
