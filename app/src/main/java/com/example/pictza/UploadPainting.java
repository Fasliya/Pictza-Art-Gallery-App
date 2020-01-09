package com.example.pictza;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pictza.Database.DatabaseHelper;

public class UploadPainting extends AppCompatActivity {

    EditText paint_title, paint_category, paint_description, paint_price;
    Button painting_upload, add_paintingImage;

    final int IMAGE_REQUEST_CODE = 999;
    public static String imgPath;

    private DatabaseHelper dbHelper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_painting);

        paint_title=findViewById(R.id.title_up);
        paint_category=findViewById(R.id.category_paint_up);
        paint_description=findViewById(R.id.description_paint_up);
        paint_price=findViewById(R.id.price_paint_up);

        add_paintingImage = findViewById(R.id.add_paintingImage);

        add_paintingImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(UploadPainting.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_REQUEST_CODE);

            }
        });

        painting_upload=findViewById(R.id.update_painting);

        painting_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_title=paint_title.getText().toString();
                String et_category=paint_category.getText().toString();
                String et_description=paint_description.getText().toString();
                String et_price=paint_price.getText().toString();

                if(et_title.equals("")|| et_category.equals("")|| et_description.equals("")|| et_price.equals("")){
                    Toast.makeText(UploadPainting.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbHelper.addPainting(et_title, et_category, et_description, et_price, imgPath)){
                    paint_title.setText("");
                    paint_category.setText("");
                    paint_description.setText("");
                    paint_price.setText("");

                    Toast.makeText(UploadPainting.this,"Successfully Published",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(UploadPainting.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == IMAGE_REQUEST_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, IMAGE_REQUEST_CODE);
            }else{
                Toast.makeText(getApplicationContext(),"You do not have permission to access gallery",Toast.LENGTH_LONG).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            String path = getPath(uri);
            imgPath = getPath(uri);
            //   System.out.println(path);
            Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public String getPath(Uri uri){
        if(uri==null){
            return null;
        }
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri,projection,null,null,null);
        if(cursor!=null){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }


}
