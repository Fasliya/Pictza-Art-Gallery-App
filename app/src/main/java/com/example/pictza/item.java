package com.example.pictza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class item extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
    }

    public void opencartitem(View view) {
        Intent intent = new Intent(item.this, cart.class);
        startActivity(intent);
    }

    public void openpayment(View view) {
        Intent intent = new Intent(item.this, payment.class);
        startActivity(intent);
    }

}
