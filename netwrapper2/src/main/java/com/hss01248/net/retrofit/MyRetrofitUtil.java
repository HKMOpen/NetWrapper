package com.hss01248.net.retrofit;

import android.content.Context;

import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

import retrofit2.Call;


/**
 * Created by Administrator on 2016/8/30.
 */
public class MyRetrofitUtil  {

    public static Context context;


    /**
     * 初始化
     * @param context
     */
    public static void init(Context context){
        MyRetrofitUtil.context = context;

    }


    public Call getString( String url,  Map map,  MyNetListener listener) {
        return RetrofitAdapter.getInstance().getString(url,map,"",listener);
    }


    public <T> Call postStandardJsonResonse( String url,  Map map,  MyNetListener<T> listener) {
        return RetrofitAdapter.getInstance().postStandardJsonResonse(url,map,"",listener);
    }


    public <T> Call getStandardJsonResonse( String url,  Map map,  MyNetListener<T> listener) {
        return RetrofitAdapter.getInstance().getStandardJsonResonse(url,map,"",listener);
    }


    public <T> Call postCommonJsonResonse( String url,  Map map,  MyNetListener<T> listener) {
        return RetrofitAdapter.getInstance().postCommonJsonResonse(url,map,"",listener);
    }


    public <T> Call getCommonJsonResonse( String url,  Map map,  MyNetListener<T> listener) {
        return RetrofitAdapter.getInstance().getCommonJsonResonse(url,map,"",listener);
    }


    public Call autoLogin() {
        return null;
    }


    public static Call autoLogin(MyNetListener myNetListener) {
        return null;
    }


    public void cancleRequest(Object tag) {
         RetrofitAdapter.getInstance().cancleRequest(tag);
    }


    public Call download(String url, String savedpath, MyNetListener callback) {
        return RetrofitAdapter.getInstance().download(url,savedpath,callback);
    }
}
