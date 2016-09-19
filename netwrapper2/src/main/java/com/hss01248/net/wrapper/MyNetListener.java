package com.hss01248.net.wrapper;


import com.hss01248.net.retrofit.progress.ProgressEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by Administrator on 2016/4/15 0015.
 */
public abstract class MyNetListener<T> {

    public String url;

    public    void onUnlogin(){
        onError("您还没有登录");
    }


    /**
     * called when the request is success bug data is empty
     */
    public  void onEmpty(){}

    public void onPreExecute() {}



    /** Called when response success. */
    public abstract void onSuccess(T response,String resonseStr);

    public  void onSuccess(T response,String responseStr,String data,int code,String msg){
            onSuccess(response,responseStr);
    }



    /**
     * Callback method that an error has been occurred with the
     * provided error code and optional user-readable message.
     */
    public void onError(String error) {}


    /**
     * 有错误码的error
     * @param error
     * @param msg
     * @param code
     */
    public void onCodeError(String error,String msg,int code) {
        onError(msg);
    }


    public void onCancel() {}

    public void onUnFound() {
        onError("没有找到该内容");
    }


    public void onNetworking() {}

    /** Inform when the cache already use,
     * it means http networking won't execute. */
    public void onUsedCache() {}


    public void onRetry() {}

    /**
     * 都是B作为单位
     */
    public void onProgressChange(long fileSize, long downloadedSize) {
    }


    /**
     * 只需要在retrofit下载时调用
     */
    public void registEventBus(){
        EventBus.getDefault().register(this);
    }

    public void unRegistEventBus(){
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void  onMessage(ProgressEvent event){
        if (event.url.equals(url)){
            onProgressChange(event.totalLength,event.totalBytesRead);

            if (event.done){
                unRegistEventBus();
            }
        }


    }


}
