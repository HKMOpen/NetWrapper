package com.hss01248.net.volley;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class VolleyAdapter {
   /* public  RequestQueue getRequestQueue() {
        return requestQueue;
    }

    private static RequestQueue requestQueue ;
    private static VolleyAdapter instance;



    private VolleyAdapter(Context context){
        requestQueue =  Volley.newRequestQueue(context);
    }

    public static VolleyAdapter getInstance(Context context){
        if (instance == null){
            synchronized (VolleyAdapter.class){
                if (instance ==  null){
                    instance = new VolleyAdapter(context);
                }
            }
        }
        return  instance;
    }


    @Override
    protected boolean isAppend() {
        return true;
    }

    @Override
    protected void addToQunue(Request request) {
        requestQueue.add(request);

    }

    @Override
    protected void cacheControl(ConfigInfo configInfo, Request request) {
        request.setShouldCache(configInfo.shouldReadCache);
        request.setCacheTime(configInfo.cacheTime);
        request.setForceGetNet(configInfo.shouldReadCache);
    }

    @Override
    protected Request newSingleUploadRequest(int method, String url, Map map, ConfigInfo configInfo, MyNetListener myListener) {
        return null;
    }

    @Override
    protected Request newDownloadRequest(int method, String url, Map map, final ConfigInfo configInfo, final MyNetListener myListener) {
        DownloadRequest request =
                new DownloadRequest(url, configInfo.filePath, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {//返回的是文件保存路徑
                        myListener.onSuccess(response,configInfo.filePath);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        myListener.onError(error.toString());
                    }
                });

        request.setOnProgressListener(new Response.ProgressListener() {
            @Override
            public void onProgress(long transferredBytes, long totalSize) {
                myListener.onProgressChange(totalSize,transferredBytes);
            }
        });

        request.setRetryPolicy(generateRetryPolicy(configInfo));//一定要设置超时时间为0,否则使用默认超时时间,下载失败

        return request;
    }

    @Override
    protected Request newStringRequest(final int method, final String url, final Map map, final ConfigInfo configInfo, final MyNetListener myListener) {
        final long time = System.currentTimeMillis();
        return new MyBaseStringRequest(method,url, getPriority(configInfo.priority),new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {


                CommonHelper.parseStringResponseInTime(time,response,method,url,map,configInfo,myListener);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {

                CommonHelper.parseErrorInTime(time,error.toString(),configInfo,myListener);

            }
        },generateRetryPolicy(configInfo),map);
    }

    @Override
    protected void setInfoToRequest(ConfigInfo configInfo, Request request) {
        request.setTag(configInfo.tag);

    }

    @Override
    public void cancleRequest(Object tag) {
        requestQueue.cancelAll(tag);
    }*/
}
