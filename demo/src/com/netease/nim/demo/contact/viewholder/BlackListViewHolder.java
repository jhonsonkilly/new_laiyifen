package com.netease.nim.demo.contact.viewholder;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.activity.BlackListAdapter;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.common.adapter.TViewHolder;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nimlib.sdk.uinfo.UserInfoProvider;
import com.netease.nimlib.sdk.uinfo.model.NimUserInfo;

/**
 * Created by huangjun on 2015/9/22.
 */
public class BlackListViewHolder extends TViewHolder {
    private HeadImageView             headImageView;
    private TextView                  accountText;
    private TextView                  tel;
    private Button                    removeBtn;
    private UserInfoProvider.UserInfo user;
    private View mView;

    @Override
    protected int getResId() {
        return R.layout.black_list_item;
    }

    @Override
    protected void inflate() {
        headImageView = findView(R.id.head_image);
        mView = findView(R.id.item_view);
        accountText = findView(R.id.account);
        //        removeBtn = findView(R.id.remove);
        tel = findView(R.id.tel);
    }

    @Override
    protected void refresh(Object item) {
        user = (NimUserInfo) item;

        accountText.setText(NimUserInfoCache.getInstance().getUserDisplayName(user.getAccount()));
        headImageView.loadBuddyAvatar(user.getAccount());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().getEventListener().onItemClick(user);
            }
        });



        tel.setText(((NimUserInfo)user).getMobile().substring(0, 3) + "*****" + ((NimUserInfo)user).getMobile().substring(((NimUserInfo)user).getMobile().length() - 3, ((NimUserInfo)user).getMobile().length()));

       /* removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().getEventListener().onRemove(user);
            }
        });*/

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAdapter().getEventListener().onRemove(user);
            }


        });
    }



    protected final BlackListAdapter getAdapter() {
        return (BlackListAdapter) adapter;
    }
}
