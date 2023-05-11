package com.example.mylibrary.yealin.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.example.mylibrary.yealin.ItemData;
import com.example.mylibrary.yealin.SampleActivity;
import com.example.mylibrary.yealin.meeting.JoinMeetingWithoutLoginActivity;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.account.LoginStatus;
import com.yealink.ylservice.model.BizCodeModel;

/**
 * @author: chenqingfu
 * @date: 2021/11/12.
 * @email: chenqf@yealink.com
 * @describe:
 */
public class PreLoginActivity extends SampleActivity {

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PreLoginActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LoginStatus loginStatus = YealinkSdk.getAccountService().getLoginStatus();
        if (LoginStatus.REGED.equals(loginStatus)) {
            goMainActivity();
        }
    }

    private void goMainActivity() {
        SampleActivity.start(this);
        finish();
    }

    @Override
    protected void onLoginSuccess() {
        goMainActivity();
    }

    @Override
    protected void onLoginOut(BizCodeModel nCode) {

    }

    @Override
    protected ItemData getDatas() {
        ItemData root = ItemData.create("PreLogin");

        ItemData talk = ItemData.create("账号登录").setTargetActivity(LoginActivity.class);
//                .addChildItem("创建会议", CreateMeetingActivity.class)
//                .addChildItem("加入会议", JoinMeetingActivity.class)
//                .addChildItem("会议UI设置", MeetingUISettingActivity.class);
        root.addChildItem(talk);

        ItemData emailLogin = ItemData.create("邮箱登录");
        emailLogin.setTargetActivity(EmailLoginActivity.class);
        root.addChildItem(emailLogin);

        ItemData regist = ItemData.create("域账号登录");
        regist.setTargetActivity(QueryServerInfoActivity.class);
        root.addChildItem(regist);

        ItemData regist1 = ItemData.create("AuthCode登录");
        regist1.setTargetActivity(AuthCodeLoginActivity.class);
        root.addChildItem(regist1);

        ItemData joinMeeting = ItemData.create("未登录入会");
        joinMeeting.setTargetActivity(JoinMeetingWithoutLoginActivity.class);
        root.addChildItem(joinMeeting);
        return root;
    }
}
