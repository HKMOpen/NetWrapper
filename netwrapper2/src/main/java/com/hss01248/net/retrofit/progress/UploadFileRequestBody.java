package com.hss01248.net.retrofit.progress;

import com.hss01248.net.config.NetDefaultConfig;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;
import okio.Sink;

/**
 * Created by Administrator on 2016/9/21 0021.
 */
public class UploadFileRequestBody extends RequestBody {

    private RequestBody mRequestBody;
    private BufferedSink bufferedSink;
    private String url;


    public UploadFileRequestBody(File file,String mimeType,String url) {
       // this.mRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        this.mRequestBody = RequestBody.create(MediaType.parse(mimeType), file);
        this.url = url;
    }

    @Override
    public MediaType contentType() {
        return mRequestBody.contentType();
    }

    //返回了本RequestBody的长度，也就是上传的totalLength
    @Override
    public long contentLength() throws IOException {
        return mRequestBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        if (bufferedSink == null) {
            //包装
            bufferedSink = Okio.buffer(sink(sink));
        }
        //写入
        mRequestBody.writeTo(bufferedSink);
        //必须调用flush，否则最后一部分数据可能不会被写入
        bufferedSink.flush();
    }

    long oldTime = 0L;

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
                long currentTime = System.currentTimeMillis();
                if (currentTime - oldTime > NetDefaultConfig.PROGRESS_INTERMEDIATE || bytesWritten == contentLength){//每300ms更新一次进度
                    oldTime = currentTime;
                    EventBus.getDefault().post(new ProgressEvent(contentLength,bytesWritten,bytesWritten == contentLength,url));
                }
            }
        };
    }
}
