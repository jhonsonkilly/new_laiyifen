package com.ody.p2p.main.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.ds.lyf_home.CategoryAdapter;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.FirstLoginCouponBean;
import com.ody.p2p.main.R;
import com.ody.p2p.retrofit.home.ModuleDataCategoryBean;
import com.ody.p2p.utils.GlideUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by user on 2017/5/22.
 */

public class FirstLoginCouponAdapter extends BaseRecyclerViewAdapter {


    public FirstLoginCouponAdapter(Context context, List datas) {
        super(context, datas);
    }

    public FirstLoginCouponAdapter(Context context) {
        super(context);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        FirstLoginCouponBean.MyCouponOutputDTO bean = (FirstLoginCouponBean.MyCouponOutputDTO) getDatas().get(position);
        CouponViewHolder holder1 = (CouponViewHolder) holder;
        holder1.tv_coupon_money.setText((int) bean.couponValue + "");
        try {
            holder1.tv_coupon_date.setText("有效期:" + transferLongToDate("yyyy.MM.dd", Long.parseLong(bean.endTime)));
        } catch (Exception e) {
            holder1.tv_coupon_date.setText("有效期:" + bean.endTime);
        }
        holder1.tv_coupon_limit.setText(bean.themeTitle);
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return (new CouponViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_firstlogincoupon, parent, false)));
    }

    //特色主题
    public static class CouponViewHolder extends BaseRecyclerViewHolder {

        public TextView tv_coupon_money;
        public TextView tv_coupon_limit;
        public TextView tv_coupon_date;

        public CouponViewHolder(View view) {
            super(view);
            tv_coupon_money = (TextView) view.findViewById(R.id.tv_coupon_money);
            tv_coupon_limit = (TextView) view.findViewById(R.id.tv_coupon_limit);
            tv_coupon_date = (TextView) view.findViewById(R.id.tv_coupon_date);
        }
    }


    /**
     * 把毫秒转化成日期
     *
     * @param dateFormat(日期格式，例如：MM/ dd/yyyy HH:mm:ss)
     * @param millSec(毫秒数)
     * @return
     */
    private String transferLongToDate(String dateFormat, Long millSec) {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }
}
