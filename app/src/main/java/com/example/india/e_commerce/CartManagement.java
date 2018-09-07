package com.example.india.e_commerce;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartManagement {
    private SQLiteDatabase database;
    private Context context;

    CartManagement(SQLiteDatabase database, Context context) {
        this.database = database;
        this.context = context;
        try {
            database.execSQL("CREATE TABLE IF NOT EXISTS cart (_id INTEGER PRIMARY KEY AUTOINCREMENT, img VARCHAR, name VARCHAR, price VARCHAR)");
        } catch (SQLException e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void addToCart(String img, String name, String price) {
        try {
            if (alreadyExists(name)) {
                Toast.makeText(context, "Product already exist in your cart", Toast.LENGTH_SHORT).show();
            } else {
                database.execSQL("INSERT INTO cart (img, name, price) VALUES ('" + img + "','" + name + "','" + price + "')");
                Toast.makeText(context, "Added To Cart", Toast.LENGTH_SHORT).show();
            }
        } catch (SQLException e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void removeFromCart(String name) {
        Cursor cursor = database.rawQuery("SELECT * FROM cart WHERE name = '" + name + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            database.execSQL("DELETE FROM cart WHERE name = '" + name + "'");
        }
        cursor.close();
    }

    public List<CartData> showCart() {
        List<CartData> data = new ArrayList<>();
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM cart ORDER BY _id DESC", null);
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    String image = cursor.getString(1);
                    String n = cursor.getString(2);
                    String p = cursor.getString(3);
                    CartData item = new CartData(n, p, image);
                    data.add(item);
                }
            }
            cursor.close();
        } catch (SQLException e) {
            Toast.makeText(context, "ERROR: " + e, Toast.LENGTH_SHORT).show();
        }

        return data;
    }

    private boolean alreadyExists(String name) {
        try {
            Cursor cursor = database.rawQuery("SELECT * FROM cart WHERE name = '" + name + "'", null);
            if (cursor.getCount() > 0) {
                cursor.close();
                return true;
            } else {
                cursor.close();
                return false;
            }
        } catch (SQLException e) {
            return false;
        }
    }
}
