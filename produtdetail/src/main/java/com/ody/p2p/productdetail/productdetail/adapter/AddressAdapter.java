package com.ody.p2p.productdetail.productdetail.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.productdetail.productdetail.bean.UserAdressBean;

import java.util.List;

/**
 * Created by ody on 2016/6/13.
 *
 *
 * 地址
 */
public class AddressAdapter extends BaseRecyclerViewAdapter{
    int checkedPostion = 0;


    public AddressAdapter(List lists, Context context) {
        super(context,lists);
    }


    public AddressAdapter.ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHodler(LayoutInflater.from(mContext).inflate(R.layout.produtdetail_listview_toadress, parent, false));
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {

        ViewHodler viewHodler = (ViewHodler) holder;
        List<UserAdressBean.Data.UsualAddress> list = getDatas();
        viewHodler.textView.setText(list.get(position).getAddressDetail());
        if (checkedPostion == position) {
            viewHodler.img_toadressok.setVisibility(View.VISIBLE);
            viewHodler.textView.setTextColor(mContext.getResources().getColor(R.color.theme_color));
        } else {
            viewHodler.img_toadressok.setVisibility(View.INVISIBLE);
            viewHodler.textView.setTextColor(mContext.getResources().getColor(R.color.sub_title_color));
        }
        viewHodler.layout_adress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                adressback.onclik(position);
            }
        });
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        return null;
    }




    adressBack adressback;

    public adressBack getAdressback() {
        return adressback;
    }

    public void setAdressback(adressBack adressback) {
        this.adressback = adressback;
    }

    public void setCheckedData(int checkedData) {
        this.checkedPostion = checkedData;
    }


    public interface adressBack{

        void onclik(int position);
    }

    @Override
    public int getItemCount() {
        return  getDatas().size();
    }

    public Object getItem(int position) {
        return getDatas().get(position);
    }
    class ViewHodler extends  BaseRecyclerViewHolder{

        View layout_adress;
        ImageView img_toadress;
        ImageView img_toadressok;
        TextView textView;

        public ViewHodler(View itemView) {
            super(itemView);
            layout_adress=itemView.findViewById(R.id.layout_adress);
            textView = (TextView) itemView.findViewById(R.id.text_toadress);
            img_toadress = (ImageView) itemView.findViewById(R.id.img_toadress);
            img_toadressok = (ImageView) itemView.findViewById(R.id.img_toadressok);
        }
    }
}
