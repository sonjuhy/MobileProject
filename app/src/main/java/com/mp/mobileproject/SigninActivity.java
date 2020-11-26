package com.mp.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SigninActivity extends AppCompatActivity {

    private EditText Name, ID, PW;
    private Button ID_Check, Name_Check, Cancle;
    private String Name_str, ID_str, PW_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Name = findViewById(R.id.Signin_Edittext_Name);
        ID = findViewById(R.id.Signin_Edittext_ID);
        PW = findViewById(R.id.Signin_Edittext_PW);
        ID_Check = findViewById(R.id.Signin_button_IDCheck);
        Name_Check = findViewById(R.id.Signin_button_Namecheck);
        Cancle = findViewById(R.id.Signin_button_cancel);

        Name_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_str = Name.getText().toString();
                //Network
            }
        });
        ID_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_str = ID.getText().toString();
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