package com.hss01248.net.wrapper;

import android.content.Context;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MyNetUtil {

   public static Context context;
    public static Netable adapter;

    public static void init(Context context,Netable adapter){
        MyNetUtil.context = context;
        MyNetUtil.adapter = adapter;
    }


    public static void getString( String url,  Map map, String tag, MyNetListener listener) {
       adapter.getString(url,map,tag,listener);
    }


    public static void postStandardJsonResonse( String url,  Map map, String tag, MyNetListener listener) {
        adapter.postStandardJsonResonse(url,map,tag,listener);
    }


    public static void getStandardJsonResonse( String url,  Map map, String tag, MyNetListener listener) {
        adapter.getStandardJsonResonse(url,map,tag,listener);
    }


    public static void postCommonJsonResonse( String url,  Map map, String tag, MyNetListener listener) {
        adapter.postCommonJsonResonse(url,map,tag,listener);
    }


    public static void getCommonJsonResonse( String url,  Map map, String tag, MyNetListener listener) {
        adapter.getCommonJsonResonse(url,map,tag,listener);
    }


    public static void autoLogin() {
        adapter.autoLogin();
    }


    public static void autoLogin(MyNetListener myNetListener) {
        adapter.autoLogin(myNetListener);
    }


    public static void cancleRequest(Object tag) {
        adapter.cancleRequest(tag);
    }


    public static void download(String url, String savedpath, MyNetListener callback) {
        adapter.download(url,savedpath,callback);
    }
}
