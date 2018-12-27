package com.cleven.clchat.API;

import com.alibaba.fastjson.JSON;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.HttpParams;
import com.lzy.okgo.model.Response;

import java.util.HashMap;
import java.util.Map;

public class OkGoUtil {
    private CLNetworkCallBack callBack;
    public CLNetworkCallBack getCallBack() {
        return callBack;
    }
    public void setCallBack(CLNetworkCallBack callBack) {
        this.callBack = callBack;
    }

    public interface CLNetworkCallBack{
        void onSuccess(Map response);
        void onFailure(Map error);
    }

    public static void getRequets(String url, Object tag, HttpParams params, final CLNetworkCallBack callback) {
        OkGo.<String>get(url)
                .tag(tag)
                .params(params)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Map parseMap = (Map)JSON.parse(response.body());
                        if (parseMap.get("error_code").equals("0")){ //请求成功
                            if (callback != null){
                                callback.onSuccess(parseMap);
                            }
                        }else {
                            if (callback != null){
                                callback.onFailure(parseMap);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        HashMap parseMap = new HashMap();
                        parseMap.put("error_code","4500");
                        parseMap.put("error_msg","请求失败");
                        if (callback != null){
                            callback.onFailure(parseMap);
                        }
                    }
                });
    }

    public static void postRequest(String url, Object tag, HttpParams params, final CLNetworkCallBack callback) {

        OkGo.<String>post(url)
                .tag(tag)
                .params(params)
                .execute(new StringCallback(){
                    @Override
                    public void onSuccess(Response<String> response) {
                        Map parseMap = (Map)JSON.parse(response.body());
                        if (parseMap.get("error_code").equals("0")){ //请求成功
                            if (callback != null){
                                callback.onSuccess(parseMap);
                            }
                        }else {
                            if (callback != null){
                                callback.onFailure(parseMap);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        HashMap parseMap = new HashMap();
                        parseMap.put("error_code","4500");
                        parseMap.put("error_msg","请求失败");
                        if (callback != null){
                            callback.onFailure(parseMap);
                        }
                    }
                });
    }
}