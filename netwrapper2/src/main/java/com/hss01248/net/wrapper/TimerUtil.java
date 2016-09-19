package com.hss01248.net.wrapper;

import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/9/3.
 */
public class TimerUtil {

    public static void doAfter(TimerTask task,long mills){
        Timer timer = new Timer();
        timer.schedule(task,mills);
    }


    public static void doAfterByHandler(Runnable task,long mills){
       new Handler().postDelayed(task,mills);
    }
}
