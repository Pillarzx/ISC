package com.team.isc.presenter;

import android.util.Log;

import com.google.gson.Gson;
import com.team.isc.common.MainApplication;
import com.team.isc.model.User;
import com.team.isc.util.SPUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZX on 2019/4/3.
 *
 */

@Deprecated
public class UserInfo {
    private String url_setRealname="http://47.103.16.59:8080/ISCServer/UserInfo";
    private String url_UserInfo="http://47.103.16.59:8080/ISCServer/UserInfo";
    public static String userjson;

    public static boolean isRealnameDone=false;

    public void setRealname(String username, final String realname){
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("username",username).add("realname",realname).build();
        Request request=new Request.Builder().url(url_setRealname).post(formBody).build();
        Call call=client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure","执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr=response.body().string();
                if(response.isSuccessful()){
                    Log.d("isc","获取数据成功了");
                    Log.d("isc","response.code()=="+response.code());
                    Log.d("isc","responseStr=="+responseStr);
                    if(responseStr.contains("Success")){
                        SPUtil.putString("realname",realname);
                        UserInfo.isRealnameDone=true;
                    }else {
                        UserInfo.isRealnameDone=false;
                    }

                }

            }
        });
    }
}
