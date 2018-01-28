package com.ody.p2p.productdetail.productdepreciate;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ody.p2p.produtdetail.R;
import com.ody.p2p.base.BaseActivity;

public class    ProductThepriceActivity extends BaseActivity implements View.OnClickListener {
    TextView txt_commit, txt_qwjg, ext_phone;//提交,降价价,手机号码
    ImageView img_back;

    @Override
    public void initPresenter() {

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.txt_commit) {
            Toast.makeText(this, getResources().getString(R.string.price_) + txt_qwjg.getText().toString() +getResources().getString(R.string.phone) + ext_phone.getText().toString(), Toast.LENGTH_LONG).show();
            finish();
            //                if (null==txt_qwjg||"".equals(txt_qwjg.getText().toString())){
//                    Toast.makeText(this,"请输入价格",Toast.LENGTH_LONG).show();
//                }
//                if (null==ext_phone||"".equals(ext_phone.getText().toString())){
//                Toast.makeText(this,"请输入号码",Toast.LENGTH_LONG).show();
//            }
        } else if (v.getId() == R.id.img_back) {
            finish();
        }
    }

    @Override
    public int bindLayout() {
        return R.layout.produtdetail_activity_product_theprice;
    }

    @Override
    public void initView(View view) {
        txt_commit = (TextView) view.findViewById(R.id.txt_commit);
        txt_qwjg = (TextView) view.findViewById(R.id.txt_qwjg);
        ext_phone = (TextView) view.findViewById(R.id.ext_phone);
        img_back = (ImageView) view.findViewById(R.id.img_back);
        txt_commit.setOnClickListener(this);
        img_back.setOnClickListener(this);

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
}
