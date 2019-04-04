package com.team.isc.presenter;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by ZX on 2019/4/3.
 * 注册服务
 */

public class Register {
    private String url="http://47.103.16.59:8080/dbserverdemo/RegLet";
    public static boolean isRegisterSuccess=false;
    private String username,password;

    public void getInput(String user,String pwd) {
        this.username=user;
        this.password=pwd;
    }

    public boolean getisRegisterSuccess() {
        return isRegisterSuccess;
    }
    public void setState(boolean b){
        this.isRegisterSuccess=b;
    }
    /**
     * 提交注册信息
     */
    public void postRegisterParameter(){
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("username",username).add("password",password).build();
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
                if(response.isSuccessful()){
                    Log.d("isc","获取数据成功了");
                    Log.d("isc","response.code()=="+response.code());
                    Log.d("isc","responseStr=="+responseStr);
                    if(responseStr.contains("Success")){
                        Register register=new Register();
                        register.setState(true);
                    }else {
                        Register register=new Register();
                        register.setState(false);
                    }

                }

            }
        });
    }
}
