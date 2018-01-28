package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.views.glide.GlideRoundTransform;

import java.util.List;

/**
 * Created by ody on 2016/6/16.
 * 评价里面的评论图片
 */
public class CommendPhotoAdapter extends BaseRecyclerViewAdapter {
    String username;
    String content;
    String userImg;


    public void setCommend(String username, String content, String userImg) {
        this.content = content;
        this.username = username;
        this.userImg=userImg;
    }


    List<String> list = getDatas();


    public CommendPhotoAdapter(Context mContext, List list) {
        super(mContext, list);

    }


    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        GlideUtil.display(mContext, list.get(position)).transform(new GlideRoundTransform(mContext, 3)).into(viewHodler.img_picture);
        viewHodler.img_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null){
                    callBack.onclikPhoto(position);
                }
            }
        });
    }

    AppriesPhotoAdapterCallBack callBack;

    public void setCallBack(AppriesPhotoAdapterCallBack callBack) {
        this.callBack = callBack;
    }

    public interface AppriesPhotoAdapterCallBack {
        void onclikPhoto(int checkpostion);
    }

    @Override
    protected CommendPhotoAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_list_gridview_item, parent, false));
    }


    class ViewHodler extends BaseRecyclerViewHolder {
        ImageView img_picture;
        public ViewHodler(View itemView) {
            super(itemView);
            img_picture = (ImageView) itemView.findViewById(R.id.img_picture);
        }
    }

}
