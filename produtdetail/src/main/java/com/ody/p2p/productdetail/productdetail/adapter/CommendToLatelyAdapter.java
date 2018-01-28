package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2016-6-5.
 * 评价这个使用在第一个页卡中
 */
public class CommendToLatelyAdapter extends BaseRecyclerViewAdapter {

    public int rb_style = -1;
    public int viewLayout = -1;

    public void setRb_style(int rb_style) {
        this.rb_style = rb_style;
    }

    public CommendToLatelyAdapter(Context context, List datas) {
        super(context, datas);
    }

    public void setViewLayout(int viewLayout) {
        this.viewLayout = viewLayout;
    }

    @Override
    protected CommendToLatelyAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        if (viewLayout != -1) {
            return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(viewLayout, parent, false));
        }
        return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_commendtorecently_item, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        final ProductComment.Data.MpcList.ListObj mdata = (ProductComment.Data.MpcList.ListObj) mDatas.get(position);
        if (rb_style != -1) {
            viewHodler.ratingbar.setVisibility(View.GONE);
            View vi = LayoutInflater.from(mContext).inflate(rb_style, null);
            viewHodler.linear_ratingbar.addView(vi);
            viewHodler.ratingbar = (RatingBar) vi;
            viewHodler.ratingbar.setIsIndicator(true);

        } else {
            viewHodler.ratingbar.setVisibility(View.VISIBLE);
        }

        //头像
        if (!StringUtils.isEmpty(mdata.getUserImg())) {
            GlideUtil.display(mContext, mdata.getUserImg() + "").into(viewHodler.img_userphoto);
        } else {
            viewHodler.img_userphoto.setImageResource(R.drawable.ic_avatar_special);
        }
        viewHodler.ratingbar.setRating(mdata.getRate());

        if (!StringUtils.isEmpty(mdata.getUserUsername())) {
            viewHodler.name.setText(mdata.getUserUsername());//用户名
        } else {
            viewHodler.name.setText(mContext.getString(R.string.anonymity_user));//用户名
        }
        viewHodler.tv_evaluateTime.setText(DateTimeUtils.formatDateTime(mdata.createTime, DateTimeUtils.DF_YYYY_MM_DD));
        if (null != mdata.mpShinePicList && mdata.mpShinePicList.size() > 0) {
            CommendPhotoAdapter imgAdapter = new CommendPhotoAdapter(mContext, mdata.mpShinePicList);
            imgAdapter.setCommend(mdata.userUsername, mdata.content, mdata.getUserImg() + "");
            imgAdapter.setCallBack(new CommendPhotoAdapter.AppriesPhotoAdapterCallBack() {
                @Override
                public void onclikPhoto(int checkpostion) {
                    callBack.clickPhoto(mdata, checkpostion);
                }
            });
            viewHodler.gridview.setLayoutManager(RecycleUtils.getGridLayoutManager(mContext, 5));
            viewHodler.gridview.setAdapter(imgAdapter);
        } else {
            viewHodler.gridview.setVisibility(View.GONE);
        }
        if (!StringUtils.isEmpty(mdata.content)) {

            viewHodler.txt_content.setVisibility(View.VISIBLE);
            viewHodler.txt_content.setText(mdata.content);
        } else {

            viewHodler.txt_content.setVisibility(View.GONE);


        }
        if (!StringUtils.isEmpty(mdata.replyContent)) {
            viewHodler.replyContent.setVisibility(View.VISIBLE);
            viewHodler.replyContent.setText("官方回复：" + mdata.replyContent);
        } else {
            viewHodler.replyContent.setVisibility(View.GONE);
        }
        viewHodler.ll_commendonclik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onclick();
            }
        });
    }

    AppriesAdapterCallBack callBack;


    public void setCallBack(AppriesAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AppriesAdapterCallBack {

        void onclick();

        void clickPhoto(ProductComment.Data.MpcList.ListObj listObj, int postion);
    }


    public class ViewHodler extends BaseRecyclerViewHolder {
        ImageView img_userphoto;
        RatingBar ratingbar;
        TextView name, tv_evaluateTime, replyContent;
        TextView txt_content;
        RecyclerView gridview;
        LinearLayout ll_commendonclik;
        RelativeLayout linear_ratingbar;

        public ViewHodler(View itemView) {
            super(itemView);
            ratingbar = (RatingBar) itemView.findViewById(R.id.rat_rating);
            img_userphoto = (ImageView) itemView.findViewById(R.id.img_userphoto);
            replyContent = (TextView) itemView.findViewById(R.id.replyContent);
            name = (TextView) itemView.findViewById(R.id.txt_username);
            txt_content = (TextView) itemView.findViewById(R.id.txt_content);
            tv_evaluateTime = (TextView) itemView.findViewById(R.id.tv_evaluateTime);
            gridview = (RecyclerView) itemView.findViewById(R.id.gridview);
            ll_commendonclik = (LinearLayout) itemView.findViewById(R.id.ll_commendonclik);
            linear_ratingbar = (RelativeLayout) itemView.findViewById(R.id.linear_ratingbar);

//            ratingbar.setProgressDrawable(mContext.getResources().getDrawable(appriesthemstyle));
        }


    }


}
