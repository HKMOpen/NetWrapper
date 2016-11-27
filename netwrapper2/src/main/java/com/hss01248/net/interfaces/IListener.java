package com.hss01248.net.interfaces;

import android.text.TextUtils;

import java.util.List;

/**
 * Created by Administrator on 2016/11/27 0027.
 */
public interface IListener<T> {

    public    void onUnlogin();


    /**
     * called when the request is success bug data is empty
     */
    public  void onEmpty();

    public void onPreExecute() ;



    /** Called when response success. */
    public abstract void onSuccess(T response,String resonseStr);

    public  void onSuccessArr(List<T> response, String resonseStr);

    public  void onSuccessObj(T response,String responseStr,String data,int code,String msg);

    public  void onSuccessArr(List<T> response, String responseStr, String data, int code, String msg);




    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    public void onError(String error);


    /**
     * 有错误码的error
     * @param error
     * @param msg
     * @param code
     */
    public void onCodeError(String error,String msg,int code);


    public void onCancel() ;

    public void onUnFound() ;


    public void onNetworking();

    /** Inform when the cache already use,
     * it means http networking won't execute. */
    public void onUsedCache() ;


    public void onRetry() ;



    public void onFinish();

}
