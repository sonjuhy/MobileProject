package com.mp.mobileproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class SigninActivity extends AppCompatActivity {

    private EditText Name, ID, PW;
    private Button ID_Check, Name_Check, Cancle, Signin;
    private String Name_str, ID_str, PW_str;
    private Network network;

    private boolean overlapID_check = false;
    private boolean overlapName_check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        Name = findViewById(R.id.Signin_Edittext_Name);
        ID = findViewById(R.id.Signin_Edittext_ID);
        PW = findViewById(R.id.Signin_Edittext_PW);
        ID_Check = findViewById(R.id.Signin_button_IDCheck);
        Name_Check = findViewById(R.id.Signin_button_Namecheck);
        Cancle = findViewById(R.id.Signin_button_cancel);
        Signin = findViewById(R.id.Signin_button_ok);

        Name_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_str = Name.getText().toString();
                if(Name_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"이름을 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    OverlapNameCheck_fun(Name_str);
                    if(overlapName_check){
                        Name_Check.setText("확인완료");
                    }
                    else{
                        Name_Check.setText("이름 체크");
                        Toast.makeText(getApplicationContext(),"이미 존재하는 이름입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        ID_Check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ID_str = ID.getText().toString();
                if(ID_str.isEmpty()){
                    Toast.makeText(getApplicationContext(),"ID를 입력하세요.",Toast.LENGTH_SHORT).show();
                }
                else {
                    OverlapIDCheck_fun(ID_str);
                    if(overlapID_check){
                        ID_Check.setText("확인완료");
                    }
                    else{
                        ID_Check.setText("ID 체크");
                        Toast.makeText(getApplicationContext(),"이미 존재하는 아이디입니다.",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name_str = Name.getText().toString();
                ID_str = ID.getText().toString();
                PW_str = PW.getText().toString();
                if(!Name_str.isEmpty() && !ID_str.isEmpty() && !PW_str.isEmpty()){
                    SignUp_fun(ID_str,PW_str,Name_str);
                }
                else if(!overlapID_check){
                    Toast.makeText(getApplicationContext(),"ID 중복체크를 하세요.",Toast.LENGTH_SHORT).show();
                }
                else if(!overlapName_check){
                    Toast.makeText(getApplicationContext(),"이름 중복체크를 하세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(getApplicationContext(),"정보를 모두 입력하세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void SignUp_fun(String... _param){
        Network network = new Network(this);
        network.execute("SignUp", _param[0],_param[1],_param[2]);
        Toast.makeText(SigninActivity.this,"Sign in Success",Toast.LENGTH_SHORT).show();
    }
    private void OverlapIDCheck_fun(String ID){
        String result = null;
        Network network = new Network(this);
        try {
            result = network.execute("SignUp_IDCheck",ID).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!result.isEmpty()){
            if(result.equals("Success")){
                overlapID_check = true;
            }
            else{
                overlapID_check = false;
            }
        }
    }
    private void OverlapNameCheck_fun(String Name){
        String result = null;
        Network network = new Network(this);
        try {
            result = network.execute("SignUp_UserCheck",Name).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(!result.isEmpty()){
            if(result.equals("Success")){
                overlapName_check = true;
            }
            else{
                overlapName_check = false;
            }
        }
    }
}