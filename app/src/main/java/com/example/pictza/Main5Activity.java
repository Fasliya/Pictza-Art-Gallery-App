package com.example.pictza;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pictza.Database.DatabaseHelper;

public class Main5Activity extends AppCompatActivity {

    EditText in_username, in_email,in_password,in_passwordcf;
    Button in_signup;

    private DatabaseHelper dbHelper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        in_username=findViewById(R.id.up_name);
        in_email=findViewById(R.id.up_email);
        in_password=findViewById(R.id.up_password);
        in_passwordcf=findViewById(R.id.passwordcf);

        in_signup=findViewById(R.id.btn_Remove);

        in_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

                String et_username=in_username.getText().toString();
                String et_email=in_email.getText().toString();
                String et_password=in_password.getText().toString();
                String et_password_confirm=in_passwordcf.getText().toString();

                if(!et_email.matches(emailPattern)){
                    Toast.makeText(Main5Activity.this,"Please Enter a valid email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(et_username.equals("")|| et_email.equals("")|| et_password.equals("")|| et_password_confirm.equals("")){
                    Toast.makeText(Main5Activity.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!et_password.equals(et_password_confirm)){
                    Toast.makeText(Main5Activity.this,"Passwords do not Match",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbHelper.addCustomer(et_username, et_email, et_password)){
                    in_username.setText("");
                    in_email.setText("");
                    in_password.setText("");
                    in_passwordcf.setText("");

                    Toast.makeText(Main5Activity.this,"You have successfully signed up",Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(Main5Activity.this, LoginActivity.class);
                    startActivity(intent);

                    Toast.makeText(Main5Activity.this,"Now you can login",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(Main5Activity.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

}
