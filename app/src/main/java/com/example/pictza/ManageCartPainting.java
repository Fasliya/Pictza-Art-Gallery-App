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

import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.PaintingModel;

import java.util.ArrayList;

public class ManageCartPainting extends AppCompatActivity {

    TableLayout table_tb;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<PaintingModel> paintingModelArrylist;


    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_painting);
        dbHelper=new DatabaseHelper(this);
        paintingModelArrylist=dbHelper.getAllPaintings();

        sv_search=findViewById(R.id.view_painting_search1);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(ManageCartPainting.this,CartSearch.class);
                intent.putExtra("painting_title",sv_search.getQuery().toString());
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
        if(paintingModelArrylist!=null) {
            for (int i = 0; i < paintingModelArrylist.size(); i++) {


                TableRow row = new TableRow(this);
                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String pid = Integer.toString(paintingModelArrylist.get(i).getPid());
                String title = paintingModelArrylist.get(i).getTitle();
                String price = paintingModelArrylist.get(i).getPrice();
                String category = paintingModelArrylist.get(i).getCategory();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(ManageCartPainting.this, CartSearch.class);
                        intent.putExtra("painting_id", pid);
                        startActivity(intent);
                    }
                });

                TextView tvid = new TextView(this);
                tvid.setText("    " + pid);
                tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
                TextView tvtitle = new TextView(this);
                tvtitle.setText("" + title);
                tvtitle.setTextAppearance(getApplicationContext(), R.style.table_row_tView2);
                TextView tvprice = new TextView(this);
                tvprice.setText("" + price);
                tvprice.setTextAppearance(getApplicationContext(), R.style.table_row_tView3);



                row.setBottom(2);

                row.addView(tvid);
                row.addView(tvtitle);
                row.addView(tvprice);
                table_tb.addView(row);
            }
        }else{
            TableRow rowMsg = new TableRow(this);
            rowMsg.setBackgroundColor(Color.parseColor("#FFFFFF"));
            TextView tvmsg = new TextView(this);
            tvmsg.setText("No Paintings");
            tvmsg.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvmsg.setGravity(Gravity.CENTER);
            rowMsg.addView(tvmsg);
            table_tb.addView(rowMsg);
        }
    }

}
