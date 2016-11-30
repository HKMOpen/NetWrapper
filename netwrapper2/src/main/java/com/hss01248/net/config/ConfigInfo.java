package com.hss01248.net.config;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;


import com.hss01248.net.interfaces.INet;
import com.hss01248.net.wrapper.MyNetApi;
import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/3.
 */
public class ConfigInfo<T> {


    //核心参数
    public int method = HttpMethod.GET;
    public String url;
    public Map params ;



    public boolean paramsAsJson = false;
    public int type = TYPE_STRING;//请求的类型,6类中的一种

    //回调
    public MyNetListener<T> listener;


    public Class<T> clazz;

    //设置标准格式json本次响应的不同字段
    public String key_data = "";
    public String key_code = "";
    public String key_msg = "";
   // public String key_isSuccess = "";

    public int code_success;
    public int code_unlogin;
    public int code_unFound;

    public boolean isCustomCodeSet;

    public boolean isResponseJsonArray() {
        return isResponseJsonArray;
    }

    public ConfigInfo<T> setResponseJsonArray() {
        isResponseJsonArray = true;
        return this;
    }

    private boolean isResponseJsonArray = false;


    public ConfigInfo<T> setParamsAsJson() {
        this.paramsAsJson = true;
        return this;
    }


    /**
     * 单个请求的
     * @param keyData
     * @param keyCode
     * @param keyMsg

     * @return
     */
    public ConfigInfo<T> setStandardJsonKey(String keyData,String keyCode,String keyMsg){
        this.key_data = keyData;
        this.key_code = keyCode;
        this.key_msg = keyMsg;
        return this;
    }

    /**
     * 单个请求的code的key可能会不一样
     * @param keyCode
     * @return
     */
    public ConfigInfo<T> setStandardJsonKeyCode(String keyCode){
        this.key_code = keyCode;
        return this;

    }

    /**
     * 单个请求用到的code的具体值
     * @param code_success
     * @param code_unlogin
     * @param code_unFound
     * @return
     */
    public ConfigInfo<T> setCustomCodeValue(int code_success,int code_unlogin,int code_unFound){
        this.code_success = code_success;
        this.code_unlogin = code_unlogin;
        this.code_unFound = code_unFound;
        isCustomCodeSet = true;
        return this;
    }



    //请求的客户端对象
    public INet client;

    public ConfigInfo<T> start(){
        client.start(this);
        return this;
    }


    //是否拼接token
    public boolean isAppendToken = true;



    public ConfigInfo<T> setIsAppendToken(boolean isAppendToken){
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


   /* //強制控制回調的最短時間,默認不控制,如果需要,則自己寫.单位毫秒
    public  int minTime = 0;

    public long startTime;

    public ConfigInfo<T> setDialogMinShowTime(int minTime){
        if (minTime >NetDefaultConfig.TIME_MINI){
            this.minTime = minTime;
        }else {
            this.minTime = NetDefaultConfig.TIME_MINI;
        }
        return this;
    }*/


    /**
     *
     * @param loadingMsg 提示语
     * @param activity  Context ,最好传入activity,当然context也可以
     * @return
     */
    public ConfigInfo<T> setShowLoadingDialog( Activity activity,String loadingMsg){
        return setShowLoadingDialog(null,loadingMsg,activity,0);
    }

    /**
     *
     * @return
     */
    public ConfigInfo<T> setShowLoadingDialog(Dialog loadingDialog){

        return  setShowLoadingDialog(loadingDialog,"",null,0);
    }



    private ConfigInfo<T> setShowLoadingDialog(Dialog loadingDialog, String msg, Activity activity, int minTime){
        if (loadingDialog == null){
            if (TextUtils.isEmpty(msg)){
                msg = "加载中...";
            }
            if (activity == null){
                this.loadingDialog = null;//todo 生成dialog,先不显示
            }else {
                try {
                    this.loadingDialog = ProgressDialog.show(activity, "", msg,false, true);
                }catch (Exception e){

                }


            }

        }else {
            this.loadingDialog = loadingDialog;
        }

      /*  this.isForceMinTime = true;

        if (minTime >NetDefaultConfig.TIME_MINI){
            this.minTime = minTime;
        }else {
            this.minTime = NetDefaultConfig.TIME_MINI;
        }*/

        return this;
    }



   // public boolean isForceMinTime = false;

    public Dialog loadingDialog;



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


    /**
     * 只支持String和json类型的请求,不支持文件下载的缓存.
     * @param shouldReadCache 是否先去读缓存
     * @param shouldCacheResponse 是否缓存response  内部已做判断,只会缓存状态是成功的那些请求
     * @param cacheTimeInSeconds 缓存的时间,单位是秒
     * @return
     */
    public ConfigInfo<T> setCacheControl(boolean shouldReadCache,boolean shouldCacheResponse,long cacheTimeInSeconds){
        this.shouldReadCache = shouldReadCache;
        this.shouldCacheResponse = shouldCacheResponse;
        this.cacheTime = cacheTimeInSeconds;
        return this;

    }



    public boolean isFromCache = false;//内部控制,不让外部设置

    //優先級,备用 volley使用
    public int priority = Priority_NORMAL;






    /**
     * 下载的一些通用策略:  downloadStratege

     * 1. 是否用url中的文件名作为最终的文件名,或者指定文件名
     * 2.如果是图片,音频,视频等多媒体文件,是否在下载完成后让mediacenter扫描一下?
     * 3. 如果是apk文件,是否在下载完成后打开?或者弹窗提示用户?
     * 4. md5校验 : 是否预先提供md5 ,下载完后与文件md5比较,以确定所下载的文件的完整性?
     * 5.断点续传的实现
     * 6.下载队列和指定同时下载文件的个数
     *
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
