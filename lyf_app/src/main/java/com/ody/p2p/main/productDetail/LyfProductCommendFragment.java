package com.ody.p2p.main.productDetail;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.main.R;
import com.ody.p2p.productdetail.photoamplification.ViewPagerActivity;
import com.ody.p2p.productdetail.productdetail.bean.PicBean;
import com.ody.p2p.productdetail.productdetail.bean.ProductComment;
import com.ody.p2p.productdetail.productdetail.frangment.productappraise.ProductCommendFragment;
import com.ody.p2p.recycleviewutils.RecycleUtils;
import com.ody.p2p.utils.GlideUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pzy on 2016/12/6.
 */
public class LyfProductCommendFragment extends ProductCommendFragment {
    @Override
    public void initView(View view) {
        super.initView(view);
        setTheambg(R.drawable.appraise_bg);
        setCommendThemeResource(R.layout.layout_ratingbar);
        setCommendBtnLayout(R.layout.product_detaile_commend_btn);
        img_notdata.setImageResource(R.drawable.detail_comment_nothing);
        tv_evaluate_notdata.setText(R.string.this_no_evaluate);
        adapter_Ratedadapter.setLayoutView(R.layout.item_product_valuate);
    }

    @Override
    public void clickPhoto(ProductComment.Data.MpcList.ListObj listObj, int postion) {
//        super.clickPhoto(listObj, postion);
        if (null!=listObj&&null!=listObj.getMpShinePicList()&&listObj.getMpShinePicList().size()>0){
            List<PicBean>  picList=new ArrayList<>();
            for (String pc:listObj.getMpShinePicList()){
                PicBean pic=new PicBean();
                pic.setPicurl(pc);
                pic.setListObj(listObj);
                picList.add(pic);
            }
            if (picList.size()>0){
                Intent intent = new Intent(getContext(), ViewPagerActivity.class);
                intent.putExtra("PicBean",(Serializable)picList);
                intent.putExtra("postion", postion);
                intent.putExtra("type", "lyf_valuate_pic");
                startActivity(intent);
            }
        }
    }

    @Override
    public void setData(ProductComment.Data.MpcList response) {
        super.setData(response);
        if (chooseNumber==4&&null != response && null != response.listObj && response.listObj.size() > 0) {
            List<PicBean>  picList=new ArrayList<>();
//            Gson gson=new Gson();
//            try {
//                ProductComment bean=gson.fromJson(text,ProductComment.class);
                if (null!=response&&null!=response.listObj&&response.listObj.size()>0){
                    for (ProductComment.Data.MpcList.ListObj listO:response.listObj){
                        if (null!=listO.getMpShinePicList()&&listO.getMpShinePicList().size()>0){
                            for (String url:listO.getMpShinePicList()){
                                PicBean pic=new PicBean();
                                pic.setPicurl(url);
                                pic.setListObj(listO);
                                picList.add(pic);
                            }
                        }

                    }
                }
//            }catch (Exception e){
//                e.printStackTrace();
//            }
            if (null!=picList&&picList.size()>0){
                pageCount = response.listObj.size();
                recy_goodappriaise.setVisibility(View.VISIBLE);
                ll_havadata.setVisibility(View.VISIBLE);

                PicGirdAdapter adapter=new PicGirdAdapter(getContext(),picList);
                recy_goodappriaise.setLayoutManager(RecycleUtils.getGridLayoutManager(getContext(),3));
                recy_goodappriaise.setAdapter(adapter);
                recy_goodappriaise.setFocusable(false);
                recy_goodappriaise.setFocusableInTouchMode(false);
                recy_goodappriaise.setBackgroundResource(R.color.background_color);
            }else{
                ll_havadata.setVisibility(View.GONE);
                ll_notdata.setVisibility(View.VISIBLE);
            }
        }
    }


    class PicGirdAdapter extends BaseRecyclerViewAdapter<PicBean>{
        public PicGirdAdapter(Context context, List datas) {
            super(context, datas);
        }

        @Override
        protected void showViewHolder(BaseRecyclerViewHolder holder,final int position) {
            ViewHolder vholder=(ViewHolder)holder;
            GlideUtil.display(getContext(),mDatas.get(position).getPicurl()).override(200,200).into(vholder.img_evaluatePic);
            vholder.img_evaluatePic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), ViewPagerActivity.class);
//                    Bundle bundle=new Bundle();
//                    bundle.putSerializable("PicBean",(Serializable)mDatas);
//                    intent.putExtras(bundle);
                    intent.putExtra("PicBean",(Serializable)mDatas);
                    intent.putExtra("postion", position);
                    intent.putExtra("type", "lyf_valuate_pic");
                    startActivity(intent);
                }
            });
        }

        @Override
        protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_evaluate_pic,null));
        }

    }
    class ViewHolder extends BaseRecyclerViewHolder{
        ImageView img_evaluatePic;
        public ViewHolder(View itemView) {
            super(itemView);
            img_evaluatePic=(ImageView)itemView.findViewById(R.id.img_evaluatePic);
        }
    }
}