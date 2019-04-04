package com.team.isc.common;


import android.app.Application;

import java.util.HashMap;

/**
 * Created by ZX on 2019/3/24.
 */

public class MainApplication extends Application {
    private static MainApplication mApp;
    public HashMap<String,String> mInfoMap=new HashMap<String, String>();

    public static MainApplication getInstance(){
        return mApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApp=this;
    }
}
