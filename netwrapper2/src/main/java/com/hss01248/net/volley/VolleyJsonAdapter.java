package com.hss01248.net.volley;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class VolleyJsonAdapter  {
   /* public <T> MyStandardJsonRequest sendRequest(final int method, final String urlTail, final Map map,
                                                 final ConfigInfo configInfo, final MyNetListener<T> myListener){
        final String url = CommonHelper.appendUrl(urlTail,true);

        myListener.url = url;

        CommonHelper.addToken(map);



        MyStandardJsonRequest request =new MyStandardJsonRequest(method, url, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }); //generateNewRequest(method,url,map,configInfo,myListener);

        request.setListener(new Response.Listener<BaseNetBean<T>>() {
            @Override
            public void onResponse(BaseNetBean baseNetBean) {

                parseStardardJsonResponse(baseNetBean,method,url,map,configInfo,myListener);



            }
        });

        request.setParams(map);//設置參數  todo 如果原url里就已經有參數了呢?

        setInfoToRequest(configInfo,request);


        *//**
         * cache control 有以下几种情况
         *
             * 完全从网络读取,不考虑缓存:   configInfo的默认值
                 * configInfo.shouldReadCache = false,
                 * configInfo.cacheTime = 0
         *
            *在缓存有效期内读缓存,过期就访问网络:类似http协议
         *      configInfo.shouldReadCache = true
         *      configInfo.cacheTime = xxx  单位是s
         *
         *   *强制访问网络,然后刷新缓存
         *      configInfo.shouldReadCache = false
         *      configInfo.cacheTime = xxx  单位是s
         *
         *      类似于方法  request.setForceGetNet(true);
         *
         *
         *
         *  第二种情况的一个应用: 只访问一次网络,以后都读缓存:
         *       configInfo.shouldReadCache = true
         *      configInfo.cacheTime = xxx  设置失效时间在几百年后
         *
         *
         *
         *
         *//*

        request.setShouldReadCache(configInfo.shouldReadCache);//请求是否从缓存读取的大开关
        request.setCacheTime(configInfo.cacheTime);//设置缓存的时间
        if (!configInfo.shouldReadCache){
            request.setForceGetNet(true);
        }


        VolleyAdapter.getInstance(MyNetUtil.context).getRequestQueue().add(request);

        return request;
    }

    private void setInfoToRequest(final ConfigInfo configInfo, final MyStandardJsonRequest request) {
        request.setTag(configInfo.tag);
        request.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return configInfo.timeout;
            }

            @Override
            public int getCurrentRetryCount() {
                return configInfo.retryCount;
            }

            @Override
            public void retry(VolleyError volleyError) throws VolleyError {

            }
        });

       // request.setPriority(configInfo.priority);





    }

    private <T> void parseStardardJsonResponse(BaseNetBean<T> baseBean, int method, String url, Map map,
                                               ConfigInfo configInfo, MyNetListener<T> myListener) {

        switch (baseBean.code){
            case BaseNetBean.CODE_SUCCESS://请求成功

                *//*{}: LinkedTreeMap, size = 0  会被解析成一个空对象
                [] ArrayList sieze=0 会被解析成一个空的list
                * *//*
                if (baseBean.data == null ) {//如果是{}或[]呢?data是否会为空?
                    myListener.onEmpty();
                } else {

                    if (baseBean.data instanceof List){
                        List data = (List) baseBean.data;
                        if (data.size() == 0){
                            myListener.onEmpty();
                        }else {
                            myListener.onSuccess(baseBean.data,"");
                        }
                    }else {
                        myListener.onSuccess(baseBean.data,"");//todo 空怎么搞?
                    }
                }
                break;
            case BaseNetBean.CODE_UN_FOUND://没有找到内容
                myListener.onUnFound();
                break;
            case BaseNetBean.CODE_UNLOGIN://未登录
                // todo
               *//* retrofitUtil2.autoLogin(new MyNetCallback() {
                    @Override
                    public void onSuccess(Object response, String resonseStr) {
                        retrofitUtil2.postStandard(urlTail,params,myListener);
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        myListener.onUnlogin();
                    }
                });*//*
                break;

            default:
                myListener.onCodeError("",baseBean.msg,baseBean.code);
                break;
        }


    }*/
}
