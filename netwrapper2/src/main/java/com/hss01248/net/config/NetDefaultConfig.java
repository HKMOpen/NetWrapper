package com.hss01248.net.config;

import android.content.Context;

/**
 *
 * 注意各參數與框架默認參數盡量保持一致
 * Created by Administrator on 2016/8/30.
 */
public class NetDefaultConfig {

    public static String baseUrl = "http://www.qxinli.com:9001/";
    public static int TIME_OUT = 15000;//单位为ms,默认15s

    public static long CACHE_TIME = 0;//单位为s,默认无缓存
    public static int RETRY_TIME = 0;//重试次数,默认两次

    public static int TIME_MINI = 1500;//网络回调至少几秒后返回,默认1500ms.--主要是用于避免dialog还没有弹出就回调成功,activity结束导致token异常

    public static final String TOKEN = "session_id";//todo


    public  static  String KEY_DATA = "data";
    public static  String KEY_CODE = "code";
    public static  String KEY_MSG = "msg";


    /**
     * copy自volley
     */
    public interface Method {
        int DEPRECATED_GET_OR_POST = -1;
        int GET = 0;
        int POST = 1;
        int PUT = 2;
        int DELETE = 3;
        int HEAD = 4;
        int OPTIONS = 5;
        int TRACE = 6;
        int PATCH = 7;
    }




    public static void init(Context context,String baseUrl){
        NetDefaultConfig.baseUrl = baseUrl;
    }

    //todo
    public static String getToken(){
        return "";
    }
}
