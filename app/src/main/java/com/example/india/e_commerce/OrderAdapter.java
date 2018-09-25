package com.example.india.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private List<MyOrdersData> list;
    private Context context;

    OrderAdapter(List<MyOrdersData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.textView.setText(list.get(position).getProduct());
        Glide.with(context)
                .asBitmap()
                .load(list.get(position).getImage())
                .into(holder.imageView);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TrackOrder.class);
                intent.putExtra("image", list.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name", list.get(holder.getAdapterPosition()).getProduct());
                intent.putExtra("status", list.get(holder.getAdapterPosition()).getStatus());
                intent.putExtra("id", list.get(holder.getAdapterPosition()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout item;
        ImageView imageView;
        TextView textView;

        ViewHolder(View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.item);
            imageView = itemView.findViewById(R.id.image);
            textView = itemView.findViewById(R.id.title);
        }
    }
}
