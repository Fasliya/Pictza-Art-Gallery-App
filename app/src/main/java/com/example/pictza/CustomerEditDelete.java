package com.example.pictza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pictza.Database.CustomerModel;
import com.example.pictza.Database.DatabaseHelper;

import java.util.ArrayList;

public class CustomerEditDelete extends AppCompatActivity {

    TextView upName,upEmail,upPassword;
    Button btnUpdate,btnRemove;
    String id;
    ArrayList<CustomerModel> customerArray;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_edit_delete);

        upName=findViewById(R.id.up_name);
        upEmail=findViewById(R.id.up_email);
        upPassword=findViewById(R.id.up_password);

        btnUpdate=findViewById(R.id.btn_Update);
        btnRemove=findViewById(R.id.btn_Remove);


        id=getIntent().getStringExtra("customer_id");
        customerArray= dbHelper.getCustomer(id);

        final int id=customerArray.get(0).getId();
        String name=customerArray.get(0).getUsername();
        String email=customerArray.get(0).getEmail();
        String password=customerArray.get(0).getPassword();

        upName.setText(customerArray.get(0).getUsername());
        upEmail.setText(""+email);
        upPassword.setText(""+password);

        final String new_username=upName.getText().toString();
        final String new_email=upEmail.getText().toString();
        final String new_password=upPassword.getText().toString();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.updateCustomer(id, upName.getText().toString(),upEmail.getText().toString(), upPassword.getText().toString())){
                    Toast.makeText(CustomerEditDelete.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CustomerEditDelete.this,auction.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CustomerEditDelete.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_box=new AlertDialog.Builder(CustomerEditDelete.this);
                alert_box.setMessage("Do You Really Want To Remove This Customer ?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbHelper.deleteCustomer(id)){
                                    Toast.makeText(CustomerEditDelete.this,"Successfully Removed",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(CustomerEditDelete.this,auction.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(CustomerEditDelete.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();

                                }
                            }
                        }).setNegativeButton("Cancel",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }) ;
                AlertDialog alert = alert_box.create();
                alert.setTitle("Alert !!!");
                alert.show();

            }
        });


    }
}
