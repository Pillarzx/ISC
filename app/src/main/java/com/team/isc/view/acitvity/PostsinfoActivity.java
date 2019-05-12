package com.team.isc.view.acitvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.model.News;
import com.team.isc.model.Newscomment;
import com.team.isc.model.Posts;
import com.team.isc.model.Postscomment;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PostsinfoActivity extends AppCompatActivity {
    ImageView postsinfo_userimg,postsinfo_img;
    TextView postsinfo_user,postsinfo_datetime,postsinfo_text,postsinfo_commentnum;
    LinearLayout postsinfo_linearadd;
    Posts posts;
    Handler handler;
    ArrayList<Postscomment> postscommentArrayList;
    //ListView item控件
    ImageView postscommentuserimage;
    TextView postscommentnickname,postscommenttext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postsinfo);
        bind();
        init();
        downloaddata();
    }
    void bind(){
        postsinfo_userimg=findViewById(R.id.postsinfo_userimg);
        postsinfo_img=findViewById(R.id.postsinfo_img);
        postsinfo_user=findViewById(R.id.postsinfo_user);
        postsinfo_datetime=findViewById(R.id.postsinfo_datetime);
        postsinfo_text=findViewById(R.id.postsinfo_text);

        postsinfo_commentnum=findViewById(R.id.postsinfo_commentnum);
        postsinfo_linearadd=findViewById(R.id.postsinfo_linearadd);
    }
    void init(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        posts=(Posts) bundle.getSerializable("posts");
        Log.d("ISC","poststostring==="+posts.toString());
        postsinfo_user.setText(posts.getUanme());
        postsinfo_datetime.setText(posts.getPdatetime());
        postsinfo_text.setText(posts.getPtext());
        postsinfo_commentnum.setText(posts.getPcommentnum());
    }

    void downloaddata(){
        getPostscommentList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what== Flag.ACTIVITYPOSTINFO_MSG){
                    postscommentArrayList=(ArrayList<Postscomment>) msg.obj;
                    postsinfo_linearadd.removeAllViews();
                    for(int i=0;i<postscommentArrayList.size();i++){
                        View viewitem = getLayoutInflater().inflate(R.layout.activity_postsinfolist_item,null);
                        postscommentuserimage=viewitem.findViewById(R.id.postscommentuserimage);
                        postscommentnickname=viewitem.findViewById(R.id.postscommentnickname);
                        postscommenttext=viewitem.findViewById(R.id.postscommenttext);

                        Postscomment current=postscommentArrayList.get(i);
                        postscommentnickname.setText(current.getUname());
                        postscommenttext.setText(current.getPctext());
                        postscommentuserimage.setImageResource(R.drawable.ic_launcher_background);
                        postsinfo_linearadd.addView(viewitem);
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
    public void getPostscommentList(){
        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("pno",posts.getPno()+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/PostscommentServlet").post(formBody).build();
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
                    Log.d("isc", "PostscommentServlet获取数据成功了");
                    Log.d("isc", "PostscommentServletresponse.code()==" + response.code());
                    Log.d("isc", "PostscommentServletresponseStr==" + responseStr);
                    Gson gson = new Gson();

                    postscommentArrayList = gson.fromJson(responseStr, new TypeToken<ArrayList<Postscomment>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.ACTIVITYPOSTINFO_MSG;
                    message.obj=postscommentArrayList;
                    handler.sendMessage(message);
                    Log.d("isc", "(内部)postscommentArrayList ==" + postscommentArrayList.toString());
                }
            }
        });
    }

    public void postinfo_back(View view) {
        finish();
    }
}
