package com.example.mylibrary.yealin.meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.IMeetingService;
import com.yealink.sdk.base.call.MeetingRequest;

public class CreateMeetingActivity extends Activity implements View.OnClickListener {

    public static final int REQ_MEETING_SETTING = 300;
    private IMeetingService mCallManager;
    private MeetingRequest mRequest;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_meeting);
        findViewById(R.id.create_meeting).setOnClickListener(this);
        findViewById(R.id.meeting_setting).setOnClickListener(this);
        mCallManager = YealinkSdk.getMeetingService();
        mRequest = new MeetingRequest();
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.create_meeting){
            mCallManager.start(CreateMeetingActivity.this, mRequest);
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

    private void test(){
        YealinkSdk.getMeetingService().getMeetingShareController().stopShareScreen();
    }
}
