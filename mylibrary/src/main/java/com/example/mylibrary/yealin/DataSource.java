package com.example.mylibrary.yealin;


import com.example.mylibrary.yealin.account.LogoutActivity;
import com.example.mylibrary.yealin.meeting.CreateMeetingActivity;
import com.example.mylibrary.yealin.meeting.JoinHistoryActivity;
import com.example.mylibrary.yealin.meeting.JoinMeetingActivity;
import com.example.mylibrary.yealin.meeting.MeetingUISettingActivity;
import com.example.mylibrary.yealin.phone.DialActivity;
import com.example.mylibrary.yealin.phone.PhoneHistoryActivity;

/**
 * Created by chenhn on 2017/10/19.
 */

public class DataSource {

    private static DataSource mDataSource;

    private ItemData root;

    private DataSource() {
        root = ItemData.create("Sample");

        ItemData talk = ItemData.create("会议")
                .addChildItem("创建会议", CreateMeetingActivity.class)
                .addChildItem("加入会议", JoinMeetingActivity.class)
                .addChildItem("会议UI设置", MeetingUISettingActivity.class);
        root.addChildItem(talk);

        ItemData dial = ItemData.create("发起p2p通话");
        dial.setTargetActivity(DialActivity.class);
        root.addChildItem(dial);
        ItemData regist = ItemData.create("退出登录");
        regist.setTargetActivity(LogoutActivity.class);
        root.addChildItem(regist);
        ItemData phoneCallHistory = ItemData.create("获取通话记录");
        phoneCallHistory.setTargetActivity(PhoneHistoryActivity.class);
        ItemData feedBack = ItemData.create("意见反馈");
        feedBack.setTargetActivity(SettingsFeedbackActivity.class);
        root.addChildItem(phoneCallHistory);
        root.addChildItem(feedBack);
        ItemData meetingHistory = ItemData.create("加入会议历史");
        meetingHistory.setTargetActivity(JoinHistoryActivity.class);
        root.addChildItem(meetingHistory);
        ItemData pushItem = ItemData.create("注册推送");
        pushItem.setTargetActivity(PushSettingActivity.class);
        root.addChildItem(pushItem);
    }

    public synchronized static DataSource instance(){
        if(mDataSource == null){
            mDataSource = new DataSource();
        }
        return mDataSource;
    }

    public ItemData getDatas() {
        return root;
    }
}
