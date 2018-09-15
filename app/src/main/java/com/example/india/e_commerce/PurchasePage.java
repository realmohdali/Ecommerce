package com.example.india.e_commerce;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PurchasePage extends AppCompatActivity {

    private String product, price, image;

    private EditText houseNo, landmark, city, areaCode, phone;

    private RelativeLayout orderPlaced, placingOrder;

    private Button proceed;

    private String userid;

    private Context context = this;

    private static final String URL = "http://realmohdali.000webhostapp.com/ecom/place_order.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        product = intent.getExtras().getString("product");
        price = intent.getExtras().getString("price");
        image = intent.getExtras().getString("image");

        getSupportActionBar().setTitle(product);

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        userid = sp.getString("userid", "");

        ImageView product_image = findViewById(R.id.image);
        TextView product_name = findViewById(R.id.product_name);
        TextView product_price = findViewById(R.id.price);

        Glide.with(this)
                .asBitmap()
                .load(image)
                .into(product_image);

        product_name.setText(product);
        product_price.setText(price);

        houseNo = findViewById(R.id.houseNo);
        landmark = findViewById(R.id.landmark);
        city = findViewById(R.id.city);
        areaCode = findViewById(R.id.areaCode);
        phone = findViewById(R.id.phone);

        Button submit = findViewById(R.id.place_order);

        orderPlaced = findViewById(R.id.orderPlaced);
        placingOrder = findViewById(R.id.placingOrder);
        proceed = findViewById(R.id.cont);

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase database = openOrCreateDatabase("CartItems", MODE_PRIVATE, null);
                CartManagement cartManagement = new CartManagement(database, context);
                cartManagement.removeFromCart(product);
                Intent intent1 = new Intent(context, MyOrders.class);
                context.startActivity(intent1);
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (houseNo.getText().toString().trim().length() > 0) {
                    if (landmark.getText().toString().trim().length() > 0) {
                        if (city.getText().toString().trim().length() > 0) {
                            if (areaCode.getText().toString().trim().length() > 0) {
                                if (phone.getText().toString().trim().length() > 0) {
                                    placeOrder();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Contact Number is empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Area Code is empty", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "City is empty", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Landmark is empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "House No and Street Name are empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void placeOrder() {
        placingOrder.setVisibility(View.VISIBLE);
        final String address, landmrk, cty, cont, areacode, qty;
        address = houseNo.getText().toString().trim();
        landmrk = landmark.getText().toString().trim();
        cty = city.getText().toString().trim();
        cont = phone.getText().toString().trim();
        areacode = areaCode.getText().toString().trim();
        qty = "1";

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            String result = jsonObject.getString("response");

                            if (result.equalsIgnoreCase("successfull")) {
                                placingOrder.setVisibility(View.GONE);
                                orderPlaced.setVisibility(View.VISIBLE);

                            } else {
                                Toast.makeText(getApplicationContext(), "Error Placing Order", Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        } finally {
                            placingOrder.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("address", address);
                params.put("product", product);
                params.put("price", price);
                params.put("landmark", landmrk);
                params.put("city", cty);
                params.put("areacode", areacode);
                params.put("contact", cont);
                params.put("qty", qty);
                params.put("image", image);
                params.put("userid", userid);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        stringRequest.setShouldCache(false);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
