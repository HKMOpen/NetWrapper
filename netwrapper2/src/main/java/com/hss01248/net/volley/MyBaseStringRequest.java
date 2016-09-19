package com.hss01248.net.volley;

/**
 * Created by Administrator on 2016/9/4.
 */
public class MyBaseStringRequest  {//extends Request<String>
   /* protected Map<String,String> mMap;

    private Response.Listener<String> mListener;


    public MyBaseStringRequest(int method, String url, Response.ErrorListener listener) {
        super(method, url, listener);
    }

    public MyBaseStringRequest(int method, String url, Priority priority, Response.Listener<String> successListener,
                               Response.ErrorListener listener, RetryPolicy retryPolicy, Map map) {
        super(method, url, priority, listener, retryPolicy);

        mListener = successListener;
        mMap = map;
        setParams(mMap);

    }






    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response) {
        String parsed;
        try {
            parsed = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
        } catch (UnsupportedEncodingException e) {
            parsed = new String(response.data);
        }
        reSetCacheControl(response);
        Log.e("data","parseNetworkResponse:"+parsed);
        return Response.success(parsed, HttpHeaderParser.parseCacheHeaders(response));
    }





    @Override
    public void setParams(Map<String, String> params) {
        super.setParams(params);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    @Override
    protected void deliverResponse(String response) {
        Log.e("deliverResponse","deliverResponse:"+response);
        if (mListener != null)
        mListener.onResponse(response);
    }


    public void setRequestHeadCacheTime(int timeInSecond){
        try {
            getHeaders().put("Cache-Control","max-age="+timeInSecond);
        } catch (AuthFailureError authFailureError) {
            authFailureError.printStackTrace();
        }

    }

    //todo 用依赖注入改写

    long cacheTime;//毫秒

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

    private void reSetCacheControl(NetworkResponse response) {
        this.setShouldCache(true);//重置cache开关
        if (!isFromCache){
            Map<String, String> headers = response.headers;
            headers.put("Cache-Control","max-age="+cacheTime);
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


    }*/
}
