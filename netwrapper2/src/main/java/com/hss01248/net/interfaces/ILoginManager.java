package com.hss01248.net.interfaces;

import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.wrapper.MyNetListener;

/**
 * Created by Administrator on 2016/9/22 0022.
 */
public interface ILoginManager {

    boolean isLogin();

    <T>  ConfigInfo<T> autoLogin();

    <T> ConfigInfo<T> autoLogin(MyNetListener<T> listener);
}
