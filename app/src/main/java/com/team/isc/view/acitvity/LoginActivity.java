package com.team.isc.view.acitvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.util.SPUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etusername,etpassword;
    Handler handler;
    private String username,password;
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
        if(SPUtil.getString("username","")!=null){
            etusername.setText(SPUtil.getString("username",""));
            etpassword.setText(SPUtil.getString("password",""));
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
        this.username=etusername.getText().toString();
        this.password=etpassword.getText().toString();
        if(username.contains(";")||username.contains("*")||username.contains(" ")||password.contains(";")||password.contains("*")||password.contains(" ")){
            etusername.setText("");
            etpassword.setText("");
            Toast.makeText(LoginActivity.this,"不得含有非法字符“；”、“*”和空格",Toast.LENGTH_SHORT).show();
        }else {
           postLoginParameter();
            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    int flag=msg.what;
                    switch (flag){
                        case Flag.LOGIN_MSGTRUE:
                            Toast.makeText(LoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(LoginActivity.this,UserActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case Flag.LOGIN_MSGFALSE:
                            etusername.setText("");
                            etpassword.setText("");
                            Toast.makeText(LoginActivity.this,"帐号或密码错误!",Toast.LENGTH_SHORT).show();
                            break;
                    }
                    super.handleMessage(msg);
                }
            };

        }

    }
    public void postLoginParameter(){
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("username",username).add("password",password).build();
        Request request=new Request.Builder().url("http://47.103.16.59:8080/ISCServer/LogLet").post(formBody).build();
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
//                    Log.d("isc","获取数据成功了");
//                    Log.d("isc","response.code()=="+response.code());
//                    Log.d("isc","responseStr=="+responseStr);
                    Message message=new Message();
                    if(responseStr.contains("true")){
                        SPUtil.putString("username",username);
                        SPUtil.putString("password",password);
                        message.what=Flag.LOGIN_MSGTRUE;
                        handler.sendMessage(message);
                    }else {
                        message.what=Flag.LOGIN_MSGFALSE;
                        handler.sendMessage(message);
                    }

                }

            }
        });
    }
}

