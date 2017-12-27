package com.zt;

import android.app.Application;

import com.itheima.retrofitutils.ItheimaHttp;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by zhangteng on 2017/12/15.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ItheimaHttp.init(this, "http://gank.io/api/data/");
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
