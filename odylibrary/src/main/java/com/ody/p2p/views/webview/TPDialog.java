package com.ody.p2p.views.webview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.utils.JumpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/3/31.
 */

public class TPDialog extends Dialog {

    public Context context;
    public List<FuncBean.Data.AdSource> source;

    public TPDialog(Context context, List<FuncBean.Data.AdSource> source, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        this.source = source;
        initDialog(context, source);
    }


    public void initDialog(Context context, final List<FuncBean.Data.AdSource> source) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_screen_recommend, null);
        ImageView image_close = (ImageView) view.findViewById(R.id.image_close);
        ImageView screen_recommend = (ImageView) view.findViewById(R.id.screen_recommend);
        setContentView(view);
        image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        if (source != null && source.size() > 0) {
            Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
        }
        screen_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("isTp", 1);
                    JumpUtils.ToActivity(JumpUtils.LOGIN, bundle);
                } else {
                    JumpUtils.toActivity(source.get(0));
                }
                dismiss();
            }

        });
    }

}
