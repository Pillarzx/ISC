package com.team.isc.view.acitvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.common.MainApplication;
import com.team.isc.presenter.Login;

public class LoginActivity extends AppCompatActivity {

    // UI references.

    private EditText etusername,etpassword;
    public static String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bind();
        init();
    }
    private void bind(){
        etusername=findViewById(R.id.username);
        etpassword=findViewById(R.id.password);
    }

    private void init(){
        SharedPreferences sharedPreferences=getSharedPreferences("useraccount", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("username","")!=null){
            etusername.setText(sharedPreferences.getString("username",""));
            etpassword.setText(sharedPreferences.getString("password",""));
        }
    }
    public void backlogin(View view){
        finish();
    }

    public void userSignup(View view){
        Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    public  void userSignin(View view){
        String username=etusername.getText().toString();
        String password=etpassword.getText().toString();
        if(username.contains(";")||username.contains("*")||username.contains(" ")||password.contains(";")||password.contains("*")||password.contains(" ")){
            etusername.setText("");
            etpassword.setText("");
            Toast.makeText(LoginActivity.this,"不得含有非法字符“；”、“*”和空格",Toast.LENGTH_SHORT).show();
        }else {
            Login login=new Login();
            login.getInput(username,password);
            login.postLoginParameter();
            if(login.getisLoginSuccess()){
                MainApplication.getInstance().mInfoMap.put("username",username);
                SharedPreferences sharedPreferences=getSharedPreferences("useraccount", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("username",username);
                editor.putString("password",password);
                editor.commit();
                Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                finish();
            }else {
                etusername.setText("");
                etpassword.setText("");
                Toast.makeText(LoginActivity.this,"帐号或密码错误!",Toast.LENGTH_SHORT).show();
            }

        }

    }


}

