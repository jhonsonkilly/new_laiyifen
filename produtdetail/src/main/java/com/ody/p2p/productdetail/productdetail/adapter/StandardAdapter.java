package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.productdetail.productdetail.bean.StandardBean;

import java.util.List;

/**
 * Created by ody on 2016/6/22.
 * <p/>
 * 第二个也卡的规格
 */
public class StandardAdapter extends BaseRecyclerViewAdapter {

    public StandardAdapter(List list, Context mcontex) {
        super(mcontex, list);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, int position) {
        ViewHodler viewHodler = (ViewHodler) holder;
        List<StandardBean.Data> list = (List<StandardBean.Data>) getDatas();
        viewHodler.txt_erjicanshu.setText(list.get(position).getAttrs().get(0).getAttrName().getName() + ":");
        viewHodler.txt_sanjisanshu.setText(list.get(position).getAttrs().get(0).getAttrVal().getValue() + "");
    }

    int layoutView=-1;
    public void setLayoutView(int layoutView){
        this.layoutView=layoutView;
    }

    @Override
    protected StandardAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        if (layoutView!=-1){
            return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(layoutView, null, false));
        }else{
            return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_list_guige_itme, null, false));
        }
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        TextView txt_zhucan;
        TextView txt_erjicanshu;
        TextView txt_sanjisanshu;

        public ViewHodler(View itemView) {
            super(itemView);
            txt_zhucan = (TextView) itemView.findViewById(R.id.txt_zhucan);
            txt_erjicanshu = (TextView) itemView.findViewById(R.id.txt_erjicanshu);
            txt_sanjisanshu = (TextView) itemView.findViewById(R.id.txt_sanjisanshu);
        }
    }

}
