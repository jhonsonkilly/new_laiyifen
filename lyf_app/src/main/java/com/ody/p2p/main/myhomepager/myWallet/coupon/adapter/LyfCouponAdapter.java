package com.ody.p2p.main.myhomepager.myWallet.coupon.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.Utils;
import com.ody.p2p.main.myhomepager.myWallet.coupon.bean.CouponBean;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import dsshare.ShareBean;
import dsshare.SharePopupWindow;

/**
 * Created by meijunqiang on 2017/3/21.
 * 描述：个人中心-我的优惠券
 */
public class LyfCouponAdapter extends RecyclerView.Adapter<LyfCouponAdapter.LyfCouponHolder> {
    private BaseActivity mContext;
    private ArrayList<CouponBean.DataEntity.CouponListEntity> mData;
    private int checkStatus;

    public LyfCouponAdapter(BaseActivity context, ArrayList<CouponBean.DataEntity.CouponListEntity> data, int checkStatus) {
        this.mContext = context;
        this.mData = data;
        this.checkStatus = checkStatus;
    }

    public void setData(List<CouponBean.DataEntity.CouponListEntity> datas, int checkStatus) {
        mData.clear();
        this.checkStatus = checkStatus;
        if (datas != null && datas.size() > 0) {
            mData.addAll(datas);
        }
        notifyDataSetChanged();
    }

    public void addData(List<CouponBean.DataEntity.CouponListEntity> datas) {
        mData.addAll(datas);
        notifyDataSetChanged();
    }

    public void addData(CouponBean.DataEntity.CouponListEntity... datas) {
        mData.addAll(Arrays.asList(datas));
        notifyDataSetChanged();
    }

    @Override
    public LyfCouponHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LyfCouponHolder(LayoutInflater.from(mContext).inflate(R.layout.item_lyf_coupon, parent, false));
    }

    @Override
    public void onBindViewHolder(final LyfCouponHolder holder, int position) {
        final CouponBean.DataEntity.CouponListEntity couponBean = mData.get(position);
        initToggleShow(holder, couponBean);
        initViewStatus(holder, couponBean);
        //数据适配
        //couponDiscountType 券类型 0是满金额，1是满折
        //couponValue  券金额或折扣，如果couponDiscountType是1，这里就是折扣，比如88折这里是0.88
        holder.itemCouponPrice.setText(Utils.buildPriceForAuto("¥", couponBean.couponValue + "", null, mContext,
                (int) mContext.getResources().getDimension(R.dimen.sp_20)));
        holder.itemCouponUsed.setText(couponBean.moneyRule);
        holder.itemCouponType.setText(couponBean.themeTitle);
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        holder.itemCouponDate.setText(format.format(couponBean.startTime) + "-" + format.format(couponBean.endTime));
        holder.itemCouponRule.setText(couponBean.refDescription);
    }

    /**
     * 根据选中状态，调整item控件样式
     *
     * @param holder
     * @param couponBean
     */
    private void initViewStatus(LyfCouponHolder holder, final CouponBean.DataEntity.CouponListEntity couponBean) {
        //事件初始化
        holder.itemCouponPresent.setVisibility(View.GONE);
        holder.itemCouponTagImg.setVisibility(View.GONE);
        holder.itemCouponPresent.setOnClickListener(null);
        switch (checkStatus) {
            case 0://可用  canShare是否可以赠送 0 不能 1能
                holder.itemView.setBackgroundResource(R.color.ffeadb);
                if (couponBean.canShare == 1) {
                    holder.itemCouponPresent.setVisibility(View.VISIBLE);
                    holder.itemCouponPresent.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //TODO:meiyizhi 调接口 赠送好友优惠券
                            getCouponShareInfo(couponBean);
                        }
                    });
                } else {
                    holder.itemCouponPresent.setVisibility(View.GONE);
                }
                holder.itemCouponPrice.setTextColor(mContext.getResources().getColor(R.color.c_ff690));
                break;
            case 1://已用

                break;
            case 2://已送

                break;
            case 3://过期
                holder.itemView.setBackgroundResource(R.color.e5e5e5);
                holder.itemCouponTagImg.setVisibility(View.VISIBLE);
                holder.itemCouponPrice.setTextColor(mContext.getResources().getColor(R.color.rectangle_bead));
                break;
        }
    }

    /**
     * 获取优惠券分享必要的信息
     *
     * @param couponBean
     */
    private void getCouponShareInfo(CouponBean.DataEntity.CouponListEntity couponBean) {
        Map<String, String> params = new HashMap<>();
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        params.put("couponCode", couponBean.couponCode);
        params.put("couponId", couponBean.couponId);
        params.put("type", "2");//优惠券类型 1欧电云 2来伊份
        mContext.showLoading();
        OkHttpManager.postAsyn(Constants.GETCOUPONSHAREINFO, new OkHttpManager.ResultCallback<ShareBean>() {
            @Override
            public void onError(Request request, Exception e) {
                mContext.hideLoading();
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
                mContext.hideLoading();
            }

            @Override
            public void onResponse(ShareBean response) {
                mContext.hideLoading();
                SharePopupWindow sharePopup = new SharePopupWindow(mContext, SharePopupWindow.SHARE_COUPON, response);
                sharePopup.showAtLocation(mContext.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        }, params);
    }

    /**
     * 底部规则的显示和隐藏
     *
     * @param holder
     * @param couponBean
     */
    private void initToggleShow(final LyfCouponHolder holder, final CouponBean.DataEntity.CouponListEntity couponBean) {
        if (couponBean.showRule) {
            holder.itemCouponShowmore.setBackgroundResource(R.drawable.common_btn_arrow_greyup);
            holder.itemCouponRule.setVisibility(View.VISIBLE);
        } else {
            holder.itemCouponShowmore.setBackgroundResource(R.drawable.common_btn_arrow_greydown);
            holder.itemCouponRule.setVisibility(View.GONE);
        }
        holder.itemCouponShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponBean.showRule = !couponBean.showRule;
                if (couponBean.showRule) {
                    holder.itemCouponShowmore.setBackgroundResource(R.drawable.common_btn_arrow_greyup);
                    holder.itemCouponRule.setVisibility(View.VISIBLE);
                } else {
                    holder.itemCouponShowmore.setBackgroundResource(R.drawable.common_btn_arrow_greydown);
                    holder.itemCouponRule.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class LyfCouponHolder extends RecyclerView.ViewHolder {
        public TextView itemCouponPrice;
        public TextView itemCouponPresent;
        public TextView itemCouponUsed;
        public TextView itemCouponType;
        public TextView itemCouponDate;
        public ImageView itemCouponTagImg;
        public ImageView itemCouponShowmore;
        public TextView itemCouponRule;

        public LyfCouponHolder(View itemView) {
            super(itemView);
            itemCouponPrice = (TextView) itemView.findViewById(R.id.item_coupon_price);
            itemCouponPresent = (TextView) itemView.findViewById(R.id.item_coupon_present);
            itemCouponUsed = (TextView) itemView.findViewById(R.id.item_coupon_used);
            itemCouponType = (TextView) itemView.findViewById(R.id.item_coupon_type);
            itemCouponDate = (TextView) itemView.findViewById(R.id.item_coupon_date);
            itemCouponTagImg = (ImageView) itemView.findViewById(R.id.item_coupon_tag_img);
            itemCouponShowmore = (ImageView) itemView.findViewById(R.id.item_coupon_showmore);
            itemCouponRule = (TextView) itemView.findViewById(R.id.item_coupon_rule);
        }
    }
}
