package com.team.isc.view.acitvity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.team.isc.R;
import com.team.isc.view.adapter.MyActFragmentStatePagerAdapter;
import com.team.isc.view.fragment.MyactInitFragment;
import com.team.isc.view.fragment.MyactSignedFragment;

import java.util.ArrayList;
import java.util.List;

public class MyActActivity extends AppCompatActivity {

    TabLayout tablayout_act;
    ViewPager viewpager_act;
    private List<Fragment> fragmentList;
    private List<String> list_Title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_act);
        bindView();
        init();
    }
    void bindView(){
        tablayout_act=findViewById(R.id.tablayout_act);
        viewpager_act=findViewById(R.id.viewpager_act);
    }
    void init(){
        fragmentList=new ArrayList<>();
        fragmentList.add(new MyactInitFragment());
        fragmentList.add(new MyactSignedFragment());
        list_Title=new ArrayList<>();
        list_Title.add("已发布活动");
        list_Title.add("已报名活动");
        MyActFragmentStatePagerAdapter myActFragmentStatePagerAdapter=new MyActFragmentStatePagerAdapter(getSupportFragmentManager(),MyActActivity.this,fragmentList,list_Title);
        viewpager_act.setAdapter(myActFragmentStatePagerAdapter);

        TabLayout.Tab tab1=tablayout_act.newTab().setText("自定义1");
        TabLayout.Tab tab2=tablayout_act.newTab().setText("自定义2");
        tablayout_act.addTab(tab1);
        tablayout_act.addTab(tab2);
        tablayout_act.setupWithViewPager(viewpager_act);
    }

    public void myActback(View view) {
        finish();
    }
}
