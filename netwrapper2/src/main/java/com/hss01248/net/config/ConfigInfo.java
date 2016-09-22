package com.hss01248.net.config;

import com.hss01248.net.wrapper.INet;
import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ConfigInfo<T> {


    //核心参数
    public int method = HttpMethod.GET;
    public String url;
    public Map<String,String> params ;
    public int type = TYPE_STRING;//请求的类型,6类中的一种

    //回调
    public MyNetListener<T> listener;


    public Class<T> clazz;

    //请求的客户端对象
    public INet client;

    public ConfigInfo<T> start(){
        client.start(this);
        return this;
    }


    //是否拼接token
    public boolean isAppendToken = true;



    public ConfigInfo<T> setAppendToken(boolean isAppendToken){
        this.isAppendToken = isAppendToken;
        return this;
    }



    //请求头
    public Map<String,String> headers ;

    public ConfigInfo<T> setHeaders(Map<String,String> headers){
        this.headers = headers;
        return this;
    }





    //重試次數
    public int retryCount = NetDefaultConfig.RETRY_TIME;

    public ConfigInfo<T> setRetryCount(int retryCount){
        this.retryCount = retryCount;
        return this;
    }


    //超時設置,ms
    public int timeout = NetDefaultConfig.TIME_OUT;

    public ConfigInfo<T> setTimeout(int timeoutInMills){
        this.timeout = timeoutInMills;
        return this;
    }


    //強制控制回調的最短時間,默認不控制,如果需要,則自己寫
    public  int minTime = 0;

    public ConfigInfo<T> setMinCallbackTime(int minTime){
        this.minTime = minTime;
        return this;
    }

    public boolean isForceMinTime = false;

    public ConfigInfo<T> setIsForceMinTime(boolean isForceMinTime){
        this.isForceMinTime = isForceMinTime;
        return this;
    }

    //用于取消请求用的
    public Object tagForCancle = "";

    public ConfigInfo<T> setTagForCancle(Object tagForCancle){
        this.tagForCancle = tagForCancle;
        return this;
    }


    //緩存控制
   // public boolean forceGetNet = true;
    public boolean shouldReadCache = false;
    public boolean shouldCacheResponse = false;
    public long cacheTime = NetDefaultConfig.CACHE_TIME; //单位秒

    public ConfigInfo<T> setShouldReadCache(boolean shouldReadCache){
        this.shouldReadCache = shouldReadCache;
        return this;
    }

    public ConfigInfo<T> setShouldCacheResponse(boolean shouldCacheResponse){
        this.shouldCacheResponse = shouldCacheResponse;
        return this;
    }

    public ConfigInfo<T> setCacheTime(long cacheTimeInSeconds){
        this.cacheTime = cacheTimeInSeconds;
        return this;
    }



    public boolean isFromCache = false;//内部控制,不让外部设置

    //優先級
    public int priority = Priority_NORMAL;






    /**
     * 下载的一些通用策略:  downloadStratege

     * 1. 是否用url中的文件名作为最终的文件名,或者指定文件名
     * 2.如果是图片,音频,视频等多媒体文件,是否在下载完成后让mediacenter扫描一下?
     * 3. 如果是apk文件,是否在下载完成后打开?或者弹窗提示用户?
     * 4. md5校验 : 是否预先提供md5 ,下载完后与文件md5比较,以确定所下载的文件的完整性?
     * 5.断点续传的实现
     * */
    //下載文件的保存路徑
    public String filePath;



    //上传的文件路径
    public Map<String, String> files;

    /*//最終的數據類型:普通string,普通json,規範的jsonobj

    public int resonseType = TYPE_STRING;*/





    public static final int TYPE_STRING = 1;//純文本,比如html
    public static final int TYPE_JSON = 2;
    public static final int TYPE_JSON_FORMATTED = 3;//jsonObject包含data,code,msg,數據全在data中,可能是obj,頁可能是array,也可能為空

    public static final int TYPE_DOWNLOAD = 4;
    public static final int TYPE_UPLOAD_WITH_PROGRESS = 5;
    public static final int TYPE_UPLOAD_NONE_PROGRESS = 6;//测试用的

//优先级

    public static final int Priority_LOW = 1;
    public static final int Priority_NORMAL = 2;
    public static final int Priority_IMMEDIATE = 3;
    public static final int Priority_HIGH = 4;

    //http方法



    /* public enum Priority {
        LOW,
        NORMAL,
        HIGH,
        IMMEDIATE
    }*/

}
