package com.netease.nim.demo.session.viewholder;

import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.SystemMessageImageNewAttachment;
import com.netease.nim.demo.session.extension.SystemMessageOnlyTextAttachment;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.adapter.Type10Modle;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class SystemMessageMsgImageViewHolder extends MsgViewHolderBase {

    private SystemMessageImageNewAttachment attachment;
    private ImageView imageView;
    private TextView title;
    private TextView content;

    public SystemMessageMsgImageViewHolder(BaseMultiItemFetchLoadAdapter adapter) {
        super(adapter);
    }

    @Override
    protected int getContentResId() {
        return R.layout.custom_system_message_ui;
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
        imageView = (ImageView) view.findViewById(R.id.image_view);
        title = (TextView)view.findViewById(R.id.tv_title);
        content = (TextView)view.findViewById(R.id.tv_content);
    }

    @Override
    protected void bindContentView() {
        try{
//            imageView.setVisibility(View.GONE);

            if (message.getAttachment() == null) {
                return;
            }

            attachment = (SystemMessageImageNewAttachment)message.getAttachment();
            String contentStr = attachment.getContent();
            Type10Modle model = new Gson().fromJson(contentStr, new TypeToken<Type10Modle>() {
            }.getType());
            title.setText(model.getSysMessageTitle());
            content.setText(model.getSysMessageInfo());

            Glide.with(context).load(model.getSysMessageImageUrl()).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}

