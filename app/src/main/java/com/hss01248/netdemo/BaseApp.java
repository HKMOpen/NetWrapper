package com.hss01248.netdemo;

import android.app.Application;

/**
 * Created by Administrator on 2016/9/4.
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
       // MyRetrofitUtil.init(getApplicationContext());

    }


}
