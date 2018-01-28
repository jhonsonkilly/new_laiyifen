package com.ody.p2p.webactivity;

import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.ody.p2p.R;

/**
 * Created by Sun on 2016/9/23.
 */

public class NoTitleNestedScrollWebFragment extends NoTitleWebFragment {


    @Override
    public int bindLayout() {
        return R.layout.fragment_nested_scroll_web;
    }

    @Override
    public void initView(View view) {
        super.initView(view);
        webView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent ev) {

                ((WebView)v).requestDisallowInterceptTouchEvent(true);

                return false;
            }
        });
    }
}
