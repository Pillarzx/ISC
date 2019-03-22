package com.team.isc.presenter.okhttp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZX on 2019/3/22.
 */

public class PostRequest {
    String url="123";

    /**
     * 提交注册信息
     * @param context
     * @param name 用户名
     * @param password 用户密码
     */
    public void postRegisterParameter(final Context context, String name, String password){
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add(name,password).build();
        Request request=new Request.Builder().url(url).post(formBody).build();
        Call call=client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure","执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr=response.body().string();
                Toast.makeText(context,"响应码:"+responseStr,Toast.LENGTH_SHORT).show();

            }
        });
    }


}
