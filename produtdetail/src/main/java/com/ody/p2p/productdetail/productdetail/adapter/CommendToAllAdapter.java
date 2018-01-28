package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
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
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * Created by Administrator on 2016-6-5.
 * 评价
 */
public class CommendToAllAdapter extends BaseRecyclerViewAdapter {

    public int rb_style = -1;

    List<ProductComment.Data.MpcList.ListObj> list = getDatas();

    public void setRb_style(int rb_style) {
        this.rb_style = rb_style;
    }

    public CommendToAllAdapter(Context context, List datas) {
        super(context, datas);
    }

    int layoutView = R.layout.produtdetail_commendtoallitme_item;

    public void setLayoutView(int layoutView) {
        this.layoutView = layoutView;
    }

    @Override
    protected CommendToAllAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(layoutView, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        if (null != list && list.size() > 0) {
            if (rb_style != -1) {
                viewHodler.ratingbar.setVisibility(View.GONE);
                View vi = LayoutInflater.from(mContext).inflate(rb_style, null);
                viewHodler.linear_ratingbar.addView(vi);
                viewHodler.ratingbar = (RatingBar) vi;
                viewHodler.ratingbar.setIsIndicator(true);
            } else {
                viewHodler.ratingbar.setVisibility(View.VISIBLE);
            }
            viewHodler.ratingbar.setRating(list.get(position).getRate());
            if (!StringUtils.isEmpty(list.get(position).userUsername)) {
                viewHodler.name.setText(list.get(position).userUsername);//用户名
            } else {
                viewHodler.name.setText(R.string.anonymity_user);//用户名
            }
            //头像
            if (!StringUtils.isEmpty(list.get(position).getUserImg())) {
                GlideUtil.display(OdyApplication.gainContext(), list.get(position).getUserImg() + "").into(viewHodler.img_userphoto);
            } else {
                viewHodler.img_userphoto.setImageResource(R.drawable.ic_avatar_special);
            }
            //评论时间
            if (!StringUtils.isEmpty(list.get(position).createTime + "")) {
                viewHodler.txt_commenddata.setText(DateTimeUtils.formatDateTime(list.get(position).createTime, DateTimeUtils.DF_YYYY_MM_DD));
                viewHodler.txt_commenddata.setVisibility(View.GONE);
            } else {
                viewHodler.txt_commenddata.setVisibility(View.GONE);
            }
            //评论时间
            if (!StringUtils.isEmpty(list.get(position).orderCreateTime + "")) {
                viewHodler.txt_buyData.setText(DateTimeUtils.formatDateTime(Long.parseLong(list.get(position).orderCreateTime), DateTimeUtils.DF_YYYY_MM_DD));
                viewHodler.txt_buyData.setVisibility(View.VISIBLE);
            } else {
                viewHodler.txt_buyData.setVisibility(View.GONE);
            }
            if (null != list.get(position).mpShinePicList && list.get(position).mpShinePicList.size() > 0) {
                viewHodler.gridview.setVisibility(View.VISIBLE);
                CommendPhotoAdapter imgAdapter = new CommendPhotoAdapter(mContext, list.get(position).mpShinePicList);
                imgAdapter.setCommend(list.get(position).userUsername, list.get(position).content, list.get(position).getUserImg() + "");
                imgAdapter.setCallBack(new CommendPhotoAdapter.AppriesPhotoAdapterCallBack() {
                    @Override
                    public void onclikPhoto(int checkpostion) {
                        callBack.clickPhoto(list.get(position), checkpostion);
                    }
                });
                viewHodler.gridview.setLayoutManager(new GridLayoutManager(mContext, 5));
                viewHodler.gridview.setAdapter(imgAdapter);
            } else {
                viewHodler.gridview.setVisibility(View.GONE);
            }

            if (null != list.get(position).getMpAttrList() && list.get(position).getMpAttrList().size() > 0) {
                viewHodler.txt_coloclass.setText("");
                for (int i = 0; i < list.get(position).getMpAttrList().size(); i++) {
                    viewHodler.txt_coloclass.append(list.get(position).mpAttrList.get(i).name + ":");
                    viewHodler.txt_coloclass.append(list.get(position).mpAttrList.get(i).value + "； ");
                }
            } else {
                viewHodler.txt_coloclass.setText(mContext.getString(R.string.notStandard));
            }
            if (!StringUtils.isEmpty(list.get(position).content)) {
                viewHodler.content_txt.setVisibility(View.VISIBLE);
                viewHodler.content_txt.setText(list.get(position).content);
            } else {

                if (list.get(position).getRate() == 5) {
                    viewHodler.content_txt.setVisibility(View.VISIBLE);
                    viewHodler.content_txt.setText("好评!");
                } else {
                    viewHodler.content_txt.setVisibility(View.GONE);
                }

            }
            if (!StringUtils.isEmpty(list.get(position).replyContent)) {
                viewHodler.replyContent.setVisibility(View.VISIBLE);
                viewHodler.replyContent.setText("官方回复："+list.get(position).replyContent);
            } else {
                viewHodler.replyContent.setVisibility(View.GONE);
            }
            viewHodler.ll_commendonclik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callBack.onclick();
                }
            });
        } else {
            viewHodler.ll_commendonclik.setVisibility(View.GONE);
        }
    }

    AppriesAdapterCallBack callBack;

    public void setCallBack(AppriesAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AppriesAdapterCallBack {

        void onclick();

        void clickPhoto(ProductComment.Data.MpcList.ListObj listObj, int postion);
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        RatingBar ratingbar;
        ImageView img_userphoto;
        TextView txt_buyData, replyContent;
        TextView txt_commenddata;
        RelativeLayout linear_ratingbar;
        TextView name;
        TextView content_txt;
        RecyclerView gridview;
        TextView txt_coloclass;
        LinearLayout ll_commendonclik;//评价跳转

        public ViewHodler(View itemView) {
            super(itemView);
            ratingbar = (RatingBar) itemView.findViewById(R.id.rat_rating);
            img_userphoto = (ImageView) itemView.findViewById(R.id.img_userphoto);
            txt_commenddata = (TextView) itemView.findViewById(R.id.txt_commenddata);
            txt_buyData = (TextView) itemView.findViewById(R.id.txt_buyData);
            name = (TextView) itemView.findViewById(R.id.txt_username);
            replyContent = (TextView) itemView.findViewById(R.id.replyContent);
            content_txt = (TextView) itemView.findViewById(R.id.content_txt);
            txt_coloclass = (TextView) itemView.findViewById(R.id.txt_coloclass);
            gridview = (RecyclerView) itemView.findViewById(R.id.gridview);
            ll_commendonclik = (LinearLayout) itemView.findViewById(R.id.ll_commendonclik);
            linear_ratingbar = (RelativeLayout) itemView.findViewById(R.id.linear_ratingbar);
//            ratingbar.setProgressDrawable(mContext.getResources().getDrawable(appriesthemstyle));
        }
    }

}
