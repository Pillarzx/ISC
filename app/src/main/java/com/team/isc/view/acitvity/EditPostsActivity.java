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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.model.Postscomment;
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

public class EditPostsActivity extends AppCompatActivity {
    String uno;
    EditText editposts_ptext;
    Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_posts);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        uno=bundle.getString("uno","");
        editposts_ptext=findViewById(R.id.editposts_ptext);
    }

    public void editpost_back(View view) {
        finish();
    }

    public void editpostssubmit(View view) {
        String ptext=editposts_ptext.getText().toString();
        if(ptext.isEmpty()){
            Toast.makeText(EditPostsActivity.this, "请输入内容后再点击发送", Toast.LENGTH_LONG).show();
        }else {
//转成json字符串后发送
            JSONObject obj=new JSONObject();
            try {
                obj.put("uno",uno);
                obj.put("ptext",URLEncoder.encode(ptext,"UTF-8") );
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String objstr=obj.toString();
            sendEditPosts(objstr);

            handler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    if(msg.what== Flag.EDITPOSTSACTIVITY_MSG){
                        Toast.makeText(EditPostsActivity.this, "发帖成功!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
            };
        }
    }

    void sendEditPosts(String objstr){
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/AddPostsServlet").post(RequestBody.create(mediaType,objstr)).build();
        Log.d("ISC","objstr)=="+objstr);
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
                    Log.d("isc", "AddPostsServletresponseStr==" + responseStr);
                    if(Util.isNumeric(responseStr)&&Integer.parseInt(responseStr)>(-1)){
                        Message message=new Message();
                        message.what=Flag.EDITPOSTSACTIVITY_MSG;
                        handler.sendMessage(message);
                    }else {
                        Looper.prepare();
                        Toast.makeText(EditPostsActivity.this, "服务器错误!请联系管理员或稍后重试！", Toast.LENGTH_LONG).show();
                        Looper.loop();
                    }
                }
            }
        });
    }
}
