package com.example.mylibrary.yealin.meeting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.example.mylibrary.R;
import com.yealink.aqua.contact.Contact;
import com.yealink.base.callback.CallBack;
import com.yealink.base.debug.YLog;
import com.yealink.base.thread.Job;
import com.yealink.base.thread.ThreadPool;
import com.yealink.base.utils.ToastUtil;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.ylservice.ServiceManager;
import com.yealink.ylservice.call.impl.meeting.entity.MeetingInviteType;
import com.yealink.ylservice.contact.entity.ContactEntity;
import com.yealink.ylservice.model.BizCodeModel;

import java.util.ArrayList;

public class ConfInviteContactActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "ConfInviteContactActivity";

    private EditText mNodeIdText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.conf_invite);
        mNodeIdText = findViewById(R.id.number);
        findViewById(R.id.invite).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ThreadPool.post(new Job<ArrayList<String>>("syncFindNodeByNumber") {
            @Override
            public ArrayList<String> run() {
                ArrayList<String> nodeIds = new ArrayList<>();
                ContactEntity contact = ServiceManager.getContactService().getContactInfoByNumber(mNodeIdText.getText().toString());
                if (contact != null) {
                    YLog.i(TAG, "onClick: nodeId=" + contact.getId());
                    nodeIds.add(contact.getId());
                }
                return nodeIds;
            }

            @Override
            public void finish(ArrayList<String> nodeIds) {
                YealinkSdk.getMeetingService().getMeetingInviteController().meetingInvite(nodeIds, MeetingInviteType.SUBJECTID, new CallBack<Void, BizCodeModel>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        ToastUtil.toast(ConfInviteContactActivity.this, "联系人邀请已发出！");
                    }

                    @Override
                    public void onFailure(BizCodeModel integer) {
                        ToastUtil.toast(ConfInviteContactActivity.this, "联系人邀请失败！");
                    }
                });
            }
        });
    }
}
