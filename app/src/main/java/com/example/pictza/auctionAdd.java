package com.example.pictza;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pictza.Database.CustomerModel;
import com.example.pictza.Database.DatabaseHelper;

import java.util.ArrayList;

public class auctionAdd extends AppCompatActivity {
    TableLayout tb_search;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<CustomerModel> customerModelArrylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auction_add);


        dbHelper=new DatabaseHelper(this);
        String d_name=getIntent().getStringExtra("customer_name");
        customerModelArrylist=dbHelper.searchCustomer(d_name);

        sv_search=findViewById(R.id.view_drug_search2);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(auctionAdd.this,auctionAdd.class);
                intent.putExtra("customer_name",sv_search.getQuery().toString());
                startActivity(intent);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        tb_search=findViewById(R.id.view_table);
        //TableLayout table = (TableLayout) findViewById(R.id.view_table);
        tb_search.setStretchAllColumns(true);
        if(customerModelArrylist!=null) {
            for(int i=0;i<customerModelArrylist.size();i++)
            {
                TableRow row=new TableRow(this);

                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String id =Integer.toString(customerModelArrylist.get(i).getId());
                String name=customerModelArrylist.get(i).getUsername();
                String email=customerModelArrylist.get(i).getEmail();
                String password=customerModelArrylist.get(i).getPassword();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(auctionAdd.this,CustomerEditDelete.class);
                        intent.putExtra("customer_id",id);
                        startActivity(intent);
                    }
                });

                TextView tvid=new TextView(this);
                tvid.setText("    "+id);
                tvid.setTextAppearance(getApplicationContext(),R.style.table_row_tView1);
                TextView tvname=new TextView(this);
                tvname.setText(""+name);
                tvname.setTextAppearance(getApplicationContext(),R.style.table_row_tView2);
                TextView tvemail=new TextView(this);
                tvemail.setText(""+email);
                tvemail.setTextAppearance(getApplicationContext(),R.style.table_row_tView3);

                row.addView(tvid);
                row.addView(tvname);
                row.addView(tvemail);
                tb_search.addView(row);
            }
        }else{
            TableRow row = new TableRow(this);
            row.setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tvid = new TextView(this);
            tvid.setText("No Customers");
            tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvid.setGravity(Gravity.CENTER);
            row.addView(tvid);
            tb_search.addView(row);
        }
    }
}
