package com.hss01248.net.retrofit.progress;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class ProgressEvent {

    public long totalLength;
    public long totalBytesRead;
    public boolean done;
    public String url;

    public ProgressEvent(long totalLength, long totalBytesRead, boolean done, String url) {
        this.totalLength = totalLength;
        this.totalBytesRead = totalBytesRead;
        this.done = done;
        this.url = url;
    }
}
