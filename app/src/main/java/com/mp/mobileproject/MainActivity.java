package com.mp.mobileproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.mp.mobileproject.BackupRestore.PopupBSActivity;
import com.mp.mobileproject.Calendar.CalendarInfo;
import com.mp.mobileproject.Calendar.CalendarPrivateActivity;
import com.mp.mobileproject.Calendar.CalendarPublicActivity;
import com.mp.mobileproject.Calendar.PopupActivity;

import java.io.File;
import java.lang.reflect.Member;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private User user;
    private ArrayList<CalendarInfo> calendarInfos;
    private String name, id, pw,Local_Path;
    private int alarm = 0;
    private final int RESULT_POPOUPBS = 1;

    private TextView nav_title, nav_sub;
    private Button button_BS, button_alarm;

    private File dir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        Local_Path = Environment.getExternalStorageDirectory().getAbsolutePath();
        dir = new File(Local_Path+"/MyCalendar/");
        Folder_Setting();

        final Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        id = intent.getStringExtra("ID");
        pw = intent.getStringExtra("PW");
        calendarInfos = (ArrayList<CalendarInfo>) intent.getSerializableExtra("Calinfo");

        user = new User(name, id, pw);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);

        nav_title = headerview.findViewById(R.id.nav_title_name);
        nav_sub = headerview.findViewById(R.id.nav_sub_name);
        nav_title.setText(user.getName()+"님 환영합니다.");
        nav_sub.setText(user.getID()+"로 로그인 되었습니다.");

        button_alarm = findViewById(R.id.MainActivity_AlarmCheck);
        button_BS = findViewById(R.id.MainActivity_BackupRestore);
        button_alarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarm = 0;
                button_alarm.setText("새로 등록된 일정 : 0");
            }
        });
        button_BS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bs_intent = new Intent(MainActivity.this, PopupBSActivity.class);
                bs_intent.putExtra("Cal_info",calendarInfos);
                bs_intent.putExtra("Path",Local_Path+"/MyCalendar/");
                startActivityForResult(bs_intent, RESULT_POPOUPBS);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final Intent intent_calendarpirvate = new Intent(MainActivity.this , CalendarPrivateActivity.class);
        final Intent intent_calendarpublic = new Intent(MainActivity.this, CalendarPublicActivity.class);
        int id = item.getItemId();
        if(id == R.id.nav_calendar_private){
            intent_calendarpirvate.putExtra("Name",user.getName());
            intent_calendarpirvate.putExtra("Cal_info",calendarInfos);
            intent_calendarpirvate.putExtra("User", user);
            Toast.makeText(getApplicationContext(),"private",Toast.LENGTH_SHORT).show();
            startActivity(intent_calendarpirvate);
        }
        else if(id == R.id.nav_calendar_public){
            intent_calendarpublic.putExtra("Name",user.getName());
            intent_calendarpublic.putExtra("Cal_info",calendarInfos);
            intent_calendarpublic.putExtra("User",user);
            Toast.makeText(getApplicationContext(),"public",Toast.LENGTH_SHORT).show();
            startActivity(intent_calendarpublic);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void Folder_Setting(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            //if storage permission isn't allow, popup to allow selecting
            System.out.println("Storage Permission is denied");
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else{
            System.out.println("Storage Permission is allowed");
        }
        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            //check existing about out storage
            System.out.println("Can use SD");
        }
        if(!dir.exists()){
            System.out.println("folder isn't exist");
            //make folder
            dir.mkdirs();
        }
        else{
            System.out.println("already exist");
        }
    }
}