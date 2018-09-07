package com.example.india.e_commerce;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ShowProducts {
    private SQLiteDatabase database;
    private List<ProductData> data;

    ShowProducts(SQLiteDatabase database) {
        this.database = database;
        data = new ArrayList<>();
    }

    List<ProductData> showData(int i) {
        if (i == 0) {
            try {
                Cursor cursor = database.rawQuery("SELECT * FROM products ORDER BY _id DESC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(1);
                        int price = cursor.getInt(2);
                        String img = cursor.getString(3);
                        int cat = cursor.getInt(4);

                        ProductData item = new ProductData(name, img, price, cat);
                        data.add(item);
                    }
                }
                cursor.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            i = i - 1;
            try {
                Cursor cursor = database.rawQuery("SELECT * FROM products WHERE cat = '" + i + "' ORDER BY _id DESC", null);
                if (cursor.getCount() > 0) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(1);
                        int price = cursor.getInt(2);
                        String img = cursor.getString(3);
                        int cat = cursor.getInt(4);

                        ProductData item = new ProductData(name, img, price, cat);
                        data.add(item);
                    }
                }
                cursor.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return data;
    }
}
