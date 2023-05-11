package com.example.mylibrary.yealin.phone;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.example.mylibrary.R;
import com.yealink.base.AppWrapper;
import com.yealink.base.adapter.CommonAdapter;
import com.yealink.base.view.CircleImageView;
import com.yealink.module.common.utils.TimeUtils;
import com.yealink.ylservice.model.CallLog;
import com.yealink.ylservice.model.CallLogGroup;
import com.yealink.ylservice.call.impl.phone.PhoneCallStatus;
import com.yealink.ylservice.model.PhoneDirection;
import com.yealink.ylservice.utils.TranslateUtils;

public class PhoneHistoryAdapter extends CommonAdapter<CallLogGroup> {

    public PhoneHistoryAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        PhoneHistoryViewHolder holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.calllog_calllog_item,viewGroup,false);
            holder = new PhoneHistoryViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (PhoneHistoryViewHolder) convertView.getTag();
        }
        CallLogGroup callLogGroup = getItem(position);
        CallLog calllog = callLogGroup.getLastCalllog();

        if (CallLog.CallState.INCOMING == calllog.getStatus()) {
            if (CallLog.CallState.MISSED == calllog.getStatus()) {
                holder.mHeader.setBackground(AppWrapper.getResources().getDrawable(R.drawable.calllog_ic_call_incoming));
                holder.mTitleText.setTextColor(AppWrapper.getResources().getColor(com.fastgo.driver.dialog.sydialoglib.R.color.red));
            } else {
                holder.mHeader.setBackground(AppWrapper.getResources().getDrawable(R.drawable.calllog_ic_call_incoming_gray));
                holder.mTitleText.setTextColor(AppWrapper.getResources().getColor(R.color.black));
            }
        } else {
            holder.mHeader.setBackground(AppWrapper.getResources().getDrawable(R.drawable.ic_call_outgoing));
            holder.mTitleText.setTextColor(AppWrapper.getResources().getColor(R.color.black));
        }
        String name = callLogGroup.getName();
        if (TextUtils.isEmpty(name)){
            name = callLogGroup.getDisplayNumber();
        }

        holder.mTitleText.setText(name+"("+callLogGroup.getCount()+")");
        holder.mTimeText.setText(TimeUtils.getFormatTimeForDayStr2(calllog.getStartTime(), AppWrapper.getResources(),"/", false));
        holder.mSubTitleText.setText(callLogGroup.getDisplayNumber());
        return convertView;
    }

    class PhoneHistoryViewHolder {
        private CircleImageView mHeader;
        private TextView mTitleText;
        private TextView mSubTitleText;
        private TextView mTimeText;
        private View mConvertView;
        private AppCompatImageView mDetailImg;
        public PhoneHistoryViewHolder(View convertView){
            mConvertView = convertView;
            mHeader = mConvertView.findViewById(R.id.header);
            mTitleText = mConvertView.findViewById(R.id.title);
            mSubTitleText = mConvertView.findViewById(R.id.subtitle);
            mDetailImg = mConvertView.findViewById(R.id.detail);
            mTimeText = mConvertView.findViewById(R.id.time);
        }
    }
}
