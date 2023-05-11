package com.example.mylibrary.yealin.meeting;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.yealink.base.utils.DensityUtils;
import com.yealink.call.meeting.view.MeetingViewGroup;

public class MyYealinkMeetingActivity extends AppCompatActivity {

    public static void start(Context activity) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT|Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setClass(activity, MyYealinkMeetingActivity.class);
        activity.startActivity(intent);
    }

    private ViewGroup mRootContainer;
    private ViewGroup mMeetingContainer;
    private MeetingViewGroup mCallView;
    public static int windowWidth = 0;
    public static int windowHeight = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_yealink_meeting);
        mRootContainer = findViewById(R.id.root_container);
        mMeetingContainer = findViewById(R.id.meeting_container);
        if(mCallView == null){
            mCallView = new MeetingViewGroup(this,null);
            mMeetingContainer.addView(mCallView);
        }
//        if (BuildConfig.DEBUG) {
//            mRootContainer.addView(new ApiTestView(this));
//        }
        mRootContainer.addView(new ApiTestView(this));
        mCallView.inflaterCallView(savedInstanceState,this);
        updateWindowSize();
    }

    private void updateWindowSize(){
        ViewGroup.LayoutParams params = mMeetingContainer.getLayoutParams();
        params.height = windowHeight < 1? ViewGroup.LayoutParams.MATCH_PARENT : DensityUtils.dp2px(this,windowHeight);
        params.width = windowWidth < 1? ViewGroup.LayoutParams.MATCH_PARENT : DensityUtils.dp2px(this,windowWidth);
        mMeetingContainer.setLayoutParams(params);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mCallView != null){
            mCallView.onResume();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        if(mCallView != null){
            mCallView.onPause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mCallView != null){
            mCallView.onDestroy();
        }
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mCallView != null && mCallView.onKeyUp(keyCode,event)) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mCallView != null && mCallView.onKeyDown(keyCode,event)) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(mCallView != null){
            mCallView.onSaveInstanceState(outState);
        }
    }

    @Override
    public void onBackPressed() {
        return;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if(mCallView != null){
            mCallView.onScreenOrientationChanged(newConfig.orientation);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(mCallView != null){
            mCallView.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (mCallView != null) {
            mCallView.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    @Override
    public void finish() {
        super.finish();
    }
}