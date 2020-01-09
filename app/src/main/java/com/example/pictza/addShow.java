package com.example.pictza;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.pictza.Database.DatabaseHelper;

import java.util.Calendar;

public class addShow extends AppCompatActivity {

    EditText location_show, date_show, time_show, description_show;
    Button add_show, add_showImage;

    final int IMAGE_REQUEST_CODE = 999;
    public static String imgPath;

    private DatabaseHelper dbHelper=new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show);

        location_show=findViewById(R.id.up_location);
        date_show=findViewById(R.id.up_date);
        time_show=findViewById(R.id.up_time);
        description_show=findViewById(R.id.price_paint_up);
        date_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getShowDate();

            }
        });

        time_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getShowTime();
            }
        });

        add_showImage = findViewById(R.id.btn_showImg);

        add_showImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCompat.requestPermissions(addShow.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, IMAGE_REQUEST_CODE);

            }
        });

        add_show=findViewById(R.id.update);

        add_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String et_location=location_show.getText().toString();
                String et_date=date_show.getText().toString();
                String et_time=time_show.getText().toString();
                String et_description=description_show.getText().toString();

                if(et_location.equals("")|| et_date.equals("")|| et_time.equals("")|| et_description.equals("")){
                    Toast.makeText(addShow.this,"Please fill all the fields",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dbHelper.addShow(et_location, et_date, et_time, et_description, imgPath)){
                    location_show.setText("");
                    date_show.setText("");
                    time_show.setText("");
                    description_show.setText("");

                    Toast.makeText(addShow.this,"Successfully Added",Toast.LENGTH_SHORT).show();


                }else {
                    Toast.makeText(addShow.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    public void getShowDate(){

        Calendar calendar = Calendar.getInstance();

        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                String today = date+"/"+month+"/"+year;
                date_show.setText(today);
            }
        },YEAR,MONTH,DATE);

        datePickerDialog.show();

    }

    public void getShowTime(){

        Calendar calendar = Calendar.getInstance();

        int HOUR = calendar.get(Calendar.HOUR);
        int MINITE = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minite) {

                String nowTime = hour+":"+minite;
                time_show.setText(nowTime);

            }
        },HOUR,MINITE,true);

        timePickerDialog.show();

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
            //Toast.makeText(getApplicationContext(),path,Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"Image Selected Successfully",Toast.LENGTH_SHORT).show();

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

    public void home(View view){

        Intent intent = new Intent(addShow.this, viewOrEdit.class);
        startActivity(intent);

    }
}
