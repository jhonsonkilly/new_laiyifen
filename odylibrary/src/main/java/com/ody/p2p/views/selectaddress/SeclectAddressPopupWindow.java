package com.ody.p2p.views.selectaddress;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import com.ody.p2p.R;
import com.ody.p2p.views.basepopupwindow.BasePopupWindow;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/6/15.
 */
public class SeclectAddressPopupWindow extends BasePopupWindow implements selectAddressPopupWindowView,View.OnClickListener{

    private String firstTitle,secondTitle,thirdTitle;
    public LocationAdapter adapter1,adapter2,adapter3;
    private TextView tv_title;
    public ImageView iv_back;
    private ImageView iv_dismiss;
    private TextView tv_first,tv_second,tv_third;
    private RecyclerView rv_select_location;

    private RequestAddressBean.Data firstData,secondData,thirdData;
    private RequestAddress mRequestAddress;
    private LinearLayoutManager manager;
    private List<RequestAddressBean.Data> mData1,mData2,mData3;
    private int mPosition = 0;

    public SeclectAddressPopupWindow(Activity context){
        super(context);
        init(context,R.layout.addressmanage_popup_layout);

        tv_title = (TextView) mMenuView.findViewById(R.id.tv_head_title);
        tv_title.setText(context.getResources().getString(R.string.addressmanage_location));

        tv_first = (TextView) mMenuView.findViewById(R.id.tv_location_first);
        tv_second = (TextView) mMenuView.findViewById(R.id.tv_location_second);
        tv_third = (TextView) mMenuView.findViewById(R.id.tv_location_third);
        iv_dismiss = (ImageView) mMenuView.findViewById(R.id.iv_dismiss);
        iv_back = (ImageView) mMenuView.findViewById(R.id.iv_head_back);
        rv_select_location = (RecyclerView) mMenuView.findViewById(R.id.rv_pop_select_location);
        dismissWindow(iv_back);

        manager = new LinearLayoutManager(mContext);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_select_location.setLayoutManager(manager);

        tv_first.setText(R.string.please_select);
        tv_second.setVisibility(View.GONE);
        tv_third.setVisibility(View.GONE);
        tv_first.setOnClickListener(this);
        tv_second.setOnClickListener(this);
        tv_third.setOnClickListener(this);
        iv_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tv_first.setTextColor(context.getResources().getColor(selectedTextColor));


        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mPosition){
                    case 0:
                        dismiss();
                        mPosition = 0;
                        break;
                    case 1:
                        mPosition = 0;
                        mData2.clear();
                        mData3.clear();
                        tv_second.setVisibility(View.GONE);
                        tv_third.setVisibility(View.GONE);
                        rv_select_location.setAdapter(adapter1);
                        break;
                    case 2:
                        mPosition = 1;
                        mData3.clear();
                        tv_third.setVisibility(View.GONE);
                        rv_select_location.setAdapter(adapter2);
                        break;
                }
                reSetText(mPosition);
            }
        });
        if(hide){
            iv_back.setVisibility(View.GONE);
        }else {
            iv_back.setVisibility(View.VISIBLE);
        }

        mData1 = new ArrayList<RequestAddressBean.Data>();
        mData2 = new ArrayList<RequestAddressBean.Data>();
        mData3 = new ArrayList<RequestAddressBean.Data>();
        adapter1 = new LocationAdapter(mContext,mData1);
        adapter2 = new LocationAdapter(mContext,mData2);
        adapter3 = new LocationAdapter(mContext,mData3);


        mRequestAddress = new RequestAddress(this);
        mRequestAddress.getAddressProvince();

    }



    private void reSetText(int position) {
        tv_first.setTextColor(mContext.getResources().getColor(normalTextColor));
        tv_second.setTextColor(mContext.getResources().getColor(normalTextColor));
        tv_third.setTextColor(mContext.getResources().getColor(normalTextColor));
        switch (position){
            case 0:
                tv_first.setTextColor(mContext.getResources().getColor(selectedTextColor));
                break;
            case 1:
                tv_second.setTextColor(mContext.getResources().getColor(selectedTextColor));
                break;
            case 2:
                tv_third.setTextColor(mContext.getResources().getColor(selectedTextColor));
                break;
        }

    }

    private int normalTextColor = R.color.main_title_color,selectedTextColor = R.color.theme_color;
    public void  setTextColor(int normalColor,int selectedTextColor){
        tv_first.setTextColor(mContext.getResources().getColor(selectedTextColor));
        normalTextColor = normalColor;
        this.selectedTextColor = selectedTextColor;
    }


    private void ref(RequestAddressBean.Data titleName,int position){
        if(position == 1){
            firstTitle = titleName.name;
            tv_first.setText(firstTitle);
            tv_second.setText(mContext.getString(R.string.please_select));
            tv_first.setVisibility(View.VISIBLE);
            tv_second.setVisibility(View.VISIBLE);
            tv_third.setVisibility(View.GONE);
            tv_first.setTextColor(mContext.getResources().getColor(normalTextColor));
            tv_second.setTextColor(mContext.getResources().getColor(selectedTextColor));
            secondTitle = null;
            thirdTitle = null;

            firstData = titleName;
            adapter1.setmSeclected(firstData);
            mData2.clear();
            mData3.clear();
            adapter2.notifyDataSetChanged();
            adapter3.notifyDataSetChanged();
            rv_select_location.setAdapter(adapter2);
            mRequestAddress.getCityAddress(firstData.code);


            secondData = null;
            thirdData = null;
        }

        if(position == 2){
            secondTitle = titleName.name;
            tv_first.setText(firstTitle);
            tv_second.setText(secondTitle);
            tv_third.setText(mContext.getString(R.string.please_select));

            tv_first.setVisibility(View.VISIBLE);
            tv_second.setVisibility(View.VISIBLE);
            tv_third.setVisibility(View.VISIBLE);
            tv_first.setTextColor(mContext.getResources().getColor(normalTextColor));
            tv_second.setTextColor(mContext.getResources().getColor(normalTextColor));
            tv_third.setTextColor(mContext.getResources().getColor(selectedTextColor));

            secondData = titleName;
            adapter2.setmSeclected(secondData);
            mData3.clear();
            adapter3.notifyDataSetChanged();
            rv_select_location.setAdapter(adapter3);
            mRequestAddress.getRegionAddress(secondData.code);

            thirdTitle = null;
            thirdData = null;
        }

        if(position == 3){
            thirdTitle = titleName.name;
            tv_first.setText(firstTitle);
            tv_second.setText(secondTitle);
            tv_third.setText(thirdTitle);

            tv_first.setVisibility(View.VISIBLE);
            tv_second.setVisibility(View.VISIBLE);
            tv_third.setVisibility(View.VISIBLE);
            tv_first.setTextColor(mContext.getResources().getColor(normalTextColor));
            tv_second.setTextColor(mContext.getResources().getColor(normalTextColor));
            tv_third.setTextColor(mContext.getResources().getColor(selectedTextColor));
            thirdData = titleName;
            adapter3.setmSeclected(thirdData);

            if(listener!= null){
                listener.getAddress(firstData,secondData,thirdData);
            }
            dismiss();
        }
    }

    private  selectAddressListener listener;

    public void setSelectAddressListener(selectAddressListener  l){
        listener = l;
    }



    /**
     * 所有省
     */
    protected List<RequestAddressBean.Data> mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    protected List<RequestAddressBean.Data> mCitisDatas;
    /**
     * key - 市 values - 区
     */
    protected List<RequestAddressBean.Data> mDistrictDatas;


    @Override
    public void showCity(RequestAddressBean bean) {
        mCitisDatas = bean.data;
        if(mCitisDatas != null && mCitisDatas.size() >0){
            mData2 = mCitisDatas;
            adapter2 = new LocationAdapter(mContext,mData2);
            rv_select_location.setAdapter(adapter2);
            adapter2.setOnItemClickListener(new LocationAdapter.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    RequestAddressBean.Data t = (RequestAddressBean.Data) adapter2.getDatas().get(position);
                    adapter2.setmSeclected(t);
                    ref(t, 2);
                    mPosition = 2;
                }
            });

        }

    }

    @Override
    public void showRegion(RequestAddressBean bean) {
        mDistrictDatas = bean.data;
        if(mDistrictDatas != null && mDistrictDatas.size() >0){
            mData3 = mDistrictDatas;
            adapter3 = new LocationAdapter(mContext,mData3);
            rv_select_location.setAdapter(adapter3);
            adapter3.setOnItemClickListener(new LocationAdapter.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    RequestAddressBean.Data t = (RequestAddressBean.Data) adapter3.getDatas().get(position);
                    adapter3.setmSeclected(t);
                    ref(t, 3);
                    mPosition = 2;
                }
            });
        }

    }

    @Override
    public void showProvince(RequestAddressBean bean) {
        mProvinceDatas = bean.data;
        if(mProvinceDatas != null && mProvinceDatas.size() >0){
            mData1 =  mProvinceDatas;
            adapter1 = new LocationAdapter(mContext,mData1);
            rv_select_location.setAdapter(adapter1);
            adapter1.setOnItemClickListener(new LocationAdapter.onItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    RequestAddressBean.Data t = (RequestAddressBean.Data) adapter1.getDatas().get(position);
                    adapter1.setmSeclected(t);
                    ref(t, 1);
                    mPosition = 1;
                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        if(v.equals(tv_first)){
            reSetText(0);
            rv_select_location.setAdapter(adapter1);
            mPosition = 0;
        }else if(v.equals(tv_second)){
            reSetText(1);
            rv_select_location.setAdapter(adapter2);
            mPosition = 1;
        }else if(v.equals(tv_third)){
            reSetText(2);
            rv_select_location.setAdapter(adapter3);
            mPosition = 2;
        }
    }
    private boolean hide= false;
    public void hideImg(boolean hide){
        this.hide = hide;
        if(iv_back != null){
            iv_back.setVisibility(View.GONE);
        }
    }
}
