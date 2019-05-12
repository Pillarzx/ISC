package com.team.isc.view.acitvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.model.User;
import com.team.isc.presenter.UserInfo;
import com.team.isc.util.SPUtil;
import com.team.isc.view.userinfo.SetRealnameActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView username,sign,realname,nickname,sex,dept,clas,num,phone,qq;
    private Button back,logout;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        bind();
        init();

    }

    private void bind(){
        username=findViewById(R.id.activity_user_username);
        sign=findViewById(R.id.activity_user_sign);
        realname=findViewById(R.id.activity_user_realname);
        nickname=findViewById(R.id.activity_user_nickname);
        sex=findViewById(R.id.activity_user_sex);
        dept=findViewById(R.id.activity_user_dept);
        clas=findViewById(R.id.activity_user_class);
        num=findViewById(R.id.activity_user_num);
        phone=findViewById(R.id.activity_user_phone);
        qq=findViewById(R.id.activity_user_qq);
        back=findViewById(R.id.activity_user_back);
        logout=findViewById(R.id.logout);

        username.setOnClickListener(this);
        sign.setOnClickListener(this);
        realname.setOnClickListener(this);
        nickname.setOnClickListener(this);
        sex.setOnClickListener(this);
        dept.setOnClickListener(this);
        clas.setOnClickListener(this);
        num.setOnClickListener(this);
        phone.setOnClickListener(this);
        qq.setOnClickListener(this);
        back.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    private void init(){
        getUserInfo();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what== Flag.USERINFO_MSG){
                    username.setText(SPUtil.getString("username"));
                    sign.setText(SPUtil.getString("sign"));
                    realname.setText(SPUtil.getString("realname"));
                    nickname.setText(SPUtil.getString("nickname"));
                    sex.setText(SPUtil.getString("sex"));
                    dept.setText(SPUtil.getString("dept"));
                    clas.setText(SPUtil.getString("class"));
                    num.setText(SPUtil.getString("num"));
                    phone.setText(SPUtil.getString("phone"));
                    qq.setText(SPUtil.getString("qq"));
                }
                super.handleMessage(msg);
            }
        };



    }

    public void getUserInfo(){
        OkHttpClient client=new OkHttpClient();

        Log.d("ISC","SPUtil.getString(username)=="+SPUtil.getString("username"));

        FormBody formBody=new FormBody.Builder().add("username", SPUtil.getString("username")).build();
        Request request=new Request.Builder().url("http://47.103.16.59:8080/ISCServer/UserInfo").post(formBody).build();
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
                    Gson gson=new Gson();
                    User user=gson.fromJson(responseStr,User.class);
                    Log.d("isc","user对象："+user.toString());
                    SPUtil.putInt("uno",user.getUno());
                    SPUtil.putInt("iid",user.getIid());
                    SPUtil.putString("username",user.getUname());
                    SPUtil.putString("nickname",user.getUnickname());
                    SPUtil.putString("sign",user.getUsign());
                    SPUtil.putInt("role",user.getUrole());
                    SPUtil.putString("realname",user.getUrealname());
                    SPUtil.putString("sex",user.getUsex());
                    SPUtil.putString("dept",user.getUdept());
                    SPUtil.putString("class",user.getUclass());
                    SPUtil.putString("num",user.getUnum());
                    SPUtil.putString("phone",user.getUphone());
                    SPUtil.putString("qq",user.getUqq());
                    SPUtil.putInt("rp",user.getUrp());
                    Message message=new Message();
                    message.what=Flag.USERINFO_MSG;
                    handler.sendMessage(message);
                }
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        init();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.activity_user_back:
                finish();
                break;
            case R.id.logout:
                SPUtil.clear();
                finish();
                break;
            case R.id.a:
                startActivity(new Intent(UserActivity.this, SetRealnameActivity.class));
                break;

        }
    }
}
