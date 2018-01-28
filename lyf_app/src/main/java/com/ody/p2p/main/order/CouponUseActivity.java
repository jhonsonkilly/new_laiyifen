package com.ody.p2p.main.order;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.coupon.CouponAdapter;
import com.ody.p2p.check.coupon.UseCouponBean;
import com.ody.p2p.check.coupon.UseCouponImpl;
import com.ody.p2p.check.coupon.UseCouponPresenter;
import com.ody.p2p.check.coupon.UseCouponView;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.ToastUtils;

/**
 * Created by ${tang} on 2016/12/2.
 */

public class CouponUseActivity extends BaseActivity implements View.OnClickListener, UseCouponView {

    private ImageView iv_back;
    private ListView lv_coupon;
    private LinearLayout ll_root;
    private UseCouponPresenter presenter;
    private CouponUseAdapter adapter;
    private RelativeLayout rl_add;
    private TextView tv_no;
    private TextView tv_yes;

    @Override
    public void initPresenter() {
        presenter = new UseCouponImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.layout_use_coupon;
    }

    @Override
    public void initView(View view) {
        iv_back= (ImageView) findViewById(R.id.iv_back);
        lv_coupon= (ListView) findViewById(R.id.lv_coupon);
        ll_root= (LinearLayout) findViewById(R.id.ll_root);
        rl_add= (RelativeLayout) findViewById(R.id.rl_add);
        tv_no= (TextView) findViewById(R.id.tv_no);
        tv_yes= (TextView) findViewById(R.id.tv_yes);

        iv_back.setOnClickListener(this);

        adapter=new CouponUseAdapter(this);
        lv_coupon.setAdapter(adapter);

        rl_add.setOnClickListener(this);
        tv_yes.setOnClickListener(this);
        tv_no.setOnClickListener(this);
    }

    @Override
    public void doBusiness(Context mContext) {
        presenter.useCouponlist();
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.iv_back){
            finish();
        }else if(v.getId()==R.id.rl_add){
//            OrderCommonPopwindow popwindow=new OrderCommonPopwindow(this);
//            popwindow.setListener(new OrderCommonPopwindow.SubmitListener() {
//                @Override
//                public void submit (String cardnum, String psw) {
//                    presenter.addCoupon(cardnum);
//                }
//            });
//            popwindow.showAtLocation(ll_root, Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL,0,0);
        }else if(v.getId()==R.id.tv_yes){
            String couponId="";
            for(int i=0;i<adapter.getCount();i++){
                if(adapter.getItem(i).selected==1){
                    couponId=adapter.getItem(i).couponId;
                }
            }
            if(TextUtils.isEmpty(couponId)){
                ToastUtils.showToast("请选择优惠券");
                return;
            }
            presenter.saveUseCoupon(couponId);
        }else if(v.getId()==R.id.tv_no){
            presenter.saveUseCoupon("");
        }
    }

    @Override
    public void useCouponList(UseCouponBean useCouponBean) {
        if(useCouponBean!=null&&useCouponBean.data!=null&&useCouponBean.data.coupons!=null){
            if(useCouponBean.data.coupons.size()>0){
               adapter.addData(useCouponBean.data.coupons);
            }
        }
    }

    @Override
    public void finishActivity() {
        JumpUtils.ToActivity(JumpUtils.CONFIRMORDER);
        finish();
    }

    @Override
    public void onError() {

    }
}
