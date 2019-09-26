package com.qf.utils;


import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class HttpUitls {

    public static  void sendRequset(String urlStr){

        try {

            URL url = new URL(urlStr);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            int responseCode = httpURLConnection.getResponseCode();
            if(responseCode == 200){
                // 发送成功

                Scanner scanner = new Scanner(httpURLConnection.getInputStream());
                while(scanner.hasNextLine()){
                    System.out.println(scanner.nextLine());
                }
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        sendRequset("http://localhost:8083/item/createHtml?gid=20");
    }
}
