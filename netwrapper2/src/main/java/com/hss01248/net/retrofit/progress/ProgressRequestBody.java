package com.hss01248.net.retrofit.progress;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by Administrator on 2016/9/20.
 */
public class ProgressRequestBody extends RequestBody {


    @Target(PARAMETER)
    @Retention(RUNTIME)
    @interface Chunked {

    }

    private BufferedSink bufferedSink;
    private  RequestBody requestBody;
    private String url;

    public ProgressRequestBody(RequestBody requestBody, String url){
        this.requestBody = requestBody;
        this.url = url;
    }

    @Override
    public MediaType contentType() {
        return requestBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return requestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        requestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    private Sink sink(Sink sink) {
        return new ForwardingSink(sink) {
            //当前写入字节数
            long bytesWritten = 0L;
            //总字节长度，避免多次调用contentLength()方法
            long contentLength = 0L;

            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                if (contentLength == 0) {
                    //获得contentLength的值，后续不再调用
                    contentLength = contentLength();
                }
                //增加当前写入的字节数
                bytesWritten += byteCount;
                //回调
                // listener.onProgress(bytesWritten, contentLength, bytesWritten == contentLength);
                EventBus.getDefault().post(new ProgressEvent(contentLength,bytesWritten,bytesWritten == contentLength,url));
            }
        };
    }


}
