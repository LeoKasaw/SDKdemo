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
import com.yealink.base.callback.CallBack;
import com.yealink.base.utils.SharedPreferencesHelper;
import com.yealink.base.utils.ToastUtil;
import com.yealink.module.common.utils.ErrorUtils;
import com.yealink.sdk.api.IAuthService;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.ylservice.account.bean.ThirdPartyInfoModel;
import com.yealink.ylservice.model.BizCodeModel;

public class PartyNumberActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String KEY_PARTY_NUMBER = "PARTY_NUMBER";

    private EditText mEtNumber;
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
        intent.setClass(activity, PartyNumberActivity.class);
        activity.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_number);
        mEtNumber = findViewById(R.id.et_party_number);
        findViewById(R.id.tv_confirm).setOnClickListener(this);
        mAuthService = YealinkSdk.getAccountService();
        YealinkSdk.getAccountService().addAccountCallBack(authListener);

        String server = SharedPreferencesHelper.getInstance().getString(KEY_PARTY_NUMBER, "");
        if (!TextUtils.isEmpty(server)) {
            mEtNumber.setText(server);
        }
    }

    private void requestThirdPartyInfo() {
        String number = mEtNumber.getText().toString();
        SharedPreferencesHelper.getInstance().putString(KEY_PARTY_NUMBER, number);
        mAuthService.requestThirdPartyInfo(number, new CallBack<ThirdPartyInfoModel, BizCodeModel>() {
            @Override
            public void onSuccess(ThirdPartyInfoModel thirdPartyInfoModel) {
                PartyLoginActivity.start(PartyNumberActivity.this);
            }

            @Override
            public void onFailure(BizCodeModel bizCodeModel) {
                ToastUtil.toast(PartyNumberActivity.this, ErrorUtils.getBizCodeMessage(bizCodeModel));
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_confirm) {
            requestThirdPartyInfo();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getAccountService().removeAccountCallBack(authListener);
    }

}
