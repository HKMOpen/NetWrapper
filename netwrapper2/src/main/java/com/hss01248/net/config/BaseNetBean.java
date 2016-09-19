package com.hss01248.net.config;

/**
 * Created by Administrator on 2016/8/30.
 */
public class BaseNetBean<T> {
    public int code;
    public String msg;
    public T data;

    //TODO
    public static final int CODE_NONE = -1;
    public static final int CODE_SUCCESS = 0;
    public static final int CODE_UNLOGIN = 2;
    public static final int CODE_UN_FOUND = 3;


    /*public class Result<T> {
    public int code;
    public String message;
    public T data;
}
那么对于data字段是User时则可以写为 Result<User> ,当是个列表的时候为 Result<List<User>>，其它同理。

文／怪盗kidou（简书作者）
原文链接：http://www.jianshu.com/p/e740196225a4
著作权归作者所有，转载请联系作者获得授权，并标注“简书作者”。*/

}
