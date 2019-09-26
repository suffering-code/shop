package com.qf.config;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest {

    static Map<String,String> map = new HashMap<String, String>();

    public static void push(String key){
        map.put(key,key);
    }

    public static  String get(String key){
        return map.get(key);
    }


    public static void main(String[] args) {

        for(int i =0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    ThreadLocalTest.push(name);
                    System.out.println(name+":"+ThreadLocalTest.get(name));
                }
            },"name_"+i).start();
        }
    }
}
