package com.example.shosho.inventory_app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ProductAdapter.RecyclerViewClickListener {
    private MyDatabase data_base = null;
    private RecyclerView recyclerView = null;
    private ProductAdapter productAdapter = null;
    private ArrayList<MyProduct> arrayList = null;
    private TextView textView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.emptyView);

        data_base = new MyDatabase(MainActivity.this);
        arrayList = data_base.readAllProducts();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        if (arrayList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            textView.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            productAdapter = new ProductAdapter(MainActivity.this, arrayList, MainActivity.this);
            recyclerView.setAdapter(productAdapter);
            recyclerView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mymenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.insertBtn:
                Intent intent = new Intent(MainActivity.this, AddNewActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void itemButtonClick(int position) {
        MyProduct product = arrayList.get(position);
        if (product.getQuantity() > 0) {
            product.setQuantity(product.getQuantity() - 1);
            data_base.modify_product(product.getMy_id(), product.getQuantity());

            arrayList.clear();
            arrayList = data_base.readAllProducts();
            if (arrayList.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                textView.setVisibility(View.GONE);

                productAdapter = null;
                productAdapter = new ProductAdapter(MainActivity.this, arrayList, MainActivity.this);
                recyclerView.setAdapter(productAdapter);
            }
        }
    }


    @Override
    public void recyclerViewItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, EditionActivity.class);
        MyProduct product = arrayList.get(position);
        intent.putExtra("selectedProduct", product);
        startActivity(intent);

    }

}