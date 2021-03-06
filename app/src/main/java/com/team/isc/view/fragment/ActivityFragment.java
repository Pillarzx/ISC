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
import com.team.isc.bean.ActivityBean;
import com.team.isc.view.acitvity.ActinfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ActivityFragment extends Fragment implements AdapterView.OnItemClickListener{

    protected View afView;
    ListView activitylistview;
    ImageView activityimg;
    TextView activitytitle,activityhost,activitydatetime,activityplace;
    ArrayList<ActivityBean> activities;
    ActivityAdapter activityAdapter;
    Handler handler;
    public ActivityFragment() {
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
        afView=inflater.inflate(R.layout.fragment_activity, container, false);
        bind();
        initView();
        activitylistview.setOnItemClickListener(this);
        return afView;
    }


    private void bind(){
        activitylistview=afView.findViewById(R.id.activity_listview);
    }
    void initView(){
        getActivityList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.ACTIVITYFRAGMENT_MSG){
                    activities=(ArrayList<ActivityBean>)msg.obj;
                    activityAdapter=new ActivityAdapter();
                    activitylistview.setAdapter(activityAdapter);
                }
                super.handleMessage(msg);
            }
        };
    }
    /**
     * 请求活动信息
     */
    public void getActivityList() {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/ActivityServlet").build();
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
//                    Log.d("isc", "ActivityServlet获取数据成功了");
//                    Log.d("isc", "ActivityServletresponse.code()==" + response.code());
//                    Log.d("isc", "ActivityServletresponseStr==" + responseStr);
                    Gson gson = new Gson();

                    activities = gson.fromJson(responseStr, new TypeToken<ArrayList<ActivityBean>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.ACTIVITYFRAGMENT_MSG;
                    message.obj=activities;
                    handler.sendMessage(message);
                    Log.d("isc", "(内部)activityArrayList==" + activities.toString());
                }
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(),ActinfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("activity",activities.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }
    @Override
    public void onResume() {
        super.onResume();
        initView();
    }

    /**
     * 活动列表适配器
     */
    class ActivityAdapter extends BaseAdapter{

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

            View viewitem=getLayoutInflater().inflate(R.layout.fragment_activitylist_item,null);
            activityimg=viewitem.findViewById(R.id.activity_img);
            activitytitle=viewitem.findViewById(R.id.activity_title);
            activityhost=viewitem.findViewById(R.id.activity_host);
            activitydatetime=viewitem.findViewById(R.id.activity_datetime);
            activityplace=viewitem.findViewById(R.id.activity_place);

            ActivityBean current=activities.get(position);
            Log.d("ISC",activities.get(position).toString());
            Log.d("ISC","position="+position);
            activitytitle.setText(current.getAtitle());
            activityhost.setText(current.getAhost());
            activitydatetime.setText(current.getAdatetime());
            activityplace.setText(current.getAplace());
            return viewitem;
        }
    }


}
