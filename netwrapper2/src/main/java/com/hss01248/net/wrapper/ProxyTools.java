package com.hss01248.net.wrapper;

import android.text.TextUtils;
import android.util.Log;

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
                Object o = method.invoke(realObj,args);
                long time1 = System.currentTimeMillis() - time0;
               // Logger.e("ShowMethodTime :obj:"+ realObj + "--method:"+ method.getName() +"--time:"+time1);
                return o;
            }
        });

    }


    public static <T> T getShowMethodInfoProxy(final T realObj){
        return (T) Proxy.newProxyInstance(ProxyTools.class.getClassLoader(), realObj.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
               // Logger.e("method name:"+ method.getName() + "--args:"+ Arrays.toString(args));
                Object o = method.invoke(realObj,args);
                return o;
            }
        });
    }


    /**
     * Proxy.newProxyInstance()做动态代理，只能是代理接口和实现类。不能代理抽象类和实现类。不然就会报转换异常错误。
     * @param realObj 不能是抽象方法
     * @param configInfo
     * @param <T>
     * @return
     */
    public static <T> T getNetListenerProxy(final T realObj, final ConfigInfo configInfo){

        Log.e("jj",realObj.getClass().getSuperclass().toString());//class com.hss01248.net.wrapper.MyNetListener
        Log.e("jj",realObj.getClass().getSuperclass().getInterfaces()[0].toString());//interface com.hss01248.net.interfaces.IListener
        return (T) Proxy.newProxyInstance(realObj.getClass().getClassLoader(), realObj.getClass().getSuperclass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, final Method method, final Object[] args) throws Throwable {



                Tool.dismiss(configInfo.loadingDialog);
               Object o= method.invoke(proxy,args);
                Log.e("jj",proxy.toString());
                /*String name = method.getName();
                if (!TextUtils.isEmpty(name) ){
                    if (name.contains("onProgressChange") || name.contains("registEventBus")
                            || name.contains("unRegistEventBus") || name.contains("onMessage"))
                        Tool.dismiss(configInfo.loadingDialog);
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
                }*/

                return o;
            }
        });

    }




}
