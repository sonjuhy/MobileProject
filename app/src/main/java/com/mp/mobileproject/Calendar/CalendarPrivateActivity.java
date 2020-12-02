package com.mp.mobileproject.Calendar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.mp.mobileproject.Network;
import com.mp.mobileproject.R;
import com.mp.mobileproject.User;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class CalendarPrivateActivity extends AppCompatActivity implements OnMonthChangedListener {

    private ListItem_RecyclerAdapter recyclerAdapter;
    private RecyclerView recyclerView;
    private CalendarDay calendarDay;

    private MaterialCalendarView materialCalendarView;
    private LocalDate localDate;
    private String name;
    private ArrayList<CalendarInfo> calendarInfos = new ArrayList<>();
    private User user;

    private int month;
    private final int RESULT_POPUP = 1;
    private final int RESULT_ADD = 2;
    private final int RESULT_MODIFY = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_private);
        setTitle("개인 일정");

        Intent intent = getIntent();
        name = intent.getStringExtra("Name");
        //calendarInfos = (ArrayList<CalendarInfo>)intent.getSerializableExtra("Cal_info");
        user = (User)intent.getSerializableExtra("User");

        recyclerView = (RecyclerView)findViewById(R.id.linearLayout_private);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        materialCalendarView = findViewById(R.id.calendarView_private);
        materialCalendarView.setSelectedDate(CalendarDay.today());

        materialCalendarView.setOnMonthChangedListener(this);
        Now_Month();
        Setting_Data(true);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new CalendarPrivateActivity.ClickListener() {
            @Override
            public void OnClick(View view, int position) {
                System.out.println("Click on priavte list");
                Listitem_calendar listitem_calendar = (Listitem_calendar) recyclerAdapter.getItem(position);
                Intent popup_intent = new Intent(CalendarPrivateActivity.this, PopupActivity.class);
                popup_intent.putExtra("Name",listitem_calendar.GetName());
                popup_intent.putExtra("Count",listitem_calendar.getCount());
                popup_intent.putExtra("Cal_info",calendarInfos);
                System.out.println("this plan count is : " + listitem_calendar.getCount());
                startActivityForResult(popup_intent,RESULT_POPUP);
            }

            @Override
            public void OnLongClick(View view, int position) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.calendar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent = new Intent(CalendarPrivateActivity.this, AddCalendarActivity.class);

        int id = item.getItemId();
        if(id == R.id.action_add){
            localDate = materialCalendarView.getSelectedDate().getDate();
            System.out.println("local date month : " + localDate.getDayOfMonth());
            System.out.println("local date month value : " + localDate.getMonthValue());
            intent.putExtra("type","private");
            intent.putExtra("user",name);
            intent.putExtra("day",localDate.getDayOfMonth());
            intent.putExtra("month",localDate.getMonthValue());
            intent.putExtra("year",localDate.getYear());
            startActivityForResult(intent,RESULT_ADD);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Network network;
        if(requestCode == RESULT_POPUP){
            switch (resultCode){
                case 1://modify
                    for(int i=0; i<calendarInfos.size();i++){
                        if(calendarInfos.get(i).getCount() == data.getIntExtra("Count",0)){
                            month = calendarInfos.get(i).getLocalDate().getMonthValue();
                            calendarInfos.get(i).setName(data.getStringExtra("Name"));
                            calendarInfos.get(i).setContent(data.getStringExtra("Content"));
                            network = new Network(this);
                            network.execute("Update_Calendar",data.getStringExtra("Name"),data.getStringExtra("Content"),Integer.toString(data.getIntExtra("Count",0)));
                            Toast.makeText(getApplicationContext(),"일정을 수정했습니다.",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    Setting_Data(false);
                    break;
                case 2://delete
                    for(int i=0; i<calendarInfos.size();i++){
                        if(calendarInfos.get(i).getCount() == data.getIntExtra("Count",0)){
                            System.out.println("Delete count : " + data.getIntExtra("Count",0));
                            month = calendarInfos.get(i).getLocalDate().getMonthValue();
                            calendarInfos.remove(i);
                            network = new Network(this);
                            network.execute("Delete_Calendar",Integer.toString(data.getIntExtra("Count",0)));
                            Toast.makeText(getApplicationContext(),"일정을 삭제했습니다.",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                    Setting_Data(false);
                    break;
                case 3:
                    break;
            }
        }
        else if(requestCode == RESULT_ADD){
            switch (resultCode){
                case 1:
                    network = new Network(this);
                    //name, content, year, month, day, user, type
                    network.execute("Add_Calendar",   data.getStringExtra("Name"),data.getStringExtra("Content"),data.getStringExtra("Year"),
                            data.getStringExtra("Month"), data.getStringExtra("Day"),
                            data.getStringExtra("User"), data.getStringExtra("Type"));
                    Toast.makeText(getApplicationContext(),"일정이 등록되었습니다.",Toast.LENGTH_SHORT).show();
                    Setting_Data(true);
                    break;
            }

        }
    }

    private void Now_Month(){
        Date time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("MM");
        String month_time = format.format(time);
        System.out.println("cal public now month : " + month_time);
        month = Integer.valueOf(month_time);
    }
    private void GetCal_fun(String Name){
        calendarInfos = new ArrayList<>();
        String result = null, tmp_Content,tmp_Member, tmp_Type, tmp_name;
        int tmp_count,tmp_Year,tmp_Month,tmp_Day;
        LocalDate localDate = null;
        Network network = new Network(this);
        try {
            result = network.execute("Get_Calendar",Name).get();
            JSONObject jsonObject = null;
            String check;
            try {
                jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("Get_Cal");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsontmp = jsonArray.getJSONObject(i);
                    check = jsontmp.getString("check");
                    if(check.equals("Failed")){
                        break;
                    }
                    tmp_name = jsontmp.getString("Name");
                    tmp_Content = jsontmp.getString("Content");
                    tmp_Member = jsontmp.getString("Member");
                    tmp_Type = jsontmp.getString("Type");
                    tmp_count = jsontmp.getInt("Count");
                    tmp_Year = jsontmp.getInt("Year");
                    tmp_Month = jsontmp.getInt("Month");
                    tmp_Day = jsontmp.getInt("Day");

                    localDate = LocalDate.of(tmp_Year,tmp_Month,tmp_Day);

                    calendarInfos.add(new CalendarInfo(tmp_name, tmp_Content, tmp_Type, localDate, tmp_count, tmp_Member));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("GetCal result : " + result);
    }
    private void Setting_Data(boolean mode){
        recyclerAdapter = new ListItem_RecyclerAdapter();
        recyclerAdapter.setContext(this);

        for(int i=0;i<calendarInfos.size();i++){
            if(calendarInfos.get(i).getMember().equals(name)){
                calendarDay = CalendarDay.from(calendarInfos.get(i).getLocalDate());
                if(calendarInfos.get(i).getType().equals("public")){
                    materialCalendarView.addDecorator(new EventDecorator(Color.CYAN, Collections.singleton(calendarDay)));
                }
                else {
                    materialCalendarView.addDecorator(new EventDecorator(Color.RED, Collections.singleton(calendarDay)));
                }

                if(calendarInfos.get(i).getLocalDate().getMonthValue() == month) {
                    if (calendarInfos.get(i).getType().equals("public")) {
                        recyclerAdapter.addItem(new Listitem_calendar(calendarInfos.get(i).getName(), 1, calendarInfos.get(i).getCount()));
                    }
                    else {
                        recyclerAdapter.addItem(new Listitem_calendar(calendarInfos.get(i).getName(), 0, calendarInfos.get(i).getCount()));
                    }
                }

            }
        }
        recyclerView.setAdapter(recyclerAdapter);

        if(mode) {
            calendarInfos = null;
            GetCal_fun(user.getName());
            Setting_Data(false);
        }
    }

    @Override
    public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
        month = date.getMonth();
        Setting_Data(false);
    }

    @Override
    public void onBackPressed() {
        System.out.println("back press");
        finish();
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
