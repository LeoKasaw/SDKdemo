package com.example.mylibrary.yealin.account;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.account.AccountInfo;
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.ylservice.model.BizCodeModel;

public class LogoutActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mInfoText;

    private IAuthListener mAuthListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {

        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {

        }

        @Override
        public void onLoginOut(BizCodeModel nCode) {
            finish();
        }

        @Override
        public void onOffline() {

        }

        @Override
        public void onMyInfoChange() {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        findViewById(R.id.login_out).setOnClickListener(this);
        YealinkSdk.getAccountService().addAccountCallBack(mAuthListener);
        mInfoText = findViewById(R.id.my_info);
        printMyInfo();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getAccountService().removeAccountCallBack(mAuthListener);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login_out){
            YealinkSdk.getAccountService().logOut();
        }
    }

    public void printMyInfo(){
        AccountInfo info = YealinkSdk.getAccountService().getAccountInfo();
        if(info == null){
            // 说明个人信息还在拉取中，等待个人信息更新
            return;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("姓名：").append(info.getName()).append("\n");
        sb.append("号码：").append(info.getNumber()).append("\n");
        sb.append("企业：").append(info.getPartyName()).append("\n");
        sb.append("性别：").append(info.getUserGender()).append("\n");
        sb.append("邮箱：").append(info.getEmailAddress()).append("\n");
        sb.append("支持立即会议：").append(info.isSupportMeetingNow()).append("\n");
        mInfoText.setText(sb.toString());
    }

}