package com.hss01248.net.old;

import android.content.Context;
import android.text.TextUtils;

import com.hss01248.net.cache.ACache;
import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.HttpMethod;
import com.hss01248.net.wrapper.MyNetListener;
import com.litesuits.android.async.SimpleTask;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public abstract class NetAdapter<T> implements Netable<T>{

    private static final String clazz = "NetAdapter";
    public  Context context;

    public void initInApp(Context context){
        this.context = context;

    }


    /**
     * 将configinfo组装成请求
     * @param configInfo
     * @return
     */
    public <E> T assembleRequest(final ConfigInfo<E> configInfo){
        String url = CommonHelper.appendUrl(configInfo.url,isAppend());

        configInfo.listener.url = url;

        if (configInfo.isAppendToken){
            CommonHelper.addToken(configInfo.params);
        }

        if (getCache(configInfo)){
            return null;
        }


        T request = generateNewRequest(configInfo);


        setInfoToRequest(configInfo,request);

        //cachecontrol

        cacheControl(configInfo,request);


        addToQunue(request);

        return request;
    }

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

                                newCommonStringRequest(configInfo);//没有缓存就去访问网络
                            }else {
                                configInfo.isFromCache = true;
                                CommonHelper.parseStringByType(time,result,configInfo,NetAdapter.this);
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

    protected abstract boolean isAppend();

    protected abstract void addToQunue(T request);

    protected abstract void cacheControl(ConfigInfo configInfo, T request);

    protected <E> T generateNewRequest(ConfigInfo<E> configInfo) {
        int requestType = configInfo.type;
        switch (requestType){
            case ConfigInfo.TYPE_STRING:
                return  newCommonStringRequest(configInfo);
            case ConfigInfo.TYPE_JSON:
                return newCommonJsonRequest(configInfo);
            case ConfigInfo.TYPE_JSON_FORMATTED:
                return newStandardJsonRequest(configInfo);
            case ConfigInfo.TYPE_DOWNLOAD:
                return newDownloadRequest(configInfo);
            case ConfigInfo.TYPE_UPLOAD_WITH_PROGRESS:
                return newSingleUploadRequest(configInfo);
            case ConfigInfo.TYPE_UPLOAD_NONE_PROGRESS:
                return newMultiUploadRequest(configInfo);
            default:return null;
        }

    }

    protected abstract <E> T newStandardJsonRequest(ConfigInfo<E> configInfo);

    protected abstract <E> T newCommonJsonRequest(ConfigInfo<E> configInfo);

    protected abstract T newMultiUploadRequest(ConfigInfo configInfo);

    protected abstract T newSingleUploadRequest(ConfigInfo configInfo);

    protected abstract T newDownloadRequest(ConfigInfo configInfo);

    protected abstract T newCommonStringRequest(ConfigInfo configInfo);




    protected abstract void setInfoToRequest(ConfigInfo configInfo,T request);

   /* protected RetryPolicy generateRetryPolicy(ConfigInfo configInfo) {
        return new DefaultRetryPolicy(configInfo.timeout,configInfo.retryCount,1.0f);
    }


    protected Request.Priority getPriority(int priority) {
        switch (priority){
            case ConfigInfo.Priority_NORMAL:
                return Request.Priority.NORMAL;
            case ConfigInfo.Priority_IMMEDIATE:
                return Request.Priority.IMMEDIATE;
            case ConfigInfo.Priority_LOW:
                return Request.Priority.LOW;
            case ConfigInfo.Priority_HIGH:
                return Request.Priority.HIGH;
        }
        return Request.Priority.NORMAL;

    }*/


    public abstract void cancleRequest(Object clazz);

   /* @Override
    public <E> T getString(String url, Map map, String clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.clazz = clazz;

        return assembleRequest(info);
    }*/
   @Override
   public <E> T getString( String url, Map map, Class<E> clazz, final MyNetListener<E> listener){

        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.clazz = clazz;

       return assembleRequest(info);
    }

    @Override
    public <E> T postString(String url, Map map, Class<E> clazz, MyNetListener<E> listener) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.clazz = clazz;
        info.method = HttpMethod.POST;

        return assembleRequest(info);
    }

    @Override
    public <E> T postStandardJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener){

        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON_FORMATTED;
        info.method = HttpMethod.POST;
        return assembleRequest(info);
    }
    @Override
    public <E> T getStandardJsonResonse(String url, Map map, Class<E> clazz, final MyNetListener<E> listener){
        ConfigInfo<E> info = new ConfigInfo<E>();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON_FORMATTED;
        return assembleRequest(info);
    }


    @Override
    public <E> T postCommonJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener){

        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,map,clazz,listener);
        info.method = HttpMethod.POST;
        info.type = ConfigInfo.TYPE_JSON;

        return assembleRequest(info);
    }

    protected  <E> void setKeyInfo(ConfigInfo<E> info, String url, Map map, Class<E> clazz, MyNetListener<E> listener){
        info.url = url;
        info.params = map;
        info.clazz = clazz;
        info.listener = listener;
    }

    @Override
    public <E> T getCommonJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener){
        ConfigInfo<E> info = new ConfigInfo<E>();
        setKeyInfo(info,url,map,clazz,listener);
        info.type = ConfigInfo.TYPE_JSON;
        return assembleRequest(info);
    }


    @Override
    public <E> T download(String url,String savedpath,MyNetListener<E> listener){
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,new HashMap(),null,listener);
        info.isAppendToken = false;
        info.type = ConfigInfo.TYPE_DOWNLOAD;
        info.filePath = savedpath;
        info.timeout = 0;
        return  assembleRequest(info);
    }

    @Override
    public <E> void resend(ConfigInfo<E> configInfo) {
            assembleRequest(configInfo);
    }

    @Override
    public <E> T upLoad(String url, Map<String, String> params, Map<String, String> files, MyNetListener<E> callback) {
        ConfigInfo<E> info = new ConfigInfo();
        setKeyInfo(info,url,params,null,callback);
        info.files = files;
        info.isAppendToken = false;
        info.type = ConfigInfo.TYPE_UPLOAD_WITH_PROGRESS;
        info.timeout = 0;
        return assembleRequest(info);
    }
}
