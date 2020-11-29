package com.mp.mobileproject.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.mp.mobileproject.R;

public class ModifyActivity extends AppCompatActivity {

    private Button modify, cancle;
    private EditText name, content;
    private String name_str, content_str;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Intent intent = getIntent();

        modify = findViewById(R.id.Modify_button_ok);
        cancle = findViewById(R.id.Modify_button_cancel);
        name = findViewById(R.id.Modify_Edittext_Name);
        content = findViewById(R.id.Modify_Edittext_Content);
        imageView = findViewById(R.id.Modify_imageView);

        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_str = name.getText().toString();
                content_str = content.getText().toString();

                if(name_str.length() == 0){
                    Toast.makeText(getApplicationContext(),"이름을 입력하시지 않으셨습니다.",Toast.LENGTH_SHORT).show();
                }
                else if(content_str.length() == 0){
                    Toast.makeText(getApplicationContext(),"내용을 입력하시지 않으셨습니다.",Toast.LENGTH_SHORT).show();
                }
                else{

                }
            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}