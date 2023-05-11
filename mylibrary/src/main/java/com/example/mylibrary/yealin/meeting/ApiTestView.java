package com.example.mylibrary.yealin.meeting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.mylibrary.R;
import com.yealink.base.AppWrapper;
import com.yealink.base.callback.CallBack;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.ylservice.call.impl.meeting.entity.MeetingInviteType;

import java.io.File;
import java.util.ArrayList;


/**
 * @author: chenqf
 * @date: 2022/4/2.
 * @email: chenqf@yealink.com
 * @describe:
 */
public class ApiTestView extends FrameLayout implements View.OnClickListener {

    private Button mBtnVideo;
    private Button mBtnAudio;
    private Button mBtnShare;
    private Button mBtnInvite;
    private Button mBtnDumpCamera;

    public ApiTestView(@NonNull Context context) {
        super(context);
        init();
    }

    public ApiTestView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ApiTestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.item_test_api, this, true);
        mBtnVideo = rootView.findViewById(R.id.btn_video);
        mBtnAudio = rootView.findViewById(R.id.btn_audio);
        mBtnShare = rootView.findViewById(R.id.btn_share);
        mBtnInvite = rootView.findViewById(R.id.btn_invite);
        mBtnDumpCamera = rootView.findViewById(R.id.btn_dumpcamera_image);
        mBtnVideo.setOnClickListener(this);
        mBtnAudio.setOnClickListener(this);
        mBtnShare.setOnClickListener(this);
        mBtnInvite.setOnClickListener(this);
        mBtnDumpCamera.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_video) {
            YealinkSdk.getMeetingService().getMeetingVideoController().muteVideo(false, (Activity) getContext());
        } else if (id == R.id.btn_audio) {
            YealinkSdk.getMeetingService().getMeetingAudioController().muteAudio(false, (Activity) getContext());
        } else if (id == R.id.btn_share) {
            YealinkSdk.getMeetingService().getMeetingShareController().startShareScreen((Activity) getContext());
        } else if (id == R.id.btn_invite) {
            ArrayList<String> list = new ArrayList<>();
            list.add("222");
            YealinkSdk.getMeetingService().getMeetingInviteController().meetingInvite(list, MeetingInviteType.SUBJECTID, null);
        } else if (id == R.id.btn_dumpcamera_image) {
            YealinkSdk.getMeetingService().getMeetingVideoController().dumpCameraImage(new CallBack<String, String>() {
                @Override
                public void onSuccess(String filePath) {
                    File file = new File(filePath);
                    getContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file)));
                    Toast.makeText(AppWrapper.getApp(), "dumpCameraImage success : " + filePath, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onFailure(String s) {
                    Toast.makeText(AppWrapper.getApp(), "dumpCameraImage failure : " + s, Toast.LENGTH_LONG).show();
                }
            });
        }
    }
}
