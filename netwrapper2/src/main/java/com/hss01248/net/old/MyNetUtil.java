package com.hss01248.net.old;

import android.content.Context;

import com.hss01248.net.wrapper.MyNetListener;

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


    public static void getString( String url,  Map map, Class clazz, MyNetListener listener) {
       adapter.getString(url,map,clazz,listener);
    }

    public static void postString( String url,  Map map, Class clazz, MyNetListener listener) {
        adapter.postString(url,map,clazz,listener);
    }


    public static void postStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        adapter.postStandardJsonResonse(url,map,clazz,listener);
    }


    public static void getStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        adapter.getStandardJsonResonse(url,map,clazz,listener);
    }


    public static void postCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        adapter.postCommonJsonResonse(url,map,clazz,listener);
    }


    public static void getCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        adapter.getCommonJsonResonse(url,map,clazz,listener);
    }


    public static void autoLogin() {
        adapter.autoLogin();
    }


    public static void autoLogin(MyNetListener myNetListener) {
        adapter.autoLogin(myNetListener);
    }


    public static void cancleRequest(Object clazz) {
        adapter.cancleRequest(clazz);
    }


    public static void download(String url, String savedpath, MyNetListener callback) {
        adapter.download(url,savedpath,callback);
    }

    public static void upLoad(String url, Map<String,String> params,Map<String,String> files, MyNetListener callback){
        adapter.upLoad(url,params,files,callback);
    }
}
