package com.example.india.e_commerce;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyOrders extends AppCompatActivity {

    private RecyclerView orderView;
    private static final String URL = "http://realmohdali.000webhostapp.com/ecom/myOrder.php";
    private List<MyOrdersData> list;
    private OrderAdapter orderAdapter;
    private Context context = this;
    private RelativeLayout loading, noOrders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("My Orders");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        orderView = findViewById(R.id.orderView);
        loading = findViewById(R.id.loading);
        noOrders = findViewById(R.id.noOrder);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(orderView.getContext(), layoutManager.getOrientation());

        orderView.addItemDecoration(dividerItemDecoration);
        orderView.setLayoutManager(layoutManager);

        list = new ArrayList<>();

        getMyOrders();
    }

    private void getMyOrders() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        final String userid = sp.getString("userid", "");

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            if (jsonArray.length() > 0) {
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                                    String product = jsonObject.getString("product");
                                    String price = jsonObject.getString("price");
                                    String status = jsonObject.getString("status");
                                    String address = jsonObject.getString("address");
                                    String landmark = jsonObject.getString("landmark");
                                    String city = jsonObject.getString("city");
                                    String areacode = jsonObject.getString("areacode");
                                    String contact = jsonObject.getString("contact");
                                    String image = jsonObject.getString("image");
                                    String id = jsonObject.getString("id");

                                    MyOrdersData item = new MyOrdersData(product, price, status, address, landmark, city, areacode, contact, image, id);
                                    list.add(item);
                                }
                                loading.setVisibility(View.GONE);
                                noOrders.setVisibility(View.GONE);
                                orderAdapter = new OrderAdapter(list, context);
                                orderView.setAdapter(orderAdapter);
                            } else {
                                loading.setVisibility(View.GONE);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                overridePendingTransition(0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}