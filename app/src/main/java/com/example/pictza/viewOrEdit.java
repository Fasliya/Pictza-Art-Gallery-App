package com.example.pictza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class viewOrEdit extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_or_edit);
    }

    public void openaddshow(View view) {
        Intent intent = new Intent(viewOrEdit.this, addShow.class);
        startActivity(intent);
    }

    public void openmanageshow(View view) {
        Intent intent = new Intent(viewOrEdit.this, manageShows.class);
        startActivity(intent);
    }
    public void home(View view){

        Intent intent = new Intent(viewOrEdit.this, HomeActivity.class);
        startActivity(intent);

    }

}
