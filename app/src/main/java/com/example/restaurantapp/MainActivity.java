package com.example.restaurantapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity{

    ListView listView;

    ArrayList<Product> arrayProductList = new ArrayList<>();

    // This is used to map product Id with Product Object
    static Map<String, Product> productMap = new ConcurrentHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();

        listView = findViewById(R.id.listView);

        arrayProductList.add(new Product("p1", R.drawable.food1, "bread", 60));
        arrayProductList.add(new Product("p2", R.drawable.food2, "cola", 20));
        arrayProductList.add(new Product("p3", R.drawable.food3, "burger", 50));

        arrayProductList.add(new Product("p4", R.drawable.food4, "donut", 90));
        arrayProductList.add(new Product("p5", R.drawable.food5, "pizza", 320));
        arrayProductList.add(new Product("p6", R.drawable.food6, "french frizz", 50));

        arrayProductList.add(new Product("p7", R.drawable.food7, "ice cream", 10));
        arrayProductList.add(new Product("p8", R.drawable.food8, "cup bone", 85));
        arrayProductList.add(new Product("p9", R.drawable.food9, "shake", 120));

        arrayProductList.add(new Product("p10", R.drawable.food10, "sand which", 40));
        arrayProductList.add(new Product("p11", R.drawable.food11, "cup cake", 135));
        arrayProductList.add(new Product("p12", R.drawable.food12, "lollipops", 150));

        //Converting arrayProductList List into product Map. This is used for to get product by product Id
        // we are mapping product id : product object
        // Product::getProductId -- is to get the product id
        //  Function.identity()-- telling the same object as value
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            productMap = arrayProductList.stream().collect(Collectors.toMap(Product::getProductId, Function.identity()));
        }


        ProductAdapter productAdapter = new ProductAdapter(MainActivity.this, arrayProductList);

        listView.setAdapter(productAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                // Show the dialog, we are passing item view to it
                showProductDialog(view);

                // Just For Checking
                TextView txt = view.findViewById(R.id.product_name);
                String myName = txt.getText().toString();
                Log.d("bha here", myName);

                Log.d("bha size CartedMap here",""+ cartedProductMap.size());

                // using for-each loop for iteration over Map.entrySet()
                for (Map.Entry<String, CartedProduct> entry : cartedProductMap.entrySet()){
                    Log.d("bha 77", "Product = " + entry.getKey() +", Count = " + entry.getValue().getCartedProductCount());
                }

            }
        });

    }


    // Assign Top menu cart feature
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_top, menu);
        return true;
    }

    //we have to override onOptionsItemSelected method in our Activity, which is called when user clicks on the item in Options menu.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_cart_item:
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                this.startActivity(intent);
                break;
        }

        return true;
    }

    // This alone in dialog box is declared in out side, because while updating the decrease count and increase count
    // to update in the dialog box too on spontaneously
    // decrease count and increase count are declared out side the showProductDialog function, so global mentioned
    TextView dia_product_count_textView;

    // Dialog box
    private void showProductDialog(View productView) {

        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView dia_product_image =  dialog.findViewById(R.id.dia_product_image);
        TextView dia_product_name = dialog.findViewById(R.id.dia_product_name);
        TextView dia_product_price = dialog.findViewById(R.id.dia_product_price);

        Button bt_decrease =  dialog.findViewById(R.id.bt_decrease);
        dia_product_count_textView = dialog.findViewById(R.id.dia_product_count_textView);
        Button bt_increase =  dialog.findViewById(R.id.bt_increase);

        // get the product id of product selected
        TextView  product_id_sel_texView = productView.findViewById(R.id.product_id);
        String  product_id_sel = product_id_sel_texView.getText().toString();

        // Image product in main activity for a selected product view
        ImageView imageView_product = productView.findViewById(R.id.product_img);
        // grab the image id which we set in the ProductAdapter getView for an image
        int img_id_product = Integer.parseInt(imageView_product.getTag().toString());
        // Pass this image id to the dialog box
        dia_product_image.setImageResource(img_id_product);

        // pass product name, product price to the dialog
        TextView product_name_sel_textView = productView.findViewById(R.id.product_name);
        dia_product_name.setText(product_name_sel_textView.getText().toString());

        TextView product_price_sel_textView = productView.findViewById(R.id.product_price);
        dia_product_price.setText(product_price_sel_textView.getText().toString());

        // Set the count In the text view of count  selected the dialog box, when user click the item and showing the dialog box
        showCartedProductCountInTextViewDialog(product_id_sel);

        // If decrease(-) button clicked, decrease product count
        bt_decrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.decreaseCartedProductCount(product_id_sel, dia_product_count_textView);
            }
        });

        // If increase(+) button clicked, increase product count
        bt_increase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utility.increaseCartedProductCount(product_id_sel, dia_product_count_textView);
            }
        });

        // Show the dialog box
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    /*private void decreaseCartedProductCount(String productId) {
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
        dia_product_count_textView.setText(cartedProduct.getCartedProductCount()+"");

        // If Count goes to zero, then remove the item from cart
        if(cartedProduct.getCartedProductCount() == 0){
            Log.d("bha bef rem siz",""+ cartedProductMap.size());
            cartedProductMap.remove(productId);
            Log.d("bha aft rem size ",""+ cartedProductMap.size());
        }

        return;

    }

    private void increaseCartedProductCount(String productId){
        // get the cartedProductMap from cart
        // cartedProductMap is created as static, so no object is required
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();
        int count;
        CartedProduct cartedProduct = null;

        // If cartedProductMap is not empty and product is  present in the cart
        if(!cartedProductMap.isEmpty() && cartedProductMap.containsKey(productId)){
            cartedProduct = cartedProductMap.get(productId);
        }else {
            //Get product details from product map, (This can be a DB call to get product details by id)
            // check whether this product id preset in the product map
            // If yes then add our product to carted product
            if(productMap.containsKey(productId)) {
                // Get the product object from the product Map
                Product product = productMap.get(productId);

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
        dia_product_count_textView.setText(cartedProduct.getCartedProductCount()+"");

        return;
    }
*/
    private void showCartedProductCountInTextViewDialog(String productId){
        Map<String, CartedProduct> cartedProductMap = Cart.getCartedProductMap();
        if(!cartedProductMap.containsKey(productId)) {
            // If product didn't selected atleast once the set tex view as 0
            dia_product_count_textView.setText("0");
            return;
        }

        // get relative CartedProduct object from Map
        CartedProduct nCartedProduct = cartedProductMap.get(productId);
        // update in the ui Dialog box TextView Count
        dia_product_count_textView.setText(nCartedProduct.getCartedProductCount()+"");
    }



}


