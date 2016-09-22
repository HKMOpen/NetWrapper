package com.hss01248.net.old;

import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface Netable<T> {
    public <E> T getString( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener);

    public <E> T postString( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener);

    public<E> T postStandardJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener);

    public<E> T getStandardJsonResonse( String url, Map map, Class<E> clazz, final MyNetListener<E> listener);



    public<E> T postCommonJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener);

    public<E> T getCommonJsonResonse( String url,  Map map, Class<E> clazz, final MyNetListener<E> listener);



    public<E> T download(String url, String savedpath, MyNetListener<E> callback);

    public  T autoLogin();

    public<E>  T autoLogin(MyNetListener<E> myNetListener);

    public  void cancleRequest(Object tag);

    public <E> void resend(ConfigInfo<E> configInfo);

    <E> T upLoad(String url, Map<String,String> params,Map<String,String> files, MyNetListener<E> callback);
}
