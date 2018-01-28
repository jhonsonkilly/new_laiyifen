package com.netease.nim.demo.groupsend.adapter;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.demo.R;
import com.netease.nim.demo.groupsend.GroupSendActivity;
import com.netease.nim.demo.groupsend.model.GroupSendModel;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmin on 2017/8/31.
 */

public class BillItemAdapter extends BaseQuickAdapter<GroupSendModel,BaseViewHolder> {

    private List<GroupSendModel> mData=new ArrayList<GroupSendModel>();

    public BillItemAdapter(@LayoutRes int layoutResId, @Nullable List<GroupSendModel> data) {
        super(layoutResId, data);
    }
    public BillItemAdapter(List<GroupSendModel> data) {
        super(data);
        mData = data;
    }


    @Override
    protected void convert(BaseViewHolder baseViewHolder, final GroupSendModel billListBean) {

        HeadImageView headImageView = (HeadImageView) baseViewHolder.getView(R.id.img_head);
        int getType = billListBean.getItemType();
        switch (getType) {
            case 2:
                final NimUserInfo userInfo = NimUserInfoCache.getInstance().getUserInfo(billListBean.getP2pAccount());
                baseViewHolder.setText(R.id.tv_name, NimUserInfoCache.getInstance().getUserDisplayNameEx(billListBean.getP2pAccount()));
                String mobile = userInfo.getMobile()+"";
                if (!(mobile.length()<11)){
                    mobile = mobile.replace(mobile.substring(4, 8), "****");
                }

                baseViewHolder.setText(R.id.tv_mobile,mobile);
                headImageView.loadBuddyAvatar(billListBean.getP2pAccount());
                baseViewHolder.setVisible(R.id.tv_mobile,true);
                break;
            case 1:

                Team team = TeamDataCache.getInstance().getTeamById(billListBean.getTeamId());
                baseViewHolder.setText(R.id.tv_name, billListBean.getName());
                headImageView.loadTeamIconByTeam(team);
                baseViewHolder.setVisible(R.id.tv_mobile,false);
                break;
        }

        if (billListBean.isSelect()){
            baseViewHolder.setImageResource(R.id.iv_select,R.drawable.icon_check_yellow);
        }else{
            baseViewHolder.setImageResource(R.id.iv_select,R.drawable.icon_nocheck);
        }

    }
}
