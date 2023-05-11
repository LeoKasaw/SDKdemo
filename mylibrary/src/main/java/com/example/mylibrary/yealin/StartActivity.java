package com.example.mylibrary.yealin;

import static com.example.mylibrary.yealin.meeting.MeetingUISettingActivity.KEY_SHOW_TOAST;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.example.mylibrary.yealin.account.PreLoginActivity;
import com.example.mylibrary.yealin.meeting.MyMeetingUi;
import com.example.mylibrary.yealin.phone.MyPhoneUI;
import com.yealink.base.utils.SharedPreferencesHelper;

import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.SdkInitListener;
import com.yealink.sdk.base.account.LoginStatus;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String KEY_INIT = "KEY_INIT";
    private static final String TAG = "SampleStartActivity";
    private EditText mEditText;
    private EditText mAppId;
    private EditText etServer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        etServer = findViewById(R.id.et_server);
        mEditText = findViewById(R.id.et_secret);
        mAppId = findViewById(R.id.et_appId);
        mEditText.setText("bV4CUlBEHKQyWXuk136jMPApTVZxTRkM");
        mAppId.setText("8e8c1d804c4a4119991e220c34575c42");
        etServer.setText("10.121.1.116:18443");
    }
    private void init(){
        YealinkSdk.initSdk(StartActivity.this.getApplication(), mEditText.getText().toString()
                , mAppId.getText().toString(), new SdkInitListener() {
                    @Override
                    public void onSuccess() {
                        YealinkSdk.getAccountService().setDispatcherHost(etServer.getText().toString().trim());
                        SharedPreferencesHelper.getInstance().putBoolean(KEY_SHOW_TOAST, false);
                        YealinkSdk.getSettingService().setShowToast(false);
                        YealinkSdk.getSettingService().setShareMoveTaskToBack(false);
                        YealinkSdk.getMeetingService().getMeetingUIController().setMeetingUIProxy(new MyMeetingUi());
                        YealinkSdk.getPhoneService().getPhoneController().setPhoneUIProxy(new MyPhoneUI());
                        goLogin();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
        if(YealinkSdk.isInit()){
            Log.i(TAG,"onCreate");
            goLogin();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // Application notify sdk init
        boolean isInit = getIntent().getBooleanExtra(KEY_INIT,false);
        if(isInit){
            Log.i(TAG,"onNewIntent");
            goLogin();
        }
    }

    private void goLogin(){
        StartActivity.this.finish();
        LoginStatus loginStatus = YealinkSdk.getAccountService().getLoginStatus();
        if (LoginStatus.REGED.equals(loginStatus)) {
            Intent intent = new Intent();
            intent.setClass(this, SampleActivity.class);
            startActivity(intent);
        } else {
            PreLoginActivity.start(this);
        }
    }

    @Override
    public void onClick(View view) {
        init();
    }
}