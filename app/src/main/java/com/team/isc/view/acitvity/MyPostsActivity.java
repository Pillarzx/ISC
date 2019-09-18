package com.team.isc.view.acitvity;

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
import com.team.isc.common.Flag;
import com.team.isc.bean.Posts;
import com.team.isc.util.SPUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyPostsActivity extends AppCompatActivity {

    ListView myposts_listview;
    ArrayList<Posts> postsArrayList;
    Handler handler;
    ImageView myposts_postsuserimg,myposts_postsimg;
    TextView myposts_postsuser,myposts_poststime,myposts_poststext,myposts_postscommentnum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_posts);
        bind();
        initView();
    }
    void bind(){
        myposts_listview=findViewById(R.id.myposts_listview);
    }

    void initView(){
        getMypostsList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.MYPOSTSACTIVITY_MSG){
                    postsArrayList=(ArrayList<Posts>) msg.obj;
                    MypostsAdapter mypostsAdapter=new MypostsAdapter();
                    myposts_listview.setAdapter(mypostsAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }

    public void getMypostsList(){
        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("uno", SPUtil.getInt("uno")+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/MypostsServlet").post(formBody).build();
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
                    Log.d("isc", "MypostsServletresponseStr==" + responseStr);
                    Gson gson = new Gson();
                    postsArrayList = gson.fromJson(responseStr, new TypeToken<ArrayList<Posts>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.MYPOSTSACTIVITY_MSG;
                    message.obj=postsArrayList;
                    handler.sendMessage(message);
                }
            }
        });
    }
    public void myPostsback(View view) {
        finish();
    }

    class MypostsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return postsArrayList.size();
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
            View viewitem=getLayoutInflater().inflate(R.layout.activity_mypostslist_item,null);
            myposts_postsuser=viewitem.findViewById(R.id.myposts_postsuser);
            myposts_postsimg=viewitem.findViewById(R.id.myposts_postsimg);
            myposts_poststext=viewitem.findViewById(R.id.myposts_poststext);
            myposts_postscommentnum=viewitem.findViewById(R.id.myposts_postscommentnum);
            myposts_poststime=viewitem.findViewById(R.id.myposts_poststime);
            myposts_postsuserimg=viewitem.findViewById(R.id.myposts_postsuserimg);

            Posts current=postsArrayList.get(position);
            myposts_postsuser.setText(current.getUanme());
            myposts_poststime.setText(current.getPdatetime());
            myposts_poststext.setText(current.getPtext());
            myposts_postscommentnum.setText(current.getPcommentnum());
            return viewitem;

        }
    }
}
