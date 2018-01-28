package com.ody.p2p.views;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StyleRes;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ody.p2p.Constants;
import com.ody.p2p.R;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.base.action.GrestCouponBean;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.webactivity.WebActivity;

import java.util.List;

/**
 * Created by user on 2017/3/31.
 */

public class TPDialog extends Dialog {

    public Context context;
    public List<FuncBean.Data.AdSource> source;
    public List<FuncBean.Data.AdSource> sourceList;
    public int type;

    public TPDialog(Context context, List<FuncBean.Data.AdSource> source, @StyleRes int themeResId, int type) {
        super(context, themeResId);
        this.context = context;
        this.source = source;
        this.type = type;
        initDialog(context, source);
    }


    public TPDialog(Context context, List<GrestCouponBean.DataEntity.RegisterCouponGuideEntity> source, @StyleRes int themeResId) {
        super(context, themeResId);
        this.context = context;
        initRegiestDialog(context, source);
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
        if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && type == 1) {
            Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
            screen_recommend.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("loginType", 1);
                    bundle.putInt("registerCoupon", 1);
                    JumpUtils.ToActivity(JumpUtils.LOGIN, bundle);
                    dismiss();
                }
            });
        } else {
            if (source != null && source.size() > 0) {
                Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
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
    }

    public void initRegiestDialog(Context context, final List<GrestCouponBean.DataEntity.RegisterCouponGuideEntity> source) {
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

        Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
        screen_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("loginType", 1);
//                bundle.putInt("registerCoupon", 1);
//                JumpUtils.ToActivity(JumpUtils.LOGIN, bundle);
//                dismiss();

                if (source.get(0).linkUrl.contains("http")) {
                    JumpUtils.ToWebActivity(source.get(0).linkUrl, WebActivity.NO_TITLE, -1, source.get(0).name);
                } else {
                    JumpUtils.ToActivity(source.get(0).linkUrl);
                }
                dismiss();
            }
        });


//        if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false) && type == 1) {
//            Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
//            screen_recommend.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Bundle bundle = new Bundle();
//                    bundle.putInt("loginType", 1);
//                    bundle.putInt("registerCoupon", 1);
//                    JumpUtils.ToActivity(JumpUtils.LOGIN, bundle);
//                    dismiss();
//                }
//            });
//        } else {
//            if (source != null && source.size() > 0) {
//                Glide.with(context).load(source.get(0).imageUrl).into(screen_recommend);
//                screen_recommend.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        if (!OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
//                            Bundle bundle = new Bundle();
//                            bundle.putInt("isTp", 1);
//                            JumpUtils.ToActivity(JumpUtils.LOGIN, bundle);
//                        } else {
//                            JumpUtils.toActivity(source.get(0));
//                        }
//                        dismiss();
//                    }
//                });
//            }
//        }
    }

}
