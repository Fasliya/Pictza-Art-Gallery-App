package com.example.pictza;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pictza.Database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText lg_email,lg_password;
    Button lg_signin;

    private DatabaseHelper dbHelper=new DatabaseHelper(this);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        lg_email=findViewById(R.id.up_name);
        lg_password=findViewById(R.id.up_password);

        lg_signin=findViewById(R.id.regi10);

        lg_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
                    case R.id.regi10:  loginUser();
                        break;

                }

            }
        });


    }

    public void gotosignup(View view){

        Intent intent = new Intent(LoginActivity.this, Main5Activity.class);
        startActivity(intent);

    }

    //public void gotoprofile(View view){

    //    Intent intent = new Intent(LoginActivity.this, profileArtist.class);
    //    startActivity(intent);

   // }

    public void loginUser(){

        String eml = lg_email.getText().toString();
        String pswd = lg_password.getText().toString();


        if(!eml.equals("") || !pswd.equals("")) {

            if (eml.equals("admin@admin.com") && pswd.equals("admin")) {
                Intent openHome = new Intent(getApplicationContext(), HomeActivity.class);
                openHome.putExtra("admin", "admin");
                startActivity(openHome);
                return;
            }

            if( dbHelper.readInfo(eml, pswd)){
                Intent openHome = new Intent(getApplicationContext(), HomeActivity.class);
                openHome.putExtra("admin","user");
                startActivity(openHome);
            }
            else {
                Toast t = Toast.makeText(getApplicationContext(), "There is no any account exists", Toast.LENGTH_SHORT);
                t.show();
            }
        }
        else{
            Toast toast = Toast.makeText(getApplicationContext(), "Please enter your credentials ", Toast.LENGTH_SHORT);
            toast.show();
        }

    }


}
