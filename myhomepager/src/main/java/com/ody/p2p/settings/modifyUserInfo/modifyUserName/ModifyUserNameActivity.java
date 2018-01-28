package com.ody.p2p.settings.modifyUserInfo.modifyUserName;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.myhomepager.R;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;

public class ModifyUserNameActivity extends BaseActivity implements View.OnClickListener {

    TextView tv_name;
    TextView tv_right_text;
    RelativeLayout rl_big_back;
    EditText et_name;
    ImageView iv_cha;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.setting_activity_modify_user_name;
    }

    @Override
    public void initView(View view) {
        tv_name = (TextView) view.findViewById(R.id.tv_name);
        tv_right_text = (TextView) view.findViewById(R.id.tv_right_text);
        rl_big_back = (RelativeLayout) view.findViewById(R.id.rl_big_back);
        et_name = (EditText) view.findViewById(R.id.et_name);
        iv_cha = (ImageView) view.findViewById(R.id.iv_cha);

        tv_name.setText(getString(R.string.change_nick));
        tv_right_text.setText(getString(R.string.complete));

        rl_big_back.setOnClickListener(this);
        tv_right_text.setOnClickListener(this);
        iv_cha.setOnClickListener(this);

        StringUtils.operateCha(et_name,iv_cha);
    }

    @Override
    public void doBusiness(Context mContext) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.equals(rl_big_back)){
            finish();
        }
        if (v.equals(tv_right_text)){
            if (et_name==null){
                return;
            }
            if (et_name.getText()==null){
                return;
            }
            if (StringUtils.isEmpty(et_name.getText().toString())){
                ToastUtils.showShort(getString(R.string.nick_null));
                return;
            }
            Intent intent = new Intent();
            intent.putExtra("name",et_name.getText().toString());
            setResult(RESULT_OK, intent);
            finish();
        }
        if (v.equals(iv_cha)){
            if (et_name==null){
                return;
            }
            et_name.setText("");
        }
    }
}
