package com.example.mylibrary.yealin;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.third.push.OnPushReceiverSimpleListener;
import com.yealink.third.push.PushConfig;

public class PushSettingActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_setting);
        findViewById(R.id.btn_register).setOnClickListener(this);
        findViewById(R.id.btn_unregister).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_unregister) {
            unregister();
        }else if (v.getId() == R.id.btn_register){
            register();
        }
    }

    //账号登录上之后调用
    private void register(){
        //推送配置
        PushConfig config = new PushConfig();
        config.setEnableHuaWei(true);
        config.setEnableOppo(true);
        config.setEnableVivo(true);
        config.setEnableXiMi(true);
        YealinkSdk.getSettingService().registerPush(getApplication(), config, new OnPushReceiverSimpleListener(){
            @Override
            public void onToken(SERVICE type, String token) {
                super.onToken(type, token);
                String platform = "";
                if (type == SERVICE.XiaoMi) {
                    platform = "xiaomi";
                } else if (type == SERVICE.HuaWei) {
                    platform = "huawei";
                } else if (type == SERVICE.OPPO) {
                    platform = "oppo";
                } else if (type == SERVICE.VIVO) {
                    platform = "vivo";
                }
                YealinkSdk.getSettingService().bindPushToken(token,platform);
            }
        });
    }

    /**
     * 反注册推送
     */
    private void unregister(){
        YealinkSdk.getSettingService().unregisterPush(this);
    }
}
