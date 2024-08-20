package com.miller.service.framework.util;

public class SleepUtils {
    public static void sleep(int timeout){
        try {
            Thread.sleep(timeout);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

}
