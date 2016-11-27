package com.hss01248.netdemo;

import android.content.Context;

import com.hss01248.net.config.ConfigInfo;

import com.hss01248.net.interfaces.ILoginManager;

import com.hss01248.net.wrapper.MyNetApi;
import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
public class NetUtil {

    public static void init(Context context, String baseUrl,ILoginManager loginManager){
        MyNetApi.init(context,baseUrl,loginManager);

    }


    /**
     * 指定标准格式json的三个字段.比如聚合api的三个字段分别是error_code(但有的又是resultcode),reason,result,error_code
     * @param data
     * @param code
     * @param msg
     * @param codeSuccess
     * @param codeUnlogin
     * @param codeUnfound
     */
    public static void initAppDefault(String tokenName,String data,String code,String msg,int codeSuccess,int codeUnlogin,int codeUnfound){
       MyNetApi.initAppDefault(tokenName,data,code,msg,codeSuccess,codeUnlogin,codeUnfound);
    }


    public static ConfigInfo getString(String url, Map map, MyNetListener listener) {
        return MyNetApi.getString(url,map,listener);
    }

    public static ConfigInfo postString( String url,  Map map,  MyNetListener listener) {
        return  MyNetApi.postString(url,map,listener);
    }


    public static ConfigInfo postStandardJson(String url, Map map, Class clazz, MyNetListener listener) {
        return MyNetApi.postStandardJson(url,map,clazz,listener);
    }


    public static ConfigInfo getStandardJson(String url, Map map, Class clazz, MyNetListener listener) {
        return  MyNetApi.getStandardJson(url,map,clazz,listener);
    }


    public static ConfigInfo postCommonJson(String url, Map map, Class clazz, MyNetListener listener) {
        return  MyNetApi.postCommonJson(url,map,clazz,listener);
    }


    public static ConfigInfo getCommonJson(String url, Map map, Class clazz, MyNetListener listener) {
        return  MyNetApi.getCommonJson(url,map,clazz,listener);
    }


    public static ConfigInfo autoLogin() {
        return  MyNetApi.autoLogin();
    }

    public static ConfigInfo autoLogin(MyNetListener myNetListener) {
        return  MyNetApi.autoLogin(myNetListener);
    }

    public static boolean isLogin(){
        return MyNetApi.isLogin();
    }


    public static void cancleRequest(ConfigInfo info) {
        MyNetApi.cancleRequest(info);
    }


    public static ConfigInfo download(String url, String savedpath, MyNetListener callback) {
        return MyNetApi.download(url,savedpath,callback);
    }

    public static ConfigInfo upLoad(String url, Map<String,String> params,Map<String,String> files, MyNetListener callback){
        return   MyNetApi.upLoad(url,params,files,callback);
    }
}
