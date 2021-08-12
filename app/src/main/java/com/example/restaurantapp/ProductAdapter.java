package com.example.restaurantapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ProductAdapter extends ArrayAdapter<Product> {


    public ProductAdapter(Context context, ArrayList<Product> arrayList) {
        super(context, 0, arrayList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currProductView = convertView;

        // if the view is null then create new view
        if(currProductView == null){
            currProductView = LayoutInflater.from(getContext()).inflate(R.layout.single_product, parent, false);
        }

        Product currProduct = getItem(position);

        TextView productId = currProductView.findViewById(R.id.product_id);
        ImageView productImageView = currProductView.findViewById(R.id.product_img);
        TextView productNameTextView = currProductView.findViewById(R.id.product_name);
        TextView productPriceTextView = currProductView.findViewById(R.id.product_price);


        productId.setText(currProduct.getProductId());

        productImageView.setImageResource(currProduct.getImageId());
        // setTag is use ful when we want to show image in dialog from product we clicked in the list view
        productImageView.setTag(currProduct.getImageId());

        productNameTextView.setText(currProduct.getName());

        productPriceTextView.setText("$ "+currProduct.getPrice());

        return currProductView;

    }

}
