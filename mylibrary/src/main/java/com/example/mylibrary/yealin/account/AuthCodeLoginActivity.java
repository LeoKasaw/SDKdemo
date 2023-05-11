package com.example.mylibrary.yealin.account;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.BuildConfig;
import com.example.mylibrary.R;
import com.example.mylibrary.yealin.SampleActivity;
import com.yealink.base.callback.CallBack;
import com.yealink.base.debug.YLog;
import com.yealink.base.utils.SharedPreferencesHelper;
import com.yealink.base.utils.ToastUtil;
import com.yealink.module.common.utils.ErrorUtils;
import com.yealink.sdk.api.IAuthService;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.account.AccountInfo;
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.sdk.base.account.LoginParam;
import com.yealink.sdk.base.account.LoginStatus;
import com.yealink.ylservice.model.BizCodeModel;

public class AuthCodeLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_NAME = "NAME";
    private static final String KEY_PWD = "PWD";
    private static final String KEY_SERVER = "SERVER";

    private EditText etAuthCode;
    private TextView mStatusText;
    private TextView mInfoText;
    private IAuthService mAuthService;

    public static void start(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity, AuthCodeLoginActivity.class);
        activity.startActivity(intent);
    }

    private IAuthListener mAuthListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {
            ToastUtil.toast(AuthCodeLoginActivity.this,"Login success!");
            printMyInfo();
            updateState();
            goMainActivity();
        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {
            ToastUtil.toast(AuthCodeLoginActivity.this, ErrorUtils.getBizCodeMessage(nCode));
        }

        @Override
        public void onLoginOut(BizCodeModel nCode) {

        }

        @Override
        public void onOffline() {
            updateState();
        }

        @Override
        public void onMyInfoChange() {
            printMyInfo();
        }
    };

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.au_code_login);
        etAuthCode = findViewById(R.id.et_auth_code);
        mStatusText = findViewById(R.id.cloud_status);
        mInfoText = findViewById(R.id.my_info);
        findViewById(R.id.login).setOnClickListener(this);
        mAuthService = YealinkSdk.getAccountService();
        mAuthService.addAccountCallBack(mAuthListener);

        if (BuildConfig.DEBUG) {
            etAuthCode.setText("d7dc75353f684db2a85023c93c15f464");
        }

        updateState();
        if(LoginStatus.REGED.equals(YealinkSdk.getAccountService().getLoginStatus())){
            goMainActivity();
        }
    }

    private void goMainActivity(){
        Intent intent = new Intent();
        intent.setClass(this, SampleActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAuthService.removeAccountCallBack(mAuthListener);
    }

    private void updateState(){
        switch (mAuthService.getLoginStatus()){
            case UNKNOWN:
                mStatusText.setText("未知");
                break;
            case REG_FAILED:
                mStatusText.setText("掉线");
                break;
            case REGED:
                mStatusText.setText("已登录");
                break;
            case REGING:
                mStatusText.setText("登录中……");
                break;
        }
    }


    private void login(){
        switch (mAuthService.getLoginStatus()){
            case UNKNOWN:
            case REG_FAILED:
                mStatusText.setText("掉线");
                break;
            case REGED:
            case REGING:
                ToastUtil.toast(this,"账号已登录或在登录中……");
                break;
        }

        String authCode = etAuthCode.getText().toString();
        mAuthService.loginByAuthCode(authCode, new CallBack<Void, BizCodeModel>() {
            @Override
            public void onSuccess(Void unused) {
                super.onSuccess(unused);
                YLog.i("TAG","onSuccess");
            }

            @Override
            public void onFailure(BizCodeModel bizCodeModel) {
                super.onFailure(bizCodeModel);
                YLog.i("TAG","onFailure="+bizCodeModel.getBizCode()+"  "+bizCodeModel.getMsg());
            }
        });
        updateState();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.login){
            login();
        }
    }

    public void printMyInfo(){
        AccountInfo info = mAuthService.getAccountInfo();
        if(info == null){
            // 说明个人信息还在拉取中，等待个人信息更新
            return;
        }
        if (!LoginStatus.REGED.equals(mAuthService.getLoginStatus())) {
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
