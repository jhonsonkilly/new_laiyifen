package com.ody.p2p.message.sysmessagelist;

import com.ody.p2p.base.BaseView;

import java.util.List;

/**
 * Created by ody on 2016/8/25.
 */
public interface SysMessageListView extends BaseView{
    void getSysMessageBean(List<SysMessageListBean.Dataes> mdata);
}
