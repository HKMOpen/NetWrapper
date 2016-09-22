package com.hss01248.net.wrapper;

import android.content.Context;

import com.hss01248.net.config.BaseNetBean;
import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.NetDefaultConfig;
import com.hss01248.net.interfaces.ILoginManager;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
public class MyNetApi {

    public static Context context;
    public static BaseNet adapter;


    public static void init(Context context,BaseNet adapter,ILoginManager loginManager){
        MyNetApi.context = context;
        MyNetApi.adapter = adapter;
        if (loginManager instanceof  BaseNet){
            throw  new RuntimeException("please implement ILoginManager independently");
            //避免可能的无限循环调用
        }
        MyNetApi.adapter.setLoginManager(loginManager);

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
    public static void setStandardJsonKey(String data,String code,String msg,int codeSuccess,int codeUnlogin,int codeUnfound){
        NetDefaultConfig.KEY_DATA = data;
        NetDefaultConfig.KEY_CODE = code;
        NetDefaultConfig.KEY_MSG = msg;
        BaseNetBean.CODE_SUCCESS = codeSuccess;
        BaseNetBean.CODE_UNLOGIN = codeUnlogin;
        BaseNetBean.CODE_UN_FOUND = codeUnfound;
    }


    public static ConfigInfo getString(String url, Map map, MyNetListener listener) {
       return adapter.getString(url,map,listener);
    }

    public static ConfigInfo postString( String url,  Map map,  MyNetListener listener) {
        return  adapter.postString(url,map,listener);
    }


    public static ConfigInfo postStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        return adapter.postStandardJsonResonse(url,map,clazz,listener);
    }


    public static ConfigInfo getStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        return  adapter.getStandardJsonResonse(url,map,clazz,listener);
    }


    public static ConfigInfo postCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        return  adapter.postCommonJsonResonse(url,map,clazz,listener);
    }


    public static ConfigInfo getCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener) {
        return  adapter.getCommonJsonResonse(url,map,clazz,listener);
    }


    public static ConfigInfo autoLogin() {
        return  adapter.autoLogin();
    }

    public static ConfigInfo autoLogin(MyNetListener myNetListener) {
        return  adapter.autoLogin(myNetListener);
    }

    public static boolean isLogin(){
        return adapter.isLogin();
    }


    public static void cancleRequest(ConfigInfo info) {
        adapter.cancleRequest(info);
    }


    public static ConfigInfo download(String url, String savedpath, MyNetListener callback) {
       return adapter.download(url,savedpath,callback);
    }

    public static ConfigInfo upLoad(String url, Map<String,String> params,Map<String,String> files, MyNetListener callback){
      return   adapter.upLoad(url,params,files,callback);
    }
}
