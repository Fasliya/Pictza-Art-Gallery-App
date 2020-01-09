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

public class CartSearch extends AppCompatActivity {

    TextView upTitle,upCategory,upDescription, upPrice, upQuantity;
    Button btnAdd;
    String pid;
    ImageView upPainting;
    ArrayList<PaintingModel> paintingArray;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_search);

        upTitle=findViewById(R.id.title_paint_up);
        upCategory=findViewById(R.id.category_paint_up);
        upDescription=findViewById(R.id.description_paint_up);
        upPrice=findViewById(R.id.price_paint_up);
        upQuantity=findViewById(R.id.quantity_paint_up);
        upPainting=findViewById(R.id.up_imagePainting3);

        btnAdd=findViewById(R.id.update_painting);



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

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_item_title=upTitle.getText().toString();
                String et_item_category=upCategory.getText().toString();
                String et_item_description=upDescription.getText().toString();
                String et_item_price=upPrice.getText().toString();
                String et_item_quantity=upQuantity.getText().toString();


                if(et_item_title.equals("")|| et_item_category.equals("")|| et_item_description.equals("")|| et_item_price.equals("")|| et_item_quantity.equals("")){
                    Toast.makeText(CartSearch.this,"Please Enter the Item Quantity",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbHelper.addCart(et_item_title, et_item_category, et_item_description, et_item_price, et_item_quantity)){

                    Toast.makeText(CartSearch.this,"Successfully Added Item to the Cart",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(CartSearch.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void back(View view){

        Intent intent = new Intent(CartSearch.this, AddOrCart.class);
        startActivity(intent);

    }

}
