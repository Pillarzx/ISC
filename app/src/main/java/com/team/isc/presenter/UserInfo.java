package com.team.isc.presenter;

import android.util.Log;

import com.team.isc.model.User;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZX on 2019/4/3.
 */

public class UserInfo {
    private String url_setRealname="47.103.16.59:8080/ISCServer/SetRealname";
    public static boolean isRealnameDone=false;

    public User getUserInfo(){
        User user=new User();

        return user;
    }

    public void setRealname(String username,String realname){
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
                        UserInfo.isRealnameDone=true;
                    }else {
                        UserInfo.isRealnameDone=false;
                    }

                }

            }
        });
    }
}
