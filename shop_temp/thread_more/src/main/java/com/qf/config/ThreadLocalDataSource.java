package com.qf.config;

/**
 * 用来ThreaLocal俩管理我们的数据源
 */
public class ThreadLocalDataSource {

    static  ThreadLocal<String> threadLocal = new ThreadLocal<String>();

    public static void add(String dataSoruce){
        threadLocal.set(dataSoruce);
    }

    public static  String get(){
        return threadLocal.get();
    }
}
