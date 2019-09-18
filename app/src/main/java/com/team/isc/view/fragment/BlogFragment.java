package com.team.isc.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.bean.Posts;
import com.team.isc.view.acitvity.PostsinfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class BlogFragment extends Fragment implements AdapterView.OnItemClickListener{
    protected View bfView;
    ListView bloglistview;
    ArrayList<Posts> postsArrayList;
    Handler handler;
    BlogAdapter blogAdapter;
    ImageView postsuserimg,postsimg;
    TextView postsuser,poststime,poststext,postscommentnum;

    public BlogFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        bfView=inflater.inflate(R.layout.fragment_blog, container, false);
        bloglistview=bfView.findViewById(R.id.blog_listview);
        initView();
        bloglistview.setOnItemClickListener(this);
        return bfView;
    }

    void initView(){
        getPostsList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.BLOGFRAGMENT_MSG){
                    postsArrayList=(ArrayList<Posts>) msg.obj;
                    blogAdapter=new BlogAdapter();
                    bloglistview.setAdapter(blogAdapter);

                }

                super.handleMessage(msg);
            }
        };
    }
    public void getPostsList(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/PostsServlet").build();
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
//                    Log.d("isc", "PostsServlet获取数据成功了");
//                    Log.d("isc", "PostsServletresponse.code()==" + response.code());
//                    Log.d("isc", "PostsServletresponseStr==" + responseStr);
                    Gson gson = new Gson();

                    postsArrayList = gson.fromJson(responseStr, new TypeToken<ArrayList<Posts>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.BLOGFRAGMENT_MSG;
                    message.obj=postsArrayList;
                    handler.sendMessage(message);
                    Log.d("isc", "(内部)postsArrayList==" + postsArrayList.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(),PostsinfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("posts",postsArrayList.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    class BlogAdapter extends BaseAdapter{

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
            View viewitem=getLayoutInflater().inflate(R.layout.fragment_bloglist_item,null);
            postsuser=viewitem.findViewById(R.id.postsuser);
            poststime=viewitem.findViewById(R.id.poststime);
            poststext=viewitem.findViewById(R.id.poststext);
            postscommentnum=viewitem.findViewById(R.id.postscommentnum);
            postsuserimg=viewitem.findViewById(R.id.postsuserimg);
            postsimg=viewitem.findViewById(R.id.postsimg);

            Posts current=postsArrayList.get(position);
            postsuser.setText(current.getUanme());
            poststime.setText(current.getPdatetime());
            poststext.setText(current.getPtext());
            postscommentnum.setText(current.getPcommentnum());
            return viewitem;
        }
    }


}
