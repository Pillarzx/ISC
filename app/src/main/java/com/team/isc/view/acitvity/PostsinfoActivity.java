package com.team.isc.view.acitvity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.bean.Posts;
import com.team.isc.bean.Postscomment;
import com.team.isc.util.SPUtil;
import com.team.isc.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class PostsinfoActivity extends AppCompatActivity {
    ImageView postsinfo_userimg,postsinfo_img;
    TextView postsinfo_user,postsinfo_datetime,postsinfo_text,postsinfo_commentnum;
    LinearLayout postsinfo_linearadd;
    Posts posts;
    Handler handler,submithandler;
    ArrayList<Postscomment> postscommentArrayList;
    //ListView item控件
    ImageView postscommentuserimage;
    TextView postscommentnickname,postscommenttext;
    EditText editpostinfo_pctext;
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
        editpostinfo_pctext=findViewById(R.id.editpostinfo_pctext);
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

    public void postinfoSubmit(View view) {
        String editpctext=editpostinfo_pctext.getText().toString();
        if(editpctext.isEmpty()){
            Toast.makeText(PostsinfoActivity.this,"请输入评论内容！",Toast.LENGTH_SHORT).show();
        }else if(SPUtil.getInt("uno",-1)==-1){
            Toast.makeText(PostsinfoActivity.this,"请在登陆后评论！",Toast.LENGTH_SHORT).show();
        } else {
            //转成json字符串后发送
            JSONObject obj=new JSONObject();
            try {
                obj.put("uno", SPUtil.getInt("uno"));
                obj.put("pno",posts.getPno());
                obj.put("pctext", URLEncoder.encode(editpctext,"UTF-8") );
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String objstr=obj.toString();
            sendpostscomment(objstr);

            submithandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what==Flag.POSTSINFOSENGCOMMEN_MSG){
                        downloaddata();
                        Toast.makeText(PostsinfoActivity.this, "发送成功！", Toast.LENGTH_LONG).show();
                        editpostinfo_pctext.setText("");
                    }
                }
            };
        }
    }

    public void sendpostscomment(String objstr){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/AddPostscommentServlet").post(RequestBody.create(mediaType,objstr)).build();
        Log.d("ISC","Postsinfo commentobjstr)=="+objstr);
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
                    Log.d("isc", "AddPostscomentServletresponseStr==" + responseStr);
                    if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                        Message message=new Message();
                        message.what=Flag.POSTSINFOSENGCOMMEN_MSG;
                        submithandler.sendMessage(message);
                    }else {
                        Looper.prepare();
                        Toast.makeText(PostsinfoActivity.this, "服务器错误!请联系管理员或稍后重试！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            }
        });
    }

}
