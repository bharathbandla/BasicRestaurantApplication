package com.example.restaurantapp;

public class CartedProduct {

    private int cartedProductCount;
    private Product product;

    public CartedProduct() {

    }

    public CartedProduct(Product product, int cartedProductCount) {
        this.product = product;
        this.cartedProductCount = cartedProductCount;
    }


    public int getCartedProductCount() {
        return cartedProductCount;
    }

    public void setCartedProductCount(int cartedProductCount) {
        this.cartedProductCount = cartedProductCount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
