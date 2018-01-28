package com.ody.p2p.productdetail.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.produtdetail.R;

/**
 * Created by hanzhifengyun on 2017/7/4.
 */

public class StarView extends LinearLayout {

    private boolean isChecked;
    private LinearLayout mLlView;
    private ImageView mIvIcon;
    private TextView mTvText;

    public StarView(Context context) {
        this(context, null);
    }

    public StarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View parentView = inflater.inflate(R.layout.layout_star_view, this, true);
        mLlView = (LinearLayout) parentView.findViewById(R.id.ll_view);
        mIvIcon = (ImageView) parentView.findViewById(R.id.iv_icon);
        mTvText = (TextView) parentView.findViewById(R.id.tv_text);
        parentView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
//                setChecked(!isChecked);
                if (startViewOnClickListener != null){
                    startViewOnClickListener.startViewOnClick();
                }
            }
        });
    }


    public void setChecked(boolean checked) {
        isChecked = checked;
        if (checked) {
            setAlreadyStarState();
        } else {
            setNotStarState();
        }
    }

    private void setNotStarState() {
        mLlView.setBackgroundResource(R.drawable.bg_star_view_not_star);
        mTvText.setText(R.string.attention);
        mTvText.setTextColor(getResources().getColor(R.color.color_text_not_star));
        mIvIcon.setVisibility(VISIBLE);
    }

    private void setAlreadyStarState() {
        mLlView.setBackgroundResource(R.drawable.bg_star_view_star);
        mTvText.setText(R.string.already_star);
        mTvText.setTextColor(getResources().getColor(R.color.color_text_already_star));
        mIvIcon.setVisibility(GONE);
    }

    StartViewOnClickListener startViewOnClickListener;
    public interface StartViewOnClickListener {
        void startViewOnClick();
    }

    public void setStartViewOnClickListener(StartViewOnClickListener startViewOnClickListener) {
        this.startViewOnClickListener = startViewOnClickListener;
    }
}
