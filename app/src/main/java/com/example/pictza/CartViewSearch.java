package com.example.pictza;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.SearchView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;


import com.example.pictza.Database.CartModel;
import com.example.pictza.Database.DatabaseHelper;

import java.util.ArrayList;

public class CartViewSearch extends AppCompatActivity {

    TableLayout tb_search;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<CartModel> cartModelArrylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view_search);


        dbHelper=new DatabaseHelper(this);
        String i_title=getIntent().getStringExtra("item_title");
        cartModelArrylist=dbHelper.searchItem(i_title);

        sv_search=findViewById(R.id.view_drug_search2);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(CartViewSearch.this,CartViewSearch.class);
                intent.putExtra("item_title",sv_search.getQuery().toString());
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
        if(cartModelArrylist!=null) {
            for(int i=0;i<cartModelArrylist.size();i++)
            {
                TableRow row=new TableRow(this);

                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String cid =Integer.toString(cartModelArrylist.get(i).getCid());
                String title=cartModelArrylist.get(i).getItemTitle();
                String price=cartModelArrylist.get(i).getItemPrice();
                String quantity=cartModelArrylist.get(i).getItemQuantity();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(CartViewSearch.this,CartEdit.class);
                        intent.putExtra("item_id",cid);
                        startActivity(intent);
                    }
                });

                TextView tvid=new TextView(this);
                tvid.setText("    "+cid);
                tvid.setTextAppearance(getApplicationContext(),R.style.table_row_tView1);
                TextView tvname=new TextView(this);
                tvname.setText(""+title);
                tvname.setTextAppearance(getApplicationContext(),R.style.table_row_tView2);
                TextView tvemail=new TextView(this);
                tvemail.setText(""+quantity);
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
            tvid.setText("No Items");
            tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvid.setGravity(Gravity.CENTER);
            row.addView(tvid);
            tb_search.addView(row);
        }
    }

}
