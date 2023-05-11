package com.example.mylibrary.yealin;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.mylibrary.R;
import com.yealink.base.AppWrapper;
import com.yealink.base.adapter.CommonAdapter;
import com.yealink.base.callback.CallBack;
import com.yealink.base.utils.ApkUtil;
import com.yealink.base.utils.SharedPreferencesHelper;
import com.yealink.base.utils.ToastUtil;
import com.yealink.base.view.DropEditText;

import com.yealink.module.common.call.ITalkActivity;
import com.yealink.module.common.utils.ErrorUtils;
import com.yealink.module.common.utils.IntentUtil;
import com.yealink.module.common.utils.Oem;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.ylservice.ServiceManager;
import com.yealink.ylservice.model.BizCodeModel;
import com.yealink.ylservice.utils.ProgressCallBack;

import com.yealink.ylservice.ytms.VersionHelper;
import com.yealink.ylservice.ytms.bean.FeedbackTypeModel;
import java.util.ArrayList;
import java.util.List;

public class SettingsFeedbackActivity extends Activity implements View.OnClickListener, ITalkActivity {
    private static final String TAG = "SettingsFeedbackActivity";
    private DropEditText mDropEditText;
    private TextView mNumberCountView;
    private EditText mDescView;
    private EditText mContactsView;
    private Switch mScAutoUploadLog;
    private QuestionTypeAdapter mQuestionTypeAdapter;
    private FeedbackTypeModel mQuestionTypeSelected = FeedbackTypeModel.Other;
    private TextView mTvHelpCenter;
    private TextView mTvTelephoneService;
    private TextView mTvOfficialWebsite;
    private LinearLayout mBottomLayer;

    private CheckBox mCbInnerFeedback;
    private SharedPreferencesHelper mHelper;
    private int mUploadProgress;
    private CallBack.Releasable mRelease;
    private Button btnCommit;
    private TextView tvProgress;


    private DropEditText.DropEditListener mDropEditListener = new DropEditText.DropEditListener() {
        @Override
        public void onWindowShow() {

        }

        @Override
        public void onTextChange(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            QuestionType questionType = mQuestionTypeAdapter.getItem(position);
            mQuestionTypeSelected = questionType.type;
            mDropEditText.setText(questionType.name);
        }

        @Override
        public void onClearAll() {

        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_feedback_activity);
        mRelease = new CallBack.Releasable();
        mHelper = SharedPreferencesHelper.getInstance();
        mDropEditText = findViewById(R.id.question_type_value);
        mNumberCountView = findViewById(R.id.number);
        mDescView = findViewById(R.id.input);
        mScAutoUploadLog = findViewById(R.id.sc_auto_upload_log);
        mContactsView = findViewById(R.id.settings_feedback_contacts_value);
        mTvHelpCenter = (TextView) findViewById(R.id.tv_help_center);
        mTvTelephoneService = (TextView) findViewById(R.id.tv_telephone_service);
        mTvOfficialWebsite = (TextView) findViewById(R.id.tv_official_website);
        mBottomLayer = findViewById(R.id.ll_bottom);
        tvProgress = findViewById(R.id.tvProgress);
        btnCommit = findViewById(R.id.btnCommit);
        mCbInnerFeedback = (CheckBox) findViewById(R.id.cb_inner_feedback);
        mTvHelpCenter.setOnClickListener(this);
        mTvTelephoneService.setOnClickListener(this);
        mTvOfficialWebsite.setOnClickListener(this);
        setTitle("设置反馈");
        mQuestionTypeAdapter = new QuestionTypeAdapter(this);
        mDropEditText.setAdapter(mQuestionTypeAdapter);
        mDropEditText.setDropEditListener(mDropEditListener);
        mDropEditText.setTvClearAllVisiable(View.GONE);
        mDropEditText.getEditText().setHintTextColor(getResources().getColor(R.color.black));
        initOemView();
        // 设置意见反馈地址
        //YealinkSdk.getFeedBackService().setAddress("https://ytms-scheduler.onylyun.com");
        btnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                feedback();
            }
        });
    }

    private void initOemView() {
        if (Oem.getInstance().getShowHelpCenter() == 1 && Oem.getInstance().getShowVideoService() == 1) {
            mBottomLayer.setVisibility(View.VISIBLE);
        } else {
            mBottomLayer.setVisibility(View.GONE);
        }

    }

    private void feedback() {
        String content = mDescView.getText().toString();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.toast(AppWrapper.getApp(), "设置内容");
            return;
        }
        upload(content);
    }

    private void upload(String content) {
        FeedbackTypeModel questionType;
        boolean uploadAudioDump = false;
        // 音频反馈，或者勾选上传dump
        if(mQuestionTypeSelected == FeedbackTypeModel.Audio || mScAutoUploadLog.isChecked() || VersionHelper.isYunCe()){
            uploadAudioDump = true;
        }
        // qa版本或者是勾选内部反馈
        if (mCbInnerFeedback.getVisibility() == View.VISIBLE && mCbInnerFeedback.isChecked()
                || Oem.getInstance().getInnerFeedback() == 1) {
            questionType = FeedbackTypeModel.Inner;
        } else {
            questionType = mQuestionTypeSelected;
        }
        String contact = mContactsView.getText().toString();
        List<String> picturePaths = new ArrayList<>();

        List<String> attachedFilePaths = new ArrayList<>();
        YealinkSdk.getFeedBackService().feedback(questionType,
                content,
                "Android" + ApkUtil.getAppVersionName(this),
                contact,
                mScAutoUploadLog.isChecked(),
                uploadAudioDump,
                picturePaths,
                attachedFilePaths,
                new ProgressCallBack<Integer, BizCodeModel>(mRelease) {
                    @Override
                    public void onSuccess(Integer integer) {
                        feedbackSuccAndExit();
                    }

                    @Override
            public void onFailure(BizCodeModel error) {
                if (error.getBizCode() == 903200){
                    String pictureMaxSize = ServiceManager.getYtmsService().getPictureMaxSize();
                    String strTips = ErrorUtils.getBizCodeMessage(error.getBizCode());
                    ToastUtil.toast(SettingsFeedbackActivity.this, String.format(strTips,pictureMaxSize));
                }else {
                    feedbackFailureAndExit();
                }
            }

            @Override
            public void onProgressUpdate(int progress) {
                if (progress == mUploadProgress) {
                    return;
                }
                mUploadProgress = progress;
                tvProgress.setText("提交进度: " + progress);
            }
        });
    }

    private void feedbackSuccAndExit() {

        mUploadProgress = 0;
        ToastUtil.toast(SettingsFeedbackActivity.this, "反馈成功");
        finish();
    }

    private void feedbackFailureAndExit() {
        mUploadProgress = 0;
        ToastUtil.toast(SettingsFeedbackActivity.this, "反馈失败");
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRelease.setRelease(true);
    }
    @Override
    public void onClick(View v) {
       if (v.getId() == R.id.tv_telephone_service) {
            clickTelephoneService();
        }
    }

    private void clickTelephoneService() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:05925702000");
        intent.setData(data);
        if (IntentUtil.isExist(this, intent)) {
            startActivity(intent);
        } else {
            ToastUtil.toast(AppWrapper.getApp(),"电话服务");
        }
    }

    private class QuestionType {
        private FeedbackTypeModel type;
        private String name;

        public QuestionType(FeedbackTypeModel type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    private class QuestionTypeAdapter extends CommonAdapter<QuestionType> {

        private List<QuestionType> mQuestionTypeData = new ArrayList<>();

        private void loadQuestionType() {
            mQuestionTypeData.add(new QuestionType(FeedbackTypeModel.Bug, "1"));
            mQuestionTypeData.add(new QuestionType(FeedbackTypeModel.Video, "2"));
            mQuestionTypeData.add(new QuestionType(FeedbackTypeModel.Audio,"3"));
            mQuestionTypeData.add(new QuestionType(FeedbackTypeModel.Suggestion, "4"));
            mQuestionTypeData.add(new QuestionType(FeedbackTypeModel.Other, "5"));
            setData(mQuestionTypeData);
        }

        public QuestionTypeAdapter(Context context) {
            super(context);
            loadQuestionType();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(SettingsFeedbackActivity.this).inflate(R.layout.settings_question_type_item, parent, false);
            }
            QuestionType type = getItem(position);
            if (!TextUtils.isEmpty(type.name)) {
                TextView textView = convertView.findViewById(R.id.text);
                textView.setText(type.name);
            }
            return convertView;
        }
    }
}
