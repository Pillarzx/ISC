package com.team.isc.view.userinfo;

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
import com.team.isc.util.Util;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetPhoneActivity extends AppCompatActivity {

    EditText setphone;
    Handler handler;
    String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_phone);
        setphone=findViewById(R.id.setphone);
    }

    public void setphoneback(View view) {
        finish();
    }

    public void setPhone(View view) {
        phone=setphone.getText().toString();
        sendPhone(phone);
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what== Flag.SETPHONEACTIVITY_MSG){
                    SPUtil.putString("phone",phone);
                    Toast.makeText(SetPhoneActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

    void sendPhone(String phone){
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("uno", SPUtil.getInt("uno")+"").add("phone",phone).build();
        Request request=new Request.Builder().url("http://47.103.16.59:8080/ISCServer/SetPhoneServlet").post(formBody).build();
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
                    if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                        Message message=new Message();
                        message.what= Flag.SETPHONEACTIVITY_MSG;
                        handler.sendMessage(message);
                    }
                }
            }
        });
    }
}
