package com.hss01248.net.config;

/**
 * Created by Administrator on 2016/9/19.
 */
public interface HttpMethod {

    int DEPRECATED_GET_OR_POST = -1;
    int GET = 0;
    int POST = 1;
    int PUT = 2;
    int DELETE = 3;
    int HEAD = 4;
    int OPTIONS = 5;
    int TRACE = 6;
    int PATCH = 7;
}
