package com.ody.p2p.main.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.main.R;


/**
 * Created by meijunqiang on 2016/10/27.
 * 描述：头部多处使用到分享，统一接口和逻辑
 */
public class ImToolBar extends LinearLayout {

    private RelativeLayout mRlHead;
    private RelativeLayout mRlBigBack;
    private TextView mTvName;
    private RelativeLayout mRlShowMore;
    private ImageView mIvRightIcon;
    private TextView mTvRightText;

    private ImageView mIvRightIcon2;

    /**
     * view初始化
     */
    private void assignViews() {
        mRlHead = (RelativeLayout) findViewById(R.id.rl_head);
        mRlBigBack = (RelativeLayout) findViewById(R.id.rl_big_back);
        mTvName = (TextView) findViewById(R.id.tv_name);
        mRlShowMore = (RelativeLayout) findViewById(R.id.rl_show_more);
        mIvRightIcon = (ImageView) findViewById(R.id.iv_right_icon);
        mIvRightIcon2 = (ImageView) findViewById(R.id.iv_right_icon2);
        mTvRightText = (TextView) findViewById(R.id.tv_right_text);
    }

    public ImToolBar(Context context) {
        this(context, null);
    }

    public ImToolBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImToolBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    //初始化控件
    private void init(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ImToolBar);
        String title = ta.getString(R.styleable.ImToolBar_tv_title);
        String text = ta.getString(R.styleable.ImToolBar_right_text);
        boolean tvHas = ta.getBoolean(R.styleable.ImToolBar_right_tv_visible, false);
        boolean imgHas = ta.getBoolean(R.styleable.ImToolBar_right_img_visible, false);
        boolean imgHas2 = ta.getBoolean(R.styleable.ImToolBar_right_img2_visible, false);
        int imgId = ta.getResourceId(R.styleable.ImToolBar_right_bg, R.drawable.appraise_bg);
        int imgId2 = ta.getResourceId(R.styleable.ImToolBar_right_bg2, R.drawable.appraise_bg);
        View.inflate(getContext(), R.layout.im_head, this);
        assignViews();
        mTvRightText.setVisibility(tvHas ? View.VISIBLE : View.GONE);
        mIvRightIcon.setVisibility(imgHas ? View.VISIBLE : View.GONE);
        mIvRightIcon2.setVisibility(imgHas2 ? View.VISIBLE : View.GONE);
        if (!TextUtils.isEmpty(title)) {
            mTvName.setText(title);
        }
        if (!TextUtils.isEmpty(text)) {
            mTvRightText.setText(text);
        }
        //不要传除了color和drawable以外的，不做判断
        if (imgId != R.drawable.appraise_bg) {
            mIvRightIcon.setImageResource(imgId);
        }//不要传除了color和drawable以外的，不做判断
        if (imgId2 != R.drawable.appraise_bg) {
            mIvRightIcon2.setImageResource(imgId2);
        }
        initEvent();
    }

    /**
     * 事件
     */
    private void initEvent() {
        mRlBigBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(getContext() instanceof Activity)) {
                    return;
                }
                ((Activity) getContext()).finish();
            }
        });
    }

    //    private SharePopupWindow shareindow;
    public ImageView getIvRightIcon2() {
        return mIvRightIcon2;
    }

    /**
     * 社区——分享
     */
    public void initShare(final String MP_ID) {
//        mRlShowMore.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != shareindow) {
//                    shareindow.dismiss();
//                    shareindow = null;
//                }
//                shareindow = new SharePopupWindow(getContext(), SharePopupWindow.SHARE_COMMUNITY, MP_ID);
//                shareindow.showAtLocation(((Activity) getContext()).getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
//            }
//        });
    }

    public RelativeLayout getRlHead() {
        return mRlHead;
    }

    public void setRlHead(RelativeLayout rlHead) {
        mRlHead = rlHead;
    }

    public RelativeLayout getRlBigBack() {
        return mRlBigBack;
    }

    public void setRlBigBack(RelativeLayout rlBigBack) {
        mRlBigBack = rlBigBack;
    }

    public TextView getTvName() {
        return mTvName;
    }

    public void setTvName(TextView tvName) {
        mTvName = tvName;
    }

    public RelativeLayout getRlShowMore() {
        return mRlShowMore;
    }

    public void setRlShowMore(RelativeLayout rlShowMore) {
        mRlShowMore = rlShowMore;
    }

    public TextView getTvRightText() {
        return mTvRightText;
    }

    public void setTvRightText(TextView tvRightText) {
        mTvRightText = tvRightText;
    }

    public ImageView getIvRightIcon() {
        return mIvRightIcon;
    }

    public void setIvRightIcon(ImageView ivRightIcon) {
        mIvRightIcon = ivRightIcon;
    }
}
