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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.bean.NewscommentBean;
import com.team.isc.common.Flag;
import com.team.isc.bean.NewsBean;
import com.team.isc.util.SPUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsinfoActivity extends AppCompatActivity {

    TextView newsinfotitle,newsinfoauth,newsinfodate,newsinfotext;
    //    ListViewForScrollView newsinfolistview;
    ArrayList<NewscommentBean> newscommentBeanArrayList;
    Handler handler;
    NewsBean newsBean;
    //    NewscommentAdapter newscommentAdapter;
    ImageView newscommentuserimage;
    TextView newscommentnickname,newscommenttext;
    LinearLayout linearadd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsinfo);
        bind();
        init();
        downloaddata();
    }

    void bind(){
        newsinfotitle=findViewById(R.id.newsinfo_title);
        newsinfoauth=findViewById(R.id.newsinfo_auth);
        newsinfodate=findViewById(R.id.newsinfo_date);
        newsinfotext=findViewById(R.id.newsinfo_text);
//        newsinfolistview=findViewById(R.id.newsinfo_listview);
        linearadd=findViewById(R.id.linearadd);
    }
    void init(){
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        newsBean =(NewsBean) bundle.getSerializable("newsBean");
        Log.d("ISC","newstostring==="+ newsBean.toString());
        newsinfotitle.setText(newsBean.getNtitle());
        newsinfoauth.setText(newsBean.getUname());
        newsinfodate.setText(newsBean.getNdate());
        newsinfotext.setText(newsBean.getNtext());
    }
    void downloaddata(){
        getNewscommentList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what== Flag.NEWSINFOACTIVITY_MSG){
                    newscommentBeanArrayList =(ArrayList<NewscommentBean>) msg.obj;
//                    newscommentAdapter=new NewscommentAdapter();
//                    newsinfolistview.setAdapter(newscommentAdapter);
                    linearadd.removeAllViews();
                    for(int i = 0; i< newscommentBeanArrayList.size(); i++){
//                        View viewitem = LayoutInflater.from(this).inflate(R.layout.item_linear, null);
                        View viewitem = getLayoutInflater().inflate(R.layout.activity_newsinfolist_item,null);
                        newscommentuserimage=viewitem.findViewById(R.id.newscommentuserimage);
                        newscommentnickname=viewitem.findViewById(R.id.newscommentnickname);
                        newscommenttext=viewitem.findViewById(R.id.newscommenttext);

                        NewscommentBean current= newscommentBeanArrayList.get(i);
                        newscommentnickname.setText(current.getUname());
                        newscommenttext.setText(current.getNctext());
                        newscommentuserimage.setImageResource(R.drawable.ic_launcher_background);
                        linearadd.addView(viewitem);
                    }
                }
                super.handleMessage(msg);
            }
        };
    }
    public void getNewscommentList(){
        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("nno", newsBean.getNno()+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/NewscommentServlet").post(formBody).build();
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
                    Log.d("isc", "NewscommentServlet获取数据成功了");
                    Log.d("isc", "NewscommentServletresponse.code()==" + response.code());
                    Log.d("isc", "NewscommentServletresponseStr==" + responseStr);
                    Gson gson = new Gson();

                    newscommentBeanArrayList = gson.fromJson(responseStr, new TypeToken<ArrayList<NewscommentBean>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.NEWSINFOACTIVITY_MSG;
                    message.obj= newscommentBeanArrayList;
                    handler.sendMessage(message);
                    Log.d("isc", "(内部)newscommentBeanArrayList==" + newscommentBeanArrayList.toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        downloaddata();
    }

    public void back(View view){
        finish();
    }

    public void addComment(View view) {
        if(SPUtil.contains("uno")){
            Intent intent=new Intent(NewsinfoActivity.this,EditNewscommentActivity.class);
            Bundle bundle=new Bundle();
            Log.d("ISC","发送news.getnno====="+ newsBean.getNno());
            bundle.putInt("nno", newsBean.getNno());
            intent.putExtras(bundle);

            startActivity(intent);
        }else {
            Toast.makeText(NewsinfoActivity.this,"请登录后留言！",Toast.LENGTH_LONG).show();
        }
    }
}
