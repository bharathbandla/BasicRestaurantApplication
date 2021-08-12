package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ArrayList<CartedProduct> cartedProductsList = null;

    public static TextView total_bill_text_view;
    public static TextView total_noOf_products_text_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        ListView cartListView = findViewById(R.id.cart_list_view);

        // These two TextViews are Static, because when a product is updated in teh cart these also have to be updated in teh bottom total price
        total_bill_text_view = findViewById(R.id.Total_bill_price);
        total_noOf_products_text_view = findViewById(R.id.Total_no_of_products);


        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();


        // getting all Carted products from the CartedProductMap values
        // Because in CartedProductMap we have productId as key and Carted products as values
        // So we are taking all values and put it into an arrayList
        if(cartedProductMap != null) {
            cartedProductsList = new ArrayList<>(cartedProductMap.values());
        }

        // Checking Whether we are getting carted items
        if(!cartedProductsList.isEmpty()) {
            for (CartedProduct cp : cartedProductsList) {
                Log.i("bha oh1 Name ", String.valueOf(cp.getProduct().getName()));
                Log.i("bha oh2 Price ", String.valueOf(cp.getProduct().getPrice()));
                Log.i("bha oh3 count ", String.valueOf(cp.getCartedProductCount()));
            }
        }

        // Adapter
        CartedProductAdapter cartedProductAdapter = new CartedProductAdapter(CartActivity.this, cartedProductsList);

        cartListView.setAdapter(cartedProductAdapter);

        // Call static updateBill function in BillDetails class to update the total bill amount, total number of items in cart
        // so that we can see live bill calculating
        BillDetails.updateBill();




    }

}