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

import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.ShowModel;

import java.util.ArrayList;

public class shows extends AppCompatActivity {

    TableLayout tb_search;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<ShowModel> showModelArrylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows);


        dbHelper=new DatabaseHelper(this);
        String d_location=getIntent().getStringExtra("show_location");
        showModelArrylist=dbHelper.searchShow(d_location);

        sv_search=findViewById(R.id.view_drug_search2);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(shows.this,shows.class);
                intent.putExtra("show_location",sv_search.getQuery().toString());
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
        if(showModelArrylist!=null) {
            for(int i=0;i<showModelArrylist.size();i++)
            {
                TableRow row=new TableRow(this);

                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String sid =Integer.toString(showModelArrylist.get(i).getSid());
                String location=showModelArrylist.get(i).getLocation();
                String date=showModelArrylist.get(i).getDate();
                String time=showModelArrylist.get(i).getTime();
                String description=showModelArrylist.get(i).getDescription();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(shows.this,profileArtist.class);
                        intent.putExtra("show_id",sid);
                        startActivity(intent);
                    }
                });

                TextView tvid=new TextView(this);
                tvid.setText("    "+sid);
                tvid.setTextAppearance(getApplicationContext(),R.style.table_row_tView1);
                TextView tvname=new TextView(this);
                tvname.setText(""+location);
                tvname.setTextAppearance(getApplicationContext(),R.style.table_row_tView2);
                TextView tvemail=new TextView(this);
                tvemail.setText(""+date);
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
            tvid.setText("No Shows Available");
            tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvid.setGravity(Gravity.CENTER);
            row.addView(tvid);
            tb_search.addView(row);
        }
    }
}
