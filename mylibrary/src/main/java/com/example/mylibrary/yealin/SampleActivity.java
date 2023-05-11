package com.example.mylibrary.yealin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.example.mylibrary.yealin.account.PreLoginActivity;
import com.yealink.base.adapter.CommonAdapter;
import com.yealink.base.callback.CallBack;
import com.yealink.base.utils.ToastUtil;
import com.yealink.module.common.router.ITalkRouter;
import com.yealink.module.router.ModuleManager;
import com.yealink.call.meetingcontrol.EventFactory;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.FileUtil;
import com.yealink.sdk.base.account.IAuthListener;
import com.yealink.sdk.base.call.MeetingStatus;
import com.yealink.ylservice.model.BizCodeModel;

import static com.yealink.sdk.base.FileUtil.REQUEST_CODE_FILE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class SampleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private ListView mListView;
    private CommonAdapter mMainAdapter;
    private ItemData mCurDatas;
    private Switch mToastEnable;

    IAuthListener authListener = new IAuthListener() {
        @Override
        public void onLoginSuccess() {
            SampleActivity.this.onLoginSuccess();
        }

        @Override
        public void onLoginFailed(BizCodeModel nCode) {

        }

        @Override
        public void onLoginOut(BizCodeModel nCode) {
            SampleActivity.this.onLoginOut(nCode);
        }

        @Override
        public void onOffline() {

        }

        @Override
        public void onMyInfoChange() {

        }
    };

    protected void onLoginSuccess() {
    }

    protected void onLoginOut(BizCodeModel nCode) {
        PreLoginActivity.start(SampleActivity.this);
        SampleActivity.this.finish();
    }

    public static void start(Activity activity){
        Intent intent = new Intent();
        intent.setClass(activity, SampleActivity.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.root);
        FileUtil.requestPermission(SampleActivity.this);
        mListView = (ListView) findViewById(R.id.listview);
        findViewById(R.id.btn_get_log).setOnClickListener(this);
        mToastEnable = (Switch) findViewById(R.id.sw_toast_enable);
        mToastEnable.setChecked(YealinkSdk.getSettingService().getToastEnable());
        mToastEnable.setOnCheckedChangeListener(this);
        mMainAdapter = new CommonAdapter<ItemData>(this){

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = LayoutInflater.from(SampleActivity.this).inflate(R.layout.item,parent,false);
                }
                TextView text = (TextView) convertView.findViewById(R.id.item_text);
                text.setText(getItem(position).getName());
                return convertView;
            }
        };
        mListView.setAdapter(mMainAdapter);
        mCurDatas = getDatas();
        mMainAdapter.setData(mCurDatas.getChildren());
        mListView.setOnItemClickListener(this);
        YealinkSdk.getAccountService().addAccountCallBack(authListener);
        EventFactory.setEvent(DemoClickEvent.class);
    }

    protected ItemData getDatas(){
        return DataSource.instance().getDatas();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getAccountService().removeAccountCallBack(authListener);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        restoreCallActivity();
    }

    @Override
    public void onBackPressed() {
        if(mCurDatas != null && mCurDatas.getParent() != null /** && !"Sample".equals(mCurDatas.getParent().getName()) **/){
            mCurDatas = mCurDatas.getParent();
            mMainAdapter.setData(mCurDatas.getChildren());
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        ItemData data = (ItemData) mMainAdapter.getItem(position);
        if(data.getChildren() != null && data.getChildren().size() > 0){
            mCurDatas = data;
            mMainAdapter.setData(data.getChildren());
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this,data.getTargetActivity());
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_log) {
            getSdkLog();
        }
    }

    private void restoreCallActivity() {
        MeetingStatus meetingStatus = YealinkSdk.getMeetingService().getMeetingStatus();
        ITalkRouter iTalkRouter = ModuleManager.getService(ITalkRouter.PATH);
        if (MeetingStatus.STATE_INMEETING.equals(meetingStatus) ||
                MeetingStatus.STATE_JOINING.equals(meetingStatus) ||
                MeetingStatus.STATE_FINISHING.equals(meetingStatus)) {
            if (iTalkRouter != null && !iTalkRouter.isMinimize()) {
                YealinkSdk.getMeetingService().restoreCallUI(this);
            }
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        if (compoundButton.getId() == R.id.sw_toast_enable){
            YealinkSdk.getSettingService().setShowToast(b);
        }
    }

    private void getSdkLog() {
        ToastUtil.toast(SampleActivity.this, "正在获取日志，请稍后，，，");
        FileUtil.getSdkLog(new CallBack() {
            @Override
            public void onSuccess(Object o) {
                ToastUtil.toast(SampleActivity.this, "获取日志完成");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE) {
            FileUtil.requestPermission(SampleActivity.this);
        }
    }
}
