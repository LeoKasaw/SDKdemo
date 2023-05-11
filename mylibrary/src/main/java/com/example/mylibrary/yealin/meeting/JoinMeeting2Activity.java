package com.example.mylibrary.yealin.meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.mylibrary.BuildConfig;
import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.IMeetingService;
import com.yealink.sdk.base.call.MeetingRequest;

public class JoinMeeting2Activity extends Activity implements View.OnClickListener {

    public static final int REQ_MEETING_SETTING = 300;

    private EditText mNumberText;
    private EditText mNickNameText;
    private EditText mPasswordText;
    private IMeetingService mMeetingService;
    private MeetingRequest mRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_meeting2);
        mNumberText = findViewById(R.id.input);
        mNickNameText = findViewById(R.id.nick_name);
        mPasswordText = findViewById(R.id.password);
        findViewById(R.id.join_meeting).setOnClickListener(this);
        findViewById(R.id.meeting_setting).setOnClickListener(this);
        mMeetingService = YealinkSdk.getMeetingService();
        mRequest = new MeetingRequest();
        if(BuildConfig.DEBUG){
//            会议号:823 0680 32391
//            密码:541632
            mNumberText.setText("823068032391");
            mPasswordText.setText("541632");
            mRequest.getMeetingOptions().server = "staging.onyealink.com";
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.join_meeting){
            joinMeeting();
        } else if(view.getId() == R.id.meeting_setting){
            MeetingSettingActivity.start(this, mRequest,REQ_MEETING_SETTING);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQ_MEETING_SETTING){
            if(resultCode == Activity.RESULT_OK){
                mRequest = data.getParcelableExtra(MeetingSettingActivity.KEY_MEETING_REQUEST);
            }
        }
    }

    private void joinMeeting(){
        mRequest.getUserInfo().nickName = mNickNameText.getText().toString();
        mRequest.getMeetingOptions().meetingId = mNumberText.getText().toString();
        mRequest.getMeetingOptions().password = mPasswordText.getText().toString();
        try{
            YealinkSdk.getMeetingService().join(this, mRequest);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
