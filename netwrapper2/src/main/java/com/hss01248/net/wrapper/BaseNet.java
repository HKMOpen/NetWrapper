package com.hss01248.net.wrapper;

import android.text.TextUtils;

import com.hss01248.net.cache.ACache;
import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.HttpMethod;
import com.hss01248.net.interfaces.ILoginManager;
import com.hss01248.net.interfaces.INet;
import com.hss01248.net.old.CommonHelper;
import com.hss01248.net.old.MyNetUtil;
import com.litesuits.android.async.SimpleTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/21.
 */
public  abstract class BaseNet<T> implements INet {//T: 请求类  call或者是Request

    private ILoginManager loginManager;

    public void setLoginManager(ILoginManager loginManager){
        this.loginManager = loginManager;
    }

    protected  <E> void setKeyInfo(ConfigInfo<E> info, String url, Map map, Class<E> clazz, MyNetListener<E> listener){
        info.url = url;
        info.params = map;
        info.clazz = clazz;
        info.listener = listener;
        info.client = this;
    }

    @Override
    public <E> ConfigInfo<E> getString(String url, Map map,  MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,null,listener);
        return info;
    }

    @Override
    public <E> ConfigInfo<E> postString(String url, Map map,  MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,null,listener);

        info.method = HttpMethod.POST;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> postStandardJson(String url, Map map, Class<E> clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON_FORMATTED;
        info.method = HttpMethod.POST;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> getStandardJson(String url, Map map, Class<E> clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo<E>();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON_FORMATTED;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> postCommonJson(String url, Map map, Class<E> clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.method = HttpMethod.POST;
        info.type = ConfigInfo.TYPE_JSON;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> getCommonJson(String url, Map map, Class<E> clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo<E>();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> download(String url, String savedpath, MyNetListener<E> callback) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,new HashMap(),null,callback);
        info.isAppendToken = false;
        info.type = ConfigInfo.TYPE_DOWNLOAD;
        info.filePath = savedpath;
        info.timeout = 0;
        return info;
    }

    @Override
    public <E> ConfigInfo<E> autoLogin() {
        if (loginManager != null){
          return   loginManager.autoLogin();
        }
        return null;
    }

    @Override
    public <E> ConfigInfo<E> autoLogin(MyNetListener<E> myNetListener) {
        if (loginManager != null){
            return   loginManager.autoLogin(myNetListener);
        }
        return null;

    }

    @Override
    public boolean isLogin() {
        if (loginManager != null){
            return loginManager.isLogin();
        }
        return false;
    }

    @Override
    public   abstract <E>  void cancleRequest(ConfigInfo<E> tConfigInfo);

    @Override
    public <E> ConfigInfo<E> resend(ConfigInfo<E> configInfo) {
        return start(configInfo);
    }

    @Override
    public <E> ConfigInfo<E> upLoad(String url, Map<String, String> params, Map<String, String> files, MyNetListener<E> callback) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,params,null,callback);
        info.files = files;
        info.isAppendToken = false;
        info.type = ConfigInfo.TYPE_UPLOAD_WITH_PROGRESS;
        info.timeout = 0;

        return info;
    }


    /**
     * 在这里组装请求,然后发出去
     * @param <E>
     * @return
     */
    @Override
    public <E> ConfigInfo<E> start(ConfigInfo<E> configInfo) {

        String url = Tool.appendUrl(configInfo.url, isAppendUrl());
        configInfo.url = url;
        configInfo.listener.url = url;

        if (configInfo.isAppendToken){

            Tool.addToken(configInfo.params);
        }

       // configInfo.client = this;

        if (getCache(configInfo)){
            return configInfo;
        }

        T request = generateNewRequest(configInfo);


        /*

        这三个方式是给volley预留的
        setInfoToRequest(configInfo,request);

        cacheControl(configInfo,request);

        addToQunue(request);*/

        return configInfo;
    }

    private <E> T generateNewRequest(ConfigInfo<E> configInfo) {
        int requestType = configInfo.type;
        switch (requestType){
            case ConfigInfo.TYPE_STRING:
            case ConfigInfo.TYPE_JSON:
            case ConfigInfo.TYPE_JSON_FORMATTED:
                return  newCommonStringRequest(configInfo);
            case ConfigInfo.TYPE_DOWNLOAD:
                return newDownloadRequest(configInfo);
            case ConfigInfo.TYPE_UPLOAD_WITH_PROGRESS:
                return newUploadRequest(configInfo);
            case ConfigInfo.TYPE_UPLOAD_NONE_PROGRESS:
                return newUploadRequestWithoutProgress(configInfo);
            default:return null;
        }
    }

    protected abstract <E> T newUploadRequestWithoutProgress(ConfigInfo<E> configInfo);

    protected abstract <E> T newUploadRequest(ConfigInfo<E> configInfo);

    protected abstract <E> T newDownloadRequest(ConfigInfo<E> configInfo);

   /* protected abstract <E> T newStandardJsonRequest(ConfigInfo<E> configInfo);

    protected abstract <E> T newCommonJsonRequest(ConfigInfo<E> configInfo);*/

    protected abstract <E> T newCommonStringRequest(ConfigInfo<E> configInfo);


    protected boolean isAppendUrl() {
        return false;
    }


    /**
     * 缓存获取. 完全的客户端控制,只针对String和Json类型.
     * @param configInfo
     * @param <E>
     * @return
     */
    private <E> boolean getCache(final ConfigInfo<E> configInfo) {
        switch (configInfo.type){
            case ConfigInfo.TYPE_STRING:
            case ConfigInfo.TYPE_JSON:
            case ConfigInfo.TYPE_JSON_FORMATTED:{

                //拿缓存
                if (configInfo.shouldReadCache){

                    final long time = System.currentTimeMillis();
                    SimpleTask<String> simple = new SimpleTask<String>() {

                        @Override
                        protected String doInBackground() {
                            return ACache.get(MyNetUtil.context).getAsString(CommonHelper.getCacheKey(configInfo));
                        }

                        @Override
                        protected void onPostExecute(String result) {

                            if (TextUtils.isEmpty(result)){
                                configInfo.shouldReadCache = false;

                                start(configInfo);//没有缓存就去访问网络
                            }else {
                                configInfo.isFromCache = true;
                                Tool.parseStringByType(time,result,configInfo);
                            }

                        }
                    };
                    simple.execute();
                }else {
                    return false;
                }

            }

            case ConfigInfo.TYPE_DOWNLOAD:
            case ConfigInfo.TYPE_UPLOAD_WITH_PROGRESS:
            case ConfigInfo.TYPE_UPLOAD_NONE_PROGRESS:
                return false;
            default:return false;
        }
    }
}
