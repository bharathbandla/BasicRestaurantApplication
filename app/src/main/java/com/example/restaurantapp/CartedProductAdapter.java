package com.example.restaurantapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;

import java.util.ArrayList;

public class CartedProductAdapter extends ArrayAdapter<CartedProduct> {


    public CartedProductAdapter(Context context, ArrayList<CartedProduct> cartedProductsList) {
        super(context,0, cartedProductsList);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View currCartProductView = convertView;

        if(currCartProductView == null){
            currCartProductView = LayoutInflater.from(getContext()).inflate(R.layout.cart_single_product, parent, false);
        }

        CartedProduct curCartedProduct = getItem(position);

        TextView carted_product_id = currCartProductView.findViewById(R.id.carted_product_id);
        ImageView carted_product_img = currCartProductView.findViewById(R.id.carted_product_img);
        TextView carted_product_name = currCartProductView.findViewById(R.id.carted_product_name);
        TextView carted_product_price = currCartProductView.findViewById(R.id.carted_product_price);
        TextView carted_product_count_textView = currCartProductView.findViewById(R.id.carted_product_count_textView);

        AppCompatImageButton cart_bt_decrease = currCartProductView.findViewById(R.id.cart_bt_decrease);
        AppCompatImageButton cart_bt_increase = currCartProductView.findViewById(R.id.cart_bt_increase);

        // curCartedProduct has two variables - count, product object
        // Getting Product object From curCartedProduct

        carted_product_id.setText(curCartedProduct.getProduct().getProductId());
        carted_product_img.setImageResource(curCartedProduct.getProduct().getImageId());
        carted_product_name.setText(curCartedProduct.getProduct().getName());
        carted_product_price.setText("$ "+curCartedProduct.getProduct().getPrice());

        // we have count in curCartedProduct its shelf as a variable
        carted_product_count_textView.setText(curCartedProduct.getCartedProductCount()+"");

        // Set tags for the buttons so when we click on increase or decrease in the item in carted item
        // in The cart activity can notice which product button is clicked

        cart_bt_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_carted_product_id = curCartedProduct.getProduct().getProductId();
                Utility.decreaseCartedProductCount(string_carted_product_id, carted_product_count_textView);

                // Call static updateBill function in BillDetails class to update the total bill amount when ever we click the + or - button for a product in Cart
                // so that we can see live bill calculating
                BillDetails.updateBill();
            }
        });
        cart_bt_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String string_carted_product_id = curCartedProduct.getProduct().getProductId();
                Utility.increaseCartedProductCount(string_carted_product_id, carted_product_count_textView);

                // Call static updateBill function in BillDetails class to update the total bill amount when ever we click the + or - button for a product in Cart
                // so that we can see live bill calculating
                BillDetails.updateBill();
            }
        });

        return currCartProductView;
    }
}
