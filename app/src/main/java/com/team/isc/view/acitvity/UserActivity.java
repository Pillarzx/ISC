package com.team.isc.view.acitvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.bean.UserBean;
import com.team.isc.util.SPUtil;
import com.team.isc.view.userinfo.SetClassActivity;
import com.team.isc.view.userinfo.SetDeptActivity;
import com.team.isc.view.userinfo.SetNicknameActivity;
import com.team.isc.view.userinfo.SetNumActivity;
import com.team.isc.view.userinfo.SetPhoneActivity;
import com.team.isc.view.userinfo.SetQQActivity;
import com.team.isc.view.userinfo.SetRealnameActivity;
import com.team.isc.view.userinfo.SetSexActivity;

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
    RelativeLayout setlayout_realname,setlayout_nickname,setlayout_sex,setlayout_dept,setlayout_class,setlayout_num,setlayout_phone,setlayout_qq;
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

        setlayout_realname=findViewById(R.id.setlayout_realname);
        setlayout_nickname=findViewById(R.id.setlayout_nickname);
        setlayout_sex=findViewById(R.id.setlayout_sex);
        setlayout_dept=findViewById(R.id.setlayout_dept);
        setlayout_class=findViewById(R.id.setlayout_class);
        setlayout_num=findViewById(R.id.setlayout_num);
        setlayout_phone=findViewById(R.id.setlayout_phone);
        setlayout_qq=findViewById(R.id.setlayout_qq);

        username.setOnClickListener(this);
        sign.setOnClickListener(this);

        setlayout_realname.setOnClickListener(this);
        setlayout_nickname.setOnClickListener(this);
        setlayout_sex.setOnClickListener(this);
        setlayout_dept.setOnClickListener(this);
        setlayout_class.setOnClickListener(this);
        setlayout_num.setOnClickListener(this);
        setlayout_phone.setOnClickListener(this);
        setlayout_qq.setOnClickListener(this);

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
                    if("f".equals(SPUtil.getString("sex"))){
                        sex.setText("女");
                    }else{
                        sex.setText("男");
                    }
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
                    UserBean userBean =gson.fromJson(responseStr, UserBean.class);
                    Log.d("isc","user对象："+ userBean.toString());
                    SPUtil.putInt("uno", userBean.getUno());
                    SPUtil.putInt("iid", userBean.getIid());
                    SPUtil.putString("username", userBean.getUname());
                    SPUtil.putString("nickname", userBean.getUnickname());
                    SPUtil.putString("sign", userBean.getUsign());
                    SPUtil.putInt("role", userBean.getUrole());
                    SPUtil.putString("realname", userBean.getUrealname());
                    SPUtil.putString("sex", userBean.getUsex());
                    SPUtil.putString("dept", userBean.getUdept());
                    SPUtil.putString("class", userBean.getUclass());
                    SPUtil.putString("num", userBean.getUnum());
                    SPUtil.putString("phone", userBean.getUphone());
                    SPUtil.putString("qq", userBean.getUqq());
                    SPUtil.putInt("rp", userBean.getUrp());
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
            case R.id.setlayout_realname:
                startActivity(new Intent(UserActivity.this, SetRealnameActivity.class));
                break;
            case R.id.setlayout_nickname:
                startActivity(new Intent(UserActivity.this, SetNicknameActivity.class));
                break;
            case R.id.setlayout_sex:
                startActivity(new Intent(UserActivity.this, SetSexActivity.class));
                break;
            case R.id.setlayout_dept:
                startActivity(new Intent(UserActivity.this, SetDeptActivity.class));
                break;
            case R.id.setlayout_class:
                startActivity(new Intent(UserActivity.this, SetClassActivity.class));
                break;
            case R.id.setlayout_num:
                startActivity(new Intent(UserActivity.this, SetNumActivity.class));
                break;
            case R.id.setlayout_phone:
                startActivity(new Intent(UserActivity.this, SetPhoneActivity.class));
                break;
            case R.id.setlayout_qq:
                startActivity(new Intent(UserActivity.this, SetQQActivity.class));
                break;

        }
    }
}
