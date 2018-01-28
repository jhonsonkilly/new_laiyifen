package com.ody.p2p.check.aftersale;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.ody.p2p.check.R;
import com.ody.p2p.check.aftersale.Bean.InitApplyRefundBean;
import com.ody.p2p.check.aftersale.Bean.SalesReturn;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.UiUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/6/29.
 */
public class RefoundAdapter extends RecyclerView.Adapter {

    protected Context mContext;
    protected List<InitApplyRefundBean.AfterSalesProductVOs> listsale;
    private boolean isEnable=true;
    private OnItemChecked onItemChecked;

    public RefoundAdapter(Context mContext) {
        this.mContext = mContext;
        listsale=new ArrayList<>();
    }

    public void setEnable(boolean isEnable){
        this.isEnable=isEnable;
    }

    public void setData(List<InitApplyRefundBean.AfterSalesProductVOs> listsale){
        this.listsale = listsale;
        notifyDataSetChanged();
    }

    public List<InitApplyRefundBean.AfterSalesProductVOs> getReturnProductlist(){
        return listsale;
    }

    public List<InitApplyRefundBean.AfterSalesProductVOs> getCheckedItemNum(){
        List<InitApplyRefundBean.AfterSalesProductVOs> data=new ArrayList<>();
        if(listsale.size()>0){
            for(int i=0;i<listsale.size();i++){
                if(listsale.get(i).checked){
                    data.add(listsale.get(i));
                }
            }
        }
        return data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.refound_list, parent, false));
    }

    public InitApplyRefundBean.AfterSalesProductVOs getItem(int position){
        return listsale.get(position);
    }

    @Override
    public int getItemCount() {
        return listsale.size();
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder= (ViewHolder) holder;
        if(isEnable){
            listsale.get(position).checkedNum=1;
            viewHolder.tv_shopcart_num.setText(listsale.get(position).checkedNum + "");
            viewHolder.checkbox_buttom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        listsale.get(position).checked=true;
                        onItemChecked.onItemCheckedListener(listsale.get(position));
                    }else{
                        listsale.get(position).checked=false;
                        onItemChecked.onItemCheckedCancelListener(listsale.get(position));
                    }
                }
            });
            viewHolder.btn_shopcart_sub.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(viewHolder.tv_shopcart_num.getText().toString());
                    if (num > 1) {
                        viewHolder.tv_shopcart_num.setText((num-1)+"");
                        listsale.get(position).checkedNum=num-1;
                    }
                }
            });

            viewHolder.btn_shopcart_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(viewHolder.tv_shopcart_num.getText().toString());
                    if (num < listsale.get(position).getMayReturnProductItemNum()) {
                        viewHolder.tv_shopcart_num.setText((num+1)+"");
                        listsale.get(position).checkedNum=num+1;
                    }
                }
            });
        }else{
            listsale.get(position).checkedNum=listsale.get(position).getMayReturnProductItemNum();
            viewHolder.tv_shopcart_num.setText(listsale.get(position).checkedNum + "");
            viewHolder.checkbox_buttom.setChecked(true);
            listsale.get(position).checked=true;
            viewHolder.checkbox_buttom.setEnabled(false);
        }

        if(!TextUtils.isEmpty(listsale.get(position).getProductPicPath())){
            GlideUtil.display(mContext, listsale.get(position).getProductPicPath()).override(200, 200).into(viewHolder.img_shopcart);
        }

        viewHolder.tv_product_name.setText(listsale.get(position).getChineseName());
        viewHolder.tv_shopcart_price.setText(mContext.getString(R.string.money_symbol)+UiUtils.getDoubleForDouble(listsale.get(position).getProductPriceFinal()));
        if (listsale.get(position).getMayReturnProductItemNum() > 0) {
            viewHolder.tv_stumk.setText(mContext.getString(R.string.choose_more) + listsale.get(position).getMayReturnProductItemNum() + mContext.getString(R.string.piece));
            viewHolder.tv_stumk.setVisibility(View.VISIBLE);
        }
    }



    interface OnItemChecked{
        void onItemCheckedListener(InitApplyRefundBean.AfterSalesProductVOs bean);
        void onItemCheckedCancelListener(InitApplyRefundBean.AfterSalesProductVOs bean);
    }

    public void setItemcheckListener(OnItemChecked onItemChecked){
        this.onItemChecked=onItemChecked;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        public CheckBox checkbox_buttom;
        public ImageView img_shopcart;
        public TextView tv_product_name;
        public TextView tv_standard;
        public TextView tv_shopcart_price;
        public Button btn_shopcart_sub;
        public Button btn_shopcart_add;
        public TextView tv_shopcart_num;
        public TextView tv_stumk;

        public ViewHolder(View itemView) {
            super(itemView);
            checkbox_buttom = (CheckBox) itemView.findViewById(R.id.checkbox_buttom);
            img_shopcart = (ImageView) itemView.findViewById(R.id.img_shopcart);
            tv_product_name = (TextView) itemView.findViewById(R.id.tv_product_name);
            tv_standard = (TextView) itemView.findViewById(R.id.tv_standard);
            tv_shopcart_price = (TextView) itemView.findViewById(R.id.tv_shopcart_price);
            tv_shopcart_num = (TextView) itemView.findViewById(R.id.tv_shopcart_num);
            tv_stumk = (TextView) itemView.findViewById(R.id.tv_stumk);
            btn_shopcart_sub = (Button) itemView.findViewById(R.id.btn_shopcart_sub);
            btn_shopcart_add = (Button) itemView.findViewById(R.id.btn_shopcart_add);
        }
    }
}
