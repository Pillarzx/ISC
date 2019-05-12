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
import com.team.isc.model.News;
import com.team.isc.view.acitvity.NewsinfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class NewsFragment extends Fragment implements AdapterView.OnItemClickListener{

    protected View nfview;
    ListView newslistview;
    ArrayList<News> newsArrayList;
    Handler handler;
    NewsAdapter newsAdapter;
    TextView newstitle,newstext,newsauth,newsdate;
    ImageView newsimage;
    public NewsFragment() {
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
        nfview= inflater.inflate(R.layout.fragment_news, container, false);
        newslistview=nfview.findViewById(R.id.news_listview);
        getNewsList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.NEWSFRAGMENT_MSG){
                    newsArrayList=(ArrayList<News>) msg.obj;
                    newsAdapter=new NewsAdapter();
                    newslistview.setAdapter(newsAdapter);
                }
                super.handleMessage(msg);
            }
        };

        newslistview.setOnItemClickListener(this);
        return nfview;

    }
    public void getNewsList(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/NewsServlet").build();
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
//                    Log.d("isc", "NewsServlet获取数据成功了");
//                    Log.d("isc", "NewsServletresponse.code()==" + response.code());
//                    Log.d("isc", "NewssServletresponseStr==" + responseStr);
                    Gson gson = new Gson();

                    newsArrayList = gson.fromJson(responseStr, new TypeToken<ArrayList<News>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.NEWSFRAGMENT_MSG;
                    message.obj=newsArrayList;
                    handler.sendMessage(message);
                    Log.d("isc", "(内部)postsArrayList==" + newsArrayList.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(),NewsinfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("news",newsArrayList.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    class NewsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsArrayList.size();
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
            View viewitem=getLayoutInflater().inflate(R.layout.fragment_newslist_item,null);
            newstitle=viewitem.findViewById(R.id.news_title);
            newstext=viewitem.findViewById(R.id.news_text);
            newsdate=viewitem.findViewById(R.id.news_date);
            newsauth=viewitem.findViewById(R.id.news_auth);
            newsimage=viewitem.findViewById(R.id.news_image);

            News current=newsArrayList.get(position);
            newstitle.setText(current.getNtitle());
            newsdate.setText(current.getNdate());
            newsauth.setText(current.getUname());
            newstext.setText(current.getNtext().substring(0,30)+"...");

            return viewitem;
        }
    }

}

