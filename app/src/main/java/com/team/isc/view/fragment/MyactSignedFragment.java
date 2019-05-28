package com.team.isc.view.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.team.isc.R;
import com.team.isc.common.Flag;
import com.team.isc.model.Activity;
import com.team.isc.util.SPUtil;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyactSignedFragment extends Fragment {

    protected View msfView;
    ListView myactsigned_listview;
    ArrayList<Activity> activities;
    Handler handler;
    TextView myactsigned_title,myactsigned_text,myactsigned_host,myactsigned_datetime,myactsigned_place,myactsigned_type,myactsigned_maxnumber,myactsigned_cost;
    MyactSignedAdapter myactSignedAdapter;
    public MyactSignedFragment() {
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
        msfView=inflater.inflate(R.layout.fragment_myact_signed, container, false);
        bind();
        initView();
        return msfView;
    }

    void bind(){
        myactsigned_listview=msfView.findViewById(R.id.myactsigned_listview);
    }
    void initView(){
        getMyactsignedList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.MYACTSIGNEDFRAGMENT_MSG){
                    activities=(ArrayList<Activity>)msg.obj;
                    myactSignedAdapter=new MyactSignedAdapter();
                    myactsigned_listview.setAdapter(myactSignedAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void getMyactsignedList() {

        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("uno", SPUtil.getInt("uno")+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/MyactsignedServlet").post(formBody).build();
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
                    Log.d("isc", "MyactsignedServletresponseStr==" + responseStr);
                    Gson gson = new Gson();
                    activities = gson.fromJson(responseStr, new TypeToken<ArrayList<Activity>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.MYACTSIGNEDFRAGMENT_MSG;
                    message.obj=activities;
                    handler.sendMessage(message);
                }
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
    }
    class MyactSignedAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return activities.size();
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

            View viewitem=getLayoutInflater().inflate(R.layout.fragment_myact_signed_listitem,null);
            myactsigned_title=viewitem.findViewById(R.id.myactsigned_title);
            myactsigned_text=viewitem.findViewById(R.id.myactsigned_text);
            myactsigned_host=viewitem.findViewById(R.id.myactsigned_host);
            myactsigned_datetime=viewitem.findViewById(R.id.myactsigned_datetime);
            myactsigned_place=viewitem.findViewById(R.id.myactsigned_place);
            myactsigned_type=viewitem.findViewById(R.id.myactsigned_type);
            myactsigned_maxnumber=viewitem.findViewById(R.id.myactsigned_maxnumber);
            myactsigned_cost=viewitem.findViewById(R.id.myactsigned_cost);

            Activity current=activities.get(position);
            myactsigned_title.setText(current.getAtitle());
            myactsigned_text.setText(current.getAdatetime());
            myactsigned_host.setText(current.getAhost());
            myactsigned_datetime.setText(current.getAdatetime());
            myactsigned_place.setText(current.getAplace());
            myactsigned_type.setText(current.getAtype());
            myactsigned_maxnumber.setText(current.getAmaxnumber()+"");
            myactsigned_cost.setText(current.getAcost()+"");
            return viewitem;
        }
    }
}
