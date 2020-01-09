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


import com.example.pictza.Database.CartModel;
import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.PaintingModel;

import java.util.ArrayList;

public class CartEdit extends AppCompatActivity {

    TextView upTitle,upCategory,upDescription, upPrice, upQuantity;
    Button btnUpdate,btnRemove;
    String cid;
    ImageView upPainting;
    ArrayList<CartModel> cartArray;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_edit);

        upTitle=findViewById(R.id.title_paint_up);
        upCategory=findViewById(R.id.category_paint_up);
        upDescription=findViewById(R.id.description_paint_up);
        upPrice=findViewById(R.id.price_paint_up);
        upQuantity=findViewById(R.id.quantity_paint_up);
        upPainting=findViewById(R.id.up_imagePainting2);

        btnUpdate=findViewById(R.id.update_painting);
        btnRemove=findViewById(R.id.remove_painting);


        cid=getIntent().getStringExtra("item_id");
        cartArray= dbHelper.getItem(cid);

        final int cid=cartArray.get(0).getCid();
        String title=cartArray.get(0).getItemTitle();
        String category=cartArray.get(0).getItemCategory();
        String description=cartArray.get(0).getItemDescription();
        String price=cartArray.get(0).getItemPrice();
        String quantity=cartArray.get(0).getItemQuantity();

        upTitle.setText(cartArray.get(0).getItemTitle());
        upCategory.setText(""+category);
        upDescription.setText(""+description);
        upPrice.setText(""+price);
        upQuantity.setText(""+quantity);

        final String new_title=upTitle.getText().toString();
        final String new_category=upCategory.getText().toString();
        final String new_description=upDescription.getText().toString();
        final String new_price=upPrice.getText().toString();
        final String new_quantity=upQuantity.getText().toString();

        Cursor cursor = dbHelper.getShowData("select * from painting");

        while (cursor.moveToNext()){
            byte[] painting = cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(painting,0,painting.length);
            upPainting.setImageBitmap(bitmap);
        }

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.updateCart(cid, upTitle.getText().toString(),upCategory.getText().toString(), upDescription.getText().toString(),upPrice.getText().toString(), upQuantity.getText().toString())){
                    Toast.makeText(CartEdit.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(CartEdit.this,CartView.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(CartEdit.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_box=new AlertDialog.Builder(CartEdit.this);
                alert_box.setMessage("Do You Really Want To Remove This Item ?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbHelper.deleteItem(cid)){
                                    Toast.makeText(CartEdit.this,"Successfully Removed",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(CartEdit.this,CartView.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(CartEdit.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();

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
