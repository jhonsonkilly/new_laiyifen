package com.netease.nim.demo.collect;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.netease.nim.demo.R;
import com.netease.nim.uikit.session.module.CollectModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//import com.bumptech.glide.annotation.GlideModule;
//import com.bumptech.glide.request.RequestOptions;

/**
 * @author SevenCheng
 */

public class CollectAdapter extends RecyclerView.Adapter {
    private Context            mContext;
    private List<CollectModel> mList;

    public CollectAdapter(Context context, List<CollectModel> models, OnLongClickListener onLongClickListener) {
        this.mContext = context;
        this.mList = models;
        this.mOnLongClickListener = onLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof ViewHolder) {

            final CollectModel collectModel = mList.get(position);
            final ViewHolder viewHolder = (ViewHolder) holder;
            viewHolder.mName.setText(collectModel.getFromNick());
            Glide.with(mContext).load(collectModel.getFormIcon()).into(viewHolder.mIcon);


            final long collect_time = collectModel.getCollect_time();
            String time = "";
            if (System.currentTimeMillis() - collect_time / 1000 / 60 / 60 / 24 <= 1) {
                time = "今天";
            } else if (System.currentTimeMillis() - collect_time / 1000 / 60 / 60 / 24 <= 2) {
                time = "昨天";
            } else {
                time = new SimpleDateFormat("yyyy-MM-dd").format(new Date(collect_time));
            }

            viewHolder.mTime.setText(time);

            //            RequestOptions requestOptions = RequestOptions.circleCropTransform();
            //            GlideApp.with(mContext).load(userInfo.getAvatar()).apply(requestOptions).into(viewHolder.mIcon);
            //            GlideApp.with(mContext).load(userInfo.getAvatar()).into(viewHolder.mIcon);


            viewHolder.mContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ShowCollectActivity.startActivity((Activity) mContext, collectModel);
                }
            });

            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    initPopup(viewHolder.mName, collectModel);

                    return true;
                }
            });

            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mOnLongClickListener!=null){
                        mOnLongClickListener.onClick(collectModel);
                    }
                }
            });


            if (collectModel.getExtension().equals("mp4")) {
                viewHolder.play_button.setVisibility(View.VISIBLE);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final Bitmap bitmap = createVideoThumbnail(collectModel.getUrl(), 100, 100);
                        ((Activity) mContext).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                viewHolder.mContent.setImageBitmap(bitmap);
                            }
                        });
                    }
                }).start();


            } else if(collectModel.getExtension().equals("jpg")){
                Glide.with(mContext).load(collectModel.getUrl()).into(viewHolder.mContent);
                viewHolder.play_button.setVisibility(View.INVISIBLE);
            }
        }
    }



    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
            // Assume this is a corrupt video file
        } catch (RuntimeException ex) {
            // Assume this is a corrupt video file.
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
                // Ignore failures while cleaning up.
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }

    private void initPopup(TextView content, final CollectModel collectModel) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(mContext.LAYOUT_INFLATER_SERVICE);
        View contentview = inflater.inflate(R.layout.popup_process, null);
        final PopupWindow popupWindow = new PopupWindow(contentview, ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        contentview.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //弹窗删除
                if (mOnLongClickListener != null) {
                    mOnLongClickListener.onLongClick(collectModel);
                }
                popupWindow.dismiss();
            }
        });

        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        //        popupWindow.showAtLocation(content, Gravity.CENTER, 10, 10);
        popupWindow.showAsDropDown(content, 10, 10);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView mIcon;
        public TextView  mName;
        public TextView  mTime;
        public ImageView mContent;
        public ImageView play_button;


        public ViewHolder(View itemView) {
            super(itemView);
            mName = (TextView) itemView.findViewById(R.id.name);
            mTime = (TextView) itemView.findViewById(R.id.time);
            mIcon = (ImageView) itemView.findViewById(R.id.icon);
            mContent = (ImageView) itemView.findViewById(R.id.content);
            play_button = (ImageView) itemView.findViewById(R.id.play_button);
        }
    }

    private OnLongClickListener mOnLongClickListener;

    public interface OnLongClickListener {
        void onLongClick(CollectModel model);
        void onClick(CollectModel model);
    }

}
