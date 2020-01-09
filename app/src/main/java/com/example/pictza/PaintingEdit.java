package com.example.pictza;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.PaintingModel;

import java.util.ArrayList;

public class PaintingEdit extends AppCompatActivity {

    TextView upTitle,upCategory,upDescription, upPrice;
    Button btnUpdate,btnRemove;
    ImageView upPainting;
    String pid;
    ArrayList<PaintingModel> paintingArray;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_painting_edit);

        upTitle=findViewById(R.id.title_paint_up);
        upCategory=findViewById(R.id.category_paint_up);
        upDescription=findViewById(R.id.description_paint_up);
        upPrice=findViewById(R.id.price_paint_up);
        upPainting=findViewById(R.id.up_imagePainting);

        btnUpdate=findViewById(R.id.update_painting);
        btnRemove=findViewById(R.id.remove_painting);


        pid=getIntent().getStringExtra("painting_id");
        paintingArray= dbHelper.getPainting(pid);

        final int pid=paintingArray.get(0).getPid();
        String title=paintingArray.get(0).getTitle();
        String category=paintingArray.get(0).getCategory();
        String description=paintingArray.get(0).getDescription();
        String price=paintingArray.get(0).getPrice();

        upTitle.setText(paintingArray.get(0).getTitle());
        upCategory.setText(""+category);
        upDescription.setText(""+description);
        upPrice.setText(""+price);

        Cursor cursor = dbHelper.getShowData("select * from painting");

        while (cursor.moveToNext()){
            byte[] painting = cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(painting,0,painting.length);
            upPainting.setImageBitmap(bitmap);
        }

        final String new_title=upTitle.getText().toString();
        final String new_category=upCategory.getText().toString();
        final String new_description=upDescription.getText().toString();
        final String new_price=upPrice.getText().toString();

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.updatePainting(pid, upTitle.getText().toString(),upCategory.getText().toString(), upDescription.getText().toString(), upPrice.getText().toString())){
                    Toast.makeText(PaintingEdit.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(PaintingEdit.this,ManagePainting.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(PaintingEdit.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_box=new AlertDialog.Builder(PaintingEdit.this);
                alert_box.setMessage("Do You Really Want To Remove This Painting ?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbHelper.deletePainting(pid)){
                                    Toast.makeText(PaintingEdit.this,"Successfully Removed",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(PaintingEdit.this,ManagePainting.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(PaintingEdit.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();

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
