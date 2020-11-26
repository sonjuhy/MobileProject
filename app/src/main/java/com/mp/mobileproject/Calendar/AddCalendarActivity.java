package com.mp.mobileproject.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mp.mobileproject.R;

public class AddCalendarActivity extends AppCompatActivity {

    private EditText Name, Content;
    private Button Add, Cancle;
    private String Name_str, Content_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar);
        Name = findViewById(R.id.Add_Edittext_Name);
        Content = findViewById(R.id.Add_Edittext_Content);
        Add = findViewById(R.id.Add_button_ok);
        Cancle = findViewById(R.id.Add_button_cancel);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_str = Name.getText().toString();
                Content_str = Content.getText().toString();
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
}