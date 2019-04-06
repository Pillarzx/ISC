package com.team.isc.view.acitvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.team.isc.R;
import com.team.isc.model.User;
import com.team.isc.presenter.UserInfo;
import com.team.isc.util.SPUtil;
import com.team.isc.view.userinfo.SetRealnameActivity;

public class UserActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView username,sign,realname,nickname,sex,dept,clas,num,phone,qq;
    private Button back,logout;


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
        UserInfo userInfo=new UserInfo();
        User user=userInfo.getUserInfo();

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
            case R.id.activity_user_realname:
                startActivity(new Intent(UserActivity.this, SetRealnameActivity.class));
                break;

        }
    }
}
