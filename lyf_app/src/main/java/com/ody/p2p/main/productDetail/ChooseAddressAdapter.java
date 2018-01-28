package com.ody.p2p.main.productDetail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.productdetail.productdetail.bean.AddressBean;

import java.util.List;


/**
 * Created by pzy on 2016/12/7.
 */
public class ChooseAddressAdapter extends BaseRecyclerViewAdapter<AddressBean.Address> {
    int choosePostion=-1;

    public ChooseAddressAdapter(Context context, List<AddressBean.Address> datas) {
        super(context, datas);
    }

    public void setChoosePostion(int choosePostion) {
        if (this.choosePostion==choosePostion){
            choosePostion=-1;
        }
        this.choosePostion = choosePostion;
        notifyDataSetChanged();
    }

    public int getChoosePostion() {
        return choosePostion;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return new viewHolder(mInflater.inflate(R.layout.item_productdetaile_choose_address, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holders, final int position) {
        viewHolder holder=(viewHolder)holders;
        AddressBean.Address mdata=mDatas.get(position);
        if (position==choosePostion){
            holder.img_choose.setVisibility(View.VISIBLE);
        }else{
            holder.img_choose.setVisibility(View.INVISIBLE);
        }
        holder.tv_address_name.setText(mdata.getProvinceName()+"  "+mdata.getCityName()+"  "+mdata.getRegionName()+"  "+mdata.getDetailAddress());
    }

    public AddressBean.Address getChooseData(){
        if (choosePostion!=-1&&mDatas.size()>choosePostion&&null!=mDatas.get(choosePostion)){
            return mDatas.get(choosePostion);
        }else{
            return null;
        }
    }

    class viewHolder extends BaseRecyclerViewHolder{
        TextView tv_address_name;
        ImageView img_choose;
        LinearLayout liner_item_choose;
        public viewHolder(View itemView) {
            super(itemView);
            liner_item_choose=(LinearLayout)itemView.findViewById(R.id.liner_item_choose);
            tv_address_name=(TextView) itemView.findViewById(R.id.tv_address_name);
            img_choose=(ImageView)itemView.findViewById(R.id.img_choose);
        }
    }
}
