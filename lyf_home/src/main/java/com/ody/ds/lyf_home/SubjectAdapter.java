package com.ody.ds.lyf_home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.FuncBean;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ViewUtils;
import com.ody.p2p.views.scrollbanner.ScrollBanner;
import com.ody.p2p.views.slidepager.AutoScrollViewPager;
import com.ody.p2p.views.slidepager.BannerBean;
import com.ody.p2p.views.slidepager.BannerPager;
import com.ody.p2p.views.slidepager.RecommendBannerPager;

import java.util.ArrayList;
import java.util.List;

import static com.ody.p2p.utils.PxUtils.dipTopx;

/**
 * Created by lxs on 2016/12/6.
 */
public class SubjectAdapter extends BaseRecyclerViewAdapter{

    public SubjectAdapter(Context context, List datas) {
        super(context, datas);
    }

    public double imgWidth;
    public double aspectRatio;

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        final AppHomePageBean.Item bean = (AppHomePageBean.Item)getDatas().get(position);
        SubjectViewHolder holder1 = (SubjectViewHolder) holder;
        if (imgWidth > 0 && aspectRatio > 0) {
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder1.iv_subject.getLayoutParams();
            params.width = (int) (OdySysEnv.SCREEN_WIDTH * imgWidth) / 100;
            params.height = (int) ((double) OdySysEnv.SCREEN_WIDTH * imgWidth / (aspectRatio * 100));
            GlideUtil.display(mContext, bean.imgUrl).into(holder1.iv_subject);
        } else {
            GlideUtil.display(mContext, bean.imgUrl, 2).into(holder1.iv_subject);
        }
        holder1.iv_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.link != null){
                    JumpUtils.ToWebActivity(mContext, bean.link.data);
                }
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new SubjectViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_subject_item, parent, false)));
    }

    //特色主题
    public static class SubjectViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_topic_title;
        public TextView tv_topic_content;
        public ImageView iv_subject;
        public RelativeLayout rl_item;


        public SubjectViewHolder(View view) {
            super(view);
            rl_item = (RelativeLayout) view.findViewById(R.id.rl_item);
            tv_topic_title = (TextView) view.findViewById(R.id.tv_topic_title);
            tv_topic_content = (TextView) view.findViewById(R.id.tv_topic_content);
            iv_subject = (ImageView) view.findViewById(R.id.iv_subject);
        }
    }

    public double getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(double imgWidth) {
        this.imgWidth = imgWidth;
    }

    public double getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(double aspectRatio) {
        this.aspectRatio = aspectRatio;
    }

}
