package com.example.india.e_commerce;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private List<CartData> data;
    private SQLiteDatabase database;
    private Context context;

    CartAdapter(List<CartData> data, SQLiteDatabase database, Context context) {
        this.data = data;
        this.database = database;
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
        Glide.with(context)
                .asBitmap()
                .load(data.get(position).getImg())
                .into(holder.imageView);
        holder.title.setText(data.get(position).getName());

        holder.foreground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = holder.getAdapterPosition();
                String product = data.get(i).getName();
                String price = "Rs. " + data.get(i).getPrice() + "/-";
                String image = data.get(i).getImg();

                Intent intent = new Intent(context, PurchasePage.class);
                intent.putExtra("product", product);
                intent.putExtra("image", image);
                intent.putExtra("price", price);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title;
        LinearLayout foreground;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            foreground = itemView.findViewById(R.id.foreground);
        }
    }

    public void removeItem(final int i) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Remove from cart?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CartManagement management = new CartManagement(database, context);
                management.removeFromCart(data.get(i).getName());
                data.remove(i);
                notifyItemRemoved(i);
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                notifyItemChanged(i);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
