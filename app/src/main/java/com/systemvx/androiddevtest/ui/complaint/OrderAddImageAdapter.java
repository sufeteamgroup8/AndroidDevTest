package com.systemvx.androiddevtest.ui.complaint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.systemvx.androiddevtest.R;

import java.util.List;

public class OrderAddImageAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final List<String> list;
    private OnitemClicke onitemClicke;

    public interface OnitemClicke {
        void OnItemClick(View view, int position);
    }

    public void setOnItemClickeLisener(OnitemClicke clickeLisener) {
        onitemClicke = clickeLisener;
    }

    public OrderAddImageAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_add_image, null, false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MyViewHolder viewHolder = (MyViewHolder) holder;
        viewHolder.itemView.setOnClickListener(view ->
                onitemClicke.OnItemClick(view, position)
        );
        Glide.with(context).load(list.get(position)).into(viewHolder.image);

    }

    @Override
    public int getItemCount() {
        if (list.size() < 3) {
            return list.size() + 1;
        } else {
            return list.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView image;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
        }
    }
}
