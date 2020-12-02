package com.mp.mobileproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

public class Network extends AsyncTask<String, Void, String> {
    private HttpURLConnection httpURLConnection = null;
    private OutputStream outputStream = null;
    private String data = null;
    private String link = "https://sonjuhy.iptime.org/Calendar";
    private String line = null;
    private String mJsonString = null;
    private int responseStatusCode = 0;
    private boolean upload_mode = false;

    private Context context;
    private InputStream inputStream = null;
    private InputStreamReader inputStreamReader = null;
    private BufferedReader bufferedReader = null;
    private StringBuilder stringBuilder = null;
    private URL url = null;

    private ProgressDialog progressDialog;

    public Network(Context context){this.context = context;}

    @Override
    protected void onPreExecute() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("로딩 중입니다.");
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... _param) {
        switch(_param[0]){
            case "Login":
                link += "/Login_Check.php";
                Login_Input(_param[1],_param[2]);
                upload_mode = true;
                break;
            case "SignUp":
                link += "/SignUp.php";
                Signin_Input(_param[1], _param[2], _param[3]);//ID, PW, Name
                upload_mode = true;
                break;
            case "SignUp_IDCheck":
                link += "/SignUP_IDCheck.php";
                OverlapID_Input(_param[1]);
                upload_mode = true;
                break;
            case "SignUp_UserCheck":
                link += "/SignUP_UserCheck.php";
                OverlapName_Input(_param[1]);
                upload_mode = true;
                break;
            case "Add_Calendar":
                link += "/Add_Calendar.php";
                AddCal_fun(_param[1],_param[2], _param[3], _param[4], _param[5], _param[6], _param[7]);
                //name, content, year, month, day, user, type
                upload_mode = true;
                break;
            case "Get_Calendar":
                link += "/Get_Calendar.php";
                GetCal_fun(_param[1]);//name
                upload_mode = true;
                break;
            case "Update_Calendar":
                link += "/Update_Calendar.php";
                Update_Calendar(_param[1],_param[2],_param[3]);//name content count
                upload_mode = true;
                break;
            case "Delete_Calendar":
                link += "/Delete_Calendar.php";

                Delete_Calendar(_param[1]);//count
                upload_mode = true;
                break;
        }
        try{
            url = new URL(link);
            httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setReadTimeout(15000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoInput(true);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();

            if(upload_mode) {
                outputStream = httpURLConnection.getOutputStream();
                outputStream.write(data.getBytes("UTF-8"));
                outputStream.flush();
                outputStream.close();
            }

            responseStatusCode = httpURLConnection.getResponseCode();

            if(responseStatusCode == HttpURLConnection.HTTP_OK){
                inputStream = httpURLConnection.getInputStream();
            }
            else{
                inputStream = httpURLConnection.getErrorStream();
            }

            inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            bufferedReader = new BufferedReader(inputStreamReader);

            stringBuilder = new StringBuilder();

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line);
            }

            httpURLConnection.disconnect();

            String test = stringBuilder.toString().trim();
            System.out.println("trim : " + test);
            return stringBuilder.toString().trim();

        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    @Override
    protected void onPostExecute(String s) {
        progressDialog.dismiss();
        super.onPostExecute(s);
    }

    private void Login_Input(String user_id, String user_pw){
        try {
            data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
            data += "&" + URLEncoder.encode("PW", "UTF-8") + "=" + URLEncoder.encode(user_pw, "UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void Signin_Input(String user_id, String user_pw, String user_name){
        try {
            data = URLEncoder.encode("ID", "UTF-8") + "=" + URLEncoder.encode(user_id, "UTF-8");
            data += "&" + URLEncoder.encode("Name","UTF-8")+"="+ URLEncoder.encode(user_name,"UTF-8");
            data += "&" + URLEncoder.encode("PW", "UTF-8") + "=" + URLEncoder.encode(user_pw, "UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void OverlapID_Input(String ID){
        try {
            data = URLEncoder.encode("ID","UTF-8")+"="+URLEncoder.encode(ID,"UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void OverlapName_Input(String Name){
        try {
            data = URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(Name,"UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void AddCal_fun(String name, String content, String year, String month, String day, String user, String type){
        try {
            data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("Content", "UTF-8") + "=" + URLEncoder.encode(content, "UTF-8");
            data += "&" + URLEncoder.encode("Member", "UTF-8") + "=" + URLEncoder.encode(user, "UTF-8");
            data += "&" + URLEncoder.encode("Type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8");
            data += "&" + URLEncoder.encode("Year", "UTF-8") + "=" + URLEncoder.encode(year, "UTF-8");
            data += "&" + URLEncoder.encode("Month", "UTF-8") + "=" + URLEncoder.encode(month, "UTF-8");
            data += "&" + URLEncoder.encode("Day", "UTF-8") + "=" + URLEncoder.encode(day, "UTF-8");
            System.out.println("add data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void GetCal_fun(String Name){
        try {
            data = URLEncoder.encode("Name","UTF-8")+"="+URLEncoder.encode(Name,"UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void Update_Calendar(String name, String Content, String count){
        try {
            data = URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8");
            data += "&" + URLEncoder.encode("Content", "UTF-8") + "=" + URLEncoder.encode(Content, "UTF-8");
            data += "&" + URLEncoder.encode("Count", "UTF-8") + "=" + URLEncoder.encode(count, "UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    private void Delete_Calendar(String count){
        System.out.println("Network delete param : " + count);
        try {
            data = URLEncoder.encode("Count", "UTF-8") + "=" + URLEncoder.encode(count, "UTF-8");
            System.out.println("Sign data : " + data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
