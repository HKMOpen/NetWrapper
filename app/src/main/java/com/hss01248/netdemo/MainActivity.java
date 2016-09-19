package com.hss01248.netdemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.hss01248.net.config.BaseNetBean;
import com.hss01248.net.retrofit.RetrofitAdapter;
import com.hss01248.net.volley.VolleyAdapter;
import com.hss01248.net.wrapper.MyJson;
import com.hss01248.net.wrapper.MyNetListener;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends Activity {

    @Bind(R.id.button)
    Button mButton;
    @Bind(R.id.button2)
    Button mButton2;
    @Bind(R.id.button3)
    Button mButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button:
               /* RetrofitAdapter.getInstance().getString("api/msg/unreadMsg/count/v1.json", new HashMap(), "dd", new MyNetCallback() {
                    @Override
                    public void onSuccess(Object response, String resonseStr) {
                        Log.e("baidu",response.toString());
                    }
                });*/

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
                VolleyAdapter.getInstance(this).download("http://www.qxinli.com/download/qxinli.apk", path, new MyNetListener<String>() {
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
    }
}
