package com.ody.p2p.main.productDetail;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;


import com.ody.p2p.AliServicePolicy;
import com.ody.p2p.Constants;
import com.ody.p2p.QiYuServicePolicy;
import com.ody.p2p.ServiceManager;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.entity.QIYuEntity;
import com.ody.p2p.main.R;
import com.ody.p2p.productdetail.ProductDetailActivity;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.TKUtil;

/**
 * Created by pzy on 2016/12/6.
 */
public class LyfProductDetailActivity extends ProductDetailActivity {

    LinearLayout ll_collect;
    LyfProductFragment lyfProductFragment;
    View viewlin;
    LinearLayout ll_custom;

    @Override
    public void doBusiness(Context mContext) {
        super.doBusiness(mContext);

        setColor(R.color.theme_color);//设置主题颜色
//        setDefaultImg(R.drawable.item_default);设置占位图

        String distributorId = OdyApplication.getValueByKey(Constants.DISTRIBUTOR_ID, "");
        TKUtil.upload(this, com.ody.p2p.main.constant.Constants.GOODS_DETAIL, distributorId, mpid, "", 1);

        iv_menu.setVisibility(View.GONE);

    }

    @Override
    public void setDataSucceed(boolean state, int type) {
        super.setDataSucceed(state, type);
        iv_menu.setVisibility(View.GONE);
    }

    @Override
    public int bindLayout() {
        return R.layout.lyf_produtdetail_activity_main;
    }

    @Override
    public void initView(View view) {
        lyfProductFragment = new LyfProductFragment();
        setDetailfragmen(lyfProductFragment);//设置新的第一个页卡
        setWebfragment(new LyfProductWebFragment());//设置新的第二个页卡
        setAppraisefragment(new LyfProductCommendFragment());//设置新的第三个页卡

        super.initView(view);

        viewlin = findViewById(R.id.viewlin);
        ll_collect = (LinearLayout) view.findViewById(R.id.ll_collect);
        ll_custom = (LinearLayout) view.findViewById(R.id.ll_custom);
        ll_collect.setOnClickListener(this);
        ll_custom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ll_collect) {
            super.onClick(v);
            if (!StringUtils.isEmpty(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, null))) {
                if (ll_collect.isSelected()) {
                    lyfProductFragment.isNotcollect();
                } else {
                    lyfProductFragment.collect();
                }
            } else {
                Bundle extra = new Bundle();
                extra.putString(Constants.LOSINGTAP, "100");
                JumpUtils.ToActivity(JumpUtils.LOGIN, extra);
                detailfragmen.LoGonType = 1;
            }
        } else if (v.getId() == R.id.tvShowAddCar) {
            if (status == true) {
//                detailfragmen.addProductShopCart();
                detailfragmen.addcarIng();
            } else {
                if (null != OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, "") && !"".equals(OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""))) {
                    Bundle bd = new Bundle();
                    bd.putSerializable("product", getProductInfoData());
                    JumpUtils.ToActivity(JumpUtils.ARRIVAL_NOTIF, bd);//到货通知
                } else {
                    Bundle bd = new Bundle();
                    bd.putString(Constants.LOSINGTAP, "100");
                    JumpUtils.ToActivity(JumpUtils.LOGIN, bd);
                }
            }
        } else if (v.getId() == R.id.ll_custom) {

            if (OdyApplication.getString(Constants.SERVICE_TOGGLE, "0").equals("0")) {
                ServiceManager.getInstance().doPolicy(new AliServicePolicy(),this);
            } else {
                ServiceManager.getInstance().doPolicy(new QiYuServicePolicy(), QIYuEntity.DETAIL,this);
            }

            //new ChatUtils().callService(getContext());

        } else {
            super.onClick(v);
        }
    }

    @Override
    public void layout_addshoppingsetEnabled(boolean statu, int sale) {
        super.layout_addshoppingsetEnabled(statu, sale);
        if (sale == 100) {
            tvShowAddCar.setVisibility(View.VISIBLE);
            tvShowAddCar.setBackgroundColor(getResources().getColor(R.color.grey));
            tvShowAddCar.setText("该区域不可售");
            tvShowAddCar.setClickable(false);
            viewlin.setVisibility(View.GONE);
        } else {
            if (sale == 1) {
                tvShowAddCar.setText(getString(R.string.arrival_notif));
                tvShowAddCar.setVisibility(View.VISIBLE);
                viewlin.setVisibility(View.GONE);
                tvBuyItNow.setBackgroundColor(getResources().getColor(R.color.theme_color));
                tvShowAddCar.setBackgroundColor(getResources().getColor(R.color.theme_color));
            }
        }
    }

    //检查是否收藏成功
    @Override
    public void collectChecked(boolean ischeck, int type) {
        super.collectChecked(ischeck, type);
        if (ischeck && type == 1) {
            isCheck(true);
        }
        if (ischeck && type == 0) {
            isCheck(false);
        }
    }

    //是否收藏了
    @Override
    public void setIfCollect(boolean ifcollect) {
        super.setIfCollect(ifcollect);
        if (ifcollect) {
            isCheck(ifcollect);
        } else {
            isCheck(ifcollect);
        }
    }

    void isCheck(boolean ifcollect) {
        ll_collect.setSelected(ifcollect);
    }
}
