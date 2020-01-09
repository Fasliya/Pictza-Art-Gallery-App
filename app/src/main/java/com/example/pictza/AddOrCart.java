package com.example.pictza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AddOrCart extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_cart);
    }

    public void home(View view){

        Intent intent = new Intent(AddOrCart.this, HomeActivity.class);
        startActivity(intent);

    }

    public void addtocart(View view) {
        Intent intent = new Intent(AddOrCart.this, ManageCartPainting.class);
        startActivity(intent);
    }

    public void viewcart(View view) {
        Intent intent = new Intent(AddOrCart.this, CartView.class);
        startActivity(intent);
    }

}
