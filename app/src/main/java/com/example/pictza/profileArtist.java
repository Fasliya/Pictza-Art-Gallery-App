package com.example.pictza;

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

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pictza.Database.DatabaseHelper;
import com.example.pictza.Database.ShowModel;

import java.util.ArrayList;

public class profileArtist extends AppCompatActivity {

    TextView upLocation,upDate,upTime, upDescription;
    ImageView upImage;
    Button btn2Update,btn2Remove;
    String sid;
    ArrayList<ShowModel> showArray;
    DatabaseHelper dbHelper=new DatabaseHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_artist);

        upLocation=findViewById(R.id.up_location);
        upDate=findViewById(R.id.up_date);
        upTime=findViewById(R.id.up_time);
        upDescription=findViewById(R.id.price_paint_up);
        upImage=findViewById(R.id.up_image);

        btn2Update=findViewById(R.id.update);
        btn2Remove=findViewById(R.id.remove);



        sid=getIntent().getStringExtra("show_id");
        showArray= dbHelper.getShow(sid);

        final int sid=showArray.get(0).getSid();
        String location=showArray.get(0).getLocation();
        String date=showArray.get(0).getDate();
        String time=showArray.get(0).getTime();
        String description=showArray.get(0).getDescription();

        upLocation.setText(showArray.get(0).getLocation());
        upDate.setText(""+date);
        upTime.setText(""+time);
        upDescription.setText(""+description);

        Cursor cursor = dbHelper.getShowData("select * from show");

        while (cursor.moveToNext()){
            byte[] image = cursor.getBlob(5);

            Bitmap bitmap = BitmapFactory.decodeByteArray(image,0,image.length);
            upImage.setImageBitmap(bitmap);
        }

        final String new_location=upLocation.getText().toString();
        final String new_date=upDate.getText().toString();
        final String new_time=upTime.getText().toString();
        final String new_description=upDescription.getText().toString();

        btn2Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dbHelper.updateShow(sid, upLocation.getText().toString(),upDate.getText().toString(), upTime.getText().toString(), upDescription.getText().toString())){
                    Toast.makeText(profileArtist.this,"Successfully Updated",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(profileArtist.this,manageShows.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(profileArtist.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }
            }
        });

        btn2Remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert_box=new AlertDialog.Builder(profileArtist.this);
                alert_box.setMessage("Do You Really Want To Remove This Show ?").setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(dbHelper.deleteShow(sid)){
                                    Toast.makeText(profileArtist.this,"Successfully Removed the Show",Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(profileArtist.this, manageShows.class);
                                    startActivity(intent);
                                }else {
                                    Toast.makeText(profileArtist.this,"Something Went Wrong",Toast.LENGTH_SHORT).show();

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
