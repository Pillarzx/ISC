package com.team.isc.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.team.isc.R;

import java.util.ArrayList;
import java.util.HashMap;

public class ActivityFragment extends Fragment {

    protected View afView;
    ListView activitylistview;
    ImageView activityimg;
    TextView activitytitle,activityhost,activitydatetime,activityplace;

    ArrayList<HashMap<String,Object>> activitylists;
    ActivityAdapter activityAdapter;

    public ActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }

    }

    private void bind(){
        activitylistview=afView.findViewById(R.id.activity_listview);
    }

    private void binditem(){
        activityimg=afView.findViewById(R.id.activity_img);
        activitytitle=afView.findViewById(R.id.activity_title);
        activityhost=afView.findViewById(R.id.activity_host);
        activitydatetime=afView.findViewById(R.id.activity_datetime);
        activityplace=afView.findViewById(R.id.activity_place);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        afView=inflater.inflate(R.layout.fragment_activity, container, false);
        bind();

        activitylists=new ArrayList<HashMap<String, Object>>();
//        activitylists=(       );

        activityAdapter=new ActivityAdapter();
        activitylistview.setAdapter(activityAdapter);
        return afView;
    }


    /**
     * 活动列表适配器
     */
    class ActivityAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return activitylists.size();
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
            binditem();

            HashMap<String,Object> current=activitylists.get(position);
            activitytitle.setText(current.get("a_title").toString());
            activityhost.setText(current.get("a_host").toString());
            activitydatetime.setText(current.get("a_datetime").toString());
            activityplace.setText(current.get("a_place").toString());
            return viewitem;
        }
    }


}
