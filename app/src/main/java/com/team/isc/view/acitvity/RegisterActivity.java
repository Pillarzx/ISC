package com.team.isc.view.acitvity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.util.SPUtil;
import com.team.isc.util.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class RegisterActivity extends Activity {

    private EditText username_register,password_register;
    private Button sign_up_button;
    Handler handler;
    String username,password;

    private void bindView(){
        username_register=findViewById(R.id.username_register);
        password_register=findViewById(R.id.password_register);
        sign_up_button=findViewById(R.id.sign_up_button);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindView();

    }

    public void back(View v){
        finish();
    }

    public void signUp(View v){
        username=username_register.getText().toString();
        password=password_register.getText().toString();
        if(username.contains(";")||username.contains("*")||username.contains(" ")||password.contains(";")||password.contains("*")||password.contains(" ")){
            username_register.setText("");
            password_register.setText("");
            Toast.makeText(RegisterActivity.this,"不得含有非法字符“；”、“*”和空格",Toast.LENGTH_SHORT).show();
        }else {
            /**
             * 提交注册信息
             */
            OkHttpClient client=new OkHttpClient();
            FormBody formBody=new FormBody.Builder().add("username",username).add("password",password).build();
            Request request=new Request.Builder().url("http://47.103.16.59:8080/ISCServer/RegLet").post(formBody).build();
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
                        if(Util.isNumeric(responseStr)){
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();

                            SPUtil.putString("username",username);
                            SPUtil.putString("password",password);
                            SPUtil.putString("uno",responseStr);
                        }else {
                            Toast.makeText(RegisterActivity.this,"用户名已存在,请重新输入",Toast.LENGTH_LONG).show();
                            username_register.setText("");
                            password_register.setText("");
                        }

                    }

                }
            });


        }
    }

}
