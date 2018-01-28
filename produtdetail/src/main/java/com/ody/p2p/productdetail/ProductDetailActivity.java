package com.ody.p2p.productdetail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.data.EventbusMessage;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.productdetail.productdetail.bean.ProductInfoBean;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.productdetail.productdetail.frangment.ProductDetailWebFragment;
import com.ody.p2p.productdetail.productdetail.frangment.productappraise.ProductCommendFragment;
import com.ody.p2p.productdetail.productdetail.frangment.productdetail.ProductFragment;
import com.ody.p2p.productdetail.views.UnderlineUtil;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.recmmend.Recommedbean;
import com.ody.p2p.recmmend.RecommendAdapter;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;
import com.ody.p2p.views.basepopupwindow.MenuPopBean;
import com.ody.p2p.views.basepopupwindow.SelectMenu;
import com.ody.p2p.views.viewpager.NoScrollViewPager;
import com.ody.p2p.webactivity.UrlBean;
import com.ody.p2p.webactivity.WebActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.router.router.ActivityRouter;


public class ProductDetailActivity extends BaseActivity implements Animator.AnimatorListener, ProductFragment.IprodutDetailActivityCallback, View.OnClickListener {


    View view;
    NoScrollViewPager pager = null;
    LinearLayout product_title, search_layout;
    List<TextView> tvliList;//页卡选项
    /**
     * //Img_return是返回键
     */
    public ImageView message_bottom_line, Img_return;
    /***
     * 这里是几个标题的控件
     */
    public TextView txt_produt, txt_deatiles, txt_evaluate;

    public List<Fragment> fragmentsList;
    UnderlineUtil underlineUtil;

    //标题栏的菜单 popup_show
    public ImageButton iv_menu;

    //有库存
    public LinearLayout bottom_layout;
    public TextView tv_incart;//购物车数量
    //立即购买的字体
    public TextView tvBuyItNow;
    //加入购物车的字体
    public TextView tvShowAddCar;

    //入口======= ====
    public String mpid = "";
    public String TAG = "";

    public LinearLayout btn_goto_cart;


    private String[] menuStr;//右角标
    private int[] menuRes;

    //规格参数的前半段URL

    public static final String STANDARD_H5URL = "/spec.html";
    public static final String SAL_H5URL = "/groupsInfo.html";

    public long cartCount;

    public boolean isState = false;

    SelectMenu menu;

    /**
     * defaultImg 项目默认的占位图
     */
    int defaultImg = OdyApplication.resPlaceHolder;

    int couponImg = R.drawable.icon_coupon_decoration;

    public int getCouponImg() {
        return couponImg;
    }

    public void setCouponImg(int couponImg) {
        this.couponImg = couponImg;
    }

    public int getDefaultImg() {
        return defaultImg;
    }

    public void setDefaultImg(int defaultImg) {
        this.defaultImg = defaultImg;
    }

    /**
     * 是否收藏了
     */
    @Override
    public void setIfCollect(boolean ifCollect) {

    }

    public ProductFragment detailfragmen;
    public ProductDetailWebFragment webfragment;
    public ProductCommendFragment appraisefragment;

    public void setDetailfragmen(ProductFragment detailfragmen) {
        this.detailfragmen = detailfragmen;
    }

    public void setWebfragment(ProductDetailWebFragment webfragment) {
        this.webfragment = webfragment;
    }

    public void setAppraisefragment(ProductCommendFragment appraisefragment) {
        this.appraisefragment = appraisefragment;
    }

    /**
     * 调用接口之后是否真的收藏成功
     *
     * @param ischeck
     * @param type
     */
    @Override
    public void collectChecked(boolean ischeck, int type) {

    }

    /**
     * 基本数据是否请求到了
     *
     * @param state
     */
    @Override
    public void setDataSucceed(boolean state, int type) {
        isState = state;
        if (!state) {
            showFailed(true, type);
            product_title.setVisibility(View.GONE);
            pager.setVisibility(View.GONE);
            iv_menu.setVisibility(View.GONE);
        } else {
            showFailed(false, type);
            pager.setVisibility(View.VISIBLE);
            product_title.setVisibility(View.VISIBLE);
            iv_menu.setVisibility(View.VISIBLE);

        }
    }

    /**
     * 对外开放的设置颜色的方法
     *
     * @param textcolor
     */
    public void setColor(int textcolor) {
        if (textcolor > 0) {
            txt_produt.setTextColor(getResources().getColor(textcolor));
//        message_bottom_line.setImageResource(getResources().getColor(textcolor));
            underlineUtil.setColor(textcolor);
        }
    }

    @Override
    public void initPresenter() {
    }

    @Override
    public int bindLayout() {
        return R.layout.produtdetail_produtdetail_activity_main;//默认的布局;
    }


    @Override
    public void initView(View view) {
        pager = (NoScrollViewPager) view.findViewById(R.id.viewpager);
        product_title = (LinearLayout) view.findViewById(R.id.product_title);
        search_layout = (LinearLayout) view.findViewById(R.id.search_layout);
        //页卡
        message_bottom_line = (ImageView) view.findViewById(R.id.message_bottom_line);
        txt_produt = (TextView) view.findViewById(R.id.txt_produt);
        txt_deatiles = (TextView) view.findViewById(R.id.txt_deatiles);
        txt_evaluate = (TextView) view.findViewById(R.id.txt_evaluate);
        iv_menu = (ImageButton) view.findViewById(R.id.iv_menu);//menu菜单点击
        Img_return = (ImageView) view.findViewById(R.id.Img_return);
        tv_incart = (TextView) view.findViewById(R.id.tv_incart);//购物车数量
        tvBuyItNow = (TextView) view.findViewById(R.id.tvBuyItNow);
        tvShowAddCar = (TextView) view.findViewById(R.id.tvShowAddCar);//接入购物车的字体
        btn_goto_cart = (LinearLayout) view.findViewById(R.id.btn_goto_cart);

        bottom_layout = (LinearLayout) view.findViewById(R.id.bottom_layout);
        tvliList = new ArrayList<TextView>();
        if (null == detailfragmen) {
            detailfragmen = new ProductFragment();
        }
        if (null == webfragment) {
            webfragment = new ProductDetailWebFragment();
        }
        if (null == appraisefragment) {
            appraisefragment = new ProductCommendFragment();
        }
        tvliList.add(txt_produt);
        tvliList.add(txt_deatiles);
        tvliList.add(txt_evaluate);
        fragmentsList = new ArrayList<Fragment>();
        fragmentsList.add(detailfragmen);
        fragmentsList.add(webfragment);
        fragmentsList.add(appraisefragment);

        underlineUtil = new UnderlineUtil(pager, this, message_bottom_line, tvliList);
        menuStr = new String[]{getString(R.string.home), getString(R.string.message), getString(R.string.share)};
        menuRes = new int[]{R.drawable.ic_homepage, R.drawable.ic_message, R.drawable.ic_share};

        onCreateveiw();
    }

    @Override
    public void doBusiness(Context mContext) {


        if (null != menuStr && menuStr.length > 0) {
            final List<MenuPopBean> popList = new ArrayList<>();
            for (int i = 0; i < menuStr.length; i++) {
                MenuPopBean bean = new MenuPopBean();
                bean.content = menuStr[i];
                if (null != menuRes && menuRes.length > 0) {
                    bean.picRes = menuRes[i];
                }
                popList.add(bean);
            }
            menu = new com.ody.p2p.views.basepopupwindow.SelectMenu(ProductDetailActivity.this, popList);
            menu.setClickSelectListener(new com.ody.p2p.views.basepopupwindow.SelectMenu.clickSelectMenuListener() {
                @Override
                public void click(int position) {
                    if (position == 0) {
                        Bundle bundle = new Bundle();
                        bundle.putInt(Constants.GO_MAIN, 0);
                        JumpUtils.ToActivity(JumpUtils.MAIN, bundle);
                    }
                    if (position == 1) {
                        menu.dismiss();
                        if (OdyApplication.getValueByKey(Constants.LOGIN_STATE, false)) {
                            JumpUtils.ToWebActivity(JumpUtils.H5, OdyApplication.H5URL + "/my/my-message.html", WebActivity.NO_TITLE, -1, "");
                        } else {
                            JumpUtils.ToActivity(JumpUtils.LOGIN);
                        }
                    }
                    if (position == 2) {
                        menu.dismiss();
                        if (OdyApplication.SCHEME.equals("saas")) {
                            ToastUtils.showShort(getString(R.string.waite_code));
                        } else {
                            detailfragmen.goToShare();
                        }
                    }
//                    if (position == 2) {
//                        getOperation().forward(null);
//                    }
                }
            });
        }
        pager.setCurrentItem(0);
        pager.addOnPageChangeListener(underlineUtil.new MyOnPageChangeListener());
        ll_notdataH5.setOnClickListener(this);
        listener();
        detailfragmen.setRecommendAdapterJumpType(getIntent().getIntExtra(Constants.PRODUCT_JUMP_TYPE, RecommendAdapter.SINGLETASK));
    }

    void listener() {

        txt_produt.setOnClickListener(underlineUtil.new MyOnClickListener(0));
        txt_deatiles.setOnClickListener(underlineUtil.new MyOnClickListener(1));
        txt_evaluate.setOnClickListener(underlineUtil.new MyOnClickListener(2));

        iv_menu.setOnClickListener(this);

        Img_return.setOnClickListener(this);
        tvShowAddCar.setOnClickListener(this);//购物车
        tvBuyItNow.setOnClickListener(this);//立即购买
        btn_goto_cart.setOnClickListener(this);
        if (iv_chat != null) {
            iv_chat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String settingId = OdyApplication.getValueByKey("settingId" + pageCode, "");
                    if (settingId != null && settingId.length() > 0) {
//                        ChatUtils.startChat(mContext, settingId, detailfragmen.goodsName, detailfragmen.goodsPrice, detailfragmen.goodsImage, detailfragmen.goodsUrl);
                    } else {
                        ToastUtils.showShort(getString(R.string.connection_service_fail));
                    }
                }
            });
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    public void onClick(View v) {

        if (v.equals(ll_notdataH5)) {//点击屏幕重新进行访问
            detailfragmen.downloadBaseInfo(mpid);
        }
        if (v.getId() == R.id.iv_menu) {
            menu.showAsDropDown(iv_menu, PxUtils.dipTopx(-55), 0);
        } else if (v.getId() == R.id.Img_return) {
            if (pagepostion == 1) {
                pager.setCurrentItem(0);
            } else if (pagepostion == 2) {
                pager.setCurrentItem(0);
            } else {
                finish();
            }
        } else if (v.getId() == R.id.tvShowAddCar) { //添加购物车
            if (status == true) {
                detailfragmen.addcarIng();
            } else {
                ToastUtils.failToast(getResources().getString(R.string.otherprodut));
            }
        } else if (v.getId() == R.id.tvBuyItNow) { //立即购买
            if (status == true) {
                detailfragmen.buyIng();
            } else {
                ToastUtils.failToast(getResources().getString(R.string.otherprodut));
            }
        } else if (v.getId() == R.id.btn_goto_cart) {
            Bundle bundle = new Bundle();
            bundle.putString("mpid", mpid);
            JumpUtils.ToActivity(JumpUtils.SHOPCART, bundle);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (pagepostion == 1) {
                pager.setCurrentItem(0);
            } else if (pagepostion == 2) {
                pager.setCurrentItem(0);
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 清空请求过的数据
     */
    public void cleaDada(int selectCartNum) {
        setProductInfoData(null);
        setCommentToAllData(null);
        setDeliveMoney(null);
        setPromotions(null);
        setCommendToRecentlyData(null, null, null);
        if (selectCartNum == 1) {
            setCartnum(-1);
        }
    }

    //购物车
    int cartnum;

    public int getCartnum() {
        return cartnum;
    }

    public void setCartnum(int cartnum) {
        this.cartnum = cartnum;
    }

    //保存第一个fragment的数据  避免加载时再次请求
    //所有数据
    ProductInfoBean productInfoData;

    public ProductInfoBean getProductInfoData() {
        return productInfoData;
    }

    public void setProductInfoData(ProductInfoBean productInfoData) {
        this.productInfoData = productInfoData;
    }

    //第一个页卡评价数据
    ProductComment.Data.MpcList commendToRecentlyData;
    String ratingUserCount;
    Integer positiveRate;

    public ProductComment.Data.MpcList getCommendToRecentlyData() {
        return commendToRecentlyData;
    }

    public String getRatingUserCount() {
        return ratingUserCount;
    }

    public Integer getPositiveRate() {
        return positiveRate;
    }

    public void setCommendToRecentlyData(ProductComment.Data.MpcList commendToRecentlyData, String ratingUserCount, Integer positiveRate) {
        this.commendToRecentlyData = commendToRecentlyData;
        this.ratingUserCount = ratingUserCount;
        this.positiveRate = positiveRate;
    }

    //猜你喜歡  数据
    Recommedbean bean;

    public Recommedbean getBean() {
        return bean;
    }

    public void setBean(Recommedbean bean) {
        this.bean = bean;
    }

    //运费数据
    public String deliveMoney;

    public String getDeliveMoney() {
        return deliveMoney;
    }

    public void setDeliveMoney(String deliveMoney) {
        this.deliveMoney = deliveMoney;
    }

    //促销数据
    List<PromotionBean.Data.PromotionInfo.Promotions> promotions;

    public List<PromotionBean.Data.PromotionInfo.Promotions> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<PromotionBean.Data.PromotionInfo.Promotions> promotions) {
        this.promotions = promotions;
    }
    //第三个页卡的评价；

    ProductComment.Data.MpcList commentToAllData;

    public ProductComment.Data.MpcList getCommentToAllData() {
        return commentToAllData;
    }

    public void setCommentToAllData(ProductComment.Data.MpcList commentToAllData) {
        this.commentToAllData = commentToAllData;
    }

    int pagepostion;//第几个页卡

    @SuppressLint("ResourceAsColor")
    protected void onCreateveiw() {
        //设置tab的背景色
        //设置当前tab页签的下划线颜色
        mpid = getIntent().getStringExtra(Constants.SP_ID);
        if (mpid == null || mpid.equals("")) {
            String keyUrl = getIntent().getStringExtra(ActivityRouter.KEY_URL);
            if (keyUrl != null && keyUrl.length() > 0) {
                UrlBean bean = JumpUtils.getUrlBean(keyUrl);
                if (bean != null) {
                    mpid = bean.mpId;
                }
            }
        }
        if (getIntent().getData() != null) {
            String distributeId = getIntent().getData().getQueryParameter("distributeId");
            String shareCode = getIntent().getData().getQueryParameter("shareCode");
            mpid = getIntent().getData().getQueryParameter("mpId");
            OdyApplication.putValueByKey(Constants.DISTRIBUTOR_ID, distributeId);
            OdyApplication.putValueByKey(Constants.SHARE_CODE, shareCode);
        }
        TAG = getIntent().getStringExtra("TAG");
        if (null != TAG && !"".equals(TAG)) {
            final String SMALL_SCREEN = "small";
            final String mrl = getIntent().getStringExtra("mrl");//"path"
            final String vid = getIntent().getStringExtra("id");
            Intent intent = null;
            try {
                intent = new Intent(ProductDetailActivity.this, Class.forName("com.odianyun.audience.live.PlayerService"));
                intent.setAction(SMALL_SCREEN);
                intent.putExtra("id", vid);
                intent.putExtra("pullUrl", mrl);
                startService(intent);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        }
        detailfragmen.setIprodutDetailActivityCallback(this);
        detailfragmen.setMpId(mpid);
        webfragment.setMpid(mpid);

        pager.setOffscreenPageLimit(2);
        pager.setAdapter(new MyViewPagerAdapter(this.getSupportFragmentManager()));
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pagepostion = position;
                if (position == 2) {
                    appraisefragment.setHide(true);
                    if (isState) {
                        appraisefragment.initAppraise(mpid);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.w("thispager", "onPageScrollStateChanged" + state);
            }
        });

    }



    public class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int arg0) {
            return fragmentsList.get(arg0);
        }

        @Override
        public int getCount() {
            return fragmentsList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // TODO Auto-generated method stub
            return "" + position;
        }


    }


    @Override
    public void onAnimationStart(Animator animator) {

    }

    public boolean mIsAnim = false;
    public boolean isDown = false;

    @Override
    public void onAnimationCancel(Animator animator) {

    }

    @Override
    public void onAnimationRepeat(Animator animator) {

    }

    @Override
    public void onAnimationEnd(Animator animator) {

        if (isDown) {
            detailfragmen.setMarginTop(product_title.getHeight());
        }
        mIsAnim = false;
    }

    @Override
    public void showTitle() {
        View view = this.product_title;
        float[] f = new float[2];
        f[0] = 0.0F;
        f[1] = -search_layout.getHeight();
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.setDuration(200);
        animator1.start();
        animator1.addListener(this);
        detailfragmen.setMarginTop(product_title.getHeight() - 2 * search_layout.getHeight());
        pager.setNoScroll(true);
    }

    @Override
    public void hideTitle() {
        View view = this.product_title;
        float[] f = new float[2];
        f[0] = -search_layout.getHeight();
        f[1] = 0F;
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(view, "translationY", f);
        animator1.setDuration(200);
        animator1.setInterpolator(new AccelerateDecelerateInterpolator());
        animator1.start();
        animator1.addListener(this);
        pager.setNoScroll(false);
    }


    @Override
    public void changeFragment(int index) {
        pager.setCurrentItem(index);
//        underlineUtil.new MyOnClickListener(2);
    }

    public boolean status;

    @Override
    public void getH5Url(String detailUrl, String standardUrl, String saleUrl) {
        webfragment.setH5Url(detailUrl, standardUrl, saleUrl);
    }

    @Override
    public void layout_addshoppingsetEnabled(boolean statu, int sale) {
        bottom_layout.setVisibility(View.VISIBLE);
        status = statu;
        if (!status) {
            if (sale == 1) {
                sokNumNull(getString(R.string.Sale));
            } else {
                sokNumNull(getString(R.string.notment));
            }
        } else {
            sokNumToNull();
        }
    }

    public void sokNumNull(String sale) {
        tvShowAddCar.setVisibility(View.GONE);
        tvBuyItNow.setBackgroundColor(getResources().getColor(R.color.grey));
        tvShowAddCar.setBackgroundColor(getResources().getColor(R.color.grey));
        tvBuyItNow.setTextColor(getResources().getColor(R.color.white));
        tvBuyItNow.setText(sale);
        tvBuyItNow.setEnabled(false);
    }

    public void sokNumToNull() {
        tvShowAddCar.setVisibility(View.VISIBLE);
        tvBuyItNow.setBackgroundColor(getResources().getColor(R.color.theme_color));
        tvShowAddCar.setBackgroundColor(getResources().getColor(R.color.theme_color));
        tvBuyItNow.setTextColor(getResources().getColor(R.color.white));
        tvBuyItNow.setText(getResources().getString(R.string.nowBuy));
        tvShowAddCar.setText(R.string.addcart);
        tvBuyItNow.setEnabled(true);
    }

    /**
     * 购物车中商品数量
     */
    @Override
    public void addShopCarCode(int productNums) {

        cartCount += productNums;
        setShopCartNum(cartCount);
        runOnUiThread(new Runnable() {//有可能取小能的ToastUtils
            @Override
            public void run() {
                com.ody.p2p.utils.ToastUtils.showShort(getString(R.string.add_to_shopcart_succeeed));
            }
        });
        EventbusMessage msg = new EventbusMessage();
        msg.flag = EventbusMessage.GET_CART_COUNT;
        EventBus.getDefault().post(msg);
    }

    @Override
    public void setpid(String mpId) {
        webfragment.setMpid(mpId);
        this.mpid = mpId;
    }


    //购物车数量
    @Override
    public void CarCode(long data) {
        cartCount = data;
        setShopCartNum(cartCount);
    }

    public void setShopCartNum(final long number) {
//        if (number < 1) {
//            tv_incart.setVisibility(View.GONE);
//        } else {
//            tv_incart.setVisibility(View.VISIBLE);
//        }
//        if (number > 99) {
//            tv_incart.setText(R.string.othermore);
//        } else {
//            tv_incart.setText(cartCount + "");
//        }
        UiUtils.setCareNum(number, 0, tv_incart);
    }

}


