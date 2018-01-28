package com.ody.p2p.shopcartview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.shopcart.R;
import com.ody.p2p.shopcart.ShopCartBean;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.ProgressDialog.ProgressDialog;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.basepopupwindow.PropertyAdapter;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.basepopupwindow.PropertyUtil;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.R.attr.data;

/**
 * Created by ody on 2016/8/29.
 */
public class ChangeGifAdapter extends BaseRecyclerViewAdapter<ShopCartBean.GiftProducts> {
    boolean flage;
    int MaxSelecked;
    long thisMpId;
    PropertyBean gifPropertyBean;//系列属性
    ShopCartBean.Promotion promotion;

    public ChangeGifAdapter(Context context, ShopCartBean.Promotion promotion, int MaxSelecked, List datas, boolean flage) {
        super(context, datas);
        this.promotion = promotion;
        this.MaxSelecked = MaxSelecked;
        this.flage = flage;

        selectIcon_true = R.drawable.selected_true;//选中按钮
        selectIcon_false = R.drawable.selected_false;//非选中按钮
        priceColor = mContext.getResources().getColor(R.color.theme_color);//价格颜色
        btnLayout = R.layout.layout_flowitem;
    }

    int selectIcon_true;//选中按钮
    int selectIcon_false;//非选中按钮
    int priceColor;//价格颜色
    int btnLayout;
    public boolean isSpanMoney=true;//是否
    /**
     * 设置选中的按钮
     *
     * @param selectIcon_true
     */
    public void setSelectIcon_true(int selectIcon_true, int selectIcon_false, int priceColor, int btnLayout) {
        this.selectIcon_true = selectIcon_true;
        this.selectIcon_false = selectIcon_false;
        this.priceColor = priceColor;
        this.btnLayout = btnLayout;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new viewholer(LayoutInflater.from(mContext).inflate(R.layout.layout_change_gif_item, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        final ShopCartBean.GiftProducts mData = mDatas.get(position);
        final viewholer holders = (viewholer) holder;
        drawLayout(holders, mData);
        if (flage) {
            holders.img_changegif_item_checked.setClickable(true);
            holders.relative_guige.setClickable(true);
            if (mData.getChecked() == 1) {
                holders.img_changegif_item_checked.setImageResource(selectIcon_true);
            } else {
                holders.img_changegif_item_checked.setImageResource(selectIcon_false);
            }
            if (mData.getMpId()==thisMpId&& null != gifPropertyBean && null != holders.relative_guige.getTag() && (int) (holders.relative_guige.getTag()) == 0) {
                holders.img_changegif_item_arrow.setImageResource(R.drawable.ic_arrowup_gray);
                holders.img_changegif_item_reclycle.setVisibility(View.VISIBLE);
                holders.img_changegif_item_reclycle.setLayoutManager(RecycleUtils.getLayoutManager(mContext));
                final PropertyAdapter adapter = new PropertyAdapter(mContext, gifPropertyBean.getData().getAttributes(), gifPropertyBean.getData().getSerialProducts(), btnLayout);
                holders.img_changegif_item_reclycle.setAdapter(adapter);
                holders.relative_guige.setTag(1);
                adapter.setPropertyChange(new PropertyAdapter.PropertyChange() {
                    @Override
                    public void refresh() {
                        ProductBean productBean = PropertyUtil.getThisProduct(gifPropertyBean);
                        if (null == productBean) {
                            return;
                        }
                        mData.setMpId(productBean.mpId);
                        mData.setGiftName(productBean.name);
                        if (null != productBean.pics && productBean.pics.size() > 0) {
                            mData.setPicUrl(productBean.pics.get(0).url);
                        }
                        mData.setPrice(productBean.price);
                        mData.setPropertyTags(getKeyValues(gifPropertyBean.getData().getAttributes()));
                        holders.relative_guige.setTag(0);
                        adapter.notifyDataSetChanged();
                        drawLayout(holders, mData);
                    }
                });
            } else {
                holders.img_changegif_item_arrow.setImageResource(R.drawable.ic_arrowdown_gray);
                holders.img_changegif_item_reclycle.setVisibility(View.GONE);
                holders.relative_guige.setTag(0);
            }
        } else {
            holders.relative_guige.setClickable(false);
            holders.img_changegif_item_checked.setClickable(false);
            holders.img_changegif_item_checked.setVisibility(View.GONE);
            holders.img_changegif_item_checked.setImageResource(R.drawable.fail_checkbox);
        }
        if (!StringUtils.isEmpty(getPropertyStr(null,mData.getPropertyTags()))){
            holders.relative_guige.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int tag = (int) v.getTag();
                    if (tag == 0) {
                        getProperty(mData.getMpId());
                    } else {
                        holders.relative_guige.setTag(1);
                        notifyDataSetChanged();
                    }
                }
            });
        }
        holders.img_changegif_item_checked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData.getChecked() == 1) {
                    mData.setChecked(0);
                    holders.img_changegif_item_checked.setImageResource(selectIcon_false);
                } else {
                    if (getCheckedNum() < MaxSelecked) {
                        mData.setChecked(1);
                        holders.img_changegif_item_checked.setImageResource(selectIcon_true);
                    } else {
                        if (promotion.getPromotionType()==1018||promotion.getPromotionType()==1019){
                            ToastUtils.failToast(mContext.getString(R.string.max_choose) + MaxSelecked +mContext.getString(R.string.individual)+mContext.getString(R.string.redemption));
                        }else{
                            ToastUtils.failToast(mContext.getString(R.string.max_choose) + MaxSelecked +mContext.getString(R.string.individual)+mContext.getString(R.string.gift));
                        }
                    }
                }
            }
        });
    }

    private void drawLayout(viewholer holders, ShopCartBean.GiftProducts mData) {
        GlideUtil.display(mContext, mData.getPicUrl()).override(200, 200).into(holders.img_changegif_item_pic);
        holders.img_changegif_item_gifname.setText(mData.getGiftName());
        holders.img_changegif_item_num.setText("x"+mData.getCheckNum());
        //换购跟赠品都不判断库存
//        if (mData.getCanSaleNum()==0){
//            holders.tv_stock_num.setText("暂无库存");
//            holders.tv_stock_num.setVisibility(View.VISIBLE);
//        }else if (mData.getCanSaleNum()<6){
//            holders.tv_stock_num.setText("库存紧张");
//            holders.tv_stock_num.setVisibility(View.VISIBLE);
//        }else{
//            holders.tv_stock_num.setVisibility(View.GONE);
//        }
//        holders.tv_stock_num.setText();
        if (isSpanMoney){
            holders.img_changegif_item_gifprice.setText(UiUtils.getMoney(mContext, mData.getPrice()));
        }else{
            holders.img_changegif_item_gifprice.setText(UiUtils.getMoneyDouble(mData.getPrice()));
        }
        holders.img_changegif_item_gifprice.setTextColor(priceColor);
        getPropertyStr(holders.img_changegif_item_stands,mData.getPropertyTags());
    }

    /**
     * 获取规格参数
     *
     * @param data
     * @return
     */
    private String getPropertyStr(TextView view,List<ShopCartBean.PropertyTags> data) {
        String str = "";
        for (ShopCartBean.PropertyTags tag : data) {
            str += tag.getName() + " " + tag.getValue() + "  ";
        }
        if (null!=view){
            if (str.length()>0){
                view.setVisibility(View.VISIBLE);
                view.setText(str);
            }else{
                view.setVisibility(View.GONE);
            }
        }
        return str;
    }


    /**
     * 获取选中数量
     */
    private int getCheckedNum() {
        int number = 0;
        for (ShopCartBean.GiftProducts data : mDatas) {
            if (data.getChecked() == 1) {
                number++;
            }
        }
        return number;
    }

    /**
     * 获取当前属性的key来匹配商品队列
     *
     * @param attrs
     * @return
     */
    private static List<ShopCartBean.PropertyTags> getKeyValues(List<PropertyBean.Attributes> attrs) {
        List<ShopCartBean.PropertyTags> tags = new ArrayList<>();
        if (null != attrs && attrs.size() > 0) {
            for (PropertyBean.Attributes attr : attrs) {
                ShopCartBean.PropertyTags Values = new ShopCartBean.PropertyTags();
                Values.setName(attr.getName());
                if (null != attr && attr.getValues().size() > 0) {
                    for (PropertyBean.Values values : attr.getValues()) {
                        if (values.getChecked()) {
                            Values.setValue(values.getValue());
                        }
                    }
                }
                tags.add(Values);
            }
        }
        return tags;
    }

    class viewholer extends BaseRecyclerViewHolder {
        RelativeLayout relative_guige;
        RecyclerView img_changegif_item_reclycle;
        ImageView img_changegif_item_checked, img_changegif_item_pic, img_changegif_item_arrow;
        TextView img_changegif_item_gifname,tv_stock_num, img_changegif_item_gifprice, img_changegif_item_stands,img_changegif_item_num;

        public viewholer(View itemView) {
            super(itemView);
            tv_stock_num=(TextView)itemView.findViewById(R.id.tv_stock_num);
            img_changegif_item_reclycle = (RecyclerView) itemView.findViewById(R.id.img_changegif_item_reclycle);
            relative_guige = (RelativeLayout) itemView.findViewById(R.id.relative_guige);
            img_changegif_item_checked = (ImageView) itemView.findViewById(R.id.img_changegif_item_checked);
            img_changegif_item_pic = (ImageView) itemView.findViewById(R.id.img_changegif_item_pic);
            img_changegif_item_arrow = (ImageView) itemView.findViewById(R.id.img_changegif_item_arrow);
            img_changegif_item_num=(TextView)itemView.findViewById(R.id.img_changegif_item_num);
            img_changegif_item_gifname = (TextView) itemView.findViewById(R.id.img_changegif_item_gifname);
            img_changegif_item_gifprice = (TextView) itemView.findViewById(R.id.img_changegif_item_gifprice);
            img_changegif_item_stands = (TextView) itemView.findViewById(R.id.img_changegif_item_stands);
        }
    }

    protected ProgressDialog progressDialog;

    /**
     * 获取赠品的系列属性
     *
     * @param productID
     */
    public void getProperty(final long productID) {
        if (thisMpId == productID) {
            notifyDataSetChanged();
            return;
        }
        Map<String, String> params = new HashMap<>();
//        mView.showLoading();
//        provinceId	Integer	否	省份ID
//        mpId	Long	是	商品ID
//        promotionId	Long	是	促销ID
//        promotionRuleId	Long	是	促销规则ID
        params.put("mpId", productID + "");
        params.put("ut", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null));
        params.put("promotionId", promotion.getPromotionId() + "");
        params.put("promotionRuleId", promotion.getPromotionRuleId() + "");
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext, "努力加载中...");
            progressDialog.show();
        } else {
            progressDialog.show();
        }
        OkHttpManager.getAsyn(Constants.CART_GIFDETAIL, params, new OkHttpManager.ResultCallback<PropertyBean>() {
            @Override
            public void onError(Request request, Exception e) {
//                ToastUtils.showShort("onError");
            }

            @Override
            public void onFailed(String code, String msg) {
                super.onFailed(code, msg);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(PropertyBean response) {
                thisMpId = productID;
                gifPropertyBean = response;
                notifyDataSetChanged();
            }
        });
    }
}