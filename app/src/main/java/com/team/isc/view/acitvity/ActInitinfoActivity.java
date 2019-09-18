package com.team.isc.view.acitvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.bean.ActivityBean;
import com.team.isc.common.Flag;
import com.team.isc.bean.Roster;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActInitinfoActivity extends AppCompatActivity {

    ActivityBean activityBean;
    ArrayList<Roster> rosters;
    ListView myactinitinfo_listview;
    Handler handler;
    MyactInitAdapter myactInitAdapter;

    TextView myactinitinfo_realname,myactinitinfo_sex,myactinitinfo_dept,myactinitinfo_class,myactinitinfo_num;
    ImageView myactinitinfo_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myact_initinfo);
        bind();
        init();
        initView();
    }

    void bind(){
        myactinitinfo_listview=findViewById(R.id.myactinitinfo_listview);
    }
    void init(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        activityBean =(ActivityBean) bundle.getSerializable("activityBean");
    }

    void initView(){
        getMyactinitinfoList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.MYACTINITINFOACTIVITY_MSG){
                    rosters=(ArrayList<Roster>)msg.obj;
                    myactInitAdapter=new MyactInitAdapter();
                    myactinitinfo_listview.setAdapter(myactInitAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void myActinitinfoback(View view) {
        finish();
    }

    public void getMyactinitinfoList() {

        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("ano", activityBean.getAno()+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/RosterlistServlet").post(formBody).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d("onFailure", "执行onFailure方法");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseStr = response.body().string();
                if (response.isSuccessful()) {
                    Gson gson = new Gson();
                    rosters = gson.fromJson(responseStr, new TypeToken<ArrayList<Roster>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.MYACTINITINFOACTIVITY_MSG;
                    message.obj=rosters;
                    handler.sendMessage(message);
                }
            }
        });
    }

    class MyactInitAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return rosters.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            View viewitem=getLayoutInflater().inflate(R.layout.activity_myact_initinfo_item,null);
            myactinitinfo_realname=viewitem.findViewById(R.id.myactinitinfo_realname);
            myactinitinfo_sex=viewitem.findViewById(R.id.myactinitinfo_sex);
            myactinitinfo_dept=viewitem.findViewById(R.id.myactinitinfo_dept);
            myactinitinfo_class=viewitem.findViewById(R.id.myactinitinfo_class);
            myactinitinfo_num=viewitem.findViewById(R.id.myactinitinfo_num);

            Roster current=rosters.get(position);
            myactinitinfo_realname.setText(current.getUrealname());
            if(current.getUsex()=="f") {
                myactinitinfo_sex.setText("女");
            }else{
                myactinitinfo_sex.setText("男");
            }
            myactinitinfo_dept.setText(current.getUdept());
            myactinitinfo_class.setText(current.getUclass());
            myactinitinfo_num.setText(current.getUnum());
            return viewitem;
        }
    }
}
