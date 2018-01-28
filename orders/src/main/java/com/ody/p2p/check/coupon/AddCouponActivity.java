package com.ody.p2p.check.coupon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.RUtils;
import com.ody.p2p.utils.ToastUtils;

import cn.campusapp.router.annotation.RouterMap;

public class AddCouponActivity extends BaseActivity implements AddCouponView, View.OnClickListener {

    private ImageView iv_back;
    private EditText et_coupon_code;
    private TextView tv_title;
    protected Button btn_ok;
    private AddCouponPresenter presenter;

    @Override
    public void initPresenter() {
        presenter = new AddCouponPresenterImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_order_add_coupon;
    }

    @Override
    public void initView(View view) {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_coupon_code = (EditText) findViewById(R.id.et_coupon_code);
        et_coupon_code.setHint(RUtils.getStringRes(this,RUtils.INPUT_COUPON_NUMBER));
        btn_ok = (Button) findViewById(R.id.btn_ok);
        tv_title= (TextView) findViewById(R.id.tv_title);
        tv_title.setText(RUtils.getStringRes(this,RUtils.ADD_COUPON));
        iv_back.setOnClickListener(this);
        btn_ok.setOnClickListener(this);
        et_coupon_code.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Drawable drawable = et_coupon_code.getCompoundDrawables()[2];
                //如果右边没有图片，不再处理
                if (drawable == null)
                    return false;
                //如果不是按下事件，不再处理
                if (event.getAction() != MotionEvent.ACTION_UP)
                    return false;
                if (event.getX() > (et_coupon_code.getWidth() - et_coupon_code.getPaddingRight()
                        - drawable.getIntrinsicWidth())) {
                    et_coupon_code.setText("");
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void finishActivity() {
        ToastUtils.showToast(getString(R.string.add_succeed));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_back) {
            finish();
        } else if (v.getId() == R.id.btn_ok) {
            if(TextUtils.isEmpty(et_coupon_code.getText().toString())){
                ToastUtils.showToast(getString(R.string.into_coupon_coed));
                return;
            }
            presenter.addCoupon(et_coupon_code.getText().toString());
        }
    }
}
