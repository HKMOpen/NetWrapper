package com.hss01248.net.config;

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

    //是否拼接token
    public boolean isAppendToken = true;

    //回调
    public MyNetListener<T> listener;



    //请求头
    public Map<String,String> headers ;





    //重試次數
    public int retryCount = NetDefaultConfig.RETRY_TIME;

    //超時設置
    public int timeout = NetDefaultConfig.TIME_OUT;

    //強制控制回調的最短時間,默認不控制,如果需要,則自己寫
    public  int minTime = 0;
    public boolean isForceMinTime = false;

    //用于取消请求用的
    public Object tag = "";


    //緩存控制
   // public boolean forceGetNet = true;
    public boolean shouldReadCache = false;
    public boolean shouldCacheResponse = false;
    public long cacheTime = NetDefaultConfig.CACHE_TIME;

    //優先級
    public int priority = Priority_NORMAL;




    //下載文件的保存路徑
    public String filePath;

    /*//最終的數據類型:普通string,普通json,規範的jsonobj

    public int resonseType = TYPE_STRING;*/





    public static final int TYPE_STRING = 1;//純文本,比如html
    public static final int TYPE_JSON = 2;
    public static final int TYPE_JSON_FORMATTED = 3;//jsonObject包含data,code,msg,數據全在data中,可能是obj,頁可能是array,也可能為空

    public static final int TYPE_DOWNLOAD = 4;
    public static final int TYPE_UPLOAD_SINGLE = 5;
    public static final int TYPE_UPLOAD_MULTIPLE = 6;

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
