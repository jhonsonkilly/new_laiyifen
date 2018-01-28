package com.netease.nim.demo.session.viewholder;

import android.util.Log;
import android.widget.TextView;

import com.netease.nim.demo.R;
import com.netease.nim.demo.contact.activity.UserProfileActivity;
import com.netease.nim.demo.session.extension.BusinessCardAttachment;
import com.netease.nim.uikit.cache.NimUserInfoCache;
import com.netease.nim.uikit.common.ui.imageview.HeadImageView;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hzliuxuanlin on 17/9/15.
 */

public class MsgViewHolderBusinessCard extends MsgViewHolderBase {

    private BusinessCardAttachment guessAttachment;
    private HeadImageView imageView;
    private TextView name;
    private String mAccount;

    @Override
    protected boolean isSystemMessage() {
        return false;
    }


    public MsgViewHolderBusinessCard(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.custom_business_card;
    }

    @Override
    protected void inflateContentView() {
        imageView = (HeadImageView) view.findViewById(R.id.rock_paper_scissors_text);
        name = (TextView) view.findViewById(R.id.tv_name);
    }

    @Override
    protected void bindContentView() {
        if (message.getAttachment() == null) {
            return;
        }
        guessAttachment = (BusinessCardAttachment) message.getAttachment();
        mAccount = guessAttachment.getBussinessCardUserId();
        List<String> list = new ArrayList<String>();
        list.add(mAccount);
        name.setText(NimUserInfoCache.getInstance().getUserDisplayNameEx(mAccount));


        imageView.loadBuddyAvatar(mAccount);
        //        imageView.setImageResource(R.drawable.message_view_rock);
        Log.i("system", guessAttachment.getBussinessCardUserId());

    }

    @Override
    protected void onItemClick() {
        super.onItemClick();
        UserProfileActivity.start(context, mAccount,"分享名片添加");

    }
}
