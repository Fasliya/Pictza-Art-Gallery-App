package com.example.pictza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class addToAuctionOr extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_auction_or);
    }

    public void viewUsers(View view){

        Intent intent = new Intent(addToAuctionOr.this, auction.class);
        startActivity(intent);

    }

    public void editUsers(View view){

        Intent intent = new Intent(addToAuctionOr.this, auction.class);
        startActivity(intent);

    }

    public void home(View view){

        Intent intent = new Intent(addToAuctionOr.this, HomeActivity.class);
        startActivity(intent);

    }

}
