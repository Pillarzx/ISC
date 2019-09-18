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
import com.team.isc.bean.ActivityBean;
import com.team.isc.common.Flag;
import com.team.isc.util.SPUtil;
import com.team.isc.view.acitvity.ActInitinfoActivity;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MyactInitFragment extends Fragment implements AdapterView.OnItemClickListener{

    protected View mifView;
    ListView myact_listview;
    ArrayList<ActivityBean> activities;
    TextView myactinit_title,myactinit_datetime,myactinit_place;
    ImageView myactinit_img;
    MyactInitAdapter myactInitAdapter;
    Handler handler;
    public MyactInitFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mifView=inflater.inflate(R.layout.fragment_myact_init, container, false);
        bind();
        initView();
        myact_listview.setOnItemClickListener(this);
        return mifView;
    }
    void bind(){
        myact_listview=mifView.findViewById(R.id.myact_listview);
    }
    void initView(){
        getMyactinitList();
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what==Flag.MYACTINITFRAGMENT_MSG){
                    activities=(ArrayList<ActivityBean>)msg.obj;
                    myactInitAdapter=new MyactInitAdapter();
                    myact_listview.setAdapter(myactInitAdapter);
                }
                super.handleMessage(msg);
            }
        };

    }
    public void getMyactinitList() {

        OkHttpClient client = new OkHttpClient();
        FormBody formBody=new FormBody.Builder().add("uno", SPUtil.getInt("uno")+"").build();
        Request request = new Request.Builder().url("http://47.103.16.59:8080/ISCServer/MyactinitServlet").post(formBody).build();
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
                    Gson gson = new Gson();
                    activities = gson.fromJson(responseStr, new TypeToken<ArrayList<ActivityBean>>() {
                    }.getType());
                    Message message = new Message();
                    message.what = Flag.MYACTINITFRAGMENT_MSG;
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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(getActivity(),ActInitinfoActivity.class);
        Bundle bundle=new Bundle();
        bundle.putSerializable("activity",activities.get(i));
        intent.putExtras(bundle);
        startActivity(intent);
    }


    class MyactInitAdapter extends BaseAdapter {

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

            View viewitem=getLayoutInflater().inflate(R.layout.fragment_myact_init_listitem,null);
            myactinit_img=viewitem.findViewById(R.id.myactinit_img);
            myactinit_title=viewitem.findViewById(R.id.myactinit_title);
            myactinit_datetime=viewitem.findViewById(R.id.myactinit_datetime);
            myactinit_place=viewitem.findViewById(R.id.myactinit_place);

            ActivityBean current=activities.get(position);
            myactinit_title.setText(current.getAtitle());
            myactinit_datetime.setText(current.getAdatetime());
            myactinit_place.setText(current.getAplace());
            return viewitem;
        }
    }
}
