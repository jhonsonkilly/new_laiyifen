package com.ody.p2p.recmmend;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.PromotionAdapter;
import com.ody.p2p.PromotionInfo;
import com.ody.p2p.R;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pzy on 2016/12/14.
 */
public class RecommendAdapter extends BaseRecyclerViewAdapter<Recommedbean.DataList> {

    private int JumpType=STANDARD;
    public static int STANDARD=0;
    public static int SINGLETASK=1;
    public static int SINGLETASK_F=2;



    public void setJumpType(int jumpType) {
        JumpType = jumpType;
    }

    public RecommendAdapter(Context context, List<Recommedbean.DataList> datas) {
        super(context, datas);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new ViewHolderRecommed(LayoutInflater.from(mContext).inflate(R.layout.recommend_adapter_item, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHolderRecommed vHolder = (ViewHolderRecommed) holder;
        final Recommedbean.DataList prdata = mDatas.get(position);
        vHolder.tv_recommed_name.setText(prdata.getMpName());
        GlideUtil.display(mContext, prdata.getSrcImgUrl()).override(200, 200).into(vHolder.img_recommend);
        vHolder.tv_recommend_price.setText(UiUtils.getMoney(mContext, prdata.getSalePrice()));
//        holderRecommedHolder.tv_recommend_price.setTextColor(priceColor);
        if (!StringUtils.isEmpty(prdata.getSourcePrice() + "")&&prdata.getSourcePrice()>0) {
            vHolder.tv_sourceprice.setVisibility(View.VISIBLE);
            vHolder.tv_sourceprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG); //中划线
            vHolder.tv_sourceprice.setText(UiUtils.getMoney(mContext, prdata.getSourcePrice()));
        } else {
            vHolder.tv_sourceprice.setVisibility(View.GONE);
        }
        if (null != prdata && null != prdata.getCommentInfo()) {
            String evaluate = prdata.getCommentInfo().getCommentNum() + "<font color='#808080'>条评论  好评</font>" + prdata.getCommentInfo().getGoodRate() + "%";
            vHolder.tv_evaluate_rate.setText(Html.fromHtml(evaluate));
        } else {
            String evaluate = 0 + "<font color='#333'>条评论  好评</font>" + 0 + "%";
            vHolder.tv_evaluate_rate.setText(Html.fromHtml(evaluate));
        }
        //促销标签
        if (null != prdata.getTagList() && prdata.getTagList().size() > 0) {
            List<PromotionInfo> promotionInfos = new ArrayList<>();
            for (Recommedbean.TagList list : prdata.getTagList()) {
                PromotionInfo pInfo = new PromotionInfo();
                pInfo.setIconUrl(list.getTagUrl());
                promotionInfos.add(pInfo);
            }
            PromotionAdapter promotionAdapter = new PromotionAdapter(mContext, promotionInfos);
            promotionAdapter.setGapDistance(6, 0, 0);//设置间距、宽、高
            LinearLayoutManager rankLayoutManager = new LinearLayoutManager(mContext);
            rankLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            vHolder.recycler_promation_label.setLayoutManager(rankLayoutManager);
            vHolder.recycler_promation_label.setAdapter(promotionAdapter);
            vHolder.recycler_promation_label.setVisibility(View.VISIBLE);
        } else {
            vHolder.recycler_promation_label.setVisibility(View.INVISIBLE);
        }
        vHolder.img_recommend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpNextACtivity(prdata);
            }
        });
        vHolder.tv_recommed_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpNextACtivity(prdata);
            }
        });
        vHolder.img_addcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCart(prdata);
            }
        });
    }

    /**
     * 关于详情的跳转处理
     * @param prdata
     */
    public void JumpNextACtivity(Recommedbean.DataList prdata){
        Bundle bd = new Bundle();
        bd.putString(Constants.SP_ID, prdata.getMpId() + "");
        if(JumpType==STANDARD){
            bd.putInt(Constants.PRODUCT_JUMP_TYPE, SINGLETASK);
        }else{
            bd.putInt(Constants.PRODUCT_JUMP_TYPE, SINGLETASK_F);
        }
        JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
        if (JumpType==SINGLETASK_F){
            ((Activity)mContext).finish();
        }
    }

    //猜你喜欢
    class ViewHolderRecommed extends BaseRecyclerViewHolder {
        public TextView tv_recommed_name;
        ImageView img_recommend, img_addcart;
        TextView tv_recommend_price;
        TextView tv_sourceprice, tv_evaluate_rate;
        RecyclerView recycler_promation_label;
        LinearLayout liner_view;

        public ViewHolderRecommed(View itemView) {
            super(itemView);
            recycler_promation_label = (RecyclerView) itemView.findViewById(R.id.recycler_promation_label);
            liner_view = (LinearLayout) itemView.findViewById(R.id.liner_view);
            tv_recommed_name = (TextView) itemView.findViewById(R.id.tv_recommed_name);
            img_recommend = (ImageView) itemView.findViewById(R.id.img_recommend);
            tv_recommend_price = (TextView) itemView.findViewById(R.id.tv_recommend_price);
            tv_sourceprice = (TextView) itemView.findViewById(R.id.tv_sourceprice);
            tv_evaluate_rate = (TextView) itemView.findViewById(R.id.tv_evaluate_rate);
            img_addcart = (ImageView) itemView.findViewById(R.id.img_addcart);
        }
    }

    RecommendCallBack recommendCallBack;

    public void setRecommendCallBack(RecommendCallBack recommendCallBack) {
        this.recommendCallBack = recommendCallBack;
    }

    public static abstract class RecommendCallBack {
        public abstract void addCart();
        public void addCart(Recommedbean.DataList dataList){}
    }

    public void addCart(final Recommedbean.DataList dataList) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", dataList.getMpId());
        params.put("num", "1");
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params, mContext.getClass().toString(), new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    ToastUtils.showShort(mContext.getString(R.string.add_succeed));
                    if (null != recommendCallBack) {
                        recommendCallBack.addCart(dataList);
                    }
                }
            }

            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
                ToastUtils.showShort(msg);
            }
        });
    }

    public void addCart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        params.put("ut", OdyApplication.getString(Constants.USER_LOGIN_UT, ""));
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params, mContext.getClass().toString(), new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    ToastUtils.showShort(mContext.getString(R.string.add_succeed));
                    if (null != recommendCallBack) {
                        recommendCallBack.addCart();
                    }
                }
            }

            @Override
            public void onFailed(String code, String json, String msg) {
                super.onFailed(code, json, msg);
                ToastUtils.showShort(msg);
            }
        });
    }
}
