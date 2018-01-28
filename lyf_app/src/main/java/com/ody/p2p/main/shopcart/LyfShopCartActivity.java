package com.ody.p2p.main.shopcart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ody.p2p.Constants;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.TKUtil;

/**
 * Created by pzy on 2016/12/20.
 */
public class LyfShopCartActivity extends BaseActivity {
    LyfShopCartFragment fragment;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_shoppingcart;
    }

    @Override
    public void initView(View view) {

    }

//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        initShopCart();
//    }

    @Override
    public void doBusiness(Context mContext) {

        String mpid = getIntent().getStringExtra("mpid");
        String distributorId = OdyApplication.getValueByKey(Constants.DISTRIBUTOR_ID, "");
        TKUtil.upload(this, com.ody.p2p.main.constant.Constants.SHOPPING_CART, distributorId, mpid, "", 1);

        initShopCart();
    }

    public void initShopCart() {
        if (null == fragment) {
            fragment = new LyfShopCartFragment();
            fragment.setType(1);
        }
        addFragment(fragment);
        String tag = getIntent().getStringExtra("TAG");
        if (null != tag && !"".equals(tag)) {
            final String SMALL_SCREEN = "small";
            final String mrl = getIntent().getStringExtra("mrl");//"path"
            final String vid = getIntent().getStringExtra("id");
            Intent intentSevice = null;
            try {
                intentSevice = new Intent(this, Class.forName("com.odianyun.audience.live.PlayerService"));
                intentSevice.setAction(SMALL_SCREEN);
                intentSevice.putExtra("id", vid);
                intentSevice.putExtra("pullUrl", mrl);
                startService(intentSevice);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.content, fragment).commitAllowingStateLoss();
    }
}
