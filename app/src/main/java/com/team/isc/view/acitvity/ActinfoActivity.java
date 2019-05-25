package com.team.isc.view.acitvity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.model.Activity;
import com.team.isc.model.Newscomment;
import com.team.isc.util.SPUtil;
import com.team.isc.util.Util;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActinfoActivity extends AppCompatActivity {

    TextView acttitle,acttext,acthost,actdatetime,acttype,actplace,actmaxnumber,actcurrentnumber,actcost;
    Activity activity;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actinfo);
        bind();
        init();
    }

    void bind(){
        acttitle=findViewById(R.id.acttitle);
        acttext=findViewById(R.id.acttext);
        acthost=findViewById(R.id.acthost);
        actdatetime=findViewById(R.id.actdatetime);
        acttype=findViewById(R.id.acttype);
        actplace=findViewById(R.id.actplace);
        actmaxnumber=findViewById(R.id.actmaxnumber);
        actcost=findViewById(R.id.actcost);
        actcurrentnumber=findViewById(R.id.actcurrentnumber);
    }
    void init(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        activity=(Activity) bundle.getSerializable("activity");
        acttitle.setText(activity.getAtitle());
        acttext.setText(activity.getAtext());
        acthost.setText(activity.getAhost());
        actdatetime.setText(activity.getAdatetime());
        acttype.setText(activity.getAtype());
        actplace.setText(activity.getAplace());
        actmaxnumber.setText(activity.getAmaxnumber()+"");
        actcost.setText(activity.getAcost()+"");
        actcurrentnumber.setText("1");//当前报名人数
    }
    public void back(View view){
        finish();
    }

    public void join(View view){
        if(Util.checkUserAccount()){
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("确认");
            builder.setMessage("请仔细确认活动信息，报名后请准时参加活动。您的信息将要发送给发布者，确认报名吗？");
            builder.setPositiveButton("确认参加", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    sendUserinfo(activity.getAno());
                    handler=new Handler(){
                        @Override
                        public void handleMessage(Message msg) {
                            super.handleMessage(msg);
                            if(msg.what==Flag.ACTINFOACTIVITY_MSG){
                                Toast.makeText(ActinfoActivity.this, "报名成功！请准时参加", Toast.LENGTH_LONG).show();
                            }else if(msg.what==Flag.ACTINFOACTIVITY_EXISTEDMSG){
                                Toast.makeText(ActinfoActivity.this, "您已报名，请勿重复报名！", Toast.LENGTH_LONG).show();
                            }else {

                            }
                        }
                    };
                }
            });
            builder.setNegativeButton("我再想想",null);
            AlertDialog alert=builder.create();
            alert.show();
        }else {
            Toast.makeText(ActinfoActivity.this,"请先完善个人信息再报名",Toast.LENGTH_LONG).show();
        }
    }

    void sendUserinfo(int ano){
        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("ano",ano+"").add("uno", SPUtil.getInt("uno")+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/RosterServlet").post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                Log.d("isc", "RosterServletresponseStr==" + responseStr);

                if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                    Message message=new Message();
                    message.what=Flag.ACTINFOACTIVITY_MSG;
                    handler.sendMessage(message);
                }else if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)==(-3)){
                    Message message=new Message();
                    message.what=Flag.ACTINFOACTIVITY_EXISTEDMSG;
                    handler.sendMessage(message);
                }else{
                    Looper.prepare();
                    Toast.makeText(ActinfoActivity.this, "服务器错误!请联系管理员或稍后重试！", Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }
}
