package com.ody.p2p.main.search;

import android.content.Context;
import android.graphics.Paint;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.main.R;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.search.searchkey.HistoryBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ${tang} on 2017/2/16.
 */

public class SearchFootAdapter extends android.widget.BaseAdapter {

    private List<HistoryBean.FootStepVO> mData;
    private Context mContext;

//    public SearchFootAdapter(Context mContext) {
//        mData = new ArrayList<>();
//        this.mContext = mContext;
//    }

    public SearchFootAdapter(Context mContext, List<HistoryBean.FootStepVO> data) {
        mData = data;
        this.mContext = mContext;
    }

    public void addData(List<HistoryBean.FootStepVO> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public HistoryBean.FootStepVO getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        FootViewHolder footViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_foot, parent, false);
            footViewHolder = new FootViewHolder();
            footViewHolder.tv_product_name = (TextView) convertView.findViewById(R.id.tv_product_name);
            footViewHolder.tv_product_cost = (TextView) convertView.findViewById(R.id.tv_product_cost);
            footViewHolder.iv_addtocart = (ImageView) convertView.findViewById(R.id.iv_addtocart);
            footViewHolder.iv_product_pic = (ImageView) convertView.findViewById(R.id.iv_product_pic);
            footViewHolder.recycler_promotion = (RecyclerView) convertView.findViewById(R.id.recycler_promotion);
            footViewHolder.tv_product_praise = (TextView) convertView.findViewById(R.id.tv_product_praise);
            footViewHolder.tv_praise_num = (TextView) convertView.findViewById(R.id.tv_praise_num);
            convertView.setTag(footViewHolder);
        } else {
            footViewHolder = (FootViewHolder) convertView.getTag();
        }
//        footViewHolder.tv_praise_num.setVisibility(View.VISIBLE);
//        footViewHolder.tv_product_praise.setVisibility(View.VISIBLE);
//        footViewHolder.tv_product_praise.setTextColor(mContext.getResources().getColor(R.color.note_color));
//        footViewHolder.tv_product_praise.setText("暂无评价");
        footViewHolder.recycler_promotion.setVisibility(View.GONE);
        footViewHolder.tv_product_name.setText(mData.get(position).name);
        GlideUtil.display(OdyApplication.gainContext(), mData.get(position).picUrl).into(footViewHolder.iv_product_pic);
        footViewHolder.tv_product_cost.setText("￥" + UiUtils.getDoubleForDouble(mData.get(position).price));
        footViewHolder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTocart(mData.get(position).mpId);
            }
        });

        return convertView;
    }

    private void addTocart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT, "");
        params.put("ut", ut);
        params.put("sessionId", OdySysEnv.getSessionId());
        OkHttpManager.getAsyn(Constants.ADD_TO_CART, params, new OkHttpManager.ResultCallback<BaseRequestBean>() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(BaseRequestBean response) {
                if (response != null && response.code.equals("0")) {
                    ToastUtils.showToast("添加成功");
                }
            }
        });
    }


    class FootViewHolder {

        public ImageView iv_product_pic;
        public TextView tv_product_name;
        public TextView tv_product_cost;
        public ImageView iv_addtocart;
        public RecyclerView recycler_tag;
        public RelativeLayout rl_search_item;
        public TextView tv_productcost_old;
        public TextView tv_product_sold;
        public RecyclerView recycler_promotion;

        public TextView tv_product_praise;
        public TextView tv_praise;
        public TextView tv_product_praise_good;
        public TextView tv_praise_num;

        public ImageView iv_tag1;
        public ImageView iv_tag2;
        public ImageView iv_tag3;
        public ImageView iv_tag4;

    }
}
