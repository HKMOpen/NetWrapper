package com.hss01248.net.retrofit;

import android.util.Log;

import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.HttpMethod;
import com.hss01248.net.config.NetDefaultConfig;
import com.hss01248.net.retrofit.progress.ProgressInterceptor;
import com.hss01248.net.retrofit.progress.UploadFileRequestBody;
import com.hss01248.net.wrapper.BaseNet;
import com.hss01248.net.wrapper.Tool;
import com.litesuits.android.async.SimpleTask;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/9/21.
 */
public class RetrofitClient extends BaseNet<Call> {


    Retrofit retrofit;
    ApiService service;


    ApiService serviceDownload;
    //需要单独为下载的call设置Retrofit: 主要是超时时间设置为0
    Retrofit retrofitDownload;


    //上传的client
    ApiService serviceUpload;
    Retrofit retrofitUpload;

    private void init() {
        //默认情况下，Retrofit只能够反序列化Http体为OkHttp的ResponseBody类型
        //并且只能够接受ResponseBody类型的参数作为@body

        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(15, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(15, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(true)//重试
                //.addInterceptor(new ProgressInterceptor())//下载时更新进度
                .build();

        retrofit = new Retrofit
                .Builder()
                .baseUrl(NetDefaultConfig.baseUrl)
                .client(client)
                // .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .build();

        service = retrofit.create(ApiService.class);
    }

    private static RetrofitClient instance;

    private RetrofitClient(){
        init();
        // initDownload();
    }

    private void initDownload() {
        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(0, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(false)//重试
                .addInterceptor(new ProgressInterceptor())//下载时更新进度
                .build();

        retrofitDownload = new Retrofit
                .Builder()
                .baseUrl(NetDefaultConfig.baseUrl)
                .client(client)
                // .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .build();

        serviceDownload = retrofitDownload.create(ApiService.class);
    }

    private void initUpload() {
        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(0, TimeUnit.SECONDS).writeTimeout(0, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(false)//重试
                // .addInterceptor(new ProgressRequestInterceptor())//上传时更新进度
               /* .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        return null;
                    }
                })*/
                .build();

        retrofitUpload = new Retrofit
                .Builder()
                .baseUrl(NetDefaultConfig.baseUrl)
                .client(client)
                .build();

        serviceUpload = retrofitUpload.create(ApiService.class);
    }

    public static  RetrofitClient getInstance(){
        if (instance == null){
            synchronized (RetrofitClient.class){
                if (instance ==  null){
                    instance = new RetrofitClient();
                }
            }
        }
        return  instance;
    }


    @Override
    protected boolean isAppendUrl() {
        return false;
    }


    @Override
    public <E> void cancleRequest(ConfigInfo<E> configInfo) {
        if (configInfo.tagForCancle instanceof Call){
            Call call = (Call) configInfo.tagForCancle;
            if (!call.isCanceled()){
                call.cancel();
            }
        }



    }

    @Override
    protected <E> Call newUploadRequestWithoutProgress(ConfigInfo<E> configInfo) {
        return null;
    }

    @Override
    protected  Call newUploadRequest(final ConfigInfo configInfo) {
        if (serviceUpload == null){
            initUpload();
        }
        configInfo.listener.registEventBus();


        Map<String, RequestBody> requestBodyMap = new HashMap<>();
/* UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, new DefaultProgressListener(mHandler,1));
        requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);
*/

        //添加文件

        if (configInfo.files != null && configInfo.files.size() >0){
            Map<String,String> files = configInfo.files;
            int count = files.size();
            if (count>0){
                Set<Map.Entry<String,String>> set = files.entrySet();
                for (Map.Entry<String,String> entry : set){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    File file = new File(value);
                    String type = Tool.getMimeType(file);
                    Log.e("type","mimetype:"+type);

                    UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, type,configInfo.url);
                    requestBodyMap.put(key+"\"; filename=\"" + file.getName(), fileRequestBody);

                    // requestBodyMap.put(key, fileRequestBody);

                }
            }

        }



        Call<ResponseBody> call = service.uploadWithProgress(configInfo.url,configInfo.params,requestBodyMap);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        configInfo.listener.onSuccess(string,string);
                    } catch (IOException e) {
                        e.printStackTrace();
                        onFailure(call,e);
                    }

                }else {
                    configInfo.listener.onError(response.code()+"");
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                configInfo.listener.onError(t.toString());

            }
        });




        return call;

    }

    @Override
    protected  Call newDownloadRequest(final ConfigInfo configInfo) {
        final long time = System.currentTimeMillis();
        if (serviceDownload == null){
            initDownload();
        }
        Call<ResponseBody> call = serviceDownload.download(configInfo.url);
        configInfo.listener.registEventBus();

        configInfo.tagForCancle = call;

        //todo 改成在子线程中执行

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if (!response.isSuccessful()){
                    Tool.parseInTime(time, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            configInfo.listener.onError(response.code()+"");
                        }
                    });

                    return;
                }


                Log.e("download","onResponse finished");
                //开子线程将文件写到指定路径中
                SimpleTask<Boolean> simple = new SimpleTask<Boolean>() {

                    @Override
                    protected Boolean doInBackground() {
                        return writeResponseBodyToDisk(response.body(),configInfo.filePath);
                    }

                    @Override
                    protected void onPostExecute(Boolean result) {
                        if (result){
                            configInfo.listener.onSuccess(configInfo.filePath,configInfo.filePath);
                        }else {
                            configInfo.listener.onError("文件下载失败");
                        }

                    }
                };
                simple.execute();
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writeResponseBodyToDisk(response.body(),configInfo.filePath,configInfo.listener);
                    }
                }).start();
*/
            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                Tool.parseInTime(time, configInfo, new Runnable() {
                    @Override
                    public void run() {
                        configInfo.listener.onError(t.toString());
                    }
                });
            }
        });
        return call;
    }
    

    @Override
    protected <E> Call newCommonStringRequest(final ConfigInfo<E> configInfo) {
        Log.e("url","newCommonStringRequest:"+configInfo.url);

        final long time = System.currentTimeMillis();

        Call<ResponseBody> call;

        if (configInfo.method == HttpMethod.GET){
            call = service.executGet(configInfo.url,configInfo.params);
        }else if (configInfo.method == HttpMethod.POST){
            call = service.executePost(configInfo.url,configInfo.params);
        }else {

            Tool.parseInTime(time, configInfo, new Runnable() {
                @Override
                public void run() {
                    configInfo.listener.onError("不是get或post方法");
                }
            });

            call = null;
            return call;
        }
        configInfo.tagForCancle = call;

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {

                if (!response.isSuccessful()){
                    Tool.parseInTime(time, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            configInfo.listener.onError(response.code()+"");
                        }
                    });

                    return;
                }

                String string = "";
                try {
                    string =  response.body().string();
                    final String finalString = string;
                    //从这里开始,分类进行解析

                    Tool.parseStringByType(time,string,configInfo);

                } catch (final IOException e) {
                    e.printStackTrace();
                    Tool.parseInTime(time, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            configInfo.listener.onError(e.toString());
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                Tool.parseInTime(time, configInfo, new Runnable() {
                    @Override
                    public void run() {
                        configInfo.listener.onError(t.toString());
                    }
                });
            }
        });

        return call;
    }



    private boolean writeResponseBodyToDisk(ResponseBody body,String path) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("io", "file download: " + fileSizeDownloaded + " of " + fileSize);//// TODO: 2016/9/21  这里也可以实现进度监听
                }

                outputStream.flush();


                return true;
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
