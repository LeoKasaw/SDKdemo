package com.example.mylibrary.yealin.meeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mylibrary.R;
import com.yealink.base.utils.SharedPreferencesHelper;
import com.yealink.base.view.TextChange;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.MeetingUIOptions;
import com.yealink.ylservice.ServiceManager;

import java.util.ArrayList;
import java.util.List;

public class MeetingUISettingActivity extends AppCompatActivity {

    private LayoutInflater mInflater;
    private ViewGroup mDevelopContainerView;
    public static final String KEY_SHOW_TOAST = "KEY_SHOW_TOAST";

    private SwitchHolder mNoInviteHolder;
    private SwitchHolder mNoMeetingEndMessageHolder;
    private SwitchHolder mNoMeetingErrorMessageHolder;
    private SwitchHolder mNoTitleBarHolder;
    private SwitchHolder mNoBottomBarHolder;
    private SwitchHolder mNoTimerBarHolder;
    private SwitchHolder mNoChatMsgHolder;

    private SwitchHolder mNoButtonVideoHolder;
    private SwitchHolder mNoButtonAudioHolder;
    private SwitchHolder mNoButtonShareHolder;
    private SwitchHolder mNoButtonParticipantsHolder;
    private SwitchHolder mNoButtonMoreHolder;
    private SwitchHolder mNoTextMeetingIdHolder;
    private SwitchHolder mNoTextPasswordHolder;
    private SwitchHolder mNoButtonLeaveHolder;
    private SwitchHolder mNoButtonSwitchCameraHolder;
    private SwitchHolder mNoButtonSwitchSpeakerHolder;
    private SwitchHolder mNoButtonChatHolder;
    private SwitchHolder mNoMoreRecordHolder;
    private SwitchHolder mNoMoreRtmpHolder;
    private SwitchHolder mNoMoreVoteHolder;
    private SwitchHolder mNoMoreQAHolder;
    private SwitchHolder mNoMoreSettingHolder;
    private SwitchHolder mNoInviteCopyInfoHolder;
    private SwitchHolder mNoInviteCompanyHolder;
    private SwitchHolder mNoInviteOtherClientHolder;
    private SwitchHolder mShowToastHolder;
    private SwitchHolder mShareMoveTaskToBackHolder;

    private MeetingUIOptions mMeetingUIOption;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_setting);
        mDevelopContainerView = findViewById(R.id.develop_group_container);
        mInflater = LayoutInflater.from(this);

        mMeetingUIOption = YealinkSdk.getMeetingService().getMeetingUIController().getMeetingUIOption();

        inflaterGroup(mDevelopContainerView, "窗口设置");
        updateMeetingWindowHeightHolder();
        updateMeetingWindowWidthHolder();

        inflaterGroup(mDevelopContainerView, "消息提示");
        updateNoChatMsg();
        updateNoMeetingEndMessage();
        updateNoMeetingErrorMessage();

        inflaterGroup(mDevelopContainerView, "标题栏");
        updateNoTitleBar();
        updateNoTextMeetingId();
        updateNoTextPassword();
        updateNoButtonLeave();
        updateNoButtonSwitchCamera();
        updateNoButtonSwitchSpeaker();

        inflaterGroup(mDevelopContainerView, "底部栏");
        updateNoBottomBar();
        updateNoTimerBar();
        updateNoButtonVideo();
        updateNoButtonAudio();
        updateNoButtonShare();
        updateNoButtonParticipants();
        updateNoButtonChat();

        inflaterGroup(mDevelopContainerView, "底部更多弹窗");
        updateNoButtonMore();
        updateNoMoreRecordHolder();
        updateNoMoreRtmpHolder();
        updateNoMoreVoteHolder();
        updateNoMoreQAHolder();
        updateNoMoreSettingHolder();

        inflaterGroup(mDevelopContainerView, "会议邀请弹窗");
        updateNoInvite();
        updateNoInviteCompanyHolder();
        updateNoInviteCopyInfo();
        updateNoInviteOtherClientHolder();

        inflaterGroup(mDevelopContainerView, "成员列表更多弹窗");
        updateNoMemberMuteHolder();
        updateNoMemberCameraOffHolder();
        updateNoMemberSpotLightOnHolder();
        updateNoMemberPrivateChatHolder();
        updateNoMemberModifyNameHolder();
        updateNoMemberSetToHostHolder();
        updateNoMemberSetToLobbyHolder();
        updateNoMemberRecordForbidHolder();
        updateNoMemberRemoveHolder();
        updateNoMemberStopSharingHolder();

        inflaterGroup(mDevelopContainerView, "其他");
        updateNoVideoViewNameHolder();
        updateShowLocalVideoInBigWindowHolder();
        updateShareMoveTaskToBackHolder();
        updateShowToastHolder();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        YealinkSdk.getMeetingService().getMeetingUIController().setMeetingUIOption(mMeetingUIOption);
        MyYealinkMeetingActivity.windowWidth = mMeetingWindowWidth;
        MyYealinkMeetingActivity.windowHeight = mMeetingWindowHeight;
    }

    private void updateNoInvite(){
        if (mNoInviteHolder == null) {
            mNoInviteHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏邀请菜单");
            mNoInviteHolder.switchView.setChecked(mMeetingUIOption.no_invite);
            mNoInviteHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_invite = b);
        }
    }

    private void updateNoMeetingEndMessage(){
        if (mNoMeetingEndMessageHolder == null) {
            mNoMeetingEndMessageHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏离开会议确认框");
            mNoMeetingEndMessageHolder.switchView.setChecked(mMeetingUIOption.no_meeting_end_message);
            mNoMeetingEndMessageHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_meeting_end_message = b);
        }
    }

    private void updateNoMeetingErrorMessage(){
        if (mNoMeetingErrorMessageHolder == null) {
            mNoMeetingErrorMessageHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏结束提示语");
            mNoMeetingErrorMessageHolder.switchView.setChecked(mMeetingUIOption.no_meeting_error_message);
            mNoMeetingErrorMessageHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_meeting_error_message = b);
        }
    }

    private void updateNoTimerBar(){
        if (mNoTimerBarHolder == null) {
            mNoTimerBarHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏时间显示");
            mNoTimerBarHolder.switchView.setChecked(mMeetingUIOption.no_timer_bar);
            mNoTimerBarHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_timer_bar = b);
        }
    }

    private void updateNoTitleBar(){
        if (mNoTitleBarHolder == null) {
            mNoTitleBarHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏顶部栏");
            mNoTitleBarHolder.switchView.setChecked(mMeetingUIOption.no_titlebar);
            mNoTitleBarHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_titlebar = b);
        }
    }

    private void updateNoBottomBar(){
        if (mNoBottomBarHolder == null) {
            mNoBottomBarHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏底部栏");
            mNoBottomBarHolder.switchView.setChecked(mMeetingUIOption.no_bottom_toolbar);
            mNoBottomBarHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_bottom_toolbar = b);
        }
    }

    private void updateNoChatMsg(){
        if (mNoChatMsgHolder == null) {
            mNoChatMsgHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏聊天提示");
            mNoChatMsgHolder.switchView.setChecked(mMeetingUIOption.no_chat_msg_toast);
            mNoChatMsgHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_chat_msg_toast = b);
        }
    }

    private void updateNoButtonVideo(){
        if (mNoButtonVideoHolder == null) {
            mNoButtonVideoHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏摄像头按钮");
            mNoButtonVideoHolder.switchView.setChecked(mMeetingUIOption.no_button_video);
            mNoButtonVideoHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_video = b);
        }
    }

    private void updateNoButtonAudio(){
        if (mNoButtonAudioHolder == null) {
            mNoButtonAudioHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏麦克风按钮");
            mNoButtonAudioHolder.switchView.setChecked(mMeetingUIOption.no_button_audio);
            mNoButtonAudioHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_audio = b);
        }
    }

    private void updateNoButtonShare(){
        if (mNoButtonShareHolder == null) {
            mNoButtonShareHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏分享按钮");
            mNoButtonShareHolder.switchView.setChecked(mMeetingUIOption.no_button_share);
            mNoButtonShareHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_share = b);
        }
    }

    private void updateNoButtonParticipants(){
        if (mNoButtonParticipantsHolder == null) {
            mNoButtonParticipantsHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏成员列表按钮");
            mNoButtonParticipantsHolder.switchView.setChecked(mMeetingUIOption.no_button_participants);
            mNoButtonParticipantsHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_participants = b);
        }
    }

    private void updateNoButtonMore(){
        if (mNoButtonMoreHolder == null) {
            mNoButtonMoreHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏更多按钮");
            mNoButtonMoreHolder.switchView.setChecked(mMeetingUIOption.no_button_more);
            mNoButtonMoreHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_more = b);
        }
    }

    private void updateNoTextMeetingId(){
        if (mNoTextMeetingIdHolder == null) {
            mNoTextMeetingIdHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏标题的会议ID");
            mNoTextMeetingIdHolder.switchView.setChecked(mMeetingUIOption.no_text_meeting_id);
            mNoTextMeetingIdHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_text_meeting_id = b);
        }
    }

    private void updateNoTextPassword(){
        if (mNoTextPasswordHolder == null) {
            mNoTextPasswordHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏密码信息");
            mNoTextPasswordHolder.switchView.setChecked(mMeetingUIOption.no_text_password);
            mNoTextPasswordHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_text_password = b);
        }
    }

    private void updateNoButtonLeave(){
        if (mNoButtonLeaveHolder == null) {
            mNoButtonLeaveHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏离开会议");
            mNoButtonLeaveHolder.switchView.setChecked(mMeetingUIOption.no_button_leave);
            mNoButtonLeaveHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_leave = b);
        }
    }

    private void updateNoButtonSwitchCamera(){
        if (mNoButtonSwitchCameraHolder == null) {
            mNoButtonSwitchCameraHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏切换摄像头");
            mNoButtonSwitchCameraHolder.switchView.setChecked(mMeetingUIOption.no_button_switch_camera);
            mNoButtonSwitchCameraHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_switch_camera = b);
        }
    }

    private void updateNoButtonSwitchSpeaker(){
        if (mNoButtonSwitchSpeakerHolder == null) {
            mNoButtonSwitchSpeakerHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏切换扬声器");
            mNoButtonSwitchSpeakerHolder.switchView.setChecked(mMeetingUIOption.no_button_switch_speaker);
            mNoButtonSwitchSpeakerHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_switch_speaker = b);
        }
    }

    private void updateNoButtonChat(){
        if (mNoButtonChatHolder == null) {
            mNoButtonChatHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏聊天菜单");
            mNoButtonChatHolder.switchView.setChecked(mMeetingUIOption.no_button_chat);
            mNoButtonChatHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_button_chat = b);
        }
    }

    private void updateNoMoreRecordHolder(){
        if (mNoMoreRecordHolder == null) {
            mNoMoreRecordHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏录制菜单");
            mNoMoreRecordHolder.switchView.setChecked(mMeetingUIOption.no_more_record);
            mNoMoreRecordHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_more_record = b);
        }
    }

    private void updateNoMoreRtmpHolder(){
        if (mNoMoreRtmpHolder == null) {
            mNoMoreRtmpHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏直播菜单");
            mNoMoreRtmpHolder.switchView.setChecked(mMeetingUIOption.no_more_rtmp);
            mNoMoreRtmpHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_more_rtmp = b);
        }
    }

    private void updateNoMoreVoteHolder(){
//        if (BuildConfig.IS_UC) {
//            return;
//        }
        if (mNoMoreVoteHolder == null) {
            mNoMoreVoteHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏投票菜单");
            mNoMoreVoteHolder.switchView.setChecked(mMeetingUIOption.no_more_vote);
            mNoMoreVoteHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_more_vote = b);
        }
    }

    private void updateNoMoreQAHolder(){
//        if (BuildConfig.IS_UC) {
//            return;
//        }
        if (mNoMoreQAHolder == null) {
            mNoMoreQAHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏问答菜单");
            mNoMoreQAHolder.switchView.setChecked(mMeetingUIOption.no_more_qa);
            mNoMoreQAHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_more_qa = b);
        }
    }

    private void updateNoMoreSettingHolder(){
        if (mNoMoreSettingHolder == null) {
            mNoMoreSettingHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏会议设置菜单");
            mNoMoreSettingHolder.switchView.setChecked(mMeetingUIOption.no_more_setting);
            mNoMoreSettingHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_more_setting = b);
        }
    }


    private void updateNoInviteCopyInfo(){
        if (mNoInviteCopyInfoHolder == null) {
            mNoInviteCopyInfoHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏会议邀请的复制信息功能");
            mNoInviteCopyInfoHolder.switchView.setChecked(mMeetingUIOption.no_invite_copy_info);
            mNoInviteCopyInfoHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_invite_copy_info = b);
        }
    }

    private void updateNoInviteCompanyHolder(){
        if (mNoInviteCompanyHolder == null) {
            mNoInviteCompanyHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏会议邀请的联系人功能");
            mNoInviteCompanyHolder.switchView.setChecked(mMeetingUIOption.no_invite_company);
            mNoInviteCompanyHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_invite_company = b);
        }
    }

    private void updateNoInviteOtherClientHolder(){
        if (mNoInviteOtherClientHolder == null) {
            mNoInviteOtherClientHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏会议邀请其他终端功能");
            mNoInviteOtherClientHolder.switchView.setChecked(mMeetingUIOption.no_invite_other_client);
            mNoInviteOtherClientHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_invite_other_client = b);
        }
    }

    private void updateShowToastHolder() {
        if (mShowToastHolder == null) {
            mShowToastHolder = inflaterSwitchItem(mDevelopContainerView, "显示Toast");
            boolean show = SharedPreferencesHelper.getInstance().getBoolean(KEY_SHOW_TOAST, true);
            mShowToastHolder.switchView.setChecked(show);
            mShowToastHolder.switchView.setOnCheckedChangeListener((compoundButton, b) -> {
                SharedPreferencesHelper.getInstance().putBoolean(KEY_SHOW_TOAST, b);
                YealinkSdk.getSettingService().setShowToast(b);
            });

        }
    }

    private void updateShareMoveTaskToBackHolder() {
        if (mShareMoveTaskToBackHolder == null) {
            mShareMoveTaskToBackHolder = inflaterSwitchItem(mDevelopContainerView, "共享屏幕退到后台");
            boolean shareMoveTaskToBack = ServiceManager.getSettingsService().isShareMoveTaskToBack();
            mShareMoveTaskToBackHolder.switchView.setChecked(shareMoveTaskToBack);
            mShareMoveTaskToBackHolder.switchView.setOnCheckedChangeListener((c, b) -> YealinkSdk.getSettingService().setShareMoveTaskToBack(b));
        }
    }


    private SwitchHolder mNoMemberMuteHolder;
    private void updateNoMemberMuteHolder(){
        if (mNoMemberMuteHolder == null) {
            mNoMemberMuteHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏音频功能");
            mNoMemberMuteHolder.switchView.setChecked(mMeetingUIOption.no_member_mic);
            mNoMemberMuteHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_mic = b);
        }
    }

    private SwitchHolder mNoMemberCameraOffHolder;
    private void updateNoMemberCameraOffHolder(){
        if (mNoMemberCameraOffHolder == null) {
            mNoMemberCameraOffHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏视频功能");
            mNoMemberCameraOffHolder.switchView.setChecked(mMeetingUIOption.no_member_camera);
            mNoMemberCameraOffHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_camera = b);
        }
    }

    private SwitchHolder mNoMemberSpotLightOnHolder;
    private void updateNoMemberSpotLightOnHolder(){
        if (mNoMemberSpotLightOnHolder == null) {
            mNoMemberSpotLightOnHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏设置焦点功能");
            mNoMemberSpotLightOnHolder.switchView.setChecked(mMeetingUIOption.no_member_spot_light);
            mNoMemberSpotLightOnHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_spot_light = b);
        }
    }

    private SwitchHolder mNoMemberPrivateChatHolder;
    private void updateNoMemberPrivateChatHolder(){
        if (mNoMemberPrivateChatHolder == null) {
            mNoMemberPrivateChatHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏私聊功能");
            mNoMemberPrivateChatHolder.switchView.setChecked(mMeetingUIOption.no_member_private_chat);
            mNoMemberPrivateChatHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_private_chat = b);
        }
    }

    private SwitchHolder mNoMemberModifyNameHolder;
    private void updateNoMemberModifyNameHolder(){
        if (mNoMemberModifyNameHolder == null) {
            mNoMemberModifyNameHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏改名功能");
            mNoMemberModifyNameHolder.switchView.setChecked(mMeetingUIOption.no_member_modify_name);
            mNoMemberModifyNameHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_modify_name = b);
        }
    }

    private SwitchHolder mNoMemberSetToHostHolder;
    private void updateNoMemberSetToHostHolder(){
        if (mNoMemberSetToHostHolder == null) {
            mNoMemberSetToHostHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏设置角色功能");
            mNoMemberSetToHostHolder.switchView.setChecked(mMeetingUIOption.no_member_role);
            mNoMemberSetToHostHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_role = b);
        }
    }

    private SwitchHolder mNoMemberSetToLobbyHolder;
    private void updateNoMemberSetToLobbyHolder(){
        if (mNoMemberSetToLobbyHolder == null) {
            mNoMemberSetToLobbyHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏移入等候室功能");
            mNoMemberSetToLobbyHolder.switchView.setChecked(mMeetingUIOption.no_member_set_to_lobby);
            mNoMemberSetToLobbyHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_set_to_lobby = b);
        }
    }

    private SwitchHolder mNoMemberRecordForbidHolder;
    private void updateNoMemberRecordForbidHolder(){
//        if (BuildConfig.IS_UC) {
//            return;
//        }
        if (mNoMemberRecordForbidHolder == null) {
            mNoMemberRecordForbidHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏录制功能");
            mNoMemberRecordForbidHolder.switchView.setChecked(mMeetingUIOption.no_member_record);
            mNoMemberRecordForbidHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_record = b);
        }
    }


    private SwitchHolder mNoMemberRemoveHolder;
    private void updateNoMemberRemoveHolder(){
        if (mNoMemberRemoveHolder == null) {
            mNoMemberRemoveHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏移除功能");
            mNoMemberRemoveHolder.switchView.setChecked(mMeetingUIOption.no_member_remove);
            mNoMemberRemoveHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_remove = b);
        }
    }

    private SwitchHolder mNoMemberStopSharingHolder;
    private void updateNoMemberStopSharingHolder(){
        if (mNoMemberStopSharingHolder == null) {
            mNoMemberStopSharingHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏停止屏幕分享功能");
            mNoMemberStopSharingHolder.switchView.setChecked(mMeetingUIOption.no_member_stop_sharing);
            mNoMemberStopSharingHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_member_stop_sharing = b);
        }
    }

    private SwitchHolder mNoVideoViewNameHolder;
    private void updateNoVideoViewNameHolder(){
        if (mNoVideoViewNameHolder == null) {
            mNoVideoViewNameHolder = inflaterSwitchItem(mDevelopContainerView, "隐藏视频窗口左下角图标和昵称");
            mNoVideoViewNameHolder.switchView.setChecked(mMeetingUIOption.no_video_view_name);
            mNoVideoViewNameHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.no_video_view_name = b);
        }
    }

    private SwitchHolder mShowLocalVideoInBigWindowHolder;
    private void updateShowLocalVideoInBigWindowHolder(){
        if (mShowLocalVideoInBigWindowHolder == null) {
            mShowLocalVideoInBigWindowHolder = inflaterSwitchItem(mDevelopContainerView, "是否大窗口显示本地画面");
            mShowLocalVideoInBigWindowHolder.switchView.setChecked(mMeetingUIOption.is_show_local_video_in_big_window);
            mShowLocalVideoInBigWindowHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingUIOption.is_show_local_video_in_big_window = b);
        }
    }

    private EditHolder mMeetingWindowWidthHolder;
    private int mMeetingWindowWidth = 0;
    private void updateMeetingWindowWidthHolder(){
        if(mMeetingWindowWidthHolder == null){
            mMeetingWindowWidthHolder = inflaterEditItem(mDevelopContainerView,"窗口宽");
            mMeetingWindowWidthHolder.editView.setText(String.valueOf(MyYealinkMeetingActivity.windowWidth));
            mMeetingWindowWidthHolder.editView.addTextChangedListener(new TextChange() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        mMeetingWindowWidth = Integer.valueOf(s.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    private EditHolder mMeetingWindowHeightHolder;
    private int mMeetingWindowHeight = 0;
    private void updateMeetingWindowHeightHolder(){
        if(mMeetingWindowHeightHolder == null){
            mMeetingWindowHeightHolder = inflaterEditItem(mDevelopContainerView,"窗口高");
            mMeetingWindowHeightHolder.editView.setText(String.valueOf(MyYealinkMeetingActivity.windowHeight));
            mMeetingWindowHeightHolder.editView.addTextChangedListener(new TextChange() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        mMeetingWindowHeight = Integer.valueOf(s.toString());
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            });

        }
    }

    private SwitchHolder inflaterSwitchItem(ViewGroup parent, String name) {
        SwitchHolder viewHolder = new SwitchHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style5, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.switchView = viewHolder.contentView.findViewById(R.id.settings_item_switch);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    private BaseHolder inflaterBaseItem(ViewGroup parent, String name) {
        BaseHolder viewHolder = new BaseHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style3, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    private KvHolder inflaterKvItem(ViewGroup parent, String name) {
        KvHolder viewHolder = new KvHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style2, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.valueView = viewHolder.contentView.findViewById(R.id.settings_item_value);
        viewHolder.imageView = viewHolder.contentView.findViewById(R.id.settings_item_arrow);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    private ImageHolder inflaterImageItem(ViewGroup parent, String name) {
        ImageHolder viewHolder = new ImageHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style1, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.imageView = viewHolder.contentView.findViewById(R.id.settings_item_arrow);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    private EditHolder inflaterEditItem(ViewGroup parent, String name) {
        EditHolder viewHolder = new EditHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style4, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.editView = viewHolder.contentView.findViewById(R.id.settings_item_editor);
        viewHolder.nameView.setText(name);
        return viewHolder;
    }

    private <T> SpinnerHolder inflaterSpinnerItem(ViewGroup parent, String name, List<Item<T>> data, AdapterView.OnItemSelectedListener listener) {
        SpinnerHolder<T> viewHolder = new SpinnerHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style6, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.nameView.setText(name);
        viewHolder.spinner = viewHolder.contentView.findViewById(R.id.settings_item_spinner);

        viewHolder.adapter = new BaseSpinnerAdapter<Item<T>>(this);
        viewHolder.data.addAll(data);
        viewHolder.adapter.addData(viewHolder.data);
        viewHolder.spinner.setAdapter(viewHolder.adapter);
        viewHolder.spinner.setOnItemSelectedListener(listener);
        return viewHolder;
    }

    private TextView inflaterGroup(ViewGroup parent, String name) {
        TextView content = (TextView) mInflater.inflate(R.layout.settings_item_group, parent, false);
        content.setText(name);
        parent.addView(content);
        return content;
    }

    private class BaseHolder {
        public View contentView;
        public TextView nameView;
    }

    private class ImageHolder extends BaseHolder {
        public ImageView imageView;
    }

    private class SwitchHolder extends BaseHolder {
        public SwitchCompat switchView;
    }

    private class KvHolder extends BaseHolder {
        public TextView valueView;
        public ImageView imageView;
    }

    private class EditHolder extends BaseHolder {
        public EditText editView;
    }

    private class SpinnerHolder<T> extends BaseHolder {
        public Spinner spinner;
        BaseSpinnerAdapter<Item<T>> adapter;
        List<Item<T>> data = new ArrayList<>();
    }
}