package com.ody.p2p.shopcart;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.ody.p2p.base.BaseActivity;

/**
 * Created by lxs on 2016/7/14.
 */
public class ShoppingCartActivity extends BaseActivity {
    ShoppingCartFragment fragment;

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


    @Override
    public void doBusiness(Context mContext) {
        if (null == fragment) {
            fragment = new ShoppingCartFragment();
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
            }catch(Exception e){
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
