package com.example.india.e_commerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private String name[];
    private int img[];

    public CartAdapter(List<CartData> data) {
        int len = name.length;
        this.name = new String[len];
        this.img = new int[len];
        for (int i = 0; i < len; i++) {
            this.name[i] = name[i];
            this.img[i] = img[i];
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageResource(img[position]);
        holder.title.setText(name[position]);
        holder.qty.setText("5");
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView, delete;
        TextView title, qty;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            qty = itemView.findViewById(R.id.qty);
            delete = itemView.findViewById(R.id.delete);
        }
    }
}
