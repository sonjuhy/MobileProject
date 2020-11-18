package com.mp.mobileproject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Network extends AsyncTask<String, Void, String> {
    private HttpURLConnection httpURLConnection = null;
    private OutputStream outputStream = null;
    private String data = null;
    private String link = "https://sonjuhy.iptime.org/home";
    private String line = null;
    private String mJsonString = null;
    private int responseStatusCode = 0;
    private int mode = 0;
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
    protected String doInBackground(String... _param) {
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
}
