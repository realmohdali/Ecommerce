package com.example.india.e_commerce;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.List;

import static com.example.india.e_commerce.GetProducts.DATA_LOADED;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private boolean drawerOpen = false;
    private SQLiteDatabase database, products;
    private RecyclerView recyclerView;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(dataLoaded);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu);

        registerDataLoaded();

        database = openOrCreateDatabase("CartItems", MODE_PRIVATE, null);

        products = openOrCreateDatabase("Products", MODE_PRIVATE, null);

        GetProducts getProducts = new GetProducts(products, getApplicationContext());
        getProducts.getData();
    }

    private BroadcastReceiver dataLoaded = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            loadActivity();
        }
    };

    private void registerDataLoaded() {
        IntentFilter filter = new IntentFilter(DATA_LOADED);
        registerReceiver(dataLoaded, filter);
    }

    private void loadActivity() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);

        loadItems(0);
        setUpDrawerLayout();
    }

    private void setUpDrawerLayout() {
        final NavigationView navigationView = findViewById(R.id.nav);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.all:
                        navigationView.getMenu().getItem(0).setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        loadItems(0);
                        return true;
                    case R.id.mobile:
                        navigationView.getMenu().getItem(1).setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        loadItems(1);
                        return true;
                    case R.id.computer:
                        navigationView.getMenu().getItem(2).setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        loadItems(2);
                        return true;
                    case R.id.other:
                        navigationView.getMenu().getItem(3).setChecked(true);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        loadItems(3);
                        return true;
                }
                return false;
            }
        });
        drawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                drawerOpen = true;
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                drawerOpen = false;
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void loadItems(int cat) {
        ShowProducts showProducts;
        List<ProductData> data;
        RecyclerView.Adapter adapter;
        RelativeLayout loading = findViewById(R.id.loading);
        switch (cat) {
            case 0:
                loading.setVisibility(View.VISIBLE);
                showProducts = new ShowProducts(products);
                data = showProducts.showData(cat);
                adapter = new myAdapter(data, database, getApplicationContext());
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);
                break;
            case 1:
                loading.setVisibility(View.VISIBLE);
                showProducts = new ShowProducts(products);
                data = showProducts.showData(cat);
                adapter = new myAdapter(data, database, getApplicationContext());
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);
                break;
            case 2:
                loading.setVisibility(View.VISIBLE);
                showProducts = new ShowProducts(products);
                data = showProducts.showData(cat);
                adapter = new myAdapter(data, database, getApplicationContext());
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);
                break;
            case 3:
                loading.setVisibility(View.VISIBLE);
                showProducts = new ShowProducts(products);
                data = showProducts.showData(cat);
                adapter = new myAdapter(data, database, getApplicationContext());
                recyclerView.setAdapter(adapter);
                loading.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                drawerOpen = true;
                break;
            case R.id.cart:
                Intent intent = new Intent(this, Cart.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerOpen) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
