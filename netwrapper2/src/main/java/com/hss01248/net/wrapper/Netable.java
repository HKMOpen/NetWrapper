package com.hss01248.net.wrapper;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface Netable<T> {
    public <E> T getString( String url,  Map map, String tag, final MyNetListener<E> listener);

    public <E> T postString( String url,  Map map, String tag, final MyNetListener<E> listener);

    public<E> T postStandardJsonResonse( String url,  Map map, String tag, final MyNetListener<E> listener);

    public<E> T getStandardJsonResonse( String url, Map map, String tag, final MyNetListener<E> listener);



    public<E> T postCommonJsonResonse( String url,  Map map, String tag, final MyNetListener<E> listener);

    public<E> T getCommonJsonResonse( String url,  Map map, String tag, final MyNetListener<E> listener);



    public<E> T download(String url, String savedpath, MyNetListener<E> callback);

    public  T autoLogin();

    public<E>  T autoLogin(MyNetListener<E> myNetListener);

    public  void cancleRequest(Object tag);
}
