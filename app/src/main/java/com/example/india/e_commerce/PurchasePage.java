package com.example.india.e_commerce;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class PurchasePage extends AppCompatActivity {

    private String product, price, image;
    private ImageView product_image;
    private TextView product_name, product_price;

    private EditText houseNo, landmark, city, areaCode;

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

        product_image = findViewById(R.id.image);
        product_name = findViewById(R.id.product_name);
        product_price = findViewById(R.id.price);

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

        Button submit = findViewById(R.id.place_order);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (houseNo.getText().toString().trim().length() > 0) {
                    if (landmark.getText().toString().trim().length() > 0) {
                        if (city.getText().toString().trim().length() > 0) {
                            if (areaCode.getText().toString().trim().length() > 0) {
                                Toast.makeText(getApplicationContext(), "You have clicked on PLACE ORDER", Toast.LENGTH_SHORT).show();
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
