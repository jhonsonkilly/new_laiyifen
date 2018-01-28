package com.netease.nim.demo.session.viewholder;

import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.session.extension.ShareAttachment;
import com.netease.nim.demo.session.model.ShareGoodsModel;
import com.netease.nim.uikit.common.ui.recyclerview.adapter.BaseMultiItemFetchLoadAdapter;
import com.netease.nim.uikit.session.viewholder.MsgViewHolderBase;

/**
 * Created by zhoujianghua on 2015/8/4.
 */
public class MsgViewHolderShare extends MsgViewHolderBase {

    private ShareAttachment attachment;
    private ImageView imageView;
    private TextView title;
    private TextView content;

    public MsgViewHolderShare(BaseMultiItemFetchLoadAdapter adapter) {
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

            attachment = (ShareAttachment)message.getAttachment();
            String contentStr = attachment.getValue();
            ShareGoodsModel model = new Gson().fromJson(contentStr, new TypeToken<ShareGoodsModel>() {
            }.getType());
//            title.setText(model.get());
//            content.setText(model.getSysMessageInfo());

//            Glide.with(context).load(model.getSysMessageImageUrl()).into(imageView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }




}

