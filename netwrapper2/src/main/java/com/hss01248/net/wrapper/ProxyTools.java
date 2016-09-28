package com.hss01248.net.wrapper;

import android.text.TextUtils;

import com.hss01248.net.config.ConfigInfo;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by Administrator on 2016/8/15 0015.
 */
public class ProxyTools {

    /**
     *
     * @param realObj
     * @param <T>
     * @return
     */
    public static <T> T getShowMethodTimeProxy(final T realObj){
      return (T) Proxy.newProxyInstance(ProxyTools.class.getClassLoader(), realObj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                long time0 = System.currentTimeMillis();
                method.invoke(realObj,args);
                long time1 = System.currentTimeMillis() - time0;
               // Logger.e("ShowMethodTime :obj:"+ realObj + "--method:"+ method.getName() +"--time:"+time1);
                return proxy;
            }
        });

    }


    public static <T> T getShowMethodInfoProxy(final T realObj){
        return (T) Proxy.newProxyInstance(ProxyTools.class.getClassLoader(), realObj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               // Logger.e("method name:"+ method.getName() + "--args:"+ Arrays.toString(args));
                method.invoke(realObj,args);
                return proxy;
            }
        });
    }

    public static <T> T getNetListenerProxy(final T realObj, final ConfigInfo configInfo){
        return (T) Proxy.newProxyInstance(ProxyTools.class.getClassLoader(), realObj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {

                String name = method.getName();
                if (!TextUtils.isEmpty(name) ){
                    if (name.contains("onProgressChange") || name.contains("registEventBus")
                            || name.contains("unRegistEventBus") || name.contains("onMessage"))
                    method.invoke(realObj,args);
                }else {
                    Tool.parseInTime(configInfo.startTime, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            try {
                                method.invoke(realObj,args);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

                return proxy;
            }
        });

    }




}
