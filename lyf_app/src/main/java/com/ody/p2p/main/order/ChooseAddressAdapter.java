package com.ody.p2p.main.order;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.addressmanage.selectaddressactivity.SelectAddressPresenter;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.recycleviewutils.RecyclerViewDragHolder;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.utils.PxUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ${tang} on 2016/11/30.
 */

public class ChooseAddressAdapter extends RecyclerView.Adapter {

    private List<AddressBean.Address> mData;

    private ItemClickListener listener;
    private SelectAddressPresenter presenter;
    private int isHomeCenter=0;

    public ChooseAddressAdapter(SelectAddressPresenter presenter,int isHomeCenter){
        mData=new ArrayList<>();
        this.presenter=presenter;
        this.isHomeCenter=isHomeCenter;
    }

    public void setData(List<AddressBean.Address> data){
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void clear() {
        mData.clear();
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View topview= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose_address,parent,false);
        topview.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        View bgview=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address_backlayout,parent,false);
        bgview.setLayoutParams(new ViewGroup.LayoutParams(PxUtils.dipTopx(120), ViewGroup.LayoutParams.MATCH_PARENT));
        return new ViewHolder(parent.getContext(),bgview,topview,RecyclerViewDragHolder.EDGE_RIGHT).getDragViewHolder();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ViewHolder holder1= (ViewHolder) RecyclerViewDragHolder.getHolder(holder);
        holder1.tv_name.setText(mData.get(position).getUserName());
        holder1.tv_address_detail.setText(mData.get(position).getProvinceName()+mData.get(position).getCityName()+mData.get(position).getRegionName()+mData.get(position).getDetailAddress());
        holder1.tv_mobile.setText(mData.get(position).getMobile());
        holder1.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //删除地址
                presenter.deleteAddress(mData.get(position).getDefaultIs(),mData.get(position).getId());
            }
        });
        holder1.tv_set_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设为默认地址
                presenter.setDefault(mData.get(position).getId(),mData.get(position).getUserName(),mData.get(position).getProvinceCode(),
                        mData.get(position).getCityId(),mData.get(position).getRegionId(),mData.get(position).getDetailAddress(),mData.get(position).getMobile(),1);
            }
        });
        if(isHomeCenter==0){
            holder1.ll_root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClickListener(mData.get(position));
                }
            });
            holder1.cb_select.setVisibility(View.VISIBLE);
            if(mData.get(position).getId().equals(OdyApplication.getValueByKey(Constants.ADDRESS_ID, ""))){
                holder1.cb_select.setChecked(true);
            }else{
                holder1.cb_select.setChecked(false);
            }
        }else{
            holder1.cb_select.setVisibility(View.GONE);
        }
        holder1.iv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", mData.get(position));
                bundle.putInt("isEdit", 1);
                if(isHomeCenter==0){
                    bundle.putBoolean("isFromOrder", true);
                }
                JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS, bundle);
            }
        });
        if(mData.get(position).getDefaultIs()==1){
            holder1.icon_default.setVisibility(View.VISIBLE);
        }else{
            holder1.icon_default.setVisibility(View.GONE);
        }

    }


    interface ItemClickListener{
        void onClickListener(AddressBean.Address address);
    }

    public void setListener(ItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerViewDragHolder{

        private CheckBox cb_select;
        private ImageView iv_edit;
        private TextView tv_name;
        private TextView tv_mobile;
        private TextView icon_default;
        private TextView tv_address_detail;
        private TextView tv_set_default;
        private TextView tv_delete;
        private RelativeLayout ll_root;

        public ViewHolder(Context context, View bgView, View topView, int mTrackingEdges) {
            super(context, bgView, topView, mTrackingEdges);
        }

        @Override
        public void initView(View itemView) {
            cb_select= (CheckBox) itemView.findViewById(R.id.cb_select);
            iv_edit= (ImageView) itemView.findViewById(R.id.iv_edit);

            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_mobile= (TextView) itemView.findViewById(R.id.tv_mobile);
            icon_default= (TextView) itemView.findViewById(R.id.icon_default);
            tv_address_detail= (TextView) itemView.findViewById(R.id.tv_address_detail);
            tv_set_default= (TextView) itemView.findViewById(R.id.tv_set_default);
            tv_delete= (TextView) itemView.findViewById(R.id.tv_delete);
            ll_root= (RelativeLayout) itemView.findViewById(R.id.ll_root);

        }
    }

}
