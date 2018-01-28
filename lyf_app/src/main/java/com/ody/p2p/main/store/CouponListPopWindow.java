package com.ody.p2p.main.store;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.main.myhomepager.myWallet.Utils;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.retrofit.coupon.CouponThemeBean;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;

import java.util.List;

/**
 * Created by user on 2017/7/18.
 */

public class CouponListPopWindow extends BasePopupWindow<CouponThemeBean> {

    private RecyclerView recyle_pop_coupon;
    private ImageView iv_dismiss;
    private CouponThemeBean data;
    private CouponListAdapter couponListAdapter;
    private Context mContext;

    public CouponListPopWindow(Context mContext) {
        super(mContext);
    }

    public CouponListPopWindow(Context mContext, CouponThemeBean data) {
        super(mContext, data);
        this.mContext = mContext;
        this.data = data;
        init(mContext, R.layout.layout_coupon_popwindow);
        initView();
    }

    public void setData(CouponThemeBean data){
        this.data = data;
        couponListAdapter.notifyDataSetChanged();
    }
    public void refreshUI(){
        couponListAdapter.notifyDataSetChanged();
    }

    //初始化控件
    private void initView() {
        recyle_pop_coupon = (RecyclerView) mMenuView.findViewById(R.id.recyle_pop_coupon);
        couponListAdapter = new CouponListAdapter(mContext, data.getData().getListObj());
        iv_dismiss = (ImageView) mMenuView.findViewById(R.id.iv_dismiss);

        iv_dismiss.setOnClickListener(this);

        recyle_pop_coupon.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
        recyle_pop_coupon.setAdapter(couponListAdapter);

        couponListAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<CouponThemeBean.DataBean.ListObjBean>() {
            @Override
            public void onItemClick(View view, int position, CouponThemeBean.DataBean.ListObjBean model) {
               if (couponItemClickListener != null){
                   couponItemClickListener.couponItemClick(position, model.getCouponThemeId());
               }
            }

            @Override
            public void onItemLongClick(View view, int position, CouponThemeBean.DataBean.ListObjBean model) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_dismiss:
                dismiss();
                break;
        }
    }

    class CouponListAdapter extends BaseRecyclerViewAdapter<CouponThemeBean.DataBean.ListObjBean> {

        public CouponListAdapter(Context context, List<CouponThemeBean.DataBean.ListObjBean> datas) {
            super(context, datas);
        }

        @Override
        protected void showViewHolder(BaseRecyclerViewHolder holders, int position) {
            viewHolder holder=(viewHolder)holders;
            CouponThemeBean.DataBean.ListObjBean listObjBean = data.getData().getListObj().get(position);
            holder.tv_coupon_price.setText("¥" + listObjBean.getCouponAmount());
            holder.tv_coupon_full_cut.setText("订单满" + listObjBean.getUseLimit() + "使用");
            holder.tv_coupon_unlimited_purchase.setText(listObjBean.getThemeDesc());
            holder.tv_coupon_valid_date.setText( Utils.millionsToDate(listObjBean.getStartTime()) + "-" +  Utils.millionsToDate(listObjBean.getEndTime()));
            if (listObjBean.getOverFlg() ==1 || listObjBean.getUserOverFlg() ==1){
                holder.tv_coupon_status.setText(mContext.getString(R.string.already_receive_completed));
                holder.ll_item.setBackgroundResource(R.drawable.store_get_coupon_bg_grey);
            }else if(listObjBean.getUserDayOverFlg() == 1){
                holder.tv_coupon_status.setText(mContext.getString(R.string.already_receive));
                holder.ll_item.setBackgroundResource(R.drawable.store_get_coupon_bg_grey);
            }else{
                holder.tv_coupon_status.setText(mContext.getString(R.string.receive_immediately));
                holder.ll_item.setBackgroundResource(R.drawable.store_get_coupon_bg_yellow);
            }
        }

        @Override
        protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
            return new viewHolder(mInflater.inflate(R.layout.item_coupon_list, parent, false));
        }

        class viewHolder extends BaseRecyclerViewHolder {
            LinearLayout ll_item;
            TextView tv_coupon_price;
            TextView tv_coupon_full_cut;
            TextView tv_coupon_unlimited_purchase;
            TextView tv_coupon_valid_date;
            TextView tv_coupon_status;

            public viewHolder(View itemView) {
                super(itemView);
                ll_item = (LinearLayout) itemView.findViewById(R.id.ll_item);
                tv_coupon_price=(TextView) itemView.findViewById(R.id.tv_coupon_price);
                tv_coupon_full_cut=(TextView) itemView.findViewById(R.id.tv_coupon_full_cut);
                tv_coupon_unlimited_purchase=(TextView) itemView.findViewById(R.id.tv_coupon_unlimited_purchase);
                tv_coupon_valid_date=(TextView) itemView.findViewById(R.id.tv_coupon_valid_date);
                tv_coupon_status=(TextView) itemView.findViewById(R.id.tv_coupon_status);
            }
        }
    }

    CouponItemClickListener couponItemClickListener;

    public interface CouponItemClickListener{
        void couponItemClick(int position, String couponThemeId);
    }

    public void setCouponItemClickListener(CouponItemClickListener couponItemClickListener) {
        this.couponItemClickListener = couponItemClickListener;
    }
}
