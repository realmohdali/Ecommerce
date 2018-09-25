package com.example.india.e_commerce;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

public class TrackOrder extends AppCompatActivity {

    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_order);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle("Track Order");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String name = intent.getExtras().getString("name");
        String image = intent.getExtras().getString("image");
        String Status = intent.getExtras().getString("status");
        id = intent.getExtras().getString("id");

        ImageView product_image = findViewById(R.id.product_image);
        TextView product_name = findViewById(R.id.product_name);
        TextView estimateTime = findViewById(R.id.estimateTime);
        ProgressBar progress = findViewById(R.id.progress);

        Glide.with(this)
                .asBitmap()
                .load(image)
                .into(product_image);
        product_name.setText(name);
        String est;
        int prog = Integer.parseInt(Status);
        switch (prog) {
            case 0:
                progress.setProgress(0);
                est = "Your order is under process";
                estimateTime.setText(est);
                break;
            case 1:
                progress.setProgress(25);
                est = "Your order is processed and will be dispatched shortly";
                estimateTime.setText(est);
                break;
            case 2:
                progress.setProgress(50);
                est = "Your order is dispatched";
                estimateTime.setText(est);
                break;
            case 3:
                progress.setProgress(75);
                est = "Your order is reached your locality and will be delivered to you within 2 days";
                estimateTime.setText(est);
                break;
            case 4:
                progress.setProgress(100);
                est = "Order Delivered";
                estimateTime.setText(est);
                break;
        }

        Button cancel = findViewById(R.id.cancel);
        if (prog == 4) {
            cancel.setVisibility(View.GONE);
        } else {
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cancelOrder();
                }
            });
        }
    }

    private void cancelOrder() {
        String URL = "http://realmohdali.000webhostapp.com/ecom/cancelOrder.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String res = jsonObject.getString("response");
                            if (res.equalsIgnoreCase("successfull")) {
                                Toast.makeText(getApplicationContext(), "Order Removed", Toast.LENGTH_SHORT).show();
                                finish();
                                overridePendingTransition(0, 0);
                            } else {
                                Toast.makeText(getApplicationContext(), "Error Removing Order", Toast.LENGTH_SHORT).show();
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
                params.put("id", id);
                return params;
            }
        };

        stringRequest.setShouldCache(false);
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
