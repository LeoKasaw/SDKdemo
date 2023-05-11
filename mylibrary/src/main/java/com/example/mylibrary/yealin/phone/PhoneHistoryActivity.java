package com.example.mylibrary.yealin.phone;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mylibrary.R;
import com.yealink.base.callback.CallBack;
import com.yealink.aqua.callhistory.callbacks.CallHistoryVectorCallRecordInfoCallback;
import com.yealink.aqua.callhistory.types.CallRecordInfo;
import com.yealink.aqua.callhistory.types.ListCallRecordInfo;
import com.yealink.aqua.common.types.CallDirection;
import com.yealink.base.debug.YLog;
import com.yealink.module.common.router.ITalkRouter;
import com.yealink.module.router.ModuleManager;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.phone.IPhoneHistoryListener;
import com.yealink.ylservice.call.impl.base.BizCodeCallback;
import com.yealink.ylservice.model.BizCodeModel;
import com.yealink.ylservice.call.impl.phone.PhoneCallRecordEntity;
import com.yealink.ylservice.model.CallLog;
import com.yealink.ylservice.model.CallLogGroup;
import java.util.ArrayList;
import java.util.List;

public class PhoneHistoryActivity extends AppCompatActivity {

    private ListView lvHistory;
    private PhoneHistoryAdapter mAdapter ;
    private static String TAG = "PhoneHistoryActivity1";
    private Button btnDialog;
    private Button btnClear;
    private Button btnDiaDelFirst;
    private Handler mUpdateAdapterHandler = new Handler(Looper.getMainLooper());
    private List<CallLogGroup> callLogGroups = new ArrayList<>();

    private IPhoneHistoryListener mPhoneHistoryListener = new IPhoneHistoryListener() {
        @Override
        public void onPhoneHistoryChangeEvent() {
            getCallHistory();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_history);
        btnDialog = findViewById(R.id.btnDialog);

        lvHistory = findViewById(R.id.lvHistory);
        btnClear = findViewById(R.id.btnDiaClearAll);
        btnDiaDelFirst = findViewById(R.id.btnDiaDelFirst);

        mAdapter = new PhoneHistoryAdapter(this);
        lvHistory.setAdapter(mAdapter);
        addListener();
        getCallHistory();

        btnDialog.setOnClickListener(view -> makecall(true));

    }


    // 获取通话历史记录
    private void getCallHistory(){
        YealinkSdk.getPhoneHistoryService().getCallHistoryList(new CallBack<List<CallLogGroup>, BizCodeModel>() {
            @Override
            public void onSuccess(List<CallLogGroup> callLogList) {
                YLog.i(TAG,callLogList.size()+"");
                callLogGroups.clear();
                callLogGroups.addAll(callLogList);
                mUpdateAdapterHandler.postDelayed(mRunnable,500);
            }
        });
    }

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            mAdapter.setData(callLogGroups);
        }
    };

    private void addListener(){
        // 监听通话记录的变化通知
        YealinkSdk.getPhoneHistoryService().addCallHistoryListener(mPhoneHistoryListener);
        //清空记录
        btnClear.setOnClickListener(view -> YealinkSdk.getPhoneHistoryService().clearCallHistory(new BizCodeCallback<Void>() {
            @Override
            protected void onSuccessGetResult(Void unused) {
                getCallHistory();
            }
        }));
        // 删除单个记录
        btnDiaDelFirst.setOnClickListener(view -> {
            if (callLogGroups == null || callLogGroups.size() == 0) {
                return;
            }
            YealinkSdk.getPhoneHistoryService().deleteCallHistory(callLogGroups.get(0).getRecordId(), new BizCodeCallback<Void>() {
                @Override
                protected void onSuccessGetResult(Void unused) {
                    getCallHistory();
                }
            });
        });
    }
    private void makecall(boolean isVideo) {
        ITalkRouter talkService = ModuleManager.getService(ITalkRouter.PATH);
        if (talkService != null) {
            talkService.dial(this, "8888", isVideo);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUpdateAdapterHandler.removeCallbacks(mRunnable);
        YealinkSdk.getPhoneHistoryService().removeCallHistoryListener(mPhoneHistoryListener);
    }
}
