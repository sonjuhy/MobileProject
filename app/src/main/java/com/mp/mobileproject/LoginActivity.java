package com.mp.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.mp.mobileproject.MainActivity.version;

public class LoginActivity extends AppCompatActivity {

    public static boolean Login_check = false;
    public static boolean Async_check = false;
    public static boolean Login_load = false;
    public static String name = null;

    private int MainActivity_code = 100;
    private int exit_code = -1;

    private TextView VersionView;
    private Button Login,Signin;
    private CheckBox AutoLogin_CheckBox;
    private EditText ID, PW;
    private String fnumber;
    private String Frist_ID = "Admin", Frist_PW ="0000",Save_ID = "", Save_PW = "";
    private String auto_id, auto_pw;
    private boolean AutoLoginCheck = false;
    public static SharedPreferences auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        final Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        final Network network = null;

        Login = (Button)findViewById(R.id.Login);
        Signin =(Button)findViewById(R.id.Signin);

        ID = (EditText)findViewById(R.id.ID_edit);
        PW = (EditText)findViewById(R.id.PW_edit);

        VersionView = (TextView)findViewById(R.id.versionTextView);
        VersionView.setText("Ver "+version);

        AutoLogin_CheckBox = findViewById(R.id.AutoLogin_checkBox);

        auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
        auto_id = auto.getString("inputID",null);
        auto_pw = auto.getString("inputPW",null);
        AutoLoginCheck = auto.getBoolean("AutoLoginCheck", false);

        if(auto_id != null && auto_pw != null && AutoLoginCheck){
            boolean Login_check;
            Login_check = AutoLogin_fun(auto_id,auto_pw);
            if(Login_check){
                intent.putExtra("Name", name);
                intent.putExtra("ID",auto_id);
                intent.putExtra("PW",auto_pw);
                intent.putExtra("fnumber",fnumber);
                Toast.makeText(getApplicationContext(),auto_id+" 아이디로 자동로그인 되었습니다",Toast.LENGTH_LONG).show();
                startActivityForResult(intent,MainActivity_code);
            }
            else{
                SharedPreferences.Editor autoLogindelete = auto.edit();
                autoLogindelete.clear();
                autoLogindelete.commit();
                Toast.makeText(getApplicationContext(),"계정 정보가 틀립니다.",Toast.LENGTH_LONG).show();
            }
        }
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(intent,MainActivity_code);
               /* String tmp_ID = ID.getText().toString(), tmp_PW = PW.getText().toString();
                Login_fun(network, tmp_ID, tmp_PW);
                if(Login_check){
                    intent.putExtra("Name", name);
                    intent.putExtra("ID",tmp_ID);
                    intent.putExtra("PW",tmp_PW);
                    intent.putExtra("fnumber",fnumber);
                    if(AutoLogin_CheckBox.isChecked()) {
                        auto = getSharedPreferences("AutoLogin", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor autoLogin = auto.edit();
                        autoLogin.putString("inputID", tmp_ID);
                        autoLogin.putString("inputPW", tmp_PW);
                        autoLogin.putBoolean("AutoLoginCheck", true);
                        autoLogin.commit();
                    }
                    //Get_Cal_fun(network, User.Name_Output());
                    Toast.makeText(LoginActivity.this , "로그인성공.",Toast.LENGTH_LONG).show();
                    startActivityForResult(intent,MainActivity_code);
                }
                else{
                    Toast.makeText(LoginActivity.this , "로그인실패.",Toast.LENGTH_LONG).show();
                    AutoLogin_CheckBox.setChecked(false);
                }*/

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
            result = network.execute("login",ID, PW).get();
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
                fnumber = jsontmp.getString("fnumber");
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
    private void Login_fun(Network network, String Input_ID, String Input_PW){
        String result = null;
        String[] tmp_result = null;
        network = new Network(this);
        try {
            result = network.execute("login",Input_ID, Input_PW).get();
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
                    fnumber = jsontmp.getString("fnumber");
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
}