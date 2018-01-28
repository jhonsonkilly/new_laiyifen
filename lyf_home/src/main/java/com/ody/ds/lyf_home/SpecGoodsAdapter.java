package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseViewHolder;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.AppHomePageBean;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.SquareImageView;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mac on 2017/9/8.
 */

public class SpecGoodsAdapter extends BaseRecyclerViewAdapter {

    public SpecGoodsAdapter(Context context, List datas) {
        super(context, datas);

    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {



        SpecGoodsAdapter.SpecGoodsViewHolder goodsViewHolder = (SpecGoodsAdapter.SpecGoodsViewHolder) holder;
        final ModuleDataBean.CmsModuleDataVO bean = (ModuleDataBean.CmsModuleDataVO) getDatas().get(position);


        goodsViewHolder.tv_product_name.setText(bean.mpName);
        goodsViewHolder.tv_product_cost.setText("¥" + bean.price);
        if (StringUtils.isEmpty(bean.promotionPrice)) {
            goodsViewHolder.tv_productcost_old.setVisibility(View.INVISIBLE);
        } else {
            goodsViewHolder.tv_productcost_old.setVisibility(View.VISIBLE);
            goodsViewHolder.tv_product_cost.setText("¥" + bean.promotionPrice);
            goodsViewHolder.tv_productcost_old.setText("¥" + UiUtils.getDoubleForDouble(bean.price));
        }
        String iconTexts = "";
        if (bean.iconTexts != null && bean.iconTexts.size() > 0) {
            for (int i = 0; i < bean.iconTexts.size(); i++) {
                iconTexts = iconTexts + " " + bean.iconTexts.get(i);
            }
        }
        goodsViewHolder.tv_product_activity.setText(iconTexts);
        goodsViewHolder.tv_productcost_old.getPaint().setAntiAlias(true);//抗锯齿
        goodsViewHolder.tv_productcost_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        GlideUtil.display(mContext, 300, bean.picUrl).into(goodsViewHolder.iv_product_pic);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) goodsViewHolder.rl_search_item.getLayoutParams();

        goodsViewHolder.rl_search_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bd = new Bundle();
                bd.putString(Constants.SP_ID, bean.mpId);
                JumpUtils.ToActivity(JumpUtils.PRODUCTDETAIL, bd);
            }
        });

                goodsViewHolder.iv_addtocart.setVisibility(View.VISIBLE);
                goodsViewHolder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addToCart(bean.mpId);
                    }
                });
    }


    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new SpecGoodsAdapter.SpecGoodsViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods, parent, false)));

    }

    public void addToCart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {
                Log.e("test", "onError=========================");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                ToastUtils.showShort(msg);
            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null) {
                    ToastUtils.showShort("添加成功");
                    EventbusMessage msg = new EventbusMessage();
                    msg.flag = EventbusMessage.GET_CART_COUNT;
                    EventBus.getDefault().post(msg);
                }
            }
        });
    }


    public static class SpecGoodsViewHolder extends BaseRecyclerViewHolder {
        public RelativeLayout rl_search_item;
        public SquareImageView iv_product_pic;
        public TextView tv_product_activity;
        public TextView tv_product_name;
        public RecyclerView recycler_promotion;
        public TextView tv_product_cost;
        public TextView tv_product_sold;
        public TextView tv_productcost_old;
        public ImageView iv_addtocart;

        public SpecGoodsViewHolder(View view) {
            super(view);
            rl_search_item = (RelativeLayout) view.findViewById(R.id.rl_search_item);
            iv_product_pic = (SquareImageView) view.findViewById(R.id.iv_product_pic);
            tv_product_activity = (TextView) view.findViewById(R.id.tv_product_activity);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_product_cost = (TextView) view.findViewById(R.id.tv_product_cost);
            tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);
            iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
        }

    }



}
