package com.example.sdkdemo;

import android.content.Context;
import android.util.Log;

public  class TestDemoJS {
  static Context  appContext;
    public static void init(Context context) {
        if (null != context) {
            appContext = context;
            Log.i("SDK初始化","SDK初始化成功");
        }
    }
}
