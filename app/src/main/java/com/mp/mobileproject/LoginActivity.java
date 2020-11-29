package com.mp.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mp.mobileproject.Calendar.CalendarInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    public static boolean Login_check = false;
    public static String name = null;

    private int MainActivity_code = 100;
    private int exit_code = -1;

    private ArrayList<CalendarInfo> calendarInfo;
    private ArrayList<CalendarInfo> tmp_cal;
    private Button Login,Signin;
    private CheckBox AutoLogin_CheckBox;
    private EditText ID, PW;
    private String Frist_ID = "Admin", Frist_PW ="0000",Save_ID = "", Save_PW = "";
    private String auto_id, auto_pw;
    private boolean AutoLoginCheck = false;
    public static SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        calendarInfo = new ArrayList<>();

        Login = (Button)findViewById(R.id.Login);
        Signin =(Button)findViewById(R.id.Signin);

        ID = (EditText)findViewById(R.id.ID_edit);
        PW = (EditText)findViewById(R.id.PW_edit);

        AutoLogin_CheckBox = findViewById(R.id.AutoLogin_checkBox);

        auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
        auto_id = auto.getString("inputID",null);
        auto_pw = auto.getString("inputPW",null);
        AutoLoginCheck = auto.getBoolean("AutoLoginCheck", false);

        if(auto_id != null && auto_pw != null && AutoLoginCheck){
            boolean Login_check;
            Login_check = AutoLogin_fun(auto_id,auto_pw);
            if(Login_check){
                GetCal_fun(name);
                intent.putExtra("Name", name);
                intent.putExtra("ID",auto_id);
                intent.putExtra("PW",auto_pw);
                intent.putExtra("Calinfo",calendarInfo);
                Toast.makeText(getApplicationContext(),auto_id+" 아이디로 자동로그인 되었습니다",Toast.LENGTH_SHORT).show();
                startActivityForResult(intent,MainActivity_code);
            }
            else{
                SharedPreferences.Editor autoLogindelete = auto.edit();
                autoLogindelete.clear();
                autoLogindelete.commit();
                Toast.makeText(getApplicationContext(),"계정 정보가 틀립니다.",Toast.LENGTH_SHORT).show();
            }
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tmp_ID = ID.getText().toString(), tmp_PW = PW.getText().toString();
                Login_fun(tmp_ID, tmp_PW);
                if(Login_check){
                    GetCal_fun(name);
                    intent.putExtra("caltest",tmp_cal);
                    intent.putExtra("Name", name);
                    intent.putExtra("ID",tmp_ID);
                    intent.putExtra("PW",tmp_PW);
                    intent.putExtra("Calinfo",calendarInfo);
                    if(AutoLogin_CheckBox.isChecked()) {
                        auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputID", tmp_ID);
                        autoLogin.putString("inputPW", tmp_PW);
                        autoLogin.putBoolean("AutoLoginCheck", true);
                        autoLogin.commit();
                    }
                    System.out.println("cal info lenght : " + calendarInfo.size());

                    for(int i =0; i<calendarInfo.size();i++){
                        System.out.println("Login sub data : "+calendarInfo.get(i).getName());
                        Log.e("sub data : ",calendarInfo.get(i).getName());
                    }
                    Toast.makeText(LoginActivity.this , "로그인성공.",Toast.LENGTH_SHORT).show();
                    startActivityForResult(intent,MainActivity_code);
                }
                else{
                    Toast.makeText(LoginActivity.this , "로그인실패.",Toast.LENGTH_SHORT).show();
                    AutoLogin_CheckBox.setChecked(false);
                }

            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_sign = new Intent(LoginActivity.this, SigninActivity.class);
                startActivity(intent_sign);
            }
        });
    }

    private boolean AutoLogin_fun(String ID, String PW){
        String result = null;
        String[] tmp_result = null;
        System.out.println("Auto login id : " + ID + " PW : " + PW);
        Network network = new Network(this);
        try {
            result = network.execute("Login",ID, PW).get();
            tmp_result = result.split("/");
            if(tmp_result.length > 1) {
                result = tmp_result[1];
            }
            JSONObject jsonObject = null;
            String check;
            jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("Login");
            for(int i=0;i<jsonArray.length();i++){
                JSONObject jsontmp = jsonArray.getJSONObject(i);
                check = jsontmp.getString("check");
                if(check.equals("Failed")){
                    Login_check = false;
                    break;
                }
                name = jsontmp.getString("name");
                Login_check = true;
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(Login_check == false){
            return false;
        }
        else{
            return true;
        }
    }
    private void Login_fun(String Input_ID, String Input_PW){
        String result = null;
        String[] tmp_result = null;
        Network network = new Network(this);
        try {
            result = network.execute("Login",Input_ID, Input_PW).get();
            tmp_result = result.split("/");
            if(tmp_result.length > 1) {
                result = tmp_result[1];
            }
            JSONObject jsonObject = null;
            String check;
            try {
                jsonObject = new JSONObject(result);

                JSONArray jsonArray = jsonObject.getJSONArray("Login");
                for(int i=0;i<jsonArray.length();i++){
                    JSONObject jsontmp = jsonArray.getJSONObject(i);
                    check = jsontmp.getString("check");
                    if(check.equals("Failed")){
                        Login_check = false;
                        break;
                    }
                    name = jsontmp.getString("name");
                    Login_check = true;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Login result : " + result);
    }
    private void GetCal_fun(String Name){
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
                        Login_check = false;
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

                    calendarInfo.add(new CalendarInfo(tmp_name, tmp_Content, tmp_Type, localDate, tmp_count, tmp_Member));
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
}