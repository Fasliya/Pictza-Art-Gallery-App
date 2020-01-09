package com.example.pictza;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class HomeActivity extends AppCompatActivity {

    ImageView user;
    TextView usertxt;
    String admin;
    int count=0;

    SharedPreferences sharedpreferences;

    public static final String MyPREFERENCES = "MyPrefs" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        admin = getIntent().getStringExtra("admin");

        ImageView user = findViewById(R.id.imageView42);
        TextView usertxt = findViewById(R.id.textView70);
        final TextView hiddentxt = findViewById(R.id.hiddentxt);

        if (count > 0){

            hiddentxt.setText("admin");

        }

        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (count > 0){

                    hiddentxt.setText("admin");

                }

                sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(admin,admin);
                editor.commit();

                // Reading from SharedPreferences
                SharedPreferences shared = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
                String check = (shared.getString(admin, ""));

                hiddentxt.setText(check);

                if (check.equals("admin")){

                    Intent intent = new Intent(HomeActivity.this, addToAuctionOr.class);
                    startActivity(intent);
                    count++;

                }else{

                    Toast t = Toast.makeText(HomeActivity.this, "Sorry, Only admin can manage users", Toast.LENGTH_SHORT);
                    t.show();

                }

            }
        });

        usertxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });

    }

    public void logout(View view) {
        Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void opencart(View view) {
        Intent intent = new Intent(HomeActivity.this, AddOrCart.class);
        startActivity(intent);
    }

    public void shows(View view) {
        Intent intent = new Intent(HomeActivity.this, viewOrEdit.class);
        startActivity(intent);
    }

    public void opensearch(View view) {
        Intent intent = new Intent(HomeActivity.this, ManagePainting.class);
        startActivity(intent);
    }

    public void openpaint(View view) {
        Intent intent = new Intent(HomeActivity.this, paintings.class);
        startActivity(intent);
    }

}
