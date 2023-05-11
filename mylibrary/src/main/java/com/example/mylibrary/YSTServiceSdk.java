package com.example.mylibrary;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.example.mylibrary.utils.LogUtils;
import com.example.mylibrary.yealin.meeting.MyMeetingUi;
import com.example.mylibrary.yealin.phone.MyPhoneUI;

public  class YSTServiceSdk {
    static Context  appContext;
    static InitListener initListeners;
    static YealinkSdkInitListener yealinkSdkInitListeners;
   static String colorStrs="#000000";

    public static void init(Context context, String appId, InitListener initListener) {
        if (null != context) {
            appContext = context;
            initListeners = initListener;
            LogUtils.i("SDK初始化成功");
            if(appId.equals("1852009")){
                //进行第三方SDK的初始化
//                YealinkSdk.initSdk(application,"A6S0HITbFucuEos9s5aWIw0oEWkZG1S4", // 全局上下文
//                        "4ddcd55e811243b2b917699719424a49", // appSecret
//                        new SdkInitListener() { // 初始化结果监听
//                            @Override
//                            public void onSuccess() {
//                            // SDK初始化成功
//                                YealinkSdk.getAccountService().setDispatcherHost("192.168.1.151");
//                                SharedPreferencesHelper.getInstance().putBoolean("KEY_SHOW_TOAST", false);
//                                YealinkSdk.getSettingService().setShowToast(false);
//                                YealinkSdk.getSettingService().setShareMoveTaskToBack(false);
//                                YealinkSdk.getMeetingService().getMeetingUIController().setMeetingUIProxy(new MyMeetingUi());
//                                YealinkSdk.getPhoneService().getPhoneController().setPhoneUIProxy(new MyPhoneUI());
//                                initListeners.sucessCallback();
//                            }
//                            @Override
//                            public void onFailure() {
//                            // SDK初始化失败
//                                initListeners.failCallback(100025);
//                            }
//                        });
                initListeners.sucessCallback();
            }else {
                LogUtils.i("SDK初始化失败，AppId错误");
                initListeners.failCallback(100024);
            }
        }
    }
    public static void initYealinkSdk(Application application,final String appSecret,final String appId, YealinkSdkInitListener yealinkSdkInitListener) {
        if (null != appContext) {
            LogUtils.i("SDK初始化成功");
                //进行第三方SDK的初始化
//                YealinkSdk.initSdk(application,appSecret, // 全局上下文
//                        appId, // appSecret
//                        new SdkInitListener() { // 初始化结果监听
//                            @Override
//                            public void onSuccess() {
////                            // SDK初始化成功
////                                YealinkSdk.getAccountService().setDispatcherHost("192.168.1.151");
////                                SharedPreferencesHelper.getInstance().putBoolean("KEY_SHOW_TOAST", false);
////                                YealinkSdk.getSettingService().setShowToast(false);
////                                YealinkSdk.getSettingService().setShareMoveTaskToBack(false);
////                                YealinkSdk.getMeetingService().getMeetingUIController().setMeetingUIProxy(new MyMeetingUi());
////                                YealinkSdk.getPhoneService().getPhoneController().setPhoneUIProxy(new MyPhoneUI());
////                                yealinkSdkInitListener.onSuccess();
//                            }
//                            @Override
//                            public void onFailure() {
//                            // SDK初始化失败
////                                yealinkSdkInitListener.onFailure();
//                            }
//                        });
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
    public interface YealinkSdkInitListener{
        void  onSuccess();
        void  onFailure();
    }
}
