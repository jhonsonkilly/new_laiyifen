package com.ody.p2p.shopcart;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyBaseAdapter;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.JumpUtils;

import java.util.List;

/**
 * 拆单
 *
 * @param <T>
 * @author admin
 */
public class DisassebleAdapter<T> extends OdyBaseAdapter<T> {

    private List<SeparateBean.ProductList> allResults;
    private Context ctx;
    private LayoutInflater layoutInflater;
    private DisasseblePopupWindow pop;


    @SuppressWarnings("unchecked")
    public DisassebleAdapter(List<T> allData, Context context,DisasseblePopupWindow pop) {
        super(allData, context);
        // TODO Auto-generated constructor stub
        this.ctx = context;
        this.pop = pop;
        allResults = (List<SeparateBean.ProductList>
                ) allData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub

        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.disasseble_listview_item, null);
            viewHolder = new ViewHolder();
            viewHolder.id_gallery = (LinearLayout) convertView.findViewById(R.id.id_gallery);
            viewHolder.goBtnJieSuan = (Button) convertView.findViewById(R.id.goBtnJieSuan);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        for (int i = 0; i < allResults.get(position).getProductList().size(); i++) {

            View view_one = layoutInflater.inflate(R.layout.activity_index_gallery_item,
                    null);
            ImageView img = (ImageView) view_one
                    .findViewById(R.id.id_index_gallery_item_image);
            GlideUtil.display(ctx,allResults.get(position).getProductList().get(i).getPicUrl()).into(img);
            viewHolder.id_gallery.addView(view_one);
        }

        viewHolder.goBtnJieSuan.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                String mpId = "";
                for (SeparateBean.Product product : allResults.get(position).getProductList()) {
                    mpId += product.getMpId() + ",";
                }
                if (mpId.length() > 0) {
                    mpId = mpId.substring(0, (mpId.length() - 1));
                }
                Bundle bd = new Bundle();
//                bd.putString(Constants.SP_ID, mpId);
//                bd.putInt("oldFlag",1);
                JumpUtils.ToActivity(JumpUtils.CONFIRMORDER, bd);
                pop.dismiss();
            }
        });
        return convertView;
    }

    static class ViewHolder {

        private LinearLayout id_gallery;

        private Button goBtnJieSuan;


    }

}
