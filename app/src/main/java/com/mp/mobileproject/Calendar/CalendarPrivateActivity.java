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
import android.widget.Toast;

import com.mp.mobileproject.MaterialCalendarView.EventDecorator;
import com.mp.mobileproject.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import org.threeten.bp.LocalDate;

import java.util.Collections;
import java.util.Date;

public class CalendarPrivateActivity extends AppCompatActivity {

    private ListItem_RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private MaterialCalendarView materialCalendarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_private);

        CalendarDay calendarDay = CalendarDay.from(LocalDate.of(2020,11,20));

        recyclerView = (RecyclerView)findViewById(R.id.linearLayout_private);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        materialCalendarView = findViewById(R.id.calendarView_private);
        materialCalendarView.setSelectedDate(CalendarDay.today());
        materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(calendarDay)));


        Setting_Data();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add){

            Toast.makeText(getApplicationContext(),"test",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void Setting_Data(){
        recyclerAdapter = new ListItem_RecyclerAdapter();
        recyclerAdapter.setContext(this);
        System.out.println("setting adapter");

        recyclerAdapter.addItem(new Listitem_calendar("계정설정", 0));
        recyclerAdapter.addItem(new Listitem_calendar("로그인설정", 0));
        recyclerAdapter.addItem(new Listitem_calendar("업데이트 확인", 0));
        recyclerAdapter.addItem(new Listitem_calendar("fourth data", 0));
        recyclerAdapter.addItem(new Listitem_calendar("fiveth data", 0));

        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new CalendarPrivateActivity.ClickListener() {
            @Override
            public void OnClick(View view, int position) {
                Listitem_calendar listitem_calendar = (Listitem_calendar) recyclerAdapter.getItem(position);
                Intent popup_intent = new Intent(CalendarPrivateActivity.this, PopupActivity.class);
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
    public interface ClickListener{
        void OnClick(View view, int position);
        void OnLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {
        private GestureDetector gestureDetector;
        private CalendarPrivateActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final CalendarPrivateActivity.ClickListener clickListener) {
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