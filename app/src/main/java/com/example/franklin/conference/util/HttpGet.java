package com.example.franklin.conference.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.franklin.conference.App.MyApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpGet {

    public static String JsonGet(String path){
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");
            //Get请求不需要DoOutPut
            conn.setDoOutput(false);
            conn.setDoInput(true);
            //设置连接超时时间和读取超时时间
            conn.setConnectTimeout(10000);
            conn.setReadTimeout(10000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            //将token放入头部
            SharedPreferences pref =  MyApplication.getContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            String token = pref.getString("JWT","");
            conn.setRequestProperty("Authorization", "JWT "+token);
            //连接服务器
            conn.connect();
            // 取得输入流，并使用Reader读取
            if (conn.getResponseCode()/100 == 2)
                in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            else
                in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            //获得结果保存在result中
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //关闭输入流
        finally{
            try{
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
