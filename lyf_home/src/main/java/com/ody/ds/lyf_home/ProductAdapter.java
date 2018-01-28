package com.ody.ds.lyf_home;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRequestBean;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.config.OdySysEnv;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.retrofit.home.ModuleDataBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.squareup.okhttp.Request;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lxs on 2016/12/6.
 */
public class ProductAdapter extends android.widget.BaseAdapter {

    public int btnBuy;
    private Context mContext;
    private List mData;

    public ProductAdapter(Context context, List datas) {
        mContext = context;
        mData = datas;
    }
    public void setData(List data){
        this.mData = data;
        notifyDataSetChanged();
    }

    public void addData(List data){
        this.mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ProductViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_product_item, parent, false);
            holder = new ProductViewHolder(convertView);

            convertView.setTag(holder);
        } else {
            holder = (ProductViewHolder) convertView.getTag();
        }
        final ModuleDataBean.CmsModuleDataVO bean = (ModuleDataBean.CmsModuleDataVO) mData.get(position);
        holder.tv_product_name.setText(bean.mpName);
        holder.tv_productcost.setText("¥" + bean.price);
        if (StringUtils.isEmpty(bean.promotionPrice)) {
            holder.tv_productcost_old.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_productcost_old.setVisibility(View.VISIBLE);
            holder.tv_productcost.setText("¥" + bean.promotionPrice);
            holder.tv_productcost_old.setText("¥" + UiUtils.getDoubleForDouble(bean.price));
        }
        String iconTexts = "";
        if (bean.iconTexts != null && bean.iconTexts.size() > 0){
            for (int i = 0; i < bean.iconTexts.size(); i++) {
                iconTexts = iconTexts + " " + bean.iconTexts.get(i);
            }
        }
        holder.tv_product_activity.setText(iconTexts);
        holder.tv_productcost_old.getPaint().setAntiAlias(true);//抗锯齿
        holder.tv_productcost_old.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        GlideUtil.display(mContext, 300, bean.picUrl).into(holder.iv_product_pic);
        if (btnBuy == 0) {
            holder.iv_addtocart.setVisibility(View.GONE);
        } else {
            holder.iv_addtocart.setVisibility(View.VISIBLE);
            holder.iv_addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToCart(bean.mpId);
                }
            });
        }
        return convertView;
    }

    //特色主题
    public static class ProductViewHolder {

        public TextView tv_product_activity;
        public TextView tv_product_name;
        public TextView tv_productcost;
        public TextView tv_productcost_old;
        public ImageView iv_addtocart;
        public ImageView iv_product_pic;


        public ProductViewHolder(View view) {
            iv_product_pic = (ImageView) view.findViewById(R.id.iv_product_pic);
            tv_product_activity = (TextView) view.findViewById(R.id.tv_product_activity);
            tv_product_name = (TextView) view.findViewById(R.id.tv_product_name);
            tv_productcost = (TextView) view.findViewById(R.id.tv_product_cost);
            tv_productcost_old = (TextView) view.findViewById(R.id.tv_productcost_old);
            iv_addtocart = (ImageView) view.findViewById(R.id.iv_addtocart);
        }
    }

    public void setBtnBuy(int btnBuy) {
        this.btnBuy = btnBuy;
        notifyDataSetChanged();
    }


    public void addToCart(String mpId) {
        Map<String, String> params = new HashMap<>();
        params.put("mpId", mpId);
        params.put("num", "1");
        String ut = OdyApplication.getString(Constants.USER_LOGIN_UT,"");
        params.put("ut",ut);
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

}
