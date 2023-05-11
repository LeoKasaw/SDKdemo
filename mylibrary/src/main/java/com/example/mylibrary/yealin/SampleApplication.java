package com.example.mylibrary.yealin;

import android.app.Activity;
import android.app.Application;

import com.yealink.base.utils.ToastUtil;
import com.yealink.module.common.OnShareEvent;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.IMeetingEvent;
import com.yealink.ylservice.ActivityStackManager;
import com.yealink.ylservice.model.BizCodeModel;

public class SampleApplication extends Application {

    IMeetingEvent mMeetingEvent = new IMeetingEvent() {
        @Override
        public void onConnected() {
            ToastUtil.toast(SampleApplication.this,"onConnected");
        }

        @Override
        public void onFinish(BizCodeModel bizCode) {
            ToastUtil.toast(SampleApplication.this,"onFinished");
        }

    };

    @Override
    public void onCreate() {
        super.onCreate();
        ActivityStackManager.getInstance().init(this);
        YealinkSdk.getMeetingService().addMeetingEvent(mMeetingEvent);
        YealinkSdk.setOnShareEvent(new OnShareEvent() {
            @Override
            public void shareToWechat(Activity activity, String url, String title, String content) {
                ToastUtil.toast(activity,"微信分享：" + content);
            }

            @Override
            public void shareToQQ(Activity activity, String shareMessage) {
                ToastUtil.toast(activity,"QQ分享" + shareMessage);
            }
        });
    }
}
