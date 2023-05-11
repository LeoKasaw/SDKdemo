package com.example.mylibrary.yealin.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.example.mylibrary.yealin.SampleActivity;
import com.yealink.base.utils.SharedPreferencesHelper;
import com.yealink.base.utils.ToastUtil;
import com.yealink.module.common.utils.ErrorUtils;
import com.yealink.sdk.api.IAuthService;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.sdk.base.account.LoginParam;
import com.yealink.sdk.base.account.LoginStatus;
import com.yealink.ylservice.model.BizCodeModel;

public class PartyLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_NAME = "ACCOUNT_NAME";
    private static final String KEY_PWD = "ACCOUNT_PWD";

    private EditText mNameText;
    private EditText mPwdText;
    private IAuthService mAuthService;

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, PartyLoginActivity.class);
        activity.startActivity(intent);
    }

    private IAuthListener mAuthListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {
            ToastUtil.toast(PartyLoginActivity.this, "Login success!");
            goMainActivity();
        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {
            ToastUtil.toast(PartyLoginActivity.this, ErrorUtils.getBizCodeMessage(nCode));
        }

        @Override
        public void onLoginOut(BizCodeModel nCode) {

        }

        @Override
        public void onOffline() {

        }

        @Override
        public void onMyInfoChange() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_login);
        mNameText = findViewById(R.id.cloud_email);
        mPwdText = findViewById(R.id.cloud_password);
        findViewById(R.id.login).setOnClickListener(this);
        mAuthService = YealinkSdk.getAccountService();
        mAuthService.addAccountCallBack(mAuthListener);

        String name = SharedPreferencesHelper.getInstance().getString(KEY_NAME, "");
        if (!TextUtils.isEmpty(name)) {
            mNameText.setText(name);
        }
        String pwd = SharedPreferencesHelper.getInstance().getString(KEY_PWD, "");
        if (!TextUtils.isEmpty(pwd)) {
            mPwdText.setText(pwd);
        }

        if (LoginStatus.REGED.equals(YealinkSdk.getAccountService().getLoginStatus())) {
            goMainActivity();
        }
    }

    private void goMainActivity() {
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

    private void login() {
        ToastUtil.toast(this, "登录中...");
        String name = mNameText.getText().toString();
        String pwd = mPwdText.getText().toString();

        SharedPreferencesHelper.getInstance().putString(KEY_NAME, name);
        SharedPreferencesHelper.getInstance().putString(KEY_PWD, pwd);

        LoginParam loginParam = new LoginParam();
        loginParam.account = name;
        loginParam.password = pwd;
        mAuthService.thirdPartyLogin(loginParam);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login) {
            login();
        }
    }
}
