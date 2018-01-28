package com.ody.p2p.productdetail.store;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.photoamplification.ViewPagerActivity;
import com.ody.p2p.productdetail.store.bean.StoreBaseInfo;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.retrofit.store.AttentionBean;
import com.ody.p2p.retrofit.store.AttentionNumberBean;
import com.ody.p2p.retrofit.store.DoAttentionBean;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.MyGridView;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.squareup.okhttp.Request;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ody.p2p.base.OdyApplication.getValueByKey;

/**
 * Created by ${tang} on 2017/6/26.
 */

public class StoreDetailActivity extends BaseActivity implements StoreHomeContract.View, View.OnClickListener {

    private ImageView iv_shop;
    private TextView tv_shop_name;
    private TextView tv_shop_label;
    private TextView tv_score;
    private TextView tv_follow_num;
    private RelativeLayout rl_like;
    private TextView tv_score1;
    private TextView tv_score2;
    private TextView tv_score3;
    private TextView tv_merchant_phone;
    private RelativeLayout rl_auth;
    private TextView tv_shop_introduce;
    private TextView tv_zone;
    private TextView tv_time;
    private StoreHomePresenter presenter;
    private MyGridView rv_brand;
    private String merchantId;
    private ShopBrandGridAdapter shopBrandAdapter;
    private RelativeLayout rl_contact;
    private ImageView iv_star;
    private TextView tv_like;
    private int likeStatus = 0;
    private ImageView iv_back;


    @Override
    public void initPresenter() {
        presenter = new StoreHomePresenter(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_shop_detail;
    }

    @Override
    public void initView(View view) {
        merchantId = getIntent().getStringExtra("merchantId");
        iv_shop = (ImageView) findViewById(R.id.iv_shop);
        tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        tv_shop_label = (TextView) findViewById(R.id.tv_shop_label);
        tv_score = (TextView) findViewById(R.id.tv_score);
        tv_follow_num = (TextView) findViewById(R.id.tv_follow_num);
        rl_like = (RelativeLayout) findViewById(R.id.rl_like);
        tv_score1 = (TextView) findViewById(R.id.tv_score1);
        tv_score2 = (TextView) findViewById(R.id.tv_score2);
        tv_score3 = (TextView) findViewById(R.id.tv_score3);
        tv_merchant_phone = (TextView) findViewById(R.id.tv_merchant_phone);
        tv_shop_introduce = (TextView) findViewById(R.id.tv_shop_introduce);
        tv_zone = (TextView) findViewById(R.id.tv_zone);
        tv_time = (TextView) findViewById(R.id.tv_time);
        rl_auth = (RelativeLayout) findViewById(R.id.rl_auth);
        rv_brand = (MyGridView) findViewById(R.id.rv_brand);
        rl_contact = (RelativeLayout) findViewById(R.id.rl_contact);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        shopBrandAdapter = new ShopBrandGridAdapter(this);
        rv_brand.setAdapter(shopBrandAdapter);
        rl_contact.setOnClickListener(this);
        rl_auth.setOnClickListener(this);
        rl_like.setOnClickListener(this);
        iv_back.setOnClickListener(this);
        iv_star = (ImageView) findViewById(R.id.iv_star);
        tv_like = (TextView) findViewById(R.id.tv_like);
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.getStoreBaseInfo(merchantId);
        presenter.getLikeStatus(merchantId);
        presenter.getAttentionNumber(merchantId);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void setStoreBaseInfo(StoreBaseInfo info) {
        if (info == null || info.getData() == null) {
            return;
        }
        GlideUtil.display(this, info.getData().getLogo()).into(iv_shop);
        tv_shop_name.setText(info.getData().getShopName());
//        tv_follow_num.setText(info.getData().getFavoriteNum() + "\n关注");

        if (!StringUtils.isEmpty(info.getData().getDsrScore())) {
            tv_score.setText(info.getData().getDsrScore().substring(0, 3) + "分");
        }

        if (!StringUtils.isEmpty(info.getData().getShopDesc())) {
            tv_shop_introduce.setText(info.getData().getShopDesc());
        }
        if (!TextUtils.isEmpty(info.getData().getDetailAddressAll())) {
            tv_zone.setText(info.getData().getDetailAddressAll());
        }
        if (info.getData().getOpenTime() > 0) {
            tv_time.setText(DateTimeUtils.formatDateTime(info.getData().getOpenTime()));
        }
        if (info.getData().brands != null && info.getData().brands.size() > 0) {
            shopBrandAdapter.setNewData(info.getData().brands);
        }
        if (TextUtils.isEmpty(info.getData().getContactMobile())) {
            tv_merchant_phone.setText("暂无");
        } else {
            tv_merchant_phone.setText(info.getData().getContactMobile());
        }
//        if (info.getData().getFlags() != null && info.getData().getFlags().size() > 0) {
//            GlideUtil.display(this, info.getData().getFlags().get(0)).into(tv_shop_label);
//        }
        if (!TextUtils.isEmpty(info.getData().getFlagName())) {
            tv_shop_label.setText(info.getData().getFlagName());
        }
        rl_contact.setTag(info.getData().getContactMobile());
        rl_auth.setTag(info.getData().getCertificateFiles());

    }

    @Override
    public void likeStatus(AttentionBean attentionBean) {
        if (attentionBean != null && attentionBean.getData() != null) {
            likeStatus = attentionBean.getIsFavorite();
            if (attentionBean.getIsFavorite() == 1) {//1:已收藏 0:未收藏
                iv_star.setVisibility(View.GONE);
                rl_like.setBackgroundResource(R.drawable.shape_banner_normal);
                tv_like.setText("已关注");
            } else {
                iv_star.setVisibility(View.VISIBLE);
                rl_like.setBackgroundResource(R.drawable.circular_themecolor);
                tv_like.setText("关注");
            }
        }
    }

    @Override
    public void likeOrCancel(DoAttentionBean doAttentionBean) {
        presenter.getStoreBaseInfo(merchantId);
    }

    @Override
    public void getAttentionNumber(AttentionNumberBean attentionNumberBean) {
        if (attentionNumberBean != null) {
            if (!StringUtils.isEmpty(attentionNumberBean.getData() + "")) {
                setStoreAttentionCount(attentionNumberBean.getData());
            } else {
                setStoreAttentionCount(0);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.rl_contact) {
            final String phoneNum = (String) rl_contact.getTag();
            if (!TextUtils.isEmpty(phoneNum)) {
                CustomDialog customDialog = new CustomDialog(this, phoneNum, 2);
                customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                    @Override
                    public void Confirm() {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNum));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
            }
        } else if (v.getId() == R.id.rl_auth) {
            List<String> urls = (List<String>) rl_auth.getTag();
            if (urls != null && urls.size() > 0) {
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                intent.putExtra("urls", (Serializable) urls);
                intent.putExtra("postion", 0);
                startActivity(intent);
            } else {
                ToastUtils.showToast("没有认证信息哦~");
            }

        } else if (v.getId() == R.id.rl_like) {
            if (likeStatus == 1) {
                iv_star.setVisibility(View.VISIBLE);
                rl_like.setBackgroundResource(R.drawable.circular_themecolor);
                tv_like.setText("关注");
                presenter.cancelLike(merchantId);
                likeStatus = 0;
            } else {
                iv_star.setVisibility(View.GONE);
                rl_like.setBackgroundResource(R.drawable.shape_banner_normal);
                tv_like.setText("已关注");
                presenter.like(merchantId);
                likeStatus = 1;
            }
        } else if (v.getId() == R.id.iv_back) {
            finish();
        }
    }

    private void setStoreAttentionCount(int favoriteNum) {
        String attentionCount = null;
        if (favoriteNum > 9999) {
            attentionCount = getAttentionFormatCount(favoriteNum);
        } else {
            attentionCount = String.valueOf(favoriteNum);
        }

        tv_follow_num.setText(attentionCount + "\n关注");
    }

    private String getAttentionFormatCount(int favoriteNum) {
        double count = (1.0 * favoriteNum) / 10000;
        return StringUtils.getOnePointCount(count) + "万";
    }
}
