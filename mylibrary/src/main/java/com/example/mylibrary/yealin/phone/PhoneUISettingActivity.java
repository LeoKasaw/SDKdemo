package com.example.mylibrary.yealin.phone;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.api.phone.PhoneUIOptions;

public class PhoneUISettingActivity extends AppCompatActivity {
    private ViewGroup mDevelopContainerView;
    private PhoneUIOptions mPhoneUIOptions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_setting);
        mDevelopContainerView = findViewById(R.id.develop_group_container);
        mPhoneUIOptions = YealinkSdk.getPhoneService().getPhoneController().getPhoneOptions();
        updateEnableAutoPickUp();
        updateEnableSupportMultipleCalls();
        updateEnableShowPhoneVisitingCard();
        updateEnableShowPhoneRingHint();
        updateEnableShowPhoneDuration();
        updateEnableShowPhoneMinimizeButton();
        updateEnableShowPhoneBarMic();
        updateEnableShowPhoneBarDtmf();
        updateEnableShowPhoneBarAudio();
        updateEnableShowPhoneBarHold();
        updateEnableShowPhoneBarTransfer();
        updateEnableShowPhoneBarShareScreen();
        updateEnableShowPhoneBarMeeting();
        updateEnableShowPhoneBarMore();
    }

    private void updateEnableAutoPickUp(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否自动接起");
        holder.switchView.setChecked(mPhoneUIOptions.enableAutoPickUp);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableAutoPickUp = b);
    }

    private void updateEnableSupportMultipleCalls(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否支持多路通话");
        holder.switchView.setChecked(mPhoneUIOptions.enableSupportMultipleCalls);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableSupportMultipleCalls = b);
    }

    private void updateEnableShowPhoneVisitingCard(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P来去电名片");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneVisitingCard);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneVisitingCard = b);
    }

    private void updateEnableShowPhoneRingHint(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P来去电通话提示");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneRingHint);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneRingHint = b);
    }

    private void updateEnableShowPhoneDuration(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P通话时长");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneDuration);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneDuration = b);
    }

    private void updateEnableShowPhoneMinimizeButton(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P最小化按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneMinimizeButton);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneMinimizeButton = b);
    }

    private void updateEnableShowPhoneBarMic(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P静音操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarMic);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarMic = b);
    }

    private void updateEnableShowPhoneBarDtmf(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P拨号盘操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarDtmf);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarDtmf = b);
    }

    private void updateEnableShowPhoneBarAudio(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P音频操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarAudio);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarAudio = b);
    }

    private void updateEnableShowPhoneBarHold(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P保持操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarHold);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarHold = b);
    }

    private void updateEnableShowPhoneBarTransfer(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P转接操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarTransfer);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarTransfer = b);
    }

    private void updateEnableShowPhoneBarShareScreen(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P共享屏幕操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarShareScreen);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarShareScreen = b);
    }

    private void updateEnableShowPhoneBarMeeting(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P升级会议操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarMeeting);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarMeeting = b);
    }

    private void updateEnableShowPhoneBarMore(){
        SwitchHolder holder = inflaterSwitchItem(mDevelopContainerView,"是否显示P2P更多操作按钮");
        holder.switchView.setChecked(mPhoneUIOptions.enableShowPhoneBarMore);
        holder.switchView.setOnCheckedChangeListener((c, b) -> mPhoneUIOptions.enableShowPhoneBarMore = b);
    }

    private SwitchHolder inflaterSwitchItem(ViewGroup parent, String name) {
        SwitchHolder viewHolder = new SwitchHolder();
        viewHolder.contentView = LayoutInflater.from(this).inflate(R.layout.settings_item_style5, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.switchView = viewHolder.contentView.findViewById(R.id.settings_item_switch);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getPhoneService().getPhoneController().setPhoneOption(mPhoneUIOptions);
    }

    private static class BaseHolder {
        public View contentView;
        public TextView nameView;
    }

    private static class SwitchHolder extends BaseHolder {
        public SwitchCompat switchView;
    }
}
