package com.hss01248.net.volley;

/**
 * Created by Administrator on 2016/9/8 0008.
 */
public class MyStandardJsonRequest<T>  {//extends Request<BaseNetBean<T>>

  /*  protected static final String PROTOCOL_CHARSET = "utf-8";

    *//** Content type for request. *//*
    private static final String PROTOCOL_CONTENT_TYPE =
            String.format("application/json; charset=%s", PROTOCOL_CHARSET);

    public void setListener(Response.Listener<BaseNetBean<T>> mListener) {
        this.mListener = mListener;
    }

    private  Response.Listener<BaseNetBean<T>> mListener;//注意,这里指定的泛型不是T,而是BaseNetBean<T>



    public MyStandardJsonRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public MyStandardJsonRequest(int method, String url, Response.ErrorListener listener, RetryPolicy retryPolicy) {
        super(method, url, listener, retryPolicy);
    }

    public MyStandardJsonRequest(int method, String url, Priority priority, Response.ErrorListener listener, RetryPolicy retryPolicy) {
        super(method, url, priority, listener, retryPolicy);
    }



    @Override
    protected Response<BaseNetBean<T>> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString =
                    new String(response.data, HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

            //gson 解析
            Gson gson = new Gson();
            Type objectType = new TypeToken<BaseNetBean<T>>() {}.getType();
            BaseNetBean<T> bean = gson.fromJson(jsonString,objectType);
            if (bean == null){
                throw new JSONException("json parse error");
            }

            //拦截响应,重置header里缓存相关字段,实现完全的缓存控制, 注意只缓存真正成功的请求
            reSetCacheControl(response,bean);

            return Response.success(bean,
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected void deliverResponse(BaseNetBean<T> response) {
        if (mListener != null){
            // todo 将code解析加上
            mListener.onResponse(response);// 直接返回类型BaseNetBean<T>的数据,让使用者去根据code解析
        }

    }



    //下方的为实现完全的缓存控制的代码




    public boolean isFromCache = false;
    public int cacheHitCount = 0;

    @Override  //怎么判断是从缓存中取的还是从网络上取的?
    public void addMarker(String tag) {
        super.addMarker(tag);
        if ("cache-hit".equals(tag)){
            cacheHitCount++;
        }else if ("cache-hit-parsed".equals(tag)){
            cacheHitCount++;
        }

        if (cacheHitCount == 2){
            isFromCache = true;
        }
    }

    private void reSetCacheControl(NetworkResponse response, BaseNetBean<T> bean) {

        if (mCacheTime == 0){//不需要缓存
            setShouldCache(false);
        }


       // this.setShouldCache(true);//重置cache开关
        if (mCacheTime > 0 && !shouldCache()){//这种情况是:强制网络更新,然后缓存回来的响应
            this.setShouldCache(true);
        }




        if (!isFromCache && bean != null && bean.code == BaseNetBean.CODE_SUCCESS){//todo 只缓存 真正拿到数据的响应,需要让用户去写
            Map<String, String> headers = response.headers;
            if (mCacheTime >0){
                headers.put("Cache-Control","max-age="+mCacheTime);
            }else {
                headers.put("Cache-Control","no-cache");
            }

            *//*cache-control
                 max-age>0 时 直接从游览器缓存中 提取
                 max-age<=0 时 向server 发送http 请求确认 ,该资源是否有修改
                 有的话 返回200 ,无的话 返回304. *//*

            //移除其他缓存控制字段:
            if (headers.containsKey("Expires")){
                headers.remove("Expires");
            }

            if (headers.containsKey("Last-Modified")){
                headers.remove("Last-Modified");
            }

            if (headers.containsKey("ETag")){
                headers.remove("ETag");
            }

            if (headers.containsKey("Pragma")){
                headers.remove("Pragma");
            }

        }

    }

    *//**
     * 缓存key的生成规则:url+body
     * @return
     *//*
    @Override
    public String getCacheKey() {
        String bodyStr = "";
        try {
            byte[]   body = getBody();
            bodyStr = new String(body,"UTF-8");
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if (bodyStr != null){
            return super.getCacheKey()+bodyStr;
        }else {
            return super.getCacheKey();
        }


    }

    @Override
    public Request<BaseNetBean<T>> setCacheTime(long cacheTime) {
        mCacheTime = cacheTime;
        try {
            getHeaders().put("Cache-Control","max-age="+mCacheTime);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }finally {
            return this;
        }
    }

    public void setShouldReadCache(boolean shouldReadCache){
        setShouldCache(shouldReadCache);
    }*/



}
