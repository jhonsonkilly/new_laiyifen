package com.ody.p2p.check.orderlist;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;

import java.util.ArrayList;
import java.util.List;

public class ShowImageViewActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager vp;
    private ArrayList<PhotoFileBean> mData;
    private Intent mIntent;
    private LayoutInflater mInflater;
    private List<View> mViews;
    private ShowImageAdapter adapter;

    @Override
    public void initPresenter() {

    }

    @Override
    public int bindLayout() {
        return R.layout.activity_show_image_view;
    }

    @Override
    public void initView(View view) {
        vp = (ViewPager) view.findViewById(R.id.vp_show_image);

        mData = (ArrayList<PhotoFileBean>) getIntent().getSerializableExtra("data");
        mInflater = LayoutInflater.from(getContext());

        mViews = new ArrayList<>();
        int count = mData.size();
        for (int i = 0; i < count; i++) {
            View container = mInflater.inflate(R.layout.item_show_image,null);
            ImageView iv = (ImageView) container.findViewById(R.id.iv_show_image_item);
            iv.setTag(R.id.image_tag,mData.get(i));
            mViews.add(container);
        }

        adapter = new ShowImageAdapter(this,mViews,mData);
        vp.setAdapter(adapter);




        initEvent();
    }

    private void initEvent() {
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
//        if(v.getId() == R.id.tv_show_image_delete){
//            int count = vp.getCurrentItem();
//            if(mViews != null && mViews.size() > 0) {
//                View content = mViews.get(count);
//                ImageView iv = (ImageView) content.findViewById(R.id.iv_show_image_item);
//                if (mData.contains(iv.getTag(R.id.image_tag))) {
//                    mData.remove(iv.getTag(R.id.image_tag));
//                }
//                vp.removeView(mViews.get(count));
//                mViews.remove(mViews.get(count));
//
//                adapter.notifyDataSetChanged();
//            }
//        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            mIntent = new Intent();
//            mIntent.putExtra("result",mData);
//            setResult(Activity.RESULT_OK, mIntent);
//            finish();
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }
}
