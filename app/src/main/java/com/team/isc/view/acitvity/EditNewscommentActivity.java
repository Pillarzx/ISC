package com.team.isc.view.acitvity;

import android.content.Intent;
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
import com.team.isc.model.Newscomment;
import com.team.isc.util.SPUtil;

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

public class EditNewscommentActivity extends AppCompatActivity {

    EditText editnewscommenttext;
    String nno;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_newscomment);
        editnewscommenttext=findViewById(R.id.editnewscommenttext);
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        nno=bundle.getInt("nno")+"";
        Log.d("isc","Oncreate.nno==="+nno);
    }

    public void editNewscommentback(View view){
        finish();
    }

  //存在中文字符格式编码bug
    public void submitComment(View view) {

        String comment = editnewscommenttext.getText().toString();
        if (comment != "") {
            OkHttpClient client = new OkHttpClient();
            FormBody formBody = null;
            try {
                formBody = new FormBody.Builder().add("uno", SPUtil.getInt("uno")+"").add("nno", nno).add("nctext", URLEncoder.encode(comment,"utf-8")).build();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/AddNewscommentServlet").post(formBody).build();
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

                    }
                    Looper.prepare();
                    if(responseStr.contains("error")){
                        Toast.makeText(EditNewscommentActivity.this, "未知错误，请联系管理员解决！", Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(EditNewscommentActivity.this, "评论成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    Looper.loop();

                }
            });
        }else {
            Toast.makeText(EditNewscommentActivity.this, "请输入内容！", Toast.LENGTH_SHORT).show();
        }
    }
}
