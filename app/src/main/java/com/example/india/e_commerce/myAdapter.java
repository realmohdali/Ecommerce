package com.example.india.e_commerce;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    String name[];
    int img[];
    int amt[];

    public myAdapter(String[] name, int[] img, int[] amt) {
        int len = name.length;
        this.name = new String[len];
        this.img = new int[len];
        this.amt = new int[len];

        for (int i = 0; i < len; i++) {
            this.name[i] = name[i];
            this.amt[i] = amt[i];
            this.img[i] = img[i];
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(name[position]);
        holder.imageView.setImageResource(img[position]);
        holder.amt.setText("Rs. " + amt[position] + "/-");
    }

    @Override
    public int getItemCount() {
        return name.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amt;
        ImageView imageView;
        Button purchase, cart;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            amt = itemView.findViewById(R.id.amt);
            imageView = itemView.findViewById(R.id.imageView);
            purchase = itemView.findViewById(R.id.purchase);
            cart = itemView.findViewById(R.id.cart);
        }
    }
}