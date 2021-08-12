package com.example.restaurantapp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cart {

    public static Map<String, CartedProduct> cartedProductMap;

    public static Map<String, CartedProduct> getCartedProductMap() {
        if(null == cartedProductMap){
            // For first time map is created, we have to create Map with String, CartedProduct
            cartedProductMap = new ConcurrentHashMap<>();
        }
        return cartedProductMap;
    }

    public static void setCartedProductMap(Map<String, CartedProduct> cartedProductMap) {
        Cart.cartedProductMap = cartedProductMap;
    }
}
