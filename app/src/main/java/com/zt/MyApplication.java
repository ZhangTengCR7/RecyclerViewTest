package com.zt;

import android.app.Application;

import com.itheima.retrofitutils.ItheimaHttp;

/**
 * Created by zhangteng on 2017/12/15.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ItheimaHttp.init(this, "http://gank.io/api/data/");
    }
}
