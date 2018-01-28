package com.netease.nim.demo.session.action;

import android.app.Activity;
import android.content.Intent;

import com.netease.nim.demo.R;
import com.netease.nim.demo.collect.CollectActivity;
import com.netease.nim.demo.collect.CollectVDModel;
import com.netease.nim.demo.session.extension.CustomAttachmentType;
import com.netease.nim.uikit.session.actions.BaseAction;
import com.netease.nim.uikit.session.module.CollectModel;
import com.netease.nim.uikit.util.ToastUtil;
import com.netease.nimlib.sdk.msg.MessageBuilder;
import com.netease.nimlib.sdk.msg.model.IMMessage;

import java.io.File;

/**
 * Created by hzxuwen on 2015/6/11.
 */
public class MyfavoriteAction extends BaseAction {


    private File mFile;

    public MyfavoriteAction() {
        super(R.drawable.favorite, R.string.input_panel_favorite);
    }

    @Override
    public void onClick() {

        //        ContactSelectActivity.Option advancedOption = TeamHelper.getContactSelectOption(null);
        //        advancedOption.multi = false;
        //        NimUIKit.startContactSelector(getActivity(), advancedOption, makeRequestCode(RequestCode.BUSINUSSCARD));
        CollectActivity.startActivityForResult(getActivity(), makeRequestCode(CustomAttachmentType.collect));

        //        sendMessage(message);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == CustomAttachmentType.collect) {
                CollectModel model = (CollectModel) data.getSerializableExtra("COLLECTMODEL");
                //                File file = (File) data.getSerializableExtra("FILE");
                String extension = model.getExtension();

                if (extension.equals("mp4")) { //发视频


                    CollectVDModel vdmodel = (CollectVDModel) data.getSerializableExtra("MODEL");
                    //                     创建视频消息
                    IMMessage message = MessageBuilder.createVideoMessage(getAccount(), getSessionType(), new File(model.getPath()), vdmodel.duration, vdmodel.width, vdmodel.height, null);
                    //
                    //                                         发送给对方
                    if (message == null) {
                        ToastUtil.showLongToast(getActivity(), "本地文件不存在");
                        return;
                    }
                    sendMessage(message);

                } else { //发图片
                    File file = new File(model.getPath());
                    if (file == null) {
                        ToastUtil.showLongToast(getActivity(), "本地文件不存在");
                        return;
                    }
                    IMMessage message = MessageBuilder.createImageMessage(getAccount(), getSessionType(), new File(model.getPath()), file.getName());
                    //发送给对方
                    sendMessage(message);
                }
            }
        }
    }


}
