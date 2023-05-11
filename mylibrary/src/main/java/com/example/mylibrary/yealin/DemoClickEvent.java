package com.example.mylibrary.yealin;

import com.yealink.base.utils.ToastUtil;
import com.yealink.call.meetingcontrol.dialog.DefaultClickEvent;


/**
 * @author: chenqf
 * @date: 2021/12/22.
 * @email: chenqf@yealink.com
 * @describe: 支持重写点击事件
 */
public class DemoClickEvent extends DefaultClickEvent {
    @Override
    public void onInviteByEnterpriseContacts() {
        if (getActivity() != null) {
            ToastUtil.toast(getActivity(), "点击邀请的Toast提示");
        }
    }

    @Override
    public void onInviteByOtherConnections() {
        super.onInviteByOtherConnections();
        ToastUtil.toast(getActivity(), "点击邀请其他时Demo的Toast提示");
    }
}
