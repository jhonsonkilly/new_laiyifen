package com.ody.p2p.productdetail.productrecommend;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.produtdetail.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016-6-19.
 */
public class OtherAppiresAdapter extends BaseAdapter {
    Context mcontext;
    ArrayList<String> list;
    public OtherAppiresAdapter(Activity mcontext, ArrayList<String> list) {
        this.mcontext=mcontext;
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHodler hodler = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_appires_other_item, null);
            hodler = new ViewHodler();
            hodler.txt_produttitle = (TextView) convertView.findViewById(R.id.txt_produttitle);
            hodler.txt_produtprice = (TextView) convertView.findViewById(R.id.txt_produtprice);
            hodler.img_produtimg = (ImageView) convertView.findViewById(R.id.img_produtimg);
            convertView.setTag(hodler);
        } else {
            hodler = (ViewHodler) convertView.getTag();
        }
        hodler.img_produtimg.setImageDrawable(mcontext.getResources().getDrawable(R.drawable.ico_collection));
        hodler.txt_produttitle.setText(mcontext.getResources().getString(R.string.produttitle));
        hodler.txt_produtprice.setText(mcontext.getResources().getString(R.string.price));
        return convertView;

    }
    class ViewHodler {
        ImageView img_produtimg;
        TextView txt_produtprice;
        TextView txt_produttitle;
    }
}

