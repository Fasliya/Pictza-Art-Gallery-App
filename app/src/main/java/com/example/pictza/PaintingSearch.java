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

import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.PaintingModel;

import java.util.ArrayList;

public class PaintingSearch extends AppCompatActivity {

    TableLayout tb_search;
    SearchView sv_search;
    private DatabaseHelper dbHelper;
    private ArrayList<PaintingModel> paintingModelArrylist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting_search);


        dbHelper=new DatabaseHelper(this);
        String p_title=getIntent().getStringExtra("painting_title");
        paintingModelArrylist=dbHelper.searchPainting(p_title);

        sv_search=findViewById(R.id.view_drug_search2);
        sv_search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Intent intent=new Intent(PaintingSearch.this,PaintingSearch.class);
                intent.putExtra("painting_title",sv_search.getQuery().toString());
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
        if(paintingModelArrylist!=null) {
            for(int i=0;i<paintingModelArrylist.size();i++)
            {
                TableRow row=new TableRow(this);

                row.setBackgroundColor(Color.parseColor("#FFFFFF"));
                final String pid =Integer.toString(paintingModelArrylist.get(i).getPid());
                String title=paintingModelArrylist.get(i).getTitle();
                String category=paintingModelArrylist.get(i).getCategory();
                String description=paintingModelArrylist.get(i).getDescription();
                String price=paintingModelArrylist.get(i).getPrice();

                row.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent=new Intent(PaintingSearch.this,PaintingEdit.class);
                        intent.putExtra("painting_id",pid);
                        startActivity(intent);
                    }
                });

                TextView tvid=new TextView(this);
                tvid.setText("    "+pid);
                tvid.setTextAppearance(getApplicationContext(),R.style.table_row_tView1);
                TextView tvname=new TextView(this);
                tvname.setText(""+title);
                tvname.setTextAppearance(getApplicationContext(),R.style.table_row_tView2);
                TextView tvemail=new TextView(this);
                tvemail.setText(""+price);
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
            tvid.setText("No Paintings");
            tvid.setTextAppearance(getApplicationContext(), R.style.table_row_tView1);
            tvid.setGravity(Gravity.CENTER);
            row.addView(tvid);
            tb_search.addView(row);
        }
    }
}
