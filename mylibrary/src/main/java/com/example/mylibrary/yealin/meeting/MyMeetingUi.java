package com.example.mylibrary.yealin.meeting;

import com.yealink.base.AppWrapper;
import com.yealink.call.meeting.ui.DefaultMeetingUI;

public class MyMeetingUi extends DefaultMeetingUI {

    @Override
    public void showMeetingCallWindow() {
        MyYealinkMeetingActivity.start(AppWrapper.getApp());
    }

}
