package com.hss01248.net.interfaces;

import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.wrapper.MyNetListener;

import java.util.Map;

/**
 * Created by Administrator on 2016/9/5.
 */
public interface INet extends ILoginManager{//extends ILoginManager
    public <E> ConfigInfo<E> getString(String url, Map map,  final MyNetListener<E> listener);

    public <E> ConfigInfo<E> postString(String url, Map map, final MyNetListener<E> listener);

    public<E> ConfigInfo<E> postStandardJsonResonse(String url, Map map, Class<E> clazz, final MyNetListener<E> listener);

    public<E> ConfigInfo<E> getStandardJsonResonse(String url, Map map, Class<E> clazz, final MyNetListener<E> listener);



    public<E> ConfigInfo<E> postCommonJsonResonse(String url, Map map, Class<E> clazz, final MyNetListener<E> listener);

    public<E> ConfigInfo<E> getCommonJsonResonse(String url, Map map, Class<E> clazz, final MyNetListener<E> listener);



    public<E> ConfigInfo<E> download(String url, String savedpath, MyNetListener<E> callback);

   /* public <E> ConfigInfo<E> autoLogin();

    public<E>  ConfigInfo<E> autoLogin(MyNetListener<E> myNetListener);

    boolean isLogin();*/

    public <E> void cancleRequest(ConfigInfo<E> tConfigInfo);

    public <E> ConfigInfo<E> resend(ConfigInfo<E> configInfo);

    <E> ConfigInfo<E> upLoad(String url, Map<String, String> params, Map<String, String> files, MyNetListener<E> callback);

    <E> ConfigInfo<E> start(ConfigInfo<E> tConfigInfo);


}
