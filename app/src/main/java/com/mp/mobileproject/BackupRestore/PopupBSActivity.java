package com.mp.mobileproject.BackupRestore;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.mp.mobileproject.Calendar.CalendarInfo;
import com.mp.mobileproject.R;

import java.util.ArrayList;

public class PopupBSActivity extends AppCompatActivity {

    private Button button_close, button_backup, button_restore;
    private TextView textView_filename;

    private String path;

    private ArrayList<CalendarInfo> calendarInfos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup_b_s);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        Intent intent = getIntent();
        calendarInfos = (ArrayList<CalendarInfo>)intent.getSerializableExtra("Cal_info");
        path = intent.getStringExtra("Path");

        button_backup = findViewById(R.id.popup_bs_backup);
        button_restore = findViewById(R.id.popup_bs_restore);
        button_close = findViewById(R.id.popup_bs_close);
        textView_filename = findViewById(R.id.popup_bs_content);

        button_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        button_backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
        button_restore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    }

}