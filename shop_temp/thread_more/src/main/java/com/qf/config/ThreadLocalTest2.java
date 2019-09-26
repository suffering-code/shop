package com.qf.config;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalTest2 {

    static  ThreadLocal<String> threadLocal = new ThreadLocal<String>(); // 容器

    public static void push(String key){
        threadLocal.set(key);
    }

    public static  String get(){
        return threadLocal.get();
    }


    public static void main(String[] args) {

        for(int i =0;i<10;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    ThreadLocalTest2.push(name);
                    System.out.println(name+":"+ ThreadLocalTest2.get());
                }
            },"name_"+i).start();
        }
    }
}
