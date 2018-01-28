package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * Created by ody on 2016/7/6.
 * 促销
 */
public class ProdutActionAdapter extends BaseRecyclerViewAdapter {
    int theme = R.color.theme_color;
    String mpId;


    public void setTheme(int theme) {
        this.theme = theme;
    }

    public void setMpId(String mpId) {
        this.mpId = mpId;
    }

    public ProdutActionAdapter(Context mcontext, List ListpromotionInfo) {
        super(mcontext, ListpromotionInfo);
    }

    @Override
    protected ProdutActionAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(mContext).inflate(R.layout.produtdetail_listview_promotion, null, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        final List<PromotionBean.Data.PromotionInfo.Promotions> list = getDatas();
        //1.7.8为单一促销
        if (OdyApplication.SCHEME.equals("lyf") || (null != list && list.size() > 0 && list.get(position).contentType != 1 && list.get(position).contentType != 7 && list.get(position).contentType != 8)) {
            viewHodler.ll_promotiononclik.setVisibility(View.VISIBLE);
            if (null != list.get(position).getIconUrl() && !"".equals(list.get(position).getIconUrl())) {
                GlideUtil.display(mContext, list.get(position).getIconUrl()).into(viewHodler.img_icoimg);
            } else {
                viewHodler.img_icoimg.setImageResource(R.drawable.item_activity);
            }
            viewHodler.TxtProductName3.setText(list.get(position).getDescription());
            viewHodler.tv_typeJump.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
            viewHodler.tv_typeJump.getPaint().setAntiAlias(true);//抗锯齿
            viewHodler.tv_typeJump.setVisibility(View.GONE);
            viewHodler.tv_more.setVisibility(View.GONE);
            if (list.get(position).contentType == 2001 || list.get(position).contentType == 2002) {
                viewHodler.tv_typeJump.setVisibility(View.VISIBLE);
                viewHodler.tv_typeJump.setText("去开团");
                viewHodler.tv_more.setVisibility(View.VISIBLE);
                viewHodler.tv_typeJump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (list.get(position).contentType == 2001) {
                        boolean isShow = OdyApplication.getValueByKey("isShow", true);
                        if(isShow){
                            JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + "/group/detail.html?" + "patchGrouponId=" + list.get(position).promotionId + "&mpId=" + mpId +"&isShow=true");
                        }else{
                            JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + "/group/detail.html?" + "patchGrouponId=" + list.get(position).promotionId + "&mpId=" + mpId +"&isShow=false");
                        }
                        OdyApplication.putValueByKey("isShow", false);
//                            JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + "/group/detail.html?" + "patchGrouponId=" + list.get(position).promotionId + "&mpId=" + mpId);
//                        } else {
//                            JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + "/group/detail.html?" + "patchGrouponId=" + list.get(position).promotionId);
//                        }
                    }
                });
            } else if (list.get(position).contentType == 3001) {
                viewHodler.tv_typeJump.setVisibility(View.VISIBLE);
                viewHodler.tv_typeJump.setText("发起砍价");
                viewHodler.tv_more.setVisibility(View.VISIBLE);
                viewHodler.tv_typeJump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + "/cut/detail.html?" + "id=" + list.get(position).promotionId);
                    }
                });
            }
            //这是是判断是否有列表
//            if (list.get(position) != null && list.get(position).getPromotionRuleList()!=null&&list.get(position).getPromotionRuleList().size() > 0 || (list.get(position).getPromotionGiftDetailList() != null && list.get(position).getPromotionGiftDetailList().size() > 0)) {
//          特价：1 满折：2 满减：3  满赠：4 秒杀：7 换购：11
//          1006是满量赠，1007是买一赠一，
//          1018是满额换购，1019是满量换购
//          4/1005/1006是满赠  1018/1019是换购
//          满减满赠時点击去商品聚合页
//            if (OdyApplication.SCHEME.equals("lyf")&&(list.get(position).getPromotionType()!=2&&list.get(position).getPromotionType()!=3&&list.get(position).getPromotionType()!=4&&list.get(position).getPromotionType()!=11&&list.get(position).getPromotionType()!=1005&&list.get(position).getPromotionType()!=1006&&list.get(position).getPromotionType()!=1018&&list.get(position).getPromotionType()!=1019)){
//                viewHodler.img_promotionon.setVisibility(View.INVISIBLE);
//            }else{
//                viewHodler.img_promotionon.setVisibility(View.VISIBLE);
//            }
            if (list.get(position).getIsJumpPage() == 1) {
                viewHodler.img_promotionon.setVisibility(View.VISIBLE);
            } else {
                viewHodler.img_promotionon.setVisibility(View.INVISIBLE);
            }
            viewHodler.ll_promotiononclik.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//读取列表
                    if (list.get(position).getIsJumpPage() == 1) {//是否跳转至促销商品聚合页
                        if (!StringUtils.isEmpty(list.get(position).getJumpPageUrl())) {
                            JumpUtils.ToWebActivity(mContext, OdyApplication.H5URL + list.get(position).getJumpPageUrl());
                        } else {
                            promotionBack.lookToGiftPromotion(list.get(position));
                        }
                    }
                }
            });
//            } else {
//                viewHodler.ll_promotiononclik.setVisibility(View.GONE);
//                viewHodler.ll_promotiononclik.setEnabled(false);
//                viewHodler.img_icoimg.setVisibility(View.GONE);
//                viewHodler.ll_preferential.setVisibility(View.GONE);
//            }
        } else {
            viewHodler.ll_promotiononclik.setVisibility(View.GONE);
            viewHodler.ll_promotiononclik.setEnabled(false);
            viewHodler.img_icoimg.setVisibility(View.GONE);
            viewHodler.ll_preferential.setVisibility(View.GONE);
        }

  /*      List<PromotionBean.Data.PromotionInfo> promotionInfo=getDatas();
        viewHodler.img_icoimg.setImageResource(R.drawable.icon_reduce);
        viewHodler.TxtProductName3.setText("满一百减两百");
        viewHodler.ll_promotiononclik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promotionBack.promotionToPresentOnclik();
            }
        });*/

    }

    PromotionBack promotionBack;

    public PromotionBack getPromotionBack() {
        return promotionBack;
    }

    public void setPromotionBack(PromotionBack promotionBack) {
        this.promotionBack = promotionBack;
    }

    public interface PromotionBack {

        void promotionToPresentOnclik();

        void lookToGiftPromotion(PromotionBean.Data.PromotionInfo.Promotions data);
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        ImageView img_icoimg;
        TextView TxtProductName3;
        ImageView img_promotionon;
        LinearLayout ll_promotiononclik;
        RelativeLayout ll_preferential;
        TextView tv_typeJump;
        TextView tv_more;

        public ViewHodler(View itemView) {
            super(itemView);
            img_icoimg = (ImageView) itemView.findViewById(R.id.img_icoimg);
            TxtProductName3 = (TextView) itemView.findViewById(R.id.TxtProductName3);
            img_promotionon = (ImageView) itemView.findViewById(R.id.img_promotionon);
            ll_promotiononclik = (LinearLayout) itemView.findViewById(R.id.ll_promotiononclik);
            ll_preferential = (RelativeLayout) itemView.findViewById(R.id.ll_preferential);
            tv_typeJump = (TextView) itemView.findViewById(R.id.tv_typeJump);
            tv_more = (TextView) itemView.findViewById(R.id.tv_more);
        }
    }
}
