package com.mp.mobileproject.Calendar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mp.mobileproject.R;

import java.util.ArrayList;

public class PopupActivity extends AppCompatActivity {

    private int count;
    private int RESULT_CODE = 1;
    private int RESULT_MODIFY = 0;

    private String name;
    private TextView textView_name, textView_content;
    private Button delete,close,modify;

    private ArrayList<CalendarInfo> calendarInfos;
    private CalendarInfo calendarInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_popup);
        Intent get_intent = getIntent();

        name = get_intent.getStringExtra("Name");
        calendarInfos = (ArrayList<CalendarInfo>)get_intent.getSerializableExtra("Cal_info");
        count = get_intent.getIntExtra("Count",0);

        Set_Calinfo(count);

        textView_name = findViewById(R.id.popup_name);
        textView_name.setText(name);
        textView_content = findViewById(R.id.popup_content);
        textView_content.setText(calendarInfo.getContent());

        delete = findViewById(R.id.popup_delete);
        close = findViewById(R.id.popup_close);
        modify = findViewById(R.id.popup_modify);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent return_intent = new Intent();
                return_intent.putExtra("Count",count);
                setResult(2, return_intent);
                System.out.println("delete button");
                finish();
            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("popup close");
                finish();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PopupActivity.this, ModifyActivity.class);
                intent.putExtra("Cal_info",calendarInfo);
                startActivityForResult(intent,RESULT_MODIFY);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_MODIFY){
            switch (resultCode){
                case 1:
                    Intent result_intent = new Intent();
                    result_intent.putExtra("Name",data.getStringExtra("Name"));
                    result_intent.putExtra("Content",data.getStringExtra("Content"));
                    result_intent.putExtra("Count",count);
                    setResult(RESULT_CODE, result_intent);
                    finish();
                    break;
            }
        }

    }

    private void Set_Calinfo(int count){
        for(int i=0; i<calendarInfos.size();i++){
            if(calendarInfos.get(i).getCount() == count){
                calendarInfo = calendarInfos.get(i);
                break;
            }
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        return;
    }
}