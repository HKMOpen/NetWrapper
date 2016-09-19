package com.hss01248.net.wrapper;

import android.text.TextUtils;
import android.util.Log;

import com.hss01248.net.config.BaseNetBean;
import com.hss01248.net.config.ConfigInfo;
import com.hss01248.net.config.NetDefaultConfig;
import com.hss01248.net.retrofit.MyRetrofitUtil;
import com.hss01248.net.retrofit.RetrofitAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/9/5 0005.
 */
public class CommonHelper {

    public static void addToken(Map map) {
        if (map != null){
            map.put(NetDefaultConfig.TOKEN, NetDefaultConfig.getToken());//每一个请求都传递sessionid
        }else {
            map = new HashMap();
            map.put(NetDefaultConfig.TOKEN, NetDefaultConfig.getToken());//每一个请求都传递sessionid
        }

    }


    public static String appendUrl(String urlTail,boolean isToAppend) {
        String url ;
        if (!isToAppend || urlTail.contains("http:")|| urlTail.contains("https:")){
            url =  urlTail;
        }else {
            url = NetDefaultConfig.baseUrl+  urlTail;
        }

        return url;
    }





    public static void parseStringResponseInTime(long time, final String response, final int method,
                                                 final String url, final Map map, final ConfigInfo configInfo,
                                                 final MyNetListener myListener) {
        long time2 = System.currentTimeMillis();
        long gap = time2 - time;

        if (configInfo.isForceMinTime && (gap < NetDefaultConfig.TIME_MINI)){
            TimerUtil.doAfter(new TimerTask() {
                @Override
                public void run() {
                    parseStringResponse(response,method,url,map,configInfo,myListener);
                }
            },(NetDefaultConfig.TIME_MINI - gap));

        }else {
           parseStringResponse(response,method,url,map,configInfo,myListener);
        }
    }

    public static void parseErrorInTime(long time, final String error,final ConfigInfo configInfo) {
        long time2 = System.currentTimeMillis();
        long gap = time2 - time;

        if (configInfo.isForceMinTime && (gap < NetDefaultConfig.TIME_MINI)){
            TimerUtil.doAfter(new TimerTask() {
                @Override
                public void run() {
                    configInfo.listener.onError(error);
                }
            },(NetDefaultConfig.TIME_MINI - gap));

        }else {
            configInfo.listener.onError(error);
        }
    }


    public static <T> void parseSuccessInTime(long time, final T responseBody,final ConfigInfo configInfo
                                        ) {
        long time2 = System.currentTimeMillis();
        long gap = time2 - time;

        if (configInfo.isForceMinTime && (gap < NetDefaultConfig.TIME_MINI)){
            TimerUtil.doAfter(new TimerTask() {
                @Override
                public void run() {
                    configInfo.listener.onSuccess(responseBody,responseBody+"");
                }
            },(NetDefaultConfig.TIME_MINI - gap));

        }else {
            configInfo.listener.onSuccess(responseBody,responseBody+"");
        }
    }

    public static <T> void parseInTime(long time, final Runnable runnable, ConfigInfo<T> configInfo
    ) {
        long time2 = System.currentTimeMillis();
        long gap = time2 - time;

        if (configInfo.isForceMinTime && (gap < NetDefaultConfig.TIME_MINI)){
            TimerUtil.doAfter(new TimerTask() {
                @Override
                public void run() {
                    runnable.run();
                }
            },(NetDefaultConfig.TIME_MINI - gap));

        }else {
            runnable.run();
        }
    }



    private static void parseStringResponse(String response, int method, String urlTail, Map map,
                                           ConfigInfo configInfo, MyNetListener myListener) {

        switch (configInfo.resonseType){
            case ConfigInfo.TYPE_STRING:
                myListener.onSuccess(response,response);
                break;
            case ConfigInfo.TYPE_JSON:{
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    myListener.onError("resonse is not a Json");
                    break;
                }
                myListener.onSuccess(jsonObject,response);}
            break;

            case ConfigInfo.TYPE_JSON_FORMATTED:
                JSONObject jsonObject = null;
                try {
                    jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                    myListener.onError("resonse is not a Json");
                    break;
                } catch (NullPointerException e2){
                    e2.printStackTrace();
                    myListener.onError("resonse is null");
                    break;

                }
                String data = jsonObject.optString(NetDefaultConfig.KEY_DATA);
                String codeStr = jsonObject.optString(NetDefaultConfig.KEY_CODE);
                String msg = jsonObject.optString(NetDefaultConfig.KEY_MSG);
                int code = BaseNetBean.CODE_NONE;
                if (!TextUtils.isEmpty(codeStr) ){
                    try {
                        code = Integer.parseInt(codeStr);
                    }catch (Exception e){

                    }
                }

                parseStandardJsonResponse(jsonObject,response,data,code,msg, method,  urlTail,  map,  configInfo,  myListener);

                // myListener.onSuccess(jsonObject,response,data,code,msg);
                break;
        }

    }


    private static void parseStandardJsonResponse(JSONObject jsonObject, String response, String data, int code,
                                           String msg, final int method, final String urlTail, final Map map,
                                           final ConfigInfo configInfo, final MyNetListener myListener) {

        switch (code){
            case BaseNetBean.CODE_SUCCESS://请求成功

                if (TextUtils.isEmpty(data) || "[]".equals(data)
                        || "{}".equals(data) || "null".equals(data)) {//注意: 如果json里该字段返回null,那么optString拿到的就是字符化的"null"
                    myListener.onEmpty();

                } else {
                    myListener.onSuccess(jsonObject,response,data,code,msg );
                }
                break;
            case BaseNetBean.CODE_UN_FOUND://没有找到内容
                myListener.onUnFound();
                break;
            case BaseNetBean.CODE_UNLOGIN://未登录

                //todo
                MyRetrofitUtil.autoLogin(new MyNetListener() {
                    @Override
                    public void onSuccess(Object response, String resonseStr) {
                        // todo assembleRequest(method,  urlTail,  map,  configInfo,  myListener);
                        RetrofitAdapter.getInstance().assembleRequest(method,urlTail,map,configInfo,myListener);
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        myListener.onUnlogin();
                    }
                });
                break;

            default:
                myListener.onError(response.toString());
                break;
        }

    }


    /**
     * 解析标准json的方法
     * @param time
     * @param baseBean
     * @param configInfo
     * @param adapter
     * @param <E>
     */
    public static <E> void parseStandardJsonObj(long time,BaseNetBean<E> baseBean, final ConfigInfo<E> configInfo,
                                                final RetrofitAdapter adapter){
        switch (baseBean.code){
            case BaseNetBean.CODE_SUCCESS://请求成功

                /*{}: LinkedTreeMap, size = 0  会被解析成一个空对象
                [] ArrayList sieze=0 会被解析成一个空的list
                * */
                if (baseBean.data == null ) {//如果是{}或[]呢?data是否会为空?


                    configInfo.listener.onEmpty();
                } else {

                    if (baseBean.data instanceof List){
                        List data = (List) baseBean.data;
                        if (data.size() == 0){
                            configInfo.listener.onEmpty();
                        }else {
                            configInfo.listener.onSuccess(baseBean.data,"");
                        }
                    }else {
                        configInfo.listener.onSuccess(baseBean.data,"");//todo 空怎么搞?
                    }



                }
                break;
            case BaseNetBean.CODE_UN_FOUND://没有找到内容
                configInfo.listener.onUnFound();
                break;
            case BaseNetBean.CODE_UNLOGIN://未登录
                adapter.autoLogin(new MyNetListener() {
                    @Override
                    public void onSuccess(Object response, String resonseStr) {
                        adapter.postStandard(urlTail,params,myListener);
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        configInfo.listener.onUnlogin();
                    }
                });
                break;

            default:
                configInfo.listener.onCodeError("",baseBean.msg,baseBean.code);
                break;
        }
    }


    public static boolean writeFile(final InputStream is, final String path,final MyNetListener callback){
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(path);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];


                long fileSizeDownloaded = 0;

                inputStream = is;
                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    Log.d("00", "file download: " + fileSizeDownloaded + " of " );
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
            callback.onError(e.toString());
            return false;
        }






















          /*  SimpleTask task = new SimpleTask<String>() {
                @Override
                protected String doInBackground() {
                    try {
                        File file = new File(path);
                        FileOutputStream fos = new FileOutputStream(file);
                        BufferedInputStream bis = new BufferedInputStream(is);
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = bis.read(buffer)) != -1) {
                            fos.write(buffer, 0, len);
                            fos.flush();
                        }
                        fos.close();
                        bis.close();
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return e.toString();
                    }

                    return path;
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    if (!path.equals(s)){
                        callback.onError(s);
                    }else {
                        callback.onSuccess(path,path);
                    }
                }

            };

            task.execute();*/





    }


}

