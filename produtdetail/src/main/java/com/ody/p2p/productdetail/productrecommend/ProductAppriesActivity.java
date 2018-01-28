package com.ody.p2p.productdetail.productrecommend;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ody.p2p.produtdetail.R;
import com.ody.p2p.base.BaseActivity;

import java.util.ArrayList;

public class ProductAppriesActivity extends BaseActivity implements View.OnClickListener {
    ImageView img_imgehistory;//浏览的商品图片
    TextView text_titlehistory, text_priceehistory;//标题和价格
    ListView list_appires;
    OtherAppiresAdapter otherAppiresAdapter;
    ArrayList<String> list = new ArrayList<>();
    ImageView img_back; //返回


    public void initPresenter() {
//        String[] imageUrls = {"商品1",
//                "商品2",
//                "商品3",
//                "商品4",};
        String[] imageUrls = {"","","","",};

        for (int i = 0; i < imageUrls.length; i++) {

            list.add(imageUrls[i]);
        }

        img_imgehistory.setImageDrawable(getResources().getDrawable(R.drawable.ico_collection));
        text_titlehistory.setText(getResources().getString(R.string.historyproduttitle));
        text_priceehistory.setText(getResources().getString(R.string.price));
        otherAppiresAdapter = new OtherAppiresAdapter(this, list);
        list_appires.setAdapter(otherAppiresAdapter);
        otherAppiresAdapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.img_back) {
            finish();
        }

    }

    @Override
    public int bindLayout() {
        return R.layout.produtdetail_activity_product_appries;
    }

    @Override
    public void initView(View view) {
        getSupportActionBar().hide();
        img_imgehistory = (ImageView) view.findViewById(R.id.img_imgehistory);
        text_titlehistory = (TextView) view.findViewById(R.id.text_titlehistory);
        text_priceehistory = (TextView) view.findViewById(R.id.text_priceehistory);
        list_appires = (ListView) view.findViewById(R.id.list_appires);
        img_back = (ImageView) view.findViewById(R.id.img_back);
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
