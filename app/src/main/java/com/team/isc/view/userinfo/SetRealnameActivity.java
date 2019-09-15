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
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SetRealnameActivity extends AppCompatActivity {
    EditText etrealname;
    Handler handler;
    String realname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_realname);
        etrealname=findViewById(R.id.setrealname);
    }
    public void back(View v){
        finish();
    }
    public void setRealname(View view){
        realname=etrealname.getText().toString();
        try {
            sendRealname(URLEncoder.encode(realname,"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(msg.what== Flag.SETREALNAMEACTIVITY_MSG){
                    SPUtil.putString("realname",realname);
                    Toast.makeText(SetRealnameActivity.this,"修改成功",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        };
    }

    void sendRealname(String realname){
        Log.d("isc", "yidanji ");
        OkHttpClient client=new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("uno",SPUtil.getInt("uno")+"").add("realname",realname).build();
        Request request=new Request.Builder().url("http://47.103.16.59:8080/ISCServer/SetRealname").post(formBody).build();
        Call call=client.newCall(request);
        Log.d("isc", "zhixingdaozhe  ");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure","执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr=response.body().string();
                Log.d("isc", "SetRealnameServlet==" + responseStr);
                if(response.isSuccessful()){
                    if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                        Message message=new Message();
                        message.what= Flag.SETREALNAMEACTIVITY_MSG;
                        handler.sendMessage(message);
                    }
                }
            }
        });
    }
}
