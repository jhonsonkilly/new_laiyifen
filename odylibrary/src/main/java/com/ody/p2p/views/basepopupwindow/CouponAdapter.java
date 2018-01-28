package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.squareup.okhttp.Request;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ody on 2016/6/17.
 */
public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.ViewHolder> {
    List<CouponBean.Data> mData;
    Context mContext;

    public CouponAdapter(Context mContext, List<CouponBean.Data> data) {
        this.mContext = mContext;
        this.mData = data;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    @Override
    public CouponAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_coupon_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(final CouponAdapter.ViewHolder holder, int position) {
        CouponBean.Data thisData = mData.get(position);
        holder.coupon_price.setText(thisData.getCouponPrice() + "");
        holder.coupon_property.setText(thisData.getCouponName());
        holder.coupon_time.setText(thisData.getCouponT());
        holder.coupon_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gainCoupon(holder.coupon_get);
            }
        });
    }

    private void gainCoupon(final TextView view) {
        Map<String, String> params = new HashMap<>();
        String url = "http://p2p.odianyun.com/api/gainCoupon.do";
        OkHttpManager.getAsyn(url, params, new OkHttpManager.ResultCallback<CouponBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                        Toast.makeText(mView.getContext(), "onError", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(CouponBean response) {
                view.setText("已領取");
                view.setTextColor(mContext.getResources().getColor(R.color.note_color));
                view.setClickable(false);
            }

        });
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView coupon_price, coupon_property, coupon_time, coupon_get;

        public ViewHolder(View view) {
            super(view);
            coupon_price = (TextView) view.findViewById(R.id.coupon_price);
            coupon_property = (TextView) view.findViewById(R.id.coupon_property);
            coupon_time = (TextView) view.findViewById(R.id.coupon_property);
            coupon_get = (TextView) view.findViewById(R.id.coupon_get);
        }
    }
}
