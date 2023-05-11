package com.example.mylibrary.yealin.account;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mylibrary.R;
import com.yealink.base.adapter.CommonAdapter;
import com.yealink.ylservice.account.bean.MeetingHistoryModel;
import com.yealink.ylservice.model.IModel;
public class JoinHistoryAdapter  extends CommonAdapter<MeetingHistoryModel> {
    public JoinHistoryAdapter(Context context) {
        super(context);
    }
    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        JoinViewModel holder;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_meeting_history,parent,false);
            holder = new JoinViewModel(convertView);
            convertView.setTag(holder);
        }else {
            holder = (JoinViewModel) convertView.getTag();
        }
        MeetingHistoryModel model = getItem(i);
        holder.title.setText(model.getMeetingTitle());
        holder.meetingId.setText("会议id： " + model.getMeetingNumber());
        return convertView;
    }
    class JoinViewModel {
        TextView title ;
        TextView meetingId;
        public JoinViewModel(View convertView){
            title = convertView.findViewById(R.id.tvTitle);
            meetingId = convertView.findViewById(R.id.tvMeetingId);
        }
    }
}
