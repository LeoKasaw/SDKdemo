package com.example.mylibrary;

import android.content.Context;
import android.util.Log;

public  class TestDemoJS {
    Context  appContext;
    InitListener initListener;
   static String colorStr="#000000";

    public  void init(Context context,String appId) {
        if (null != context) {
            appContext = context;
            Log.i("SDK初始化","SDK初始化成功");
            if(appId.equals("1852009")){
                initListener.sucessCallback();
            }else {
                Log.i("SDK初始化","SDK初始化失败，AppId错误");
                initListener.failCallback(100024);
            }
        }
    }
    public   void setInitListener(InitListener initListener){
        this.initListener=initListener;
    }
    public  void setColorStr(String colorStr){
        this.colorStr=colorStr;
    }
    public static String  getColorStr(){
        return colorStr;
    }
    interface InitListener{
        void  sucessCallback();
        void  failCallback(int code);
    }

}
