package com.example.mylibrary.yealin.account;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

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
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.sdk.base.account.LoginParam;
import com.yealink.sdk.base.account.LoginStatus;
import com.yealink.ylservice.account.bean.ServiceFeatureModel;
import com.yealink.ylservice.model.BizCodeModel;

public class QueryServerInfoActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "QueryServerInfoActivity";

    private static final String KEY_YMS_SERVER = "YMS_SERVER";

    private EditText mServerText;
    private IAuthService mAuthService;

    IAuthListener authListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {
            finish();
        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {

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

    public static void start(Activity activity) {
        Intent intent = new Intent();
        intent.setClass(activity, QueryServerInfoActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_server_info);
        mServerText = findViewById(R.id.cloud_server);
        findViewById(R.id.tv_query_server).setOnClickListener(this);
        mAuthService = YealinkSdk.getAccountService();
        YealinkSdk.getAccountService().addAccountCallBack(authListener);

        if (BuildConfig.DEBUG) {
            mServerText.setText("onylyun.com");
        }
        String server = SharedPreferencesHelper.getInstance().getString(KEY_YMS_SERVER, "");
        if (!TextUtils.isEmpty(server)) {
            mServerText.setText(server);
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

    private void requestDispatcherHost() {
        String server = mServerText.getText().toString();
        SharedPreferencesHelper.getInstance().putString(KEY_YMS_SERVER, server);
        mAuthService.requestDispatcherHost(server, new CallBack<ServiceFeatureModel.TenantMode, BizCodeModel>() {
            @Override
            public void onSuccess(ServiceFeatureModel.TenantMode tenantMode) {
                YLog.i(TAG, "requestDispatcherHost onSuccess: " + tenantMode);
                if (ServiceFeatureModel.TenantMode.Single.equals(tenantMode)) {
                    // 单租户直接跳转域账号登录页，引导用户输入账号密码
                    PartyLoginActivity.start(QueryServerInfoActivity.this);
                } else if (ServiceFeatureModel.TenantMode.Multi.equals(tenantMode)) {
                    // 多租户需再输入企业号确认所登录的企业
                    PartyNumberActivity.start(QueryServerInfoActivity.this);
                }
            }

            @Override
            public void onFailure(BizCodeModel bizCodeModel) {
                ToastUtil.toast(QueryServerInfoActivity.this, ErrorUtils.getBizCodeMessage(bizCodeModel));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_query_server) {
            requestDispatcherHost();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getAccountService().removeAccountCallBack(authListener);
    }

}
