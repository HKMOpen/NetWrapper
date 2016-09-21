# NetWrapper

基于retrofit封装,面向接口,链式调用,使用时不设计retrofit中的类.可继承BaseNet类来无缝切换到其他网络,如volley.





# 已完成

缓存完全由客户端自行控制(ACache)

上传和下载的进度回调(填了很大的坑)







# todo

## 下载策略

```
/**
 * 下载的一些通用策略:  downloadStratege

 * 1. 是否用url中的文件名作为最终的文件名,或者指定文件名
 * 2.如果是图片,音频,视频等多媒体文件,是否在下载完成后让mediacenter扫描一下?
 * 3. 如果是apk文件,是否在下载完成后打开?或者弹窗提示用户?
 * 4. md5校验 : 是否预先提供md5 ,下载完后与文件md5比较,以确定所下载的文件的完整性?
 * 5.断点续传的实现
 * */
```



## 缓存

无网络时读缓存

利用http请求头来完全屏蔽okhttp的缓存体系

缓存文件夹大小的设置





# usage

```
getString(String url, Map map, MyNetListener listener).setXxx()....start();

//中间的setXxx可以没有,如:

 MyNetApi.getString("http://www.qxinli.com/Application/about/androidAbout.html", 
 new HashMap(),  
 new MyNetListener<String>() {
                    @Override
                    public void onSuccess(String response, String resonseStr) {
                        Logger.e(response);

                    }
                }).start();
                
其他api:

postString( String url,  Map map,  MyNetListener listener).start()



//标准json

postStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener).start()

getStandardJsonResonse( String url,  Map map, Class clazz, MyNetListener listener).start()


//普通jsonObject和JsonArray

postCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener).start()

getCommonJsonResonse( String url,  Map map, Class clazz, MyNetListener listener).start()

//上传和下载

download(String url, String savedpath, MyNetListener listener).start()

upLoad(String url, Map<String,String> params,Map<String,String> files, MyNetListener callback).start()

```



## 供链式调用的相关的set方法有:

 ![setmethods](setmethods.jpg)



## json的解析: 

如果是jsonObject,

clazz传入实体类的Class,同时MyNetListener泛型设置为该实体类

如果JsonArray,:

clazz传入数组元素类的Class,同时MyNetListener泛型设置为该实体类,其回调采用

```
onSuccessArr(List<T> response,String resonseStr)
```



# 标准格式json的解析

默认字段 data,code,msg. 如果需要自定义,调用这个方法:

```
public static void setStandardJsonKey(String data,String code,String msg){
    NetDefaultConfig.KEY_DATA = data;
    NetDefaultConfig.KEY_CODE = code;
    NetDefaultConfig.KEY_MSG = msg;
}
```

api:

getStandardJsonResonse

postStandardJsonResonse



