package com.example.madcamp1_2_2;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.example.madcamp1_2_2.Fragment3.converted_x;
import static com.example.madcamp1_2_2.Fragment3.converted_y;
import static com.example.madcamp1_2_2.Fragment3.pass_time;

public class Weather extends Thread{
    public void func() throws IOException, JSONException {
        Date date = new Date();
        SimpleDateFormat fm1 = new SimpleDateFormat("yyyyMMdd");

        String endPoint =  "http://apis.data.go.kr/1360000/VilageFcstInfoService/";
        String serviceKey = "uRxzKkBrZH1sg%2F%2FKM94ye5ng3DwmYY3M7VZcHM5aKBbEsyzUT7PxbTLTOC4LSecPQfufYsPsEn%2B8falkhuQHUg%3D%3D";
        String pageNo = "1";
        String numOfRows = "10";
        /**String baseDate = Fragment3.pass_date; //원하는 날짜
        String baseTime = Fragment3.pass_time; //원하는 시간
        String nx = Double.toString(Fragment3.converted_x); //위경도임.
        String ny = Double.toString(Fragment3.converted_y); //위경도 정보는 api문서 볼 것*/
        String baseDate = fm1.format(date); //원하는 날짜
        Log.d("weather", baseDate);
        String baseTime = pass_time; //원하는 시간
        Log.d("weather", baseTime);
        Log.d("weather2", String.valueOf((int) Math.round(converted_x)));

        String nx = Integer.toString((int) Math.round(converted_x)); //위경도임.
        Log.d("weather3", nx);
        String ny = Integer.toString((int) Math.round(converted_y)); //위경도 정보는 api문서 볼 것
        Log.d("pass_testtest2", nx + ' ' + ny);


        String s = endPoint+"getVilageFcst?serviceKey="+serviceKey
                +"&pageNo=" + pageNo
                +"&numOfRows=" + numOfRows
                +"+&dataType=JSON"
                + "&base_date=" + baseDate
                +"&base_time="+baseTime
                +"&nx="+nx
                +"&ny="+ny;

        Log.d("weather test", s);

        URL url = new URL(s);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader bufferedReader;
        if(conn.getResponseCode() == 200) {
            bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        }else{
            bufferedReader = null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        String result= stringBuilder.toString();
        conn.disconnect();

        JSONObject mainObject = new JSONObject(result);
        JSONArray itemArray = mainObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
        for(int i=0; i<itemArray.length(); i++){
            JSONObject item = itemArray.getJSONObject(i);
            String category = item.getString("category");
            String value = item.getString("fcstValue");
            System.out.println(category+"  "+value);

            if (category.equals("POP") && Integer.parseInt(value) >= 30) {
                Fragment3.is_rain = true;
                break;
            }
            else Fragment3.is_rain = true;
        }
    }
}