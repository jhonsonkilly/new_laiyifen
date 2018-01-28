package com.netease.nim.demo.session.viewholder;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.SystemMessageOnlyTextAttachment;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.adapter.Type9Model;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;
import com.ody.p2p.utils.JumpUtils;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class SystemMessageMsgOnlyTextViewHolder extends MsgViewHolderBase {

    private SystemMessageOnlyTextAttachment attachment;
    private ImageView imageView;
    private TextView title;
    private TextView content;
    private String mSysMessageDetaiUrl;

    public SystemMessageMsgOnlyTextViewHolder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return com.netease.nim.uikit.R.layout.custom_system_onlymessage_ui;
    }

    @Override
    protected boolean isShowHeadImage() {

        return false;
    }

    @Override
    protected boolean isMiddleItem() {
        return true;
    }

    @Override
    protected boolean isSystemMessage() {
        return false;
    }


    @Override
    protected void inflateContentView() {
        //        imageView = (ImageView) view.findViewById(R.id.imageView);
        title = (TextView) view.findViewById(R.id.tv_title);
        content = (TextView) view.findViewById(R.id.tv_content);
    }

    @Override
    protected void bindContentView() {
        try {
            //            imageView.setVisibility(View.GONE);

            if (message.getAttachment() == null) {
                return;
            }

            attachment = (SystemMessageOnlyTextAttachment) message.getAttachment();
            String contentStr = attachment.getContent();
            Type9Model model = new Gson().fromJson(contentStr, new TypeToken<Type9Model>() {
            }.getType());
            mSysMessageDetaiUrl = model.getSysMessageDetaiUrl();
            title.setText(model.getSysMessageTitle());
            content.setText(model.getSysMessageInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onItemClick() {
        super.onItemClick();
        if (!mSysMessageDetaiUrl.equals("")) {

            JumpUtils.ToWebActivity(context, mSysMessageDetaiUrl);
        }
        // Html5Activity.startActivity(context, mSysMessageDetaiUrl);
    }
}

