package com.hss01248.netdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.hss01248.net.retrofit.RetrofitAdapter;
import com.hss01248.net.wrapper.MyJson;
import com.hss01248.net.wrapper.MyNetListener;
import com.hss01248.net.wrapper.MyNetUtil;
import com.hss01248.netdemo.bean.GetCommonJsonBean;
import com.hss01248.netdemo.bean.GetStandardJsonBean;
import com.hss01248.netdemo.bean.PostCommonJsonBean;
import com.hss01248.netdemo.bean.PostStandardJsonArray;
import com.orhanobut.logger.Logger;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {


    @Bind(R.id.get_string)
    Button getString;
    @Bind(R.id.post_string)
    Button postString;
    @Bind(R.id.get_json)
    Button getJson;
    @Bind(R.id.post_json)
    Button postJson;
    @Bind(R.id.get_standard_json)
    Button getStandardJson;
    @Bind(R.id.post_standard_json)
    Button postStandardJson;
    @Bind(R.id.download)
    Button download;
    @Bind(R.id.upload)
    Button upload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        RetrofitAdapter adapter =  RetrofitAdapter.getInstance();
        MyNetUtil.init(this,adapter);
        Logger.init("net");
    }

    @OnClick({R.id.get_string, R.id.post_string, R.id.get_json, R.id.post_json, R.id.get_standard_json, R.id.post_standard_json, R.id.download, R.id.upload})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.get_string:
                MyNetUtil.getString("http://www.qxinli.com/Application/about/androidAbout.html", new HashMap(), String.class, new MyNetListener<String>() {
                    @Override
                    public void onSuccess(String response, String resonseStr) {
                        Logger.e(response);

                    }
                });
                break;
            case R.id.post_string:
                Map<String,String> map = new HashMap<>();
                map.put("pageSize","30");
                map.put("articleId","1738");
                map.put("pageIndex","1");
                MyNetUtil.postString("http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json",
                        map, String.class, new MyNetListener<String>() {
                    @Override
                    public void onSuccess(String response, String resonseStr) {
                        Logger.e(response);
                    }
                });
                break;
            case R.id.get_json:
                Map<String,String> map2 = new HashMap<>();
                MyNetUtil.getCommonJsonResonse("http://www.qxinli.com:9001/api/version/latestVersion/v1.json",
                        map2, GetCommonJsonBean.class, new MyNetListener<GetCommonJsonBean>() {
                    @Override
                    public void onSuccess(GetCommonJsonBean response, String resonseStr) {
                        Logger.json(MyJson.toJsonStr(response));
                    }
                });
                break;
            case R.id.post_json:
                Map<String,String> map3 = new HashMap<>();
                map3.put("pageSize","30");
                map3.put("articleId","1738");
                map3.put("pageIndex","1");
                MyNetUtil.postCommonJsonResonse("http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json",
                        map3, PostCommonJsonBean.class, new MyNetListener<PostCommonJsonBean>() {
                    @Override
                    public void onSuccess(PostCommonJsonBean response, String resonseStr) {
                        Logger.json(MyJson.toJsonStr(response));
                    }
                });

                break;
            case R.id.get_standard_json:
                Map<String,String> map4 = new HashMap<>();
                MyNetUtil.getStandardJsonResonse("http://www.qxinli.com:9001/api/version/latestVersion/v1.json",
                        map4, GetStandardJsonBean.class, new MyNetListener<GetStandardJsonBean>() {
                            @Override
                            public void onSuccess(GetStandardJsonBean response, String resonseStr) {
                                Logger.json(MyJson.toJsonStr(response));
                            }

                            @Override
                            public void onError(String error) {
                                super.onError(error);
                                Logger.e("code:"+error);
                            }
                        });
                break;
            case R.id.post_standard_json:

                Map<String,String> map5 = new HashMap<>();
                map5.put("pageSize","30");
                map5.put("articleId","1738");
                map5.put("pageIndex","1");
                MyNetUtil.postStandardJsonResonse("http://www.qxinli.com:9001/api/article/getArticleCommentList/v1.json",
                        map5, PostStandardJsonArray.class, new MyNetListener<PostStandardJsonArray>() {
                            @Override
                            public void onSuccess(PostStandardJsonArray response, String resonseStr) {
                                //Logger.json(MyJson.toJsonStr(response));
                            }

                            @Override
                            public void onSuccessArr(List<PostStandardJsonArray> response, String responseStr, String data, int code, String msg) {
                                super.onSuccessArr(response, responseStr, data, code, msg);
                                Logger.json(MyJson.toJsonStr(response));
                            }
                        });
                break;
            case R.id.download:
                File dir = Environment.getExternalStorageDirectory();
                File file = new File(dir,"qxinli.apk");
                if (file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                MyNetUtil.download("http://www.qxinli.com/download/qxinli.apk", file.getAbsolutePath(), new MyNetListener() {
                    @Override
                    public void onSuccess(Object response, String onSuccess) {
                        Logger.e("onSuccess:"+onSuccess);
                    }

                    @Override
                    public void onProgressChange(long fileSize, long downloadedSize) {
                        super.onProgressChange(fileSize, downloadedSize);
                        Logger.e("progress:"+downloadedSize);
                    }
                });
                break;
            case R.id.upload:

                Map<String,String> map6 = new HashMap<>();
               // map6.put("uploadFile","1474363536041.jpg");
               // map6.put("api_secret",app_secert);


                Map<String,String> map7 = new HashMap<>();
                map7.put("uploadFile","/storage/emulated/0/DCIM/1474363536041.jpg");

                MyNetUtil.upLoad("http://192.168.1.100:8080/gm/file/q_uploadAndroidApk.do",
                        map6,map7, new MyNetListener<String>() {
                            @Override
                            public void onSuccess(String response, String resonseStr) {
                                Logger.e(resonseStr);
                            }

                            @Override
                            public void onError(String error) {
                                super.onError(error);
                                Logger.e("error:"+error);
                            }
                        });
                break;
        }
    }

    String app_key = "4d3d1f40e7a841316084b64c0c4575b1";
    String app_secert = "VQ8bciAjkUl4fiTTvafdvTLnBNGlSS";

  /*  @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
               *//* RetrofitAdapter.getInstance().getString("api/msg/unreadMsg/count/v1.json", new HashMap(), "dd", new MyNetCallback() {
                    @Override
                    public void onSuccess(Object response, String resonseStr) {
                        Log.e("baidu",response.toString());
                    }
                });*//*

                String obj1 = "{\"data\":null,\"code\":0}";
                String obj2 = "{\"data\":{},\"code\":0}";
                String obj3 = "{\"data\":[],\"code\":0}";

                BaseNetBean<TestBean> netBean1 = MyJson.parseObject(obj1,BaseNetBean.class);
                BaseNetBean<TestBean> netBean2 = MyJson.parseObject(obj2,BaseNetBean.class);
                BaseNetBean<List<TestBean>> netBean3 = MyJson.parseObject(obj3,BaseNetBean.class);

                break;
            case R.id.button2:
                Map<String, String> map = new HashMap<String, String>();
               // map.put("id", "145");
                RetrofitAdapter.getInstance().postStandardJsonResonse("api/voice/categoryList/v1.json",
                        map, "kk", new MyNetListener<JSONObject>() {
                    @Override
                    public void onSuccess(JSONObject response, String resonseStr) {
                        Log.e("postStandardJsonResonse","onSuccess");
                    }

                    @Override
                    public void onSuccess(JSONObject response, String responseStr, String data, int code, String msg) {
                       // super.onSuccess(response, responseStr, data, code, msg);
                        Log.e("postStandardJsonResonse","onSuccess long "+ "code:"+code + "--msg:"+ msg + "--data:"+data);
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        Log.e("postStandardJsonResonse","onError:"+error);
                    }
                });
                break;
            case R.id.button3:
                File file = new File(Environment.getExternalStorageDirectory(),"qxinli.apk");
                if (!file.exists()){
                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                String path = file.getAbsolutePath();
                RetrofitAdapter.getInstance().download("http://www.qxinli.com/download/qxinli.apk", path, new MyNetListener<String>() {
                    @Override
                    public void onSuccess(String response, String resonseStr) {
                        Log.e("download","onSuccess:"+ response);
                    }

                    @Override
                    public void onProgressChange(long fileSize, long downloadedSize) {
                        super.onProgressChange(fileSize, downloadedSize);
                        Log.e("download","onProgressChange:"+downloadedSize+ "--totalsize:"+ fileSize);
                    }

                    @Override
                    public void onError(String error) {
                        super.onError(error);
                        Log.e("download","error:"+error);
                    }
                });
                break;
        }
    }*/
}
