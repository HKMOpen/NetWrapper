package com.hss01248.net.wrapper;

import com.hss01248.net.config.ConfigInfo;

/**
 * Created by Administrator on 2016/9/19.
 */
public class NetUtil2 {

    public static ConfigInfo setConfig(){
        return new ConfigInfo();
    }

    public static void assignRequest(ConfigInfo configInfo){

        switch (configInfo.type){
            case ConfigInfo.TYPE_STRING:
                break;
            case ConfigInfo.TYPE_JSON:
                break;
            case ConfigInfo.TYPE_JSON_FORMATTED:
                break;

        }

    }

}
