package com.netease.nim.uikit.common.ui.imageview;

/**
 * Created by jasmin on 2017/12/21.
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.netease.nim.uikit.NimUIKit;
import com.netease.nim.uikit.R;
import com.netease.nim.uikit.common.activity.UI;

import java.io.Serializable;

/**
 * 用于展示图片
 */
public class ShowHeadImage extends UI {
    public static final String DATA = "SHOWHEAD";
    private ImageView mPic;
    private String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_headimage);

        imageUrl = getIntent().getStringExtra(DATA);
        initView();
        initPic();
    }

    private void initView() {
        mPic = (ImageView) findViewById(R.id.iv_pic);
        mPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initPic() {
//        if (imageUrl != null && (!imageUrl.equals(""))){
            Glide.with(this)
                    .load(imageUrl).asBitmap()
                    .placeholder(NimUIKit.getUserInfoProvider()
                            .getDefaultIconResId()).error(NimUIKit.getUserInfoProvider()
                    .getDefaultIconResId())
                    .into(mPic);
//        }else{
//            Glide.with(this)
//                    .load(NimUIKit.getUserInfoProvider()
//                            .getDefaultIconResId()).asBitmap()
//                    .fitCenter()
//                    .into(mPic);
//        }

    }

    public static void startActivity(Activity activity, String data) {
        Intent intent = new Intent();
        intent.putExtra(DATA, (Serializable) data);
        intent.setClass(activity, ShowHeadImage.class);
        activity.startActivity(intent);
    }


}

