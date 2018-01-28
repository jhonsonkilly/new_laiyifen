package com.ody.p2p.check.orderlist;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.check.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.flowLayout.FlowView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by tangmeijuan on 16/6/20.
 */
public class EvaluateAdapter extends BaseRecyclerViewAdapter<EvaluateData> {
    private List<ArrayList<PhotoFileBean>> mPhotoPath;
    //    public EvaluateData[] mEvaluateData;
    public List<EvaluateData> mEvaluateData;
    public int rb_style = -1;

    public EvaluateAdapter(Context context, List<EvaluateData> datas, List<ArrayList<PhotoFileBean>> path) {
        super(context, datas);
        mPhotoPath = path;
        mEvaluateData = new ArrayList<>();
        for (int i = 0; i < path.size(); i++) {
            EvaluateData dat = new EvaluateData();
            mEvaluateData.add(dat);
        }
    }

    public List<EvaluateData> getEvaluateData() {
        return mEvaluateData;
    }

    public List<ArrayList<PhotoFileBean>> getmPhotoPath() {
        return mPhotoPath;
    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holder, final int position) {
        if (holder instanceof EvaluateHolder) {
            final EvaluateHolder hold = (EvaluateHolder) holder;
            int count = 0;
            if (null != mPhotoPath && mPhotoPath.size() > position && null != mPhotoPath.get(position) && mPhotoPath.get(position).size() > 0) {
                count = mPhotoPath.get(position).size();
            }
            final int num = count;
            EvaluateData bean = mDatas.get(position);
//            final EvaluateData mEvaluate = new EvaluateData();

            GlideUtil.display(mContext, bean.getMpPicPath()).into(hold.iv_content);
            hold.tv_name.setText(bean.getMpName());
            if (rb_style != -1) {
                View vi = LayoutInflater.from(mContext).inflate(rb_style, null);
                hold.linear_ratingbar.addView(vi);
                hold.rb = (RatingBar) vi;
//                hold.rb.setProgressDrawable(ld);
//                hold.rb.setProgressDrawable(mContext.getResources().getDrawable(rb_style));
            } else {
                hold.rb.setVisibility(View.VISIBLE);
            }
//            hold.rb.setRating(1);
            hold.lay_flow.removeAllViews();
            View lay = (View) hold.iv_camera.getParent();
            ImageView iv_clear = (ImageView) lay.findViewById(R.id.iv_evaluate_clear);
            iv_clear.setVisibility(View.GONE);
            hold.lay_flow.addView(lay, 0);

            hold.iv_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        if (num < 5) {
                            mListener.clickCamera(position);
                        } else {
                            ToastUtils.showStr(mContext.getString(R.string.max_add_five_photos));
                        }
                    }
                }
            });
            if (mPhotoPath != null && mPhotoPath.size() > position && mPhotoPath.get(position) != null && mPhotoPath.get(position).size() > 0) {
                int size = mPhotoPath.get(position).size();
                for (int i = 0; i < size; i++) {
                    show(hold.lay_flow, position, i);
                }

                List<String> list = new ArrayList<>();
                for (int i = 0; i < mPhotoPath.get(position).size(); i++) {
                    list.add(mPhotoPath.get(position).get(i).getInternetPath());
                }
                mEvaluateData.get(position).setMpcPicList(list);
            }

            mEvaluateData.get(position).setMpId(bean.getMpId());
            mEvaluateData.get(position).setMpName(bean.getMpName());
            mEvaluateData.get(position).setOrderCode(bean.getOrderCode());
            mEvaluateData.get(position).setOrderId(bean.getOrderId());
            mEvaluateData.get(position).setRate((int) hold.rb.getRating());

            hold.et_input.setText(mEvaluateData.get(position).getContent());
            hold.rb.setRating(mEvaluateData.get(position).getRate());

            hold.et_input.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (!StringUtils.isEmpty(hold.et_input.getText().toString())) {
                        mEvaluateData.get(position).setContent(hold.et_input.getText().toString());
                    }
                }
            });
            hold.rb.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (rating < 1) {
                        hold.rb.setRating(1);
                    }
                    mEvaluateData.get(position).setRate((int) hold.rb.getRating());
                }
            });
        }

    }

//    @Override
//    public void onBindViewHolder(BaseRecyclerViewHolder holder, int position) {
//        super.onBindViewHolder(holder, position);
////        if (holder instanceof EvaluateHolder) {
////            final EvaluateHolder hold = (EvaluateHolder) holder;
////            final int count = mPhotoPath.get(position).size();
////            hold.lay_flow.removeAllViews();
////            hold.lay_flow.addView((View) hold.iv_camera.getParent(), 0);
////            hold.iv_camera.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    if (mListener != null) {
////                        if(count < 5) {
////                            mListener.clickCamera(position);
////                        }
////                        else {
////                            ToastUtils.showStr("最多添加5张图片");
////                        }
////                    }
////                }
////            });
////            if (mPhotoPath != null && mPhotoPath.size() > position && mPhotoPath.get(position) != null && mPhotoPath.get(position).size() > 0) {
////                int size = mPhotoPath.get(position).size();
////                for (int i = 0; i < size; i ++) {
////                    show(hold.lay_flow, position, i);
////                }
////            }
////
////            final EvaluateData mEvaluate = new EvaluateData();
////            EvaluateData bean = mDatas.get(position);
////            mEvaluate.setMpId(bean.getMpId());
////            mEvaluate.setMpName(bean.getMpName());
////            mEvaluate.setOrderCode(bean.getOrderCode());
////            mEvaluate.setOrderId(bean.getOrderId());
////            hold.et_input.addTextChangedListener(new TextWatcher() {
////                @Override
////                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
////
////                }
////
////                @Override
////                public void onTextChanged(CharSequence s, int start, int before, int count) {
////                    if (!StringUtils.isEmpty(hold.et_input.getText().toString())) {
////                        mEvaluate.setContent(hold.et_input.getText().toString());
////                    }
////                }
////
////                @Override
////                public void afterTextChanged(Editable s) {
////
////                }
////            });
////            mEvaluate.setRate(hold.rb.getNumStars());
////            List<String> list = new ArrayList<>();
////            for (int i = 0; i < mPhotoPath.get(position).size(); i++) {
////                list.add(mPhotoPath.get(position).get(i).getFilePath());
////            }
////            mEvaluate.setMpcPicList(list);
////            mEvaluateData.add(mEvaluate);
////
////        }
//
//    }

    private void show(final ViewGroup parent, final int position, final int i) {
        final RelativeLayout lay = (RelativeLayout) mInflater.inflate(R.layout.item_iv, parent, false);
        final ImageView iv = (ImageView) lay.findViewById(R.id.iv_evaluate_camera);
        iv.setTag(R.id.image_tag, new File(mPhotoPath.get(position).get(i).getFilePath()).getName());
        GlideUtil.display(mContext, mPhotoPath.get(position).get(i).getFilePath()).into(iv);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    mListener.showBitmap(position, mPhotoPath.get(position));
                }
            }
        });
        ImageView iv_clear = (ImageView) lay.findViewById(R.id.iv_evaluate_clear);
        iv_clear.setVisibility(View.VISIBLE);

        iv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "iv clear -- >");
                delete(position, (String) iv.getTag(R.id.image_tag));
                parent.removeView(lay);
            }
        });
        parent.addView(lay, 0);
    }

    private void delete(int position, String tag) {
        for (int i = 0; i < mPhotoPath.get(position).size(); i++) {
            Log.e("TAG", "tag --- >" + tag);
            if (new File(mPhotoPath.get(position).get(i).getFilePath()).getName().equals(tag)) {
                mPhotoPath.get(position).remove(i);
            }
        }

    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_evaluate_content, parent, false);
        return new EvaluateHolder(view);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class EvaluateHolder extends BaseRecyclerViewHolder {
        TextView tv_name;
        ImageView iv_content;
        RatingBar rb;
        EditText et_input;
        FlowView lay_flow;
        RelativeLayout linear_ratingbar;
        ImageView iv_camera;

        public EvaluateHolder(View itemView) {
            super(itemView);
            linear_ratingbar = (RelativeLayout) itemView.findViewById(R.id.linear_ratingbar);
            iv_content = (ImageView) itemView.findViewById(R.id.iv_evaluate_img);
            tv_name = (TextView) itemView.findViewById(R.id.tv_evaluate_name);
            rb = (RatingBar) itemView.findViewById(R.id.rb_evaluate);
            et_input = (EditText) itemView.findViewById(R.id.et_evaluate_input);

            lay_flow = (FlowView) itemView.findViewById(R.id.lay_evaluate_flow);
            View view = mInflater.inflate(R.layout.item_iv, lay_flow, false);
            iv_camera = (ImageView) view.findViewById(R.id.iv_evaluate_camera);
            iv_camera.setImageResource(R.drawable.xiangji);
            lay_flow.addView(view, 0);
        }
    }

    public interface OnEvaluateAdapterListener {
        void clickCamera(int position);

        void showBitmap(int position, ArrayList<PhotoFileBean> list);
    }

    private OnEvaluateAdapterListener mListener;

    public void setOnEvaluateListener(OnEvaluateAdapterListener Listener) {
        this.mListener = Listener;
    }
}
