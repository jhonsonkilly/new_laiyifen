/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.ody.p2p.productdetail.photoamplification;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.productdetail.productdetail.bean.PicBean;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.DateTimeUtils;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.CircleImageView;
import com.ody.p2p.views.photoview.PhotoView;
import com.ody.p2p.views.photoview.PhotoViewAttacher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;


public class ViewPagerActivity extends BaseActivity {

    private ViewPager mViewPager;
    ImageView img_back;
    private TextView mTipTxt, count, txt_uername, txt_remark, tv_time;
    CircleImageView img_userphoto;
    RatingBar rat_rating;
    String type;

    @Override
    public void initPresenter() {
        List<String> urls = getIntent().getStringArrayListExtra("urls");
        int postion = getIntent().getIntExtra("postion", 0);
        String username = getIntent().getStringExtra("username");
        String apprisea = getIntent().getStringExtra("apprisea");
        String userImg = getIntent().getStringExtra("userImg");
        final List<PicBean> picList = (List<PicBean>) getIntent().getSerializableExtra("PicBean");
        mTipTxt.setText("1");
        if (null != type && type.equals("apprieList")) {
            if (!StringUtils.isEmpty(userImg)) {
                GlideUtil.display(mContext, userImg + "").into(img_userphoto);
            } else {
                img_userphoto.setImageResource(R.drawable.ic_avatar_special);
            }
            txt_uername.setText(username);
            txt_remark.setText(apprisea);
            mViewPager.setAdapter(new SamplePagerAdapter(urls));
            mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    mTipTxt.setText((position + 1) + "");
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // TODO Auto-generated method stub
                }
            });
        } else if (null != type && type.equals("lyf_valuate_pic") && null != picList && picList.size() > 0) {
            List<String> picist = new ArrayList<>();
            for (PicBean urlBean : picList) {
                picist.add(urlBean.getPicurl());
            }
            setInitView(picList.get(0));
            mViewPager.setAdapter(new SamplePagerAdapter(picist));
            mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    mTipTxt.setText((position + 1) + "");
                    setInitView(picList.get(position));
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // TODO Auto-generated method stub
                }
            });
            img_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        } else {
            mViewPager.setAdapter(new SamplePagerAdapter(urls));
            mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

                @Override
                public void onPageSelected(int position) {
                    mTipTxt.setText((position + 1) + "");
                }

                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                    // TODO Auto-generated method stub
                }
            });
        }
        count.setText(" / " + mViewPager.getAdapter().getCount());
        mViewPager.setCurrentItem(postion);
    }

    /**
     * 填充界面
     *
     * @param pic
     */
    private void setInitView(PicBean pic) {
        if (!StringUtils.isEmpty(pic.getPicurl())) {
            GlideUtil.display(mContext, pic.getPicurl() + "").into(img_userphoto);
        } else {
            img_userphoto.setImageResource(R.drawable.ic_avatar_special);
        }
        rat_rating.setRating(pic.getListObj().getRate());
        txt_uername.setText(pic.getListObj().getUserUsername());
        txt_remark.setText(pic.getListObj().content);
        tv_time.setText(DateTimeUtils.formatDateTime(pic.getListObj().createTime, DateTimeUtils.DF_YYYY_MM_DD));
    }

    @Override
    public int bindLayout() {
        type = getIntent().getStringExtra("type");
        if (null != type && type.equals("apprieList")) {
            return R.layout.produt_appriesdeatail;
        } else if (null != type && type.equals("lyf_valuate_pic")) {
            return R.layout.productdetail_valuate_viewpager;
        } else {
            return R.layout.produtdetail_activity_view_pager;
        }
    }

    @Override
    public void initView(View view) {
        mViewPager = (ViewPager) view.findViewById(R.id.view_pager);
        mTipTxt = (TextView) view.findViewById(R.id.tip);
        count = (TextView) view.findViewById(R.id.count);
        if (null != type && type.equals("apprieList")) {
            img_userphoto = (CircleImageView) view.findViewById(R.id.img_userphoto);
            txt_uername = (TextView) view.findViewById(R.id.txt_uername);
            txt_remark = (TextView) view.findViewById(R.id.txt_remark);
        } else if (null != type && type.equals("lyf_valuate_pic")) {
            img_userphoto = (CircleImageView) view.findViewById(R.id.img_userphoto);
            txt_uername = (TextView) view.findViewById(R.id.txt_uername);
            txt_remark = (TextView) view.findViewById(R.id.txt_remark);
            tv_time = (TextView) view.findViewById(R.id.tv_time);
            rat_rating = (RatingBar) view.findViewById(R.id.rat_rating);
            img_back = (ImageView) view.findViewById(R.id.img_back);
        } else {

        }

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

    class SamplePagerAdapter extends PagerAdapter {

        private List<String> sDrawables;

        public SamplePagerAdapter(List<String> urls) {
            // TODO Auto-generated constructor stub
            sDrawables = urls;
        }

        @Override
        public int getCount() {
            return sDrawables.size();
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
            // photoView.setImageResource(sDrawables[position]);

            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

                @Override
                public void onPhotoTap(View view, float x, float y) {
                    // TODO Auto-generated method stub
                    ViewPagerActivity.this.finish();
                }
            });
            String sdr = sDrawables.get(position);
            GlideUtil.display(ViewPagerActivity.this, sdr).override(400, 400).into(photoView);
            // Now just add PhotoView to ViewPager and return it
            container.addView(photoView, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
