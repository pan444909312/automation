package com.miller.service.framework.util;

public class SleepUtils {
    /**
     * @param interval 每次超时时间,单位为s
     * @param timeout 总超时时间，单位为s
     */
    public static void sleep(int interval,int timeout){
        if(interval <=0) return;
        while (true){
            interval = interval * 1000;
            timeout = timeout * 1000;
            try {
                Thread.sleep(interval);
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            timeout -= interval;
            if(timeout <= 0 ){
                break;
            }
        }

    }
}
