package com.mp.mobileproject.Calendar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mp.mobileproject.MaterialCalendarView.EventDecorator;
import com.mp.mobileproject.R;
import com.mp.mobileproject.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.Collections;

public class CalendarPublicActivity extends AppCompatActivity implements OnMonthChangedListener {

    private ListItem_RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private LocalDate localDate;
    private MaterialCalendarView materialCalendarView;
    private String name;
    private CalendarDay calendarDay;

    private ArrayList<CalendarInfo> calendarInfos;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_public);

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        calendarInfos = (ArrayList<CalendarInfo>)intent.getSerializableExtra("Cal_info");
        user = (User)intent.getSerializableExtra("User");

        recyclerView = (RecyclerView)findViewById(R.id.linearLayout_public);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        materialCalendarView = findViewById(R.id.calendarView_public);
        materialCalendarView.setSelectedDate(CalendarDay.today());

        for(int i=0;i<calendarInfos.size();i++){
            calendarDay = CalendarDay.from(calendarInfos.get(i).getLocalDate());
            materialCalendarView.addDecorator(new EventDecorator(Color.CYAN, Collections.singleton(calendarDay)));
        }

        materialCalendarView.setOnMonthChangedListener(this);
        Setting_Data();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(CalendarPublicActivity.this, AddCalendarActivity.class);

        int id = item.getItemId();
        if(id == R.id.action_add){
            localDate = materialCalendarView.getSelectedDate().getDate();
            System.out.println("local date month : " + localDate.getDayOfMonth());
            System.out.println("local date month value : " + localDate.getMonthValue());
            intent.putExtra("type","public");
            intent.putExtra("user",name);
            intent.putExtra("day",localDate.getDayOfMonth());
            intent.putExtra("month",localDate.getMonthValue());
            intent.putExtra("year",localDate.getYear());
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void Setting_Data(){
        recyclerAdapter = new ListItem_RecyclerAdapter();
        recyclerAdapter.setContext(this);
        System.out.println("setting adapter");

        for(int i=0;i<calendarInfos.size();i++){
            recyclerAdapter.addItem(new Listitem_calendar(calendarInfos.get(i).getName(),1));
        }

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(new CalendarPublicActivity.RecyclerTouchListener(getApplicationContext(), recyclerView, new CalendarPublicActivity.ClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Listitem_calendar listitem_calendar = (Listitem_calendar) recyclerAdapter.getItem(position);
                Intent popup_intent = new Intent(CalendarPublicActivity.this, PopupActivity.class);
                popup_intent.putExtra("Name",listitem_calendar.GetName());
                //popup_intent.putExtra("Content",listitem_calendar.G)
                startActivity(popup_intent);
                /*switch (setting_listitem.getType()){
                    case 1:
                        Intent ChangeUserData = new Intent(CalendarPrivateActivity.this, ChangeUserDataActivity.class);
                        startActivity(ChangeUserData);
                        break;
                    case 2:
                        Intent LoginSetting = new Intent(CalendarPrivateActivity.this, AccountActivity.class);
                        startActivityForResult(LoginSetting, Account_code);
                        Toast.makeText(getApplicationContext(), "아직 기능 구현 중 입니다.", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Intent Update_intent = new Intent(CalendarPrivateActivity.this, UpdateActivity.class);
                        startActivity(Update_intent);
                        break;
                }*/
            }

            @Override
            public void OnLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        Toast.makeText(getApplicationContext(),"Change month : "+date.getMonth(),Toast.LENGTH_SHORT).show();
    }

    public interface ClickListener{
        void OnClick(View view, int position);
        void OnLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private CalendarPublicActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CalendarPublicActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.OnLongClick(child, recyclerView.getChildAdapterPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
            View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
            if(child != null && clickListener != null && gestureDetector.onTouchEvent(motionEvent)){
                clickListener.OnClick(child, recyclerView.getChildAdapterPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean b) {

        }
    }
}