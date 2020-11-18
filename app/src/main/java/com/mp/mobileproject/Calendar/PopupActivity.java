package com.mp.mobileproject.Calendar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.mp.mobileproject.R;

public class PopupActivity extends AppCompatActivity {

    private String name;
    private TextView textView_name;
    private Button delete,close,modify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        Intent get_intent = getIntent();
        name = get_intent.getStringExtra("Name");

        textView_name = findViewById(R.id.popup_name);
        textView_name.setText(name);

        delete = findViewById(R.id.popup_delete);
        close = findViewById(R.id.popup_close);
        modify = findViewById(R.id.popup_modify);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            return false;
        }
        return true;
    }
}