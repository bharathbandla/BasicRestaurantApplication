package com.example.restaurantapp;


import android.util.Log;
import android.widget.TextView;

import java.util.Map;


// This is used for update the count of when we press on Increase Or Decrease button
// Count should be decrease or increase
public class Utility {

    // For decreasing the count
    // we have to pass productId and the view we have to update
    public static void decreaseCartedProductCount(String productId, TextView dia_product_count_textView) {
        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        int count;
        CartedProduct cartedProduct;

        // If cartedProductMap is  empty or product is not present in the cart,  so product is not clicked add button at least once
        if(cartedProductMap.isEmpty() || !cartedProductMap.containsKey(productId)){
            return;
        }

        // get relative CartedProduct object from Map
        cartedProduct = cartedProductMap.get(productId);
        count = cartedProduct.getCartedProductCount();

        // checking if count is > 0
        if(count > 0){
            // decrease count by 1
            cartedProduct.setCartedProductCount(--count);
        }

        // Map won't update automatically, so we have to update
        // Map can't take duplicates, if any duplicate is present in the map, first present will be override by new item
        // so our map has keys with product id, after modifying the object , insert newly into the map
        // since map won't allow duplicates new product will override existing one
        cartedProductMap.put(productId, cartedProduct);

        // update in the ui Dialog box TextView Count
        dia_product_count_textView.setText(String.valueOf(cartedProduct.getCartedProductCount()));

        // If Count goes to zero, then remove the item from cart
        if(cartedProduct.getCartedProductCount() == 0){
            Log.d("bha bef rem siz",""+ cartedProductMap.size());
            cartedProductMap.remove(productId);
            Log.d("bha aft rem size ",""+ cartedProductMap.size());
        }


    }


    // For increase the count
    // we have to pass productId and the view we have to update
    public static void increaseCartedProductCount(String productId, TextView dia_product_count_textView) {
        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();
        int count;
        CartedProduct cartedProduct = null;

        // If cartedProductMap is not empty and product is  present in the cart
        if(!cartedProductMap.isEmpty() && cartedProductMap.containsKey(productId)){
            cartedProduct = cartedProductMap.get(productId);
        }else {
            // In Main Activity we have a product map, that is static, so we can take that
            //Get product details from product map, (This can be a DB call to get product details by id)
            // check whether this product id preset in the product map
            // If yes then add our product to carted product
            if(MainActivity.productMap.containsKey(productId)) {
                // Get the product object from the product Map
                Product product = MainActivity.productMap.get(productId);

                // product is not present in the cart, so create a new productItem into the cart
                cartedProduct = new CartedProduct(product, 0);
            }
        }

        if(null == cartedProduct)
            return;
        count = cartedProduct.getCartedProductCount();

        // increase count by 1
        cartedProduct.setCartedProductCount(++count);

        // Map won't update automatically, so we have to update
        // Map can't take duplicates, if any duplicate is present in the map, first present will be override by new item
        // so our map has keys with product id, after modifying the object , insert newly into the map
        // since map won't allow duplicates new product will override existing one
        cartedProductMap.put(productId, cartedProduct);


        // update in the ui Dialog box TextView Count
        dia_product_count_textView.setText(String.valueOf(cartedProduct.getCartedProductCount()));

    }

}
