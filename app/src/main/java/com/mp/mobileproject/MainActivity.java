package com.mp.mobileproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.mp.mobileproject.Calendar.CalendarInfo;
import com.mp.mobileproject.Calendar.CalendarPrivateActivity;
import com.mp.mobileproject.Calendar.CalendarPublicActivity;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private User user;
    private ArrayList<CalendarInfo> calendarInfos;
    private String name, id, pw;

    private TextView nav_title, nav_sub;

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

        Intent intent = getIntent();
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
}