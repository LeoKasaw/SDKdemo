package com.example.mylibrary;

import android.content.Context;
import android.util.Log;

public  class TestDemoJS {
    static Context  appContext;
    static InitListener initListeners;
   static String colorStrs="#000000";

    public static void init(Context context,String appId,InitListener initListener) {
        if (null != context) {
            appContext = context;
            initListeners = initListener;
            Log.i("SDK初始化","SDK初始化成功");
            if(appId.equals("1852009")){
                initListeners.sucessCallback();
            }else {
                Log.i("SDK初始化","SDK初始化失败，AppId错误");
                initListeners.failCallback(100024);
            }
        }
    }
    public  static void setInitListener(InitListener initListener){
        initListeners=initListener;
    }
    public static void setColorStr(String colorStr){
        colorStrs=colorStr;
    }
    public static String  getColorStr(){
        return colorStrs;
    }
   public interface InitListener{
        void  sucessCallback();
        void  failCallback(int code);
    }

}
