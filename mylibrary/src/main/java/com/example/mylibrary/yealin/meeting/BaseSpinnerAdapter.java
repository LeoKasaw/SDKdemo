package com.example.mylibrary.yealin.meeting;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;


import com.example.mylibrary.R;

import java.util.ArrayList;
import java.util.List;

public class BaseSpinnerAdapter<Data> extends BaseAdapter implements SpinnerAdapter {

    protected List<Data> mDataList;
    protected Context mContext;

    public BaseSpinnerAdapter(Context context) {
        super();
        mDataList = new ArrayList<>();
        mContext = context;
    }

    public void setData(List<Data> dataList) {
        if (dataList != null) {
            mDataList.clear();
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void addData(List<Data> dataList) {
        if (dataList != null) {
            mDataList.addAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void addData(Data data) {
        if (data != null) {
            mDataList.add(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(Data data) {
        if (data != null) {
            mDataList.remove(data);
            notifyDataSetChanged();
        }
    }

    public void removeData(List<Data> dataList) {
        if (dataList != null) {
            mDataList.removeAll(dataList);
            notifyDataSetChanged();
        }
    }

    public void clearData() {
        if (mDataList != null) {
            mDataList.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        if (mDataList == null) {
            return 0;
        }
        return mDataList.size();
    }

    @Override
    public Data getItem(int position) {
        if (mDataList == null || mDataList.size() <= position || position < 0) {
            return null;
        }
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public List<Data> getDataList() {
        return mDataList;
    }


    @Override
    public View getDropDownView(int i, View view, ViewGroup viewGroup) {
        return getView(i, view, viewGroup);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.settings_sp_item, viewGroup, false);
        }
        TextView content = view.findViewById(R.id.text);
        Item data = (Item<String>) getItem(i);
        content.setText(data.getDisplayName());
        content.setTag(data);
        return view;
    }
}
