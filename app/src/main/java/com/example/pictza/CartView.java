package com.example.pictza;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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

public class CartView extends AppCompatActivity {

    TableLayout table_tb;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<CartModel> cartModelArrylist;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_view);
        dbHelper=new DatabaseHelper(this);
        cartModelArrylist=dbHelper.getAllItems();

        sv_search=findViewById(R.id.view_painting_search1);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(CartView.this,CartViewSearch.class);
                intent.putExtra("item_title",sv_search.getQuery().toString());
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });




        table_tb=findViewById(R.id.view_table);
        //TableLayout table = (TableLayout) findViewById(R.id.view_table);
        table_tb.setStretchAllColumns(true);
        if(cartModelArrylist!=null) {
            for (int i = 0; i < cartModelArrylist.size(); i++) {


                TableRow row = new TableRow(this);
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String cid = Integer.toString(cartModelArrylist.get(i).getCid());
                String title = cartModelArrylist.get(i).getItemTitle();
                String price = cartModelArrylist.get(i).getItemPrice();
                String quantity = cartModelArrylist.get(i).getItemQuantity();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(CartView.this, CartEdit.class);
                        intent.putExtra("item_id", cid);
                        startActivity(intent);
                    }
                });

                TextView tvid = new TextView(this);
                tvid.setText("    " + cid);
                tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
                TextView tvname = new TextView(this);
                tvname.setText("" + title);
                tvname.setTextAppearance(getApplicationContext(), R.style.table_row_tView2);
                TextView tvemail = new TextView(this);
                tvemail.setText("" + quantity);
                tvemail.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);



                row.setBottom(2);

                row.addView(tvid);
                row.addView(tvname);
                row.addView(tvemail);
                table_tb.addView(row);
            }
        }else{
            TableRow rowMsg = new TableRow(this);
            rowMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tvmsg = new TextView(this);
            tvmsg.setText("No Items in Cart");
            tvmsg.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvmsg.setGravity(Gravity.CENTER);
            rowMsg.addView(tvmsg);
            table_tb.addView(rowMsg);
        }
    }

}
