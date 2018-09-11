package com.example.india.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

class myAdapter extends RecyclerView.Adapter<myAdapter.ViewHolder> {
    private List<ProductData> data;
    private SQLiteDatabase database;
    private Context context;

    myAdapter(List<ProductData> data, SQLiteDatabase database, Context context) {
        this.data = data;
        this.context = context;
        this.database = database;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText(data.get(position).getName());
        Glide.with(context)
                .asBitmap()
                .load(data.get(position).getImage())
                .into(holder.imageView);
        String price = "Rs. " + data.get(position).getPrice() + "/-";
        holder.amt.setText(price);
        holder.purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
                boolean login = sp.getBoolean("login", false);

                if (login) {

                    int i = holder.getAdapterPosition();
                    String product = data.get(i).getName();
                    String price = "Rs. " + data.get(i).getPrice() + "/-";
                    String image = data.get(i).getImage();

                    Intent intent = new Intent(context, PurchasePage.class);
                    intent.putExtra("product", product);
                    intent.putExtra("image", image);
                    intent.putExtra("price", price);
                    context.startActivity(intent);
                } else {
                    int i = holder.getAdapterPosition();
                    String product = data.get(i).getName();
                    String price = "Rs. " + data.get(i).getPrice() + "/-";
                    String image = data.get(i).getImage();

                    Intent intent = new Intent(context, Login.class);
                    intent.putExtra("product", product);
                    intent.putExtra("image", image);
                    intent.putExtra("price", price);
                    context.startActivity(intent);
                }
            }
        });
        holder.cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String image = data.get(holder.getAdapterPosition()).getImage();
                String n = data.get(holder.getAdapterPosition()).getName();
                String price = String.valueOf(data.get(holder.getAdapterPosition()).getPrice());
                CartManagement cartManagement = new CartManagement(database, context);
                cartManagement.addToCart(image, n, price);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, amt;
        ImageView imageView;
        LinearLayout purchase, cart;

        ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            amt = itemView.findViewById(R.id.amt);
            imageView = itemView.findViewById(R.id.imageView);
            purchase = itemView.findViewById(R.id.purchase);
            cart = itemView.findViewById(R.id.cart);
        }
    }
}
