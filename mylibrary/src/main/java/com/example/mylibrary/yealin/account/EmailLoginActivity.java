package com.example.mylibrary.yealin.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.BuildConfig;
import com.example.mylibrary.R;
import com.example.mylibrary.yealin.SampleActivity;
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

public class EmailLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_NAME = "KEY_EMAIL_NAME";
    private static final String KEY_PWD = "KEY_EMAIL_PWD";
    private static final String KEY_SERVER = "KEY_EMAIL_SERVER";

    private EditText mNameText;
    private EditText mPwdText;
    private EditText mServerText;
    private TextView mStatusText;
    private TextView mInfoText;
    private IAuthService mAuthService;

    public static void start(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity, EmailLoginActivity.class);
        activity.startActivity(intent);
    }

    private IAuthListener mAuthListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {
            ToastUtil.toast(EmailLoginActivity.this,"Login success!");
            printMyInfo();
            updateState();
            goMainActivity();
        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {
            ToastUtil.toast(EmailLoginActivity.this, ErrorUtils.getBizCodeMessage(nCode));
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);
        mNameText = findViewById(R.id.cloud_email);
        mPwdText = findViewById(R.id.cloud_password);
        mServerText = findViewById(R.id.cloud_server);
        mStatusText = findViewById(R.id.cloud_status);
        mInfoText = findViewById(R.id.my_info);
        findViewById(R.id.login).setOnClickListener(this);
        mAuthService = YealinkSdk.getAccountService();
        mAuthService.addAccountCallBack(mAuthListener);

        if (BuildConfig.DEBUG) {
            mNameText.setText("aaaa@163.com");
            mPwdText.setText("v123456789");
            mServerText.setText("onylyun.com");
        }
        String name = SharedPreferencesHelper.getInstance().getString(KEY_NAME,"");
        if(!TextUtils.isEmpty(name)){
            mNameText.setText(name);
        }
        String pwd = SharedPreferencesHelper.getInstance().getString(KEY_PWD,"");
        if(!TextUtils.isEmpty(pwd)){
            mPwdText.setText(pwd);
        }
        String server = SharedPreferencesHelper.getInstance().getString(KEY_SERVER,"");
        if(!TextUtils.isEmpty(server)){
            mServerText.setText(server);
        }

        printMyInfo();
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

        String name = mNameText.getText().toString();
        String pwd = mPwdText.getText().toString();
        String server = mServerText.getText().toString();
        mAuthService.setDispatcherHost(server);

        SharedPreferencesHelper.getInstance().putString(KEY_NAME,name);
        SharedPreferencesHelper.getInstance().putString(KEY_PWD,pwd);
        SharedPreferencesHelper.getInstance().putString(KEY_SERVER,server);

        LoginParam loginParam = new LoginParam();
        loginParam.account = name;
        loginParam.password = pwd;
        loginParam.loginType = LoginParam.LoginType.Email;
        mAuthService.login(loginParam);
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
