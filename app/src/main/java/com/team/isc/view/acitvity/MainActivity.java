package com.team.isc.view.acitvity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.isc.R;
import com.team.isc.common.MainApplication;
import com.team.isc.view.MyViewPager;
import com.team.isc.view.adapter.MainFragmentStatePagerAdapter;
import com.team.isc.view.fragment.ActivityFragment;
import com.team.isc.view.fragment.BlankFragment;
import com.team.isc.view.fragment.BlogFragment;
import com.team.isc.view.fragment.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private List<Fragment> fragments = new ArrayList<Fragment>();
    private MyViewPager myViewPager;
    private LinearLayout llHome, llBlog, llActivity, llBlank;
    private ImageView ivChat, ivFriends, ivContacts, ivSettings, ivCurrent,drawerheadimg;
    private TextView tvChat, tvFriends, tvContacts, tvSettings, tvCurrent;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab; //右下角浮标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);

        bindView();
        initView();
        initData();

    }

    /**
     * 绑定控件
     */
    private void bindView(){
        myViewPager = (MyViewPager) findViewById(R.id.viewPager);
        //侧边栏
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        drawerheadimg = (ImageView)findViewById(R.id.drawer_headimg);
        llHome = (LinearLayout) findViewById(R.id.llHome);
        llBlog = (LinearLayout) findViewById(R.id.llBlog);
        llActivity = (LinearLayout) findViewById(R.id.llActivity);
        llBlank = (LinearLayout) findViewById(R.id.llBlank);

        ivChat = (ImageView) findViewById(R.id.ivChat);
        ivFriends = (ImageView) findViewById(R.id.ivFriends);
        ivContacts = (ImageView) findViewById(R.id.ivContacts);
        ivSettings = (ImageView) findViewById(R.id.ivSettings);

        tvChat = (TextView) findViewById(R.id.tvChat);
        tvFriends = (TextView) findViewById(R.id.tvFriends);
        tvContacts = (TextView) findViewById(R.id.tvContacts);
        tvSettings = (TextView) findViewById(R.id.tvSettings);
    }

    /**
     * 初始化控件
     */
    private void initView() {
        llHome.setOnClickListener(this);
        llBlog.setOnClickListener(this);
        llActivity.setOnClickListener(this);
        llBlank.setOnClickListener(this);

        ivChat.setSelected(true);
        tvChat.setSelected(true);
        ivCurrent = ivChat;
        tvCurrent = tvChat;

        myViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                changeTab(position);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
        myViewPager.setOffscreenPageLimit(2); //设置向左和向右都缓存limit个页面
    }

    /**
     * 初始化界面
     */
    private void initData() {
        Fragment homeFragment = new HomeFragment();
        Fragment blogFragment = new BlogFragment();
        Fragment activityFragment = new ActivityFragment();
        Fragment blankFragment = new BlankFragment();
        fragments.add(homeFragment);
        fragments.add(blogFragment);
        fragments.add(activityFragment);
        fragments.add(blankFragment);

        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }
            @Override
            public void onDrawerOpened(View drawerView) {
                myViewPager.setScroll(true);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                myViewPager.setScroll(false);
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        toggle.syncState();

        //Fragment适配器
        MainFragmentStatePagerAdapter adapter = new MainFragmentStatePagerAdapter(getSupportFragmentManager(), fragments);
        myViewPager.setAdapter(adapter);

        //右下角浮标点击事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });
    }

    /**
     * 左上角抽屉控件
     */
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    /**
//     * 不用自带的actionbar，待删除
//     * @param menu
//     * @return
//     */
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_nd, menu);
//        return true;
//    }

    /**
     * toolbar点击
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 侧滑栏内事件
     * @param item
     * @return
     */
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        item.setCheckable(true);
        item.setChecked(true);
        drawer.closeDrawers();
        int id = item.getItemId();

        switch (id){
            case R.id.drawer_headimg:   //有问题，待解决
//                Toast.makeText(MainActivity.this,"已单击头像",Toast.LENGTH_SHORT).show();
//                drawerheadimg.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
//                        startActivity(intent);
//                    }
//                });
                break;
            case R.id.myact:
                Toast.makeText(MainActivity.this,"已单击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mycomment:
                Toast.makeText(MainActivity.this,"已单击",Toast.LENGTH_SHORT).show();
                break;
            case R.id.myfavorite:
                Toast.makeText(MainActivity.this,"已单击",Toast.LENGTH_SHORT).show();
                break;

            case R.id.nav_manage: break;
            case R.id.nav_share: break;
            case R.id.nav_send: break;
            default:break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * 底部控件点击事件
     * @param v
     */
    @Override
    public void onClick(View v) {
        changeTab(v.getId());
    }

    private void changeTab(int id) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        switch (id) {
            case R.id.llHome:
                myViewPager.setCurrentItem(0);
            case 0:
                ivChat.setSelected(true);
                ivCurrent = ivChat;
                tvChat.setSelected(true);
                tvCurrent = tvChat;
                break;
            case R.id.llBlog:
                myViewPager.setCurrentItem(1);
            case 1:
                ivFriends.setSelected(true);
                ivCurrent = ivFriends;
                tvFriends.setSelected(true);
                tvCurrent = tvFriends;
                break;
            case R.id.llActivity:
                myViewPager.setCurrentItem(2);
            case 2:
                ivContacts.setSelected(true);
                ivCurrent = ivContacts;
                tvContacts.setSelected(true);
                tvCurrent = tvContacts;
                break;
            case R.id.llBlank:
                myViewPager.setCurrentItem(3);
            case 3:
                ivSettings.setSelected(true);
                ivCurrent = ivSettings;
                tvSettings.setSelected(true);
                tvCurrent = tvSettings;
                break;
            default:
                break;
        }
    }

    public void drawerHeadimg(View view){
        SharedPreferences usersp=getSharedPreferences("useraccount", Context.MODE_PRIVATE);
        if(usersp.getString("username","")!=""){
            Intent intent=new Intent(MainActivity.this,UserActivity.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }

}
