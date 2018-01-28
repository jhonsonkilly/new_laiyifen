package com.ody.p2p.main.specificfunction;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.main.R;

import java.util.IllegalFormatCodePointException;

/**
 * Created by lxs on 2017/2/24.
 */
public class PointCardSearchActivity extends BaseActivity implements View.OnClickListener,PointCardSearchView{

    protected EditText et_cardnum;
    protected EditText et_password;
    protected ImageView iv_back;
    protected TextView tv_sure;

    protected PointCardSearchPresenter mPresenter;

    @Override
    public void initPresenter() {
        mPresenter = new PointCardSearchImpl(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_pointcard;
    }

    @Override
    public void initView(View view) {
        et_cardnum = (EditText) view.findViewById(R.id.et_cardnum);
        et_password = (EditText) view.findViewById(R.id.et_password);
        iv_back = (ImageView) view.findViewById(R.id.iv_back);
        tv_sure = (TextView) view.findViewById(R.id.tv_sure);
    }

    @Override
    public void doBusiness(Context mContext) {
        et_cardnum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cardnum.getText().toString().length() > 0 && et_password.getText().toString().length() > 0){
                    tv_sure.setEnabled(true);
                }else {
                    tv_sure.setEnabled(false);
                }
            }
        });
        et_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (et_cardnum.getText().toString().length() > 0 && et_password.getText().toString().length() > 0){
                    tv_sure.setEnabled(true);
                }else {
                    tv_sure.setEnabled(false);
                }
            }
        });
        tv_sure.setOnClickListener(this);
        iv_back.setOnClickListener(this);
    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_sure){
            mPresenter.searchCardInfo(et_cardnum.getText().toString(),et_password.getText().toString());
        }
        if (v.getId() == R.id.iv_back){
            finish();
        }
    }

    @Override
    public void initData(PointCardBean bean) {
        if (bean.data != null){

        }
    }
}
