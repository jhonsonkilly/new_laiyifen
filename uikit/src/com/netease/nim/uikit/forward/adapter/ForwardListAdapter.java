package com.netease.nim.uikit.forward.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.cache.TeamDataCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.forward.model.GroupSendModel;
import com.netease.nimlib.sdk.team.model.Team;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jasmin on 2017/8/31.
 */

public class ForwardListAdapter extends BaseQuickAdapter<GroupSendModel,BaseViewHolder> {

    private List<GroupSendModel> mData=new ArrayList<GroupSendModel>();

    public ForwardListAdapter(@LayoutRes int layoutResId, @Nullable List<GroupSendModel> data) {
        super(layoutResId, data);
    }
    public ForwardListAdapter(List<GroupSendModel> data) {
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
                    mobile.replace(mobile.substring(4, 8), "****");
                }

                baseViewHolder.setText(R.id.tv_mobile,userInfo.getMobile());
                headImageView.loadBuddyAvatar(billListBean.getP2pAccount());
                break;
            case 1:
                Team team = TeamDataCache.getInstance().getTeamById(billListBean.getTeamId());
                baseViewHolder.setText(R.id.tv_name, billListBean.getName());
                headImageView.loadTeamIconByTeam(team);
                baseViewHolder.setText(R.id.tv_mobile,"");
                break;
        }

        if (billListBean.isSelect()){
            baseViewHolder.setImageResource(R.id.iv_select,R.drawable.icon_check_yellow);
        }else{
            baseViewHolder.setImageResource(R.id.iv_select,R.drawable.icon_nocheck);
        }

    }
}
