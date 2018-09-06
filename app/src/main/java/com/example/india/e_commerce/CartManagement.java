package com.example.india.e_commerce;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class CartManagement {
    private SQLiteDatabase database;

    public CartManagement(SQLiteDatabase database) {
        this.database = database;
        database.execSQL("CREATE TABLE IF NOT EXISTS cart (_id int, img int, name VARCHAR, price VARCHAR)");
    }

    public void addToCart(int img, String name, String price) {
        database.execSQL("INSERT INTO cart (img, name, price) VALUES ('" + img + "', '" + name + "', '" + price + "')");
    }

    public void removeFromCart(int img) {
        Cursor cursor = database.rawQuery("SELECT * FROM cart WHERE img = '" + img + "'", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            database.execSQL("DELETE FROM cart WHERE img = '" + img + "'");
        }
        cursor.close();
    }

    public List<CartData> showCart() {
        List<CartData> data = new ArrayList<>();
        Cursor cursor = database.rawQuery("SELECT * FROM cart", null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            int image = cursor.getInt(1);
            String n = cursor.getString(2);
            String p = cursor.getString(3);
            CartData item = new CartData(n, p, image);
            data.add(item);
        }
        cursor.close();
        return data;
    }
}
