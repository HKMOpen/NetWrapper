package com.hss01248.net.retrofit.progress;

import java.io.IOException;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ProgressRequstInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

      Request request =  chain.request();
      Headers headers =  request.headers();
        HttpUrl url = request.url();
       final RequestBody body =  request.body();

        Request.Builder requestBuilder = request.newBuilder();
        RequestBody formBody = new ProgressRequestBody(body,url.toString());


        request = requestBuilder.post(formBody).headers(headers).url(url)
                .build();
        return chain.proceed(request);

    }
}
