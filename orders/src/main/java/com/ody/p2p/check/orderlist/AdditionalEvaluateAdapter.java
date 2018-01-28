package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ${tang} on 2016/10/19.
 */
public class AdditionalEvaluateAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<AdditionalevaluteBean.UserComment.UserMPCommentListBean> mData;

    public AdditionalEvaluateAdapter(Context context){
        this.context=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_addtional_evaluate,parent,false));
    }

    public List<AdditionalevaluteBean.UserComment.UserMPCommentListBean> getAll(){
        return mData;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder viewHolder= (ViewHolder) holder;
        if(!TextUtils.isEmpty(mData.get(position).mpPicPath)){
            GlideUtil.display(context, mData.get(position).mpPicPath).into(viewHolder.iv_evaluate_img);
        }
        viewHolder.tv_evaluate_name.setText(mData.get(position).mpName);
        viewHolder.rb_evaluate.setRating(mData.get(position).rate);
        if(!TextUtils.isEmpty(mData.get(position).content)){
            viewHolder.tv_eva_content.setText(mData.get(position).content);
        }else{
            viewHolder.tv_eva_content.setVisibility(View.GONE);
        }

        if(mData.get(position).mpShinePicList!=null&&mData.get(position).mpShinePicList.size()>0){
            viewHolder.rv_evaluate_pics.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            viewHolder.rv_evaluate_pics.setLayoutManager(linearLayoutManager);
            viewHolder.rv_evaluate_pics.setAdapter(new ImageAdapter(context,mData.get(position).mpShinePicList));
        }else{
            viewHolder.rv_evaluate_pics.setVisibility(View.GONE);
        }
        viewHolder.et_additional.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                viewHolder.tv_text_length.setText(s.toString().length()+"/200");
                mData.get(position).addtionalcontent=s.toString();
            }
        });
        viewHolder.rb_evaluate.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (rating < 1) {
                    viewHolder.rb_evaluate.setRating(1);
                }
                mData.get(position).rate=viewHolder.rb_evaluate.getRating();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    private class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView iv_evaluate_img;
        private TextView tv_evaluate_name;
        private RatingBar rb_evaluate;
        private TextView tv_eva_content;
        private RecyclerView rv_evaluate_pics;
        private EditText et_additional;
        private TextView tv_text_length;

        public ViewHolder(View itemView) {
            super(itemView);
            iv_evaluate_img= (ImageView) itemView.findViewById(R.id.iv_evaluate_img);
            tv_evaluate_name= (TextView) itemView.findViewById(R.id.tv_evaluate_name);
            tv_eva_content= (TextView) itemView.findViewById(R.id.tv_eva_content);
            rb_evaluate= (RatingBar) itemView.findViewById(R.id.rb_evaluate);
            rv_evaluate_pics= (RecyclerView) itemView.findViewById(R.id.rv_evaluate_pics);
            et_additional= (EditText) itemView.findViewById(R.id.et_additional);
            tv_text_length= (TextView) itemView.findViewById(R.id.tv_text_length);
        }
    }


}
