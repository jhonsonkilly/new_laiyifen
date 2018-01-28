package com.ody.p2p.productdetail.store;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.okhttputils.OkHttpManager;
import com.ody.p2p.productdetail.store.bean.StoreBaseInfo;
import com.ody.p2p.productdetail.store.fragment.StoreActivityFragment;
import com.ody.p2p.productdetail.store.fragment.StoreAllShopFragment;
import com.ody.p2p.productdetail.store.fragment.StoreNewShopFragment;
import com.ody.p2p.productdetail.store.util.FastBlurUtil;
import com.ody.p2p.productdetail.views.StarView;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.retrofit.RetrofitFactory;
import com.ody.p2p.retrofit.store.AttentionBean;
import com.ody.p2p.retrofit.store.AttentionNumberBean;
import com.ody.p2p.retrofit.store.DoAttentionBean;
import com.ody.p2p.retrofit.store.MerIndexPageBean;
import com.ody.p2p.retrofit.store.StoreActivityCountBean;
import com.ody.p2p.retrofit.subscribers.ApiSubscriber;
import com.ody.p2p.retrofit.subscribers.SubscriberListener;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.MessageUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.tablayout.CommonTabChooser;
import com.ody.p2p.views.tablayout.TabChooserBean;
import com.ody.p2p.views.tablayout.TabSelectListener;
import com.ody.p2p.webactivity.NoTitleWebFragment;
import com.squareup.okhttp.Request;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.ody.p2p.base.OdyApplication.getValueByKey;

/**
 * Created by meijunqiang on 2017/6/22.
 * 描述:商家店铺首页
 */

public class StoreHomeActivity extends BaseActivity implements StoreHomeContract.View, View.OnClickListener {

    private CommonTabChooser storeHomeTab;
    private CommonTabChooser storeHomeCopyTab;
    private CommonTabChooser storeHomeTabButtom;
    protected FrameLayout storeHomeFlContent;
    private String[] tabContent = new String[]{"全部商品", "上新", "店铺活动"};//tab文字描述
    private String[] tabContents = new String[]{"店铺首页", "全部商品", "上新", "店铺活动"};//tab文字描述
    private String[] tabTittle = {"0", "0", "0"};//可能的文字标题
    private String[] tabTittles = {"", "0", "0", "0"};//可能的文字标题
    private int[] tabImg = new int[]{R.drawable.select_classification, R.drawable.select_buycart, R.drawable.select_my};//优先级低的图像标题
    private int[] tabImgs = new int[]{R.drawable.select_store_home, R.drawable.select_classification, R.drawable.select_buycart, R.drawable.select_my};//优先级低的图像标题
    //    private String[] tabButtomContents = new String[]{"店铺详情", "商品分类", "联系客服"};//底部tab文字描述
    private String[] tabButtomContents = new String[]{"店铺详情", "商品分类"};//底部tab文字描述
    private int[] tabButtomImgs = new int[]{R.drawable.store_nav_detail, R.drawable.store_nav_classify, R.drawable.store_nav_service};//优先级低的图像标题
    //    private List<TabChooserBean> tabBeans = new ArrayList<>();//构建的tab集合
    private List<TabChooserBean> tabButtomBeans = new ArrayList<>();//构建的底部tab集合
    private List<Fragment> mFragments = new ArrayList<>();
    private NoTitleWebFragment mStoreHome;
    protected Fragment mStoreNewShopFragment;
    protected Fragment mStoreAllShopFragment;
    protected StoreActivityFragment mStoreActivityFragment;
    private AppBarLayout storeHomeAppbarTop;
    private StoreHomeContract.Presenter mPresenter;

    private ImageView mIvStoreLogo; // 店铺logo
    private TextView mTvStoreTitle; //店铺名称
    private TextView mTvStoreAttentionCount; //关注人数
    private TextView mTvStoreGrade; //评分
    private StarView starView;
    private int isFavorite;
    private String merchantId;
    private boolean flag;//判断tabhome有三个还是四个
    protected String currentFragment;
    private String storeName;
    private String storeScore;
    private String attention;
    private String allGoods;
    private String newGoods;
    private String logoUrl;
    private String storeActivity;
    private String flagName;

    private EditText et_keyword;
    private ImageView store_home_iv_message;
    private ImageView store_image;
    protected Toolbar toolBar;
    protected TextView tv_shop_label;

    @Override
    public int bindLayout() {
        return R.layout.activity_float_activity2;
    }

    @Override
    public void initView(View view) {
        storeName = (String) getIntent().getExtras().get("storeName");
        storeScore = (String) getIntent().getExtras().get("storeScore");
        attention = (String) getIntent().getExtras().get("attention");
        allGoods = (String) getIntent().getExtras().get("allGoods");
        newGoods = (String) getIntent().getExtras().get("newGoods");
        logoUrl = (String) getIntent().getExtras().get("logoUrl");
        storeActivity = (String) getIntent().getExtras().get("storeActivity");
        merchantId = (String) getIntent().getExtras().get("merchantId");
        flagName = (String) getIntent().getExtras().get("flagName");
        findViewById(R.id.store_home_iv_back).setOnClickListener(this);
        et_keyword = (EditText) findViewById(R.id.store_home_et_search);
//        et_keyword.setOnClickListener(this);

        et_keyword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keyword = et_keyword.getText().toString();
                    if (StringUtils.isEmpty(keyword)) {
                        ToastUtils.showToast(R.string.please_input_keyword);
                    } else {
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.SEARCH_KEY, et_keyword.getText().toString());
                        bundle.putString(Constants.MERCHANT_ID, merchantId);
                        bundle.putString("store", "store");
                        bundle.putBoolean("StoreFinish", true);
                        JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bundle);
                    }
                }
                return false;
            }
        });
        findViewById(R.id.store_home_iv_category).setOnClickListener(this);
        store_home_iv_message = (ImageView) findViewById(R.id.store_home_iv_message);
        store_home_iv_message.setOnClickListener(this);
        storeHomeAppbarTop = (AppBarLayout) findViewById(R.id.store_home_appbar_top);
        storeHomeTab = (CommonTabChooser) findViewById(R.id.store_home_tab);
        storeHomeTabButtom = (CommonTabChooser) findViewById(R.id.store_home_tab_buttom);
        storeHomeFlContent = (FrameLayout) findViewById(R.id.store_home_fl_content);
        mIvStoreLogo = (ImageView) findViewById(R.id.iv_storeLogo);
        mTvStoreTitle = (TextView) findViewById(R.id.tv_store_title);
        mTvStoreAttentionCount = (TextView) findViewById(R.id.tv_store_attention_count);
        mTvStoreGrade = (TextView) findViewById(R.id.tv_store_grade);
        tv_shop_label = (TextView) findViewById(R.id.tv_shop_label);
        if (!StringUtils.isEmpty(flagName)) {
            tv_shop_label.setVisibility(View.VISIBLE);
            tv_shop_label.setText(flagName);
        }
        starView = (StarView) findViewById(R.id.star_view);

        toolBar = (Toolbar) findViewById(R.id.toolBar);

        store_image = (ImageView) findViewById(R.id.store_image);
        store_image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        store_image.setImageBitmap(getBitmap());

        storeHomeTabButtom.setStatus(-1);

        if (!StringUtils.isEmpty(logoUrl)) {
            Glide.with(this).load(logoUrl).into(mIvStoreLogo);
        }

        mTvStoreTitle.setText(storeName);
        mTvStoreGrade.setText(storeScore + "分");
//        mTvStoreAttentionCount.setText(attention);

        if (!StringUtils.isEmpty(allGoods)) {
            tabTittle[0] = allGoods;
            tabTittles[1] = allGoods;
        }

        if (!StringUtils.isEmpty(newGoods)) {
            tabTittle[1] = newGoods;
            tabTittles[2] = newGoods;
        }

        if (!StringUtils.isEmpty(storeActivity)) {
            tabTittle[2] = storeActivity;
            tabTittles[3] = storeActivity;
        }
    }


    /**
     * 关注
     */
    private void doAttention(long merchantId) {
        RetrofitFactory.doAttention(getValueByKey(Constants.USER_LOGIN_UT, ""), 3, String.valueOf(merchantId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<DoAttentionBean>(new SubscriberListener<DoAttentionBean>() {
                    @Override
                    public void onNext(DoAttentionBean doAttentionBean) {
                        if (doAttentionBean != null && doAttentionBean.getCode().equals("0")) {
                            isFavorite = 1;
                            starView.setChecked(true);
                            ToastUtils.showToast(R.string.add_collect_success);
                        }
                    }
                }));
    }

    /**
     * 取消关注
     */
    private void cancelAttention(long merchantId) {
        RetrofitFactory.cancelAttention(getValueByKey(Constants.USER_LOGIN_UT, ""), 3, String.valueOf(merchantId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<DoAttentionBean>(new SubscriberListener<DoAttentionBean>() {
                    @Override
                    public void onNext(DoAttentionBean doAttentionBean) {
                        if (doAttentionBean != null && doAttentionBean.getCode().equals("0")) {
                            isFavorite = 0;
                            starView.setChecked(false);
                            ToastUtils.showToast(R.string.cancel_collect_success);
                        }
                    }
                }));
    }

    private void getMerchantPromotionListCount(long merchantId) {
        RetrofitFactory.getMerchantPromotionListCount(String.valueOf(merchantId), "2,3,4", 1, true, false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<StoreActivityCountBean>(new SubscriberListener<StoreActivityCountBean>() {
                    @Override
                    public void onNext(StoreActivityCountBean storeActivityCountBean) {
                        if (storeActivityCountBean != null) {
                            storeActivity = storeActivityCountBean.getData().getTotal() + "";
                        }

                    }
                }));
    }

    @Override
    public void doBusiness(Context mContext) {
//        getMerIndexPage(Long.parseLong(merchantId));
        getMerchantPromotionListCount(Long.parseLong(merchantId));
        getAttentionStatus(Long.parseLong(merchantId));

        starView.setStartViewOnClickListener(new StarView.StartViewOnClickListener() {
            @Override
            public void startViewOnClick() {
                if (OdyApplication.getIsLogin()) {
                    if (isFavorite == 0) {//未关注
                        doAttention(Long.parseLong(merchantId));
                    }
                    if (isFavorite == 1) {//已关注
                        cancelAttention(Long.parseLong(merchantId));
                    }
                } else {
                    JumpUtils.ToActivity(JumpUtils.LOGIN);
                }
            }
        });

        storeHomeTabButtom.setTabSelectListener(new TabSelectListener() {
            @Override
            public void select(int position) {
                if (position == 0) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.MERCHANT_ID, merchantId);
                    JumpUtils.ToActivity(JumpUtils.SHOP_INFO, bundle);//店铺详情
                    storeHomeTabButtom.setStatus(-1);
                } else if (position == 1) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.MERCHANT_ID, merchantId);
                    JumpUtils.ToActivity(JumpUtils.SHOP_CATEGORT, bundle);//商品分类
                } else if (position == 2) {
                    ToastUtils.showShort(tabButtomBeans.get(position).tabcontent);
                }
                storeHomeTabButtom.setStatus(-1);
            }
        });
        //底部选择器适配
        for (int i = 0; i < tabButtomContents.length; i++) {
            TabChooserBean bean = new TabChooserBean();
            bean.imagesrc = tabButtomImgs[i];
            bean.tabcontent = tabButtomContents[i];
            tabButtomBeans.add(bean);
        }
        storeHomeTabButtom.setTabList(tabButtomBeans);
        storeHomeAppbarTop.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
                if (offset == 0) {
                    Log.d("addOnChangedListener", "0 / " + offset);
                } else if (Math.abs(offset) >= appBarLayout.getTotalScrollRange()) {
                    Log.d("addOnChangedListener", "1 / " + offset);
                    //处理收起事件
                    toolBar.setBackgroundResource(R.color.white);
                } else {
                    Log.d("addOnChangedListener", "2 / " + offset);
                    toolBar.setBackgroundResource(R.color.transparent_color);
                    //处理展开事件
                }
            }
        });
    }

    /**
     * 查询关注的状态
     */
    private void getAttentionStatus(long merchantId) {
        RetrofitFactory.getAttentionStatus(getValueByKey(Constants.USER_LOGIN_UT, ""), 3, String.valueOf(merchantId))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<AttentionBean>(new SubscriberListener<AttentionBean>() {
                    @Override
                    public void onNext(AttentionBean attentionBean) {
                        if (attentionBean != null) {
                            if (attentionBean.getIsFavorite() == 0) {//未关注
                                isFavorite = 0;
                                starView.setChecked(false);
                            } else if (attentionBean.getIsFavorite() == 1) {//已关注
                                isFavorite = 1;
                                starView.setChecked(true);
                            }
                        }
                    }
                }));
    }

    /**
     * 是否显示店铺首页
     *
     * @param merchantId
     */
    protected void getMerIndexPage(final long merchantId) {
        RetrofitFactory.getMerIndexPage(merchantId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ApiSubscriber<MerIndexPageBean>(new SubscriberListener<MerIndexPageBean>() {
                    @Override
                    public void onNext(MerIndexPageBean merIndexPageBean) {
                        if (merIndexPageBean != null) {
                            if (StringUtils.isEmpty(merIndexPageBean.getData())) {
                                if (mStoreAllShopFragment == null) {
                                    mStoreAllShopFragment = new StoreAllShopFragment();
                                }
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.MERCHANT_ID, String.valueOf(merchantId));
                                mStoreAllShopFragment.setArguments(bundle);

                                if (mStoreNewShopFragment == null) {
                                    mStoreNewShopFragment = new StoreNewShopFragment();
                                }
                                if (mStoreActivityFragment == null) {
                                    mStoreActivityFragment = new StoreActivityFragment();
                                }
                                mStoreActivityFragment.setArguments(bundle);
//                                mFragments.add(mStoreHome);
                                mFragments.add(mStoreAllShopFragment);
                                mFragments.add(mStoreNewShopFragment);
                                mFragments.add(mStoreActivityFragment);
                                currentFragment = "LyfStoreAllShopFragment";
                                replaceFragment(mStoreAllShopFragment);
                                flag = false;
                                initStoreHomeTab(flag);
                            } else {
                                mStoreHome = new NoTitleWebFragment();
                                mStoreHome.setLoadType(NoTitleWebFragment.JUMP_REPLACE);
                                mStoreHome.setUrl(merIndexPageBean.getData());
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants.MERCHANT_ID, String.valueOf(merchantId));
                                mStoreAllShopFragment.setArguments(bundle);
                                if (mStoreAllShopFragment == null) {
                                    mStoreAllShopFragment = new StoreAllShopFragment();
                                }
                                if (mStoreNewShopFragment == null) {
                                    mStoreNewShopFragment = new StoreNewShopFragment();
                                }
                                if (mStoreActivityFragment == null) {
                                    mStoreActivityFragment = new StoreActivityFragment();
                                }
                                mStoreActivityFragment.setArguments(bundle);
                                mFragments.add(mStoreHome);
                                mFragments.add(mStoreAllShopFragment);
                                mFragments.add(mStoreNewShopFragment);
                                mFragments.add(mStoreActivityFragment);
                                currentFragment = "LyfStoreHomeActivity";
                                replaceFragment(mStoreHome);
                                flag = true;
                                initStoreHomeTab(flag);
                            }
                        }

                        //跟新数量
                        mStoreActivityFragment.setStoreActivityCountListener(new StoreActivityFragment.StoreActivityCountListener() {
                            @Override
                            public void storeActivityCount(int count) {
                                if (flag) {
                                    tabTittles[3] = String.valueOf(count);
                                    storeHomeTab.setOneTab(3, String.valueOf(count));
                                } else {
                                    tabTittle[2] = String.valueOf(count);
                                    storeHomeTab.setOneTab(2, String.valueOf(count));
                                }
                            }
                        });
                    }
                }));
    }

    /**
     * 初始化店铺的tab
     *
     * @param flag true：4个 false：3个
     */
    private void initStoreHomeTab(boolean flag) {
        List<TabChooserBean> tabBeans = new ArrayList<>();
        if (flag) {
            for (int i = 0; i < tabContents.length; i++) {
                TabChooserBean bean = new TabChooserBean();
                bean.tabTitle = tabTittles[i];
                bean.imagesrc = tabImgs[i];
                bean.tabcontent = tabContents[i];
                tabBeans.add(bean);
            }
            storeHomeTab.setTabList(tabBeans, UiUtils.dip2px(getContext(), 15));
        } else {
            //顶部选择器适配
            for (int i = 0; i < tabContent.length; i++) {
                TabChooserBean bean = new TabChooserBean();
                bean.tabTitle = tabTittle[i];
                bean.imagesrc = tabImg[i];
                bean.tabcontent = tabContent[i];
                tabBeans.add(bean);
            }
            storeHomeTab.setTabList(tabBeans, UiUtils.dip2px(getContext(), 15));
        }

        storeHomeTab.setTabSelectListener(new TabSelectListener() {
            @Override
            public void select(int position) {
                replaceFragment(mFragments.get(position));
                storeHomeFlContent.scrollTo(0, 0);
                currentFragment = mFragments.get(position).getClass().getSimpleName();
            }
        });
    }

    /**
     * 替换fragment
     *
     * @param fragment
     */
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.store_home_fl_content, fragment).commit();
    }

    @Override
    public void resume() {
        if (storeHomeTabButtom != null) {
            storeHomeTabButtom.clearStatus();
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public void initPresenter() {
        mPresenter = new StoreHomePresenter(this);
        mPresenter.getStoreBaseInfo(merchantId);
        mPresenter.getAttentionNumber(merchantId);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.store_home_iv_back) {
            finish();
        } else if (i == R.id.store_home_et_search) {
//            ToastUtils.showShort("搜索");
            String keyword = et_keyword.getText().toString();
            if (StringUtils.isEmpty(keyword)) {
//                ToastUtils.showLongToast("关键字不能为空");
            } else {
                Bundle bundle = new Bundle();
                bundle.putString(Constants.SEARCH_KEY, keyword);
                bundle.putString("store", "store");
                bundle.putBoolean("StoreFinish", true);
                JumpUtils.ToActivity(JumpUtils.SEARCH_RESULT, bundle);
            }

        } else if (i == R.id.store_home_iv_category) {//分类
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.GO_MAIN, 1);
            bundle.putString(Constants.MERCHANT_ID, merchantId);
            JumpUtils.ToActivity(JumpUtils.SHOP_CATEGORT, bundle);
        } else if (i == R.id.store_home_iv_message) {//更多
//            Bundle bundle = new Bundle();
//            bundle.putInt(Constants.GO_MAIN, 0);
//            JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
//            showPopWindow(store_home_iv_message);
            MessageUtil.setMegScan(getContext(), store_home_iv_message);
        }
    }

    private PopupWindow popupWindow;

    private void showPopWindow(View view) {
        View contentView = LayoutInflater.from(mContext).inflate(
                R.layout.pop_window_more, null);
        TextView store_home_tv = (TextView) contentView.findViewById(R.id.store_home_tv);

        if (popupWindow == null) {
            popupWindow = new PopupWindow(contentView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        }

        store_home_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.GO_MAIN, 0);
                JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                popupWindow.dismiss();
            }
        });

        popupWindow.setBackgroundDrawable(getResources().getDrawable(
                R.drawable.selectmenu_bg_downward));
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.showAsDropDown(view);
    }


    @Override
    public void setStoreBaseInfo(StoreBaseInfo info) {
        StoreBaseInfo.DataBean data = info.getData();
        if (data != null) {
            mTvStoreTitle.setText(data.getShopName());
//            if(data.getSkuNum() == 0){
//                mTvStoreGrade.setText("暂无");
//            }else{
//                mTvStoreGrade.setText(data.getSkuNum() + "分");
//            }

            if (StringUtils.isEmpty(data.getDsrScore())) {
                mTvStoreGrade.setText("暂无");
            } else {
                mTvStoreGrade.setText(data.getDsrScore().substring(0, 3) + "分");
            }

            int favoriteNum = data.getFavoriteNum();
//            setStoreAttentionCount(favoriteNum);
            int allGoods = data.getMpNums();
            int newGoods = data.getNewMpNums();
            if (!StringUtils.isEmpty(allGoods + "")) {
                tabTittle[0] = allGoods + "";
                tabTittles[1] = allGoods + "";
            }

            if (!StringUtils.isEmpty(newGoods + "")) {
                tabTittle[1] = newGoods + "";
                tabTittles[2] = newGoods + "";
            }

            if (!StringUtils.isEmpty(storeActivity)) {
                tabTittle[2] = storeActivity;
                tabTittles[3] = storeActivity;
            }
            getMerIndexPage(data.getMerchantId());
            if (!StringUtils.isEmpty(data.getLogo())) {
                Glide.with(this).load(data.getLogo()).into(mIvStoreLogo);
            }
//
//
//
//            mTvStoreAttentionCount.setText(data.getFavoriteNum());
        }

    }

    @Override
    public void likeStatus(AttentionBean attentionBean) {

    }

    @Override
    public void likeOrCancel(DoAttentionBean doAttentionBean) {

    }

    @Override
    public void getAttentionNumber(AttentionNumberBean attentionNumberBean) {
        if(attentionNumberBean != null){
            if(!StringUtils.isEmpty(attentionNumberBean.getData() + "")){
                setStoreAttentionCount(attentionNumberBean.getData());
            }else{
                setStoreAttentionCount(0);
            }
        }
    }

    private void setStoreAttentionCount(int favoriteNum) {
        String attentionCount = null;
        if (favoriteNum > 9999) {
            attentionCount = getAttentionFormatCount(favoriteNum);
        } else {
            attentionCount = String.valueOf(favoriteNum);
        }

        mTvStoreAttentionCount.setText(attentionCount);
    }

    private String getAttentionFormatCount(int favoriteNum) {
        double count = (1.0 * favoriteNum) / 10000;
        return StringUtils.getOnePointCount(count) + "万";
    }

    /**
     * 获取虚化图片
     *
     * @return
     */
    private Bitmap getBitmap() {
        int scaleRatio = 2;
        int blurRadius = 8;
        Bitmap originBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.default_store_img);
        Bitmap scaledBitmap = Bitmap.createScaledBitmap(originBitmap,
                originBitmap.getWidth() / scaleRatio, originBitmap.getHeight() / scaleRatio, false);
        Bitmap blurBitmap = FastBlurUtil.doBlur(scaledBitmap, blurRadius, true);
        return blurBitmap;
    }

}
