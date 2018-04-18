package com.example.shosho.inventory_app;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductsViewHolder> {
    private Context context = null;
    private ArrayList<MyProduct> productArrayList = null;
    private static RecyclerViewClickListener itemClickListener = null;

    public ProductAdapter(Context context, ArrayList<MyProduct> productArrayList, RecyclerViewClickListener listener) {
        this.context = context;
        this.productArrayList = productArrayList;
        this.itemClickListener = listener;
    }

    public interface RecyclerViewClickListener {
        public void recyclerViewItemClick(int position);
        public void itemButtonClick(int position);
    }

    @Override
    public ProductsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product, parent, false);
        return new ProductsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductsViewHolder holder, int position) {
        MyProduct product = productArrayList.get(position);
        holder.txt_name.setText(product.getName());
        holder.txt_quantity.setText(String.valueOf(product.getQuantity()));
        holder.txt_price.setText(String.valueOf(product.getPrice()));
    }

    @Override
    public int getItemCount() {
        return productArrayList.size();
    }

    public class ProductsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView txt_name;
        TextView txt_quantity;
        TextView txt_price;
        Button sell;

        public ProductsViewHolder(View itemView) {
            super(itemView);
            txt_name = (TextView) itemView.findViewById(R.id.text_name);
            txt_quantity = (TextView) itemView.findViewById(R.id.text_quantity);
            txt_price = (TextView) itemView.findViewById(R.id.text_price);
            sell = (Button) itemView.findViewById(R.id.button_sell);
            itemView.setOnClickListener(this);
            sell.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == sell.getId()) {
                itemClickListener.itemButtonClick(getLayoutPosition());
            } else {
                itemClickListener.recyclerViewItemClick(getLayoutPosition());
            }
        }
    }

}
