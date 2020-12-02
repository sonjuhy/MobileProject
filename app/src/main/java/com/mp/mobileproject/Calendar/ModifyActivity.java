package com.mp.mobileproject.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mp.mobileproject.R;

import java.util.ArrayList;

public class ModifyActivity extends AppCompatActivity {

    private Button modify, cancle;
    private EditText name, content;
    private String name_str, content_str;
    private ImageView imageView;
    private TextView modify_date;

    private final int RESULT_CODE = 1;

    private CalendarInfo calendarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        Intent intent = getIntent();

        calendarInfo = (CalendarInfo) intent.getSerializableExtra("Cal_info");

        modify = findViewById(R.id.Modify_button_ok);
        cancle = findViewById(R.id.Modify_button_cancel);
        name = findViewById(R.id.Modify_Edittext_Name);
        content = findViewById(R.id.Modify_Edittext_Content);
        imageView = findViewById(R.id.Modify_imageView);
        modify_date = findViewById(R.id.Modify_Date2_textView);

        name.setText(calendarInfo.getName());
        content.setText(calendarInfo.getContent());
        modify_date.setText(calendarInfo.getLocalDate().getMonthValue()+"월"+calendarInfo.getLocalDate().getDayOfMonth()+"일");
        if(calendarInfo.getType().equals("public")){
            imageView.setImageResource(R.drawable.schdule_public);
        }

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
                    Intent result_intent = new Intent();
                    result_intent.putExtra("Name",name_str);
                    result_intent.putExtra("Content",content_str);
                    setResult(RESULT_CODE, result_intent);
                    finish();
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