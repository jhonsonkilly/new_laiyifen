package com.netease.nim.demo.collect;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.netease.nim.demo.R;
import com.netease.nim.demo.yidiancard.widget.IOSDialog;
import com.netease.nim.uikit.common.activity.UI;
import com.netease.nim.uikit.model.ToolBarOptions;
import com.netease.nim.uikit.session.module.CollectModel;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的收藏
 */
public class CollectActivity extends UI {
    public static String PATH = "collect.txt";
    private RecyclerView mRcv;
    private List<CollectModel> mModels = new ArrayList<>();
    private CollectAdapter mCollectAdapter;
    private File           mFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);

        ToolBarOptions options = new ToolBarOptions();
        options.titleString = "我的收藏";
        setToolBar(R.id.toolbar, options);
        initView();
        initData();
    }

    private void initData() {
        mModels.clear();
        Gson gson = new Gson();
        String cache = getCache();
        if (!cache.equals("")) {
            List<CollectModel> models = gson.fromJson(cache, new TypeToken<List<CollectModel>>() {
            }.getType());

            mModels.addAll(models);
        }
        mCollectAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mRcv = (RecyclerView) findViewById(R.id.rcv);
        mRcv.setLayoutManager(new LinearLayoutManager(this));
        mCollectAdapter = new CollectAdapter(this, mModels, new CollectAdapter.OnLongClickListener() {
            @Override
            public void onLongClick(CollectModel model) {
                mModels.remove(model);
                addCache(new Gson().toJson(mModels));
                initData();
            }

            @Override
            public void onClick(CollectModel model) {

                showHintDialog(model);

            }
        });
        mRcv.setAdapter(mCollectAdapter);

    }


    public static void startActivityForResult(Activity activity, int reqCode) {
        Intent intent = new Intent();
        intent.setClass(activity, CollectActivity.class);
        activity.startActivityForResult(intent, reqCode);
    }


    /**
     * 取文件
     *
     * @return
     */
    public String getCache() {
        try {
            FileInputStream inStream = openFileInput(PATH);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int length = -1;
            while ((length = inStream.read(buffer)) != -1) {
                stream.write(buffer, 0, length);
            }
            stream.close();
            inStream.close();
            return stream.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * 存文件
     *
     * @param cacheData
     */
    public void addCache(String cacheData) {
        try {
            FileOutputStream outStream = openFileOutput(PATH, Context.MODE_PRIVATE);
            outStream.write(cacheData.getBytes());
            outStream.close();
        } catch (FileNotFoundException e) {
            return;
        } catch (IOException e) {
            return;
        }
    }

    private void showHintDialog(final CollectModel model) {
        final IOSDialog dialog = new IOSDialog(this, R.style.customDialog, R.layout.dialog_black);
        dialog.show();
        TextView tvCancel = (TextView) dialog.findViewById(R.id.cancel);
        TextView content = (TextView) dialog.findViewById(R.id.msg);
        TextView tvOk = (TextView) dialog.findViewById(R.id.ok);

        content.setText("是否发送" + (model.getExtension().equals("mp4") ? "视频" : "图片") + "给好友");


        tvOk.setText("发送");
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (model.getExtension().equals("mp4")) {
                    saveVD(model);
                } else {
                    saveImg(model);
                }


                dialog.dismiss();

            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }


    private void saveImg(final CollectModel model) {
        Intent intent = new Intent();
        intent.putExtra("COLLECTMODEL", model);
        setResult(Activity.RESULT_OK, intent);
        finish();


    }


    /**
     * 保存视频并发送
     *
     * @param model
     */
    public void saveVD(final CollectModel model) {
        MediaPlayer mediaPlayer;
        try {
            mediaPlayer = MediaPlayer.create(CollectActivity.this, Uri.fromFile(new File(model.getPath())));
            // 视频文件持续时间
            long duration = mediaPlayer == null ? 0 : mediaPlayer.getDuration();
            // 视频高度
            int height = mediaPlayer == null ? 0 : mediaPlayer.getVideoHeight();
            // 视频宽度
            int width = mediaPlayer == null ? 0 : mediaPlayer.getVideoWidth();

            CollectVDModel mCollectVDModel = new CollectVDModel(duration, height, width);

            Intent intent = new Intent();
            intent.putExtra("COLLECTMODEL", model);
            intent.putExtra("MODEL", mCollectVDModel);
            setResult(Activity.RESULT_OK, intent);
            finish();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
