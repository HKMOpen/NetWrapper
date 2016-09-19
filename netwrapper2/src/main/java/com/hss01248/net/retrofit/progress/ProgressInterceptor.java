package com.hss01248.net.retrofit.progress;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ProgressInterceptor implements Interceptor {




    public ProgressInterceptor(){
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException
    {
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(new ProgressResponseBody(originalResponse.body(),chain.request().url().toString())).build();
    }

}
