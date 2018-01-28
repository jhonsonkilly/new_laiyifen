package com.netease.nim.demo.chatroom.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.session.module.CollectModel;
import com.netease.nimlib.sdk.msg.model.IMMessage;

/**
 * 用于展示收藏夹里的视频和图片
 */
public class ShowIMGActivity extends UI implements View.OnClickListener {
    public static final String DATA = "SHOWPIC";
    private VideoView mVideo;
    private CollectModel mCollectModel;
    private View mPicView;
    private View video_view;
    private ImageView mPic;
    private AlertDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_collect);
        mCollectModel = (CollectModel) getIntent().getSerializableExtra(DATA);
        initView();
    }

    private void initView() {
        findViewById(R.id.layout).setOnClickListener(this);
        mPicView = findViewById(R.id.pic);
        video_view = findViewById(R.id.video_view);
        mPic = (ImageView) findViewById(R.id.iv_pic);

        if (mCollectModel.getExtension().equals("mp4")) {
            initVideo();
        } else if (mCollectModel.getExtension().equals("jpg") || mCollectModel.getExtension().equals("png") || mCollectModel.getExtension().equalsIgnoreCase("JPEG")) {
            initPic();
        }
    }

    private void initPic() {
        mPicView.setVisibility(View.VISIBLE);
        //        GlideApp.with(this).load(mCollectModel.getUrl()).into(mPic);
        Glide.with(this).load(mCollectModel.getUrl()).asBitmap().into(mPic);
    }

    private void initVideo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.customDialog);
        builder.setView(LayoutInflater.from(this).inflate(R.layout.alert_dialog, null));
        mDialog = builder.create();
        mDialog.show();
        video_view.setVisibility(View.VISIBLE);
        mVideo = (VideoView) findViewById(R.id.video);
        mVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mDialog.dismiss();
            }
        });
        mVideo.setMediaController(new MediaController(this));
        //        mVideo.setVideoURI(Uri.parse(mCollectModel.getUrl()));
        mVideo.setVideoPath(mCollectModel.getUrl());
        mVideo.start();

        mVideo.requestFocus();
    }

    public static void startActivity(Activity activity, IMMessage imMessage) {
        Intent intent = new Intent();
        intent.putExtra(DATA, imMessage);
        intent.setClass(activity, ShowIMGActivity.class);
        activity.startActivity(intent);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.layout) {
            finish();
        }
    }
}
