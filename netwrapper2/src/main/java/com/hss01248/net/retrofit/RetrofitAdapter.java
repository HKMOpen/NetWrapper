package com.hss01248.net.retrofit;

import android.util.Log;

import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.HttpMethod;
import com.hss01248.net.config.NetDefaultConfig;
import com.hss01248.net.retrofit.progress.ProgressInterceptor;
import com.hss01248.net.wrapper.CommonHelper;
import com.hss01248.net.wrapper.MyJson;
import com.hss01248.net.wrapper.MyNetListener;
import com.hss01248.net.wrapper.NetAdapter;

import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class RetrofitAdapter extends NetAdapter<Call> {

    private static final String TAG = "RetrofitAdapter";
    Retrofit retrofit;
    ApiService service;


    ApiService serviceDownload;
    //需要单独为下载的call设置Retrofit: 主要是超时时间设置为0
    Retrofit retrofitDownload;



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

    private static RetrofitAdapter instance;

    private RetrofitAdapter(){
       init();
       // initDownload();
    }

    private void initDownload() {
        OkHttpClient.Builder httpBuilder=new OkHttpClient.Builder();
        OkHttpClient client=httpBuilder.readTimeout(0, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(0, TimeUnit.SECONDS) //设置超时
                .retryOnConnectionFailure(true)//重试
                .addInterceptor(new ProgressInterceptor())//下载时更新进度
                .build();

        retrofitDownload = new Retrofit
                .Builder()
                .baseUrl(NetDefaultConfig.baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create()) // 使用Gson作为数据转换器
                .build();

        serviceDownload = retrofitDownload.create(ApiService.class);
    }

    public static  RetrofitAdapter getInstance(){
        if (instance == null){
            synchronized (RetrofitAdapter.class){
                if (instance ==  null){
                    instance = new RetrofitAdapter();
                }
            }
        }
        return  instance;
    }


    @Override
    protected boolean isAppend() {
        return false;
    }

    @Override
    protected void addToQunue(Call request) {
        //空实现即可

    }

    @Override
    protected void cacheControl(ConfigInfo configInfo, Call request) {
        //todo

    }

    @Override
    protected <E> Call newStandardJsonRequest(final ConfigInfo<E> configInfo) {
        final long time = System.currentTimeMillis();
        Call<ResponseBody> call;

        if (configInfo.method == HttpMethod.GET){
            call = service.executGet(configInfo.url,configInfo.params);
        }else if (configInfo.method == HttpMethod.POST){
            call = service.executePost(configInfo.url,configInfo.params);
        }else {
            call = null;
            return call;
        }

        configInfo.tag = call;


        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = "";
                try {
                    string =  response.body().string();

                    CommonHelper.parseStandJsonStr(string, time, configInfo,RetrofitAdapter.this);

                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call,e);
                }catch (JSONException e) {
                    e.printStackTrace();
                    onFailure(call,e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                CommonHelper.parseInTime(time, configInfo, new Runnable() {
                    @Override
                    public void run() {
                        configInfo.listener.onError(t.toString());
                    }
                });
            }
        });

      /*  call.enqueue(new Callback<BaseNetBean<E>>() {
            @Override
            public void onResponse(Call<BaseNetBean<E>> call, Response<BaseNetBean<E>> response) {
                BaseNetBean<E> baseBean = response.body();
                CommonHelper.parseStandardJsonObj(time,baseBean,configInfo,RetrofitAdapter.this);

            }

            @Override
            public void onFailure(Call<BaseNetBean<E>> call, Throwable t) {
               CommonHelper.parseErrorInTime(time,t.toString(),configInfo);

            }
        });*/
        return call;
    }


    @Override
    protected <E> Call newCommonJsonRequest(final ConfigInfo<E> configInfo) {
       /* final long time = System.currentTimeMillis();

        final Call<E> call;

        if (configInfo.method == HttpMethod.GET){
            call = service.getCommonJson(configInfo.url,configInfo.params,configInfo);// 怎么把E传到getCommonJson方法上去?接口上不能再定义泛型
            // java.lang.IllegalArgumentException: Method return type must not include a type variable or wildcard: retrofit2.Call<T>
        }else if (configInfo.method == HttpMethod.POST){
            call = service.postCommonJson(configInfo.url,configInfo.params,configInfo);
        }else {
            call = null;
            return call;
        }
        configInfo.tag = call;

        call.enqueue(new Callback<E>() {
            @Override
            public void onResponse(Call<E> call, final Response<E> response) {
                final E baseBean = response.body();
                CommonHelper.parseInTime(time, new Runnable() {
                    @Override
                    public void run() {
                        configInfo.listener.onSuccess(baseBean,baseBean.toString());
                    }
                }, configInfo);

            }

            @Override
            public void onFailure(Call<E> call, Throwable t) {
                CommonHelper.parseErrorInTime(time,t.toString(),configInfo);

            }
        });*/

        final long time = System.currentTimeMillis();
        Call<ResponseBody> call;

        if (configInfo.method == HttpMethod.GET){
            call = service.executGet(configInfo.url,configInfo.params);
        }else if (configInfo.method == HttpMethod.POST){
            call = service.executePost(configInfo.url,configInfo.params);
        }else {
            call = null;
            return call;
        }

        configInfo.tag = call;



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = "";
                try {
                    string =  response.body().string();

                    if (CommonHelper.isJsonEmpty(string)){//先看是否为空
                        CommonHelper.parseInTime(time,configInfo, new Runnable() {
                            @Override
                            public void run() {
                                configInfo.listener.onEmpty();
                            }
                        });
                    }else {//解析jsonArray
                        /*if (configInfo.isJsonArray){
                            final List<E> beans = MyJson.parseArray(string,configInfo.clazz);
                            final String finalString1 = string;
                            CommonHelper.parseInTime(time, new Runnable() {
                                @Override
                                public void run() {
                                    configInfo.listener.onSuccess(beans, finalString1);
                                }
                            },configInfo);

                        }else {//解析jsonObj*/
                            final E bean = MyJson.parseObject(string,configInfo.clazz);
                            final String finalString1 = string;
                        //configInfo.listener.onSuccess(bean, finalString1);
                            CommonHelper.parseInTime(time,configInfo, new Runnable() {
                                @Override
                                public void run() {
                                    configInfo.listener.onSuccess(bean, finalString1);
                                }
                            });

                    }






                    //gson 解析 放到子线程  https://www.zhihu.com/question/27216298
                   /* Gson gson = new Gson();
                    Type objectType = new TypeToken<E>() {}.getType();
                    final E bean = gson.fromJson(string,objectType);
                    Log.e("tag",bean+"");
                    if (bean == null){
                        onFailure(call,new JSONException("json parse error"));
                    }else {
                        final String finalString = string;
                        CommonHelper.parseInTime(time, new Runnable() {
                            @Override
                            public void run() {
                                configInfo.listener.onSuccess(bean, finalString);
                            }
                        },configInfo);
                    }*/
                } catch (IOException e) {
                    e.printStackTrace();
                    onFailure(call,e);
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                CommonHelper.parseInTime(time, configInfo, new Runnable() {
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
    protected Call newMultiUploadRequest(final ConfigInfo configInfo) {
       // final long time = System.currentTimeMillis();
        Map<String, RequestBody> params = new HashMap<>();
        List<MultipartBody.Part> parts =  new ArrayList<>();


        if (configInfo.files != null && configInfo.files.size() >0){
            Map<String,String> map = configInfo.files;
            int size = map.size();
            if (size>0){
                Set<Map.Entry<String,String>>  set = map.entrySet();
                for (Map.Entry<String,String> entry : set){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    File file = new File(value);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
                    parts.add(part);

                }
            }

        }


        Map<String,String> map = configInfo.params;
        int size = map.size();
        if (size>0){
            Set<Map.Entry<String,String>>  set = map.entrySet();
            for (Map.Entry<String,String> entry : set){
                String key = entry.getKey();
                String value = entry.getValue();
                RequestBody body =RequestBody.create(MediaType.parse("multipart/form-data"), value);
                params.put(key,body);

            }
        }


        Call<ResponseBody> call = service.upload(configInfo.url,params,parts);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                if (response.isSuccessful()){
                    try {
                        String string = response.body().string();
                        configInfo.listener.onSuccess("",string);
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
    protected Call newSingleUploadRequest(final ConfigInfo configInfo) {
        final long time = System.currentTimeMillis();
        Map<String, RequestBody> params = new HashMap<>();
        List<MultipartBody.Part> parts =  new ArrayList<>();


        if (configInfo.files != null && configInfo.files.size() >0){
            Map<String,String> map = configInfo.files;
            int size = map.size();
            if (size>0){
                Set<Map.Entry<String,String>>  set = map.entrySet();
                for (Map.Entry<String,String> entry : set){
                    String key = entry.getKey();
                    String value = entry.getValue();
                    File file = new File(value);
                    RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpeg"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData(key, file.getName(), requestBody);
                    parts.add(part);

                }
            }

        }


       Map<String,String> map = configInfo.params;
        int size = map.size();
        if (size>0){
          Set<Map.Entry<String,String>>  set = map.entrySet();
            for (Map.Entry<String,String> entry : set){
                String key = entry.getKey();
                String value = entry.getValue();
                RequestBody body =RequestBody.create(MediaType.parse("multipart/form-data"), value);
                params.put(key,body);

            }
        }


        Call<ResponseBody> call = service.upload(configInfo.url,params,parts);
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
    protected Call newDownloadRequest( final ConfigInfo configInfo) {
        final long time = System.currentTimeMillis();
        if (serviceDownload == null){
            initDownload();
        }
        Call<ResponseBody> call = serviceDownload.download(configInfo.url);
        configInfo.listener.registEventBus();

        configInfo.tag = call;

        //todo 改成在子线程中执行

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Log.e("download","onResponse finished");
                //开子线程将文件写到指定路径中
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        writeResponseBodyToDisk(response.body(),configInfo.filePath,configInfo.listener);
                    }
                }).start();

            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                CommonHelper.parseInTime(time, configInfo, new Runnable() {
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
    protected Call newCommonStringRequest(final ConfigInfo configInfo) {

        Log.e("url","newCommonStringRequest:"+configInfo.url);

        final long time = System.currentTimeMillis();
        Call<ResponseBody> call;

        if (configInfo.method == HttpMethod.GET){
            call = service.executGet(configInfo.url,configInfo.params);
        }else if (configInfo.method == HttpMethod.POST){
            call = service.executePost(configInfo.url,configInfo.params);
        }else {
            call = null;
            return call;
        }

        configInfo.tag = call;



        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String string = "";
                try {
                    string =  response.body().string();
                    final String finalString = string;
                    CommonHelper.parseInTime(time, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            configInfo.listener.onSuccess(finalString, finalString);
                        }
                    });
                } catch (final IOException e) {
                    e.printStackTrace();
                    CommonHelper.parseInTime(time, configInfo, new Runnable() {
                        @Override
                        public void run() {
                            configInfo.listener.onError(e.toString());
                        }
                    });
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, final Throwable t) {
                CommonHelper.parseInTime(time, configInfo, new Runnable() {
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
    protected void setInfoToRequest(ConfigInfo configInfo, Call request) {




    }

    @Override
    public Call autoLogin() {
        return null;
    }

    @Override
    public <E> Call autoLogin(MyNetListener<E> myNetListener) {
        return null;
    }

    @Override
    public void cancleRequest(Object tag) {

        if (tag instanceof  Call){
            Call call = (Call) tag;
            if (!call.isCanceled()){
                call.cancel();
            }

        }


    }


    private boolean writeResponseBodyToDisk(ResponseBody body,String path,MyNetListener callback) {
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

                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();
                callback.onSuccess(path,path);

                return true;
            } catch (IOException e) {
                callback.onError(e.toString());
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
            return false;
        }
    }


}
