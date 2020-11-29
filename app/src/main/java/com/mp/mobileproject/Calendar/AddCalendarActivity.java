package com.mp.mobileproject.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mp.mobileproject.Network;
import com.mp.mobileproject.R;
import com.mp.mobileproject.SigninActivity;

public class AddCalendarActivity extends AppCompatActivity {

    private EditText Name, Content;
    private Button Add, Cancle;
    private TextView textView;
    private ImageView imageView;
    private String Name_str, Content_str, type, user;
    private int day, month, year;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        Intent intent = getIntent();

        type = intent.getStringExtra("type");
        user = intent.getStringExtra("user");
        day = intent.getIntExtra("day",0);
        month = intent.getIntExtra("month",0);
        year = intent.getIntExtra("year", 0);

        Name = findViewById(R.id.Add_Edittext_Name);
        Content = findViewById(R.id.Add_Edittext_Content);
        Add = findViewById(R.id.Add_button_ok);
        Cancle = findViewById(R.id.Add_button_cancel);
        textView = findViewById(R.id.Add_Date2_textView);
        imageView = findViewById(R.id.Add_imageView);

        if(type.equals("public")){
            imageView.setImageResource(R.drawable.schdule_public);
        }
        textView.setText(Integer.toString(month)+"월"+Integer.toString(day)+"일");

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_str = Name.getText().toString();
                Content_str = Content.getText().toString();
                if(Name_str.length() >50){
                    Toast.makeText(getApplicationContext(),"이름이 50자가 넘습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(Content_str.length() > 200){
                    Toast.makeText(getApplicationContext(),"내용이 200자 넘습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(Name_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"이름을 작성하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(Content_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"내용을 작성하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    AddCal_fun(Name_str,Content_str,Integer.toString(year),Integer.toString(month),Integer.toString(day),user, type);
                }
                //network
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void AddCal_fun(String... _param){//name, content, year, month, day, user, type
        Network network = new Network(this);
        network.execute("Add_Calendar", _param[0],_param[1],_param[2], _param[3], _param[4], _param[5], _param[6]);
        Toast.makeText(getApplicationContext(),"일정이 등록되었습니다.",Toast.LENGTH_SHORT).show();
    }
}