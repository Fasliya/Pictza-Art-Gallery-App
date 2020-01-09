package com.example.pictza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class paintings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paintings);
    }

    public void home(View view){

        Intent intent = new Intent(paintings.this, HomeActivity.class);
        startActivity(intent);

    }
    public void add(View view){

        Intent intent = new Intent(paintings.this, UploadPainting.class);
        startActivity(intent);

    }
    public void manage(View view){

        Intent intent = new Intent(paintings.this, ManagePainting.class);
        startActivity(intent);

    }
}
