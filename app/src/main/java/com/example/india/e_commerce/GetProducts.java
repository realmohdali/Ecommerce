package com.example.india.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetProducts {
    private SQLiteDatabase database;
    private Context context;
    private static final String URL = "http://realmohdali.000webhostapp.com/ecom/showProducts.php";
    public static final String DATA_LOADED = "com.example.india.e_commerce.DATA_LOADED";
    private List<ProductData> data;

    GetProducts(SQLiteDatabase database, Context context) {
        this.database = database;
        this.context = context;
        data = new ArrayList<>();

        try {
            database.execSQL("DROP TABLE IF EXISTS products");
            database.execSQL("CREATE TABLE products (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, price INTEGER, image VARCHAR, cat INTEGER)");
        } catch (SQLException e) {
            Toast.makeText(context, "Error: " + e, Toast.LENGTH_SHORT).show();
        }
    }

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);

                                String image = "http://realmohdali.000webhostapp.com/ecom/";
                                image += jsonObject.getString("img");
                                String name = jsonObject.getString("product");
                                int price = jsonObject.getInt("price");
                                int cat = jsonObject.getInt("cat");

                                ProductData item = new ProductData(name, image, price, cat);
                                data.add(item);
                            }
                            addToDatabase();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void addToDatabase() {
        for (int i = 0; i < data.size(); i++) {
            String name = data.get(i).getName();
            String img = data.get(i).getImage();
            int price = data.get(i).getPrice();
            int cat = data.get(i).getCat();

            database.execSQL("INSERT INTO products (name, price, image, cat) VALUES ('" + name + "','" + price + "','" + img + "','" + cat + "')");
        }
        Intent intent = new Intent(DATA_LOADED);
        context.sendBroadcast(intent);
    }
}
