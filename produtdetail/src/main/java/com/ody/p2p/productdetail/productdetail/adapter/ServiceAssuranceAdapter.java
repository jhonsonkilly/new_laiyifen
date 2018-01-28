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
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.views.HtmlTextView;
import com.ody.p2p.views.basepopupwindow.ProductBean;

import java.util.List;

/**
 * Created by ody on 2016/6/17.、
 * 服务保障
 */
public class ServiceAssuranceAdapter extends BaseRecyclerViewAdapter {

    public ServiceAssuranceAdapter(Context mcontext, List list) {
        super(mcontext, list);
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder,final int position) {
        final ViewHodler viewHodler = (ViewHodler) holder;
        final List<ProductBean.securityVOList> list = getDatas();
        viewHodler.txt_servicetype.setText(list.get(position).title);//标题

//        viewHodler.txt_servicedeatile.setHtmlText(
//                "<html><head><meta name=\"viewport\" content=\"initial-scale=1,maximum-scale=1,user-scalable=no\"><style>img {max-width:100%}</style></head><body>"+
//                        list.get(position).content+"</body></html>");
        viewHodler.txt_servicedeatile.setHtmlText(list.get(position).content);

        if (!StringUtils.isEmpty(list.get(position).url)) {//图标
            GlideUtil.display(mContext, list.get(position).url + "").override(25, 25).into(viewHodler.img_serviceimg);
        } else {
            viewHodler.img_serviceimg.setImageResource(R.drawable.icon_safrtydefault);
        }
    }

    @Override
    protected ServiceAssuranceAdapter.ViewHodler createViewHold(ViewGroup parent, int viewType) {
        return new ViewHodler(LayoutInflater.from(parent.getContext()).inflate(R.layout.produtdetail_slide_service_item, parent, false));
    }

    class ViewHodler extends BaseRecyclerViewHolder {
        //        LinearLayout dridview;
        ImageView img_serviceimg;
        TextView txt_servicetype;
        HtmlTextView txt_servicedeatile;

        public ViewHodler(View itemView) {
            super(itemView);
            img_serviceimg = (ImageView) itemView.findViewById(R.id.img_serviceimg);
            txt_servicetype = (TextView) itemView.findViewById(R.id.txt_servicetype);
            txt_servicedeatile = (HtmlTextView) itemView.findViewById(R.id.txt_servicedeatile);
        }
    }
}
