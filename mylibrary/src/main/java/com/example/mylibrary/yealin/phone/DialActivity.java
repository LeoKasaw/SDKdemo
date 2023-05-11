package com.example.mylibrary.yealin.phone;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.example.mylibrary.R;
import com.yealink.base.utils.ToastUtil;
import com.yealink.sdk.api.YealinkSdk;

public class DialActivity extends FragmentActivity implements View.OnClickListener {

    private EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dial);
        findViewById(R.id.btn_dial).setOnClickListener(this);
        findViewById(R.id.btn_phone_ui_setting).setOnClickListener(this);
        mEditText = findViewById(R.id.et_number);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_dial) {
            String num = mEditText.getText().toString().trim();
            if (TextUtils.isEmpty(num)) {
                ToastUtil.toast(DialActivity.this, "输入不能为空");
            } else {
                YealinkSdk.getPhoneService().dial(DialActivity.this, num);
            }
        }else if (view.getId() == R.id.btn_phone_ui_setting){
            startActivity(new Intent(this,PhoneUISettingActivity.class));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
