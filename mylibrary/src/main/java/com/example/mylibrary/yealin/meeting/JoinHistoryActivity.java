package com.example.mylibrary.yealin.meeting;

import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.example.mylibrary.yealin.account.JoinHistoryAdapter;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.ylservice.account.bean.MeetingHistoryModel;
import com.yealink.ylservice.call.impl.base.BizCodeCallback;
import com.yealink.ylservice.model.IModel;

import java.util.List;

public class JoinHistoryActivity extends AppCompatActivity {

    private JoinHistoryAdapter mAdapter;
    private ListView listview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_history);
        mAdapter = new JoinHistoryAdapter(this);
        listview = findViewById(R.id.listview);
        listview.setAdapter(mAdapter);
        YealinkSdk.getJoinMeetingService().getMeetingHistory(new BizCodeCallback<List<MeetingHistoryModel>>() {
            @Override
            protected void onSuccessGetResult(List<MeetingHistoryModel> iModels) {
                mAdapter.setData(iModels);
            }
        });

    }
}
