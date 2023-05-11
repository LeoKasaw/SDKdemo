package com.example.mylibrary.yealin.meeting;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.example.mylibrary.R;
import com.yealink.sdk.api.YealinkSdk;
import com.yealink.sdk.base.call.MeetingRequest;
import com.yealink.ylservice.ServiceManager;

import com.yealink.ylservice.utils.Constance;

import java.util.ArrayList;
import java.util.List;

public class MeetingSettingActivity extends AppCompatActivity {

    public static final String KEY_MEETING_REQUEST = "MeetingRequest";

    private MeetingRequest mMeetingRequest;
    private LayoutInflater mInflater;
    private ViewGroup mDevelopContainerView;
    private SwitchHolder mOpenCameraHolder;
    private SwitchHolder mOpenMicHolder;
    private SwitchHolder mEnableAutoHideControlBarHolder;
    private SwitchHolder mEnableSkipPreviewHolder;
    private EditAndBtnHolder   mCameraSnapshotDumpDirHolder;
    private SwitchHolder mHDVideoEncodeHolder;

    public static void start(Activity act,MeetingRequest request,int requestCode){
        Intent intent = new Intent();
        intent.setClass(act,MeetingSettingActivity.class);
        intent.putExtra(KEY_MEETING_REQUEST,request);
        act.startActivityForResult(intent,requestCode);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meeting_setting);
        mDevelopContainerView = findViewById(R.id.develop_group_container);
        mInflater = LayoutInflater.from(this);
        mMeetingRequest = getIntent().getParcelableExtra(KEY_MEETING_REQUEST);

        inflaterGroup(mDevelopContainerView, "Meeting Option");
        updateOpenCamera();
        updateOpenMic();
        updateEnableAutoHideControlBar();
        /**
         * updateEnableSkipInvite();
         */
        updateEnableSkipPreView();
        updateCameraSnapshotDumpDir();
        updateVideoHDVideoHolder();
        updateVideoConfigHolder();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra(KEY_MEETING_REQUEST, mMeetingRequest);
        setResult(Activity.RESULT_OK, intent);
        super.onBackPressed();
    }

    private void updateVideoConfigHolder() {
        ArrayList<Item<Object>> objects = new ArrayList<>();
        Item<Object> objectItem = new Item<>();
        objectItem.setDisplayName("480");
        objects.add(objectItem);
        Item<Object> item720 = new Item<>();
        item720.setDisplayName("720");
        objects.add(item720);
        Item<Object> item1080 = new Item<>();
        item1080.setDisplayName("1080");
        objects.add(item1080);
        inflaterSpinnerItem(mDevelopContainerView, "订阅分辨率", objects, new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        YealinkSdk.getMeetingService().setCustomVideoConfig(Constance.LEVEL480);
                        break;
                    case 1:
                        YealinkSdk.getMeetingService().setCustomVideoConfig(Constance.LEVEL720);
                        break;
                    case 2:
                        YealinkSdk.getMeetingService().setCustomVideoConfig(Constance.LEVEL1080);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void updateOpenCamera(){
        if (mOpenCameraHolder == null) {
            mOpenCameraHolder = inflaterSwitchItem(mDevelopContainerView, "默认打开摄像头");
            mOpenCameraHolder.switchView.setChecked(mMeetingRequest.getMeetingOptions().openCamera);
            mOpenCameraHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingRequest.getMeetingOptions().openCamera = b);
        }
    }

    private void updateOpenMic(){
        if (mOpenMicHolder == null) {
            mOpenMicHolder = inflaterSwitchItem(mDevelopContainerView, "默认打开麦克风");
            mOpenMicHolder.switchView.setChecked(mMeetingRequest.getMeetingOptions().openMic);
            mOpenMicHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingRequest.getMeetingOptions().openMic = b);
        }
    }

    private void updateEnableAutoHideControlBar(){
        if (mEnableAutoHideControlBarHolder == null) {
            mEnableAutoHideControlBarHolder = inflaterSwitchItem(mDevelopContainerView, "始终显示会议控制栏");
            mEnableAutoHideControlBarHolder.switchView.setChecked(!mMeetingRequest.getMeetingOptions().enableAutoHideControlBar);
            mEnableAutoHideControlBarHolder.switchView.setOnCheckedChangeListener((c, b) -> mMeetingRequest.getMeetingOptions().enableAutoHideControlBar = !b);
        }
    }

    private void updateEnableSkipPreView(){
        if (mEnableSkipPreviewHolder == null){
            mEnableSkipPreviewHolder = inflaterSwitchItem(mDevelopContainerView,"加入会议跳过视频预览");
            mEnableSkipPreviewHolder.switchView.setChecked( YealinkSdk.getJoinMeetingService().getEnableSkipPreviewWhenJoinMeeting());
            mEnableSkipPreviewHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    YealinkSdk.getJoinMeetingService().enableSkipPreviewWhenJoinMeeting(b);
                }
            });
        }
    }

    private void updateCameraSnapshotDumpDir(){
        if (mCameraSnapshotDumpDirHolder == null){
            mCameraSnapshotDumpDirHolder = inflaterEditWithBtnItem(mDevelopContainerView,"会议截图保存路径");
            mCameraSnapshotDumpDirHolder.editView.setText(ServiceManager.getSettingsService().getCameraSnapshotDumpDir().toString());
            mCameraSnapshotDumpDirHolder.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    YealinkSdk.getMeetingService().setCameraSnapshotDumpDir(mCameraSnapshotDumpDirHolder.editView.getText().toString());
                }
            });
        }

    }

    private void updateVideoHDVideoHolder(){
        if (mHDVideoEncodeHolder == null) {
            mHDVideoEncodeHolder = inflaterSwitchItem(mDevelopContainerView, "高清画质(Beta)");
            mHDVideoEncodeHolder.switchView.setChecked(YealinkSdk.getSettingService().isEnableHdVideoEncode());
            mHDVideoEncodeHolder.switchView.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    YealinkSdk.getSettingService().setEnableHdVideoEncode(b);
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

    private EditAndBtnHolder inflaterEditWithBtnItem(ViewGroup parent, String name) {
        EditAndBtnHolder viewHolder = new EditAndBtnHolder();
        viewHolder.contentView = mInflater.inflate(R.layout.settings_item_style7, parent, false);
        parent.addView(viewHolder.contentView);
        viewHolder.editView = viewHolder.contentView.findViewById(R.id.settings_item_editor);
        viewHolder.nameView = viewHolder.contentView.findViewById(R.id.settings_item_name);
        viewHolder.nameView.setText(name);
        viewHolder.btnOk = viewHolder.contentView.findViewById(R.id.btnOk);
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
        int customVideoConfig = YealinkSdk.getMeetingService().getCustomVideoConfig();
        switch (customVideoConfig) {
            case Constance.INVALID:
                break;
            case Constance.LEVEL480:
                viewHolder.spinner.setSelection(0);
                break;
            case Constance.LEVEL720:
                viewHolder.spinner.setSelection(1);
                break;
            case Constance.LEVEL1080:
                viewHolder.spinner.setSelection(2);
                break;
        }

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

    private class EditAndBtnHolder extends BaseHolder {
        public EditText editView;
        public Button btnOk;
        public TextView nameView;
    }

    private class SpinnerHolder<T> extends BaseHolder {
        public Spinner spinner;
        BaseSpinnerAdapter<Item<T>> adapter;
        List<Item<T>> data = new ArrayList<>();
    }
}