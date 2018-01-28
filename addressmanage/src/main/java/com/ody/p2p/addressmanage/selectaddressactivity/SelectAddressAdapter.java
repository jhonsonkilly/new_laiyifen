package com.ody.p2p.addressmanage.selectaddressactivity;

import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.Constants;
import com.ody.p2p.addressmanage.BuildConfig;
import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.OdyApplication;

import java.util.List;

/**
 * Created by ody on 2016/6/17.
 */
public class SelectAddressAdapter extends BaseRecyclerViewAdapter<AddressBean.Address> {

    private Context mContext;
    private List<AddressBean.Address> datas;
    private AddressBean.Address addressDefault;
    private String selected_addressId;
    private int mDefaultColorId = 0;
    private int mSelectAddressResId = 0;
    private int mRadioBtnBackResId = 0;

    public SelectAddressAdapter(Context context, List<AddressBean.Address> list) {
        super(context, list);
        mContext = context;
        datas = list;
        findDefault(datas);
        selected_addressId = OdyApplication.getValueByKey(Constants.ADDRESS_ID, "");
    }


//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public AddressBean.Address getItem(int position) {
//        return datas.get(position);
//    }


//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    @Override
    protected void showViewHolder(BaseRecyclerViewHolder holders, final int position) {
        AddressBean.Address bean = datas.get(position);

        SpannableStringBuilder stringBuilder;
        final int[] lay_width = new int[1];
        final ViewHolder holder = (ViewHolder) holders;
        holder.lay_first.post(new Runnable() {
            @Override
            public void run() {
                lay_width[0] = holder.lay_first.getWidth();
                holder.tv_name.setMaxWidth(lay_width[0] - dip2px(mContext, 120));
            }
        });

        if (mRadioBtnBackResId != 0) {
            holder.btn_default.setButtonDrawable(mRadioBtnBackResId);
        }

        //设置选中地址
        if (selected_addressId.equals(bean.getId())) {
            holder.btn_default.setChecked(true);
        } else {
            holder.btn_default.setChecked(false);
        }

        if(OdyApplication.OVERSEA==1){
            holder.tv_certification.setVisibility(View.VISIBLE);
            if(!TextUtils.isEmpty(bean.getIdentityCardNumber())){
                holder.tv_certification.setText(R.string.already_authentication);
                holder.tv_certification.setTextColor(mContext.getResources().getColor(R.color.scan_border));
                holder.tv_certification.setBackgroundResource(R.drawable.shape_green_stroke);
            }else {
                holder.tv_certification.setText(R.string.unalready_authentication);
                holder.tv_certification.setTextColor(mContext.getResources().getColor(R.color.theme_color));
                holder.tv_certification.setBackgroundResource(R.drawable.shape_red_stroke);
            }
        }else{
            holder.tv_certification.setVisibility(View.GONE);
        }

        if (bean.getDefaultIs() == 1) {
            //如果没有选择地址，把默认地址选中
            if (selected_addressId.equals("-1")) {
                holder.btn_default.setChecked(true);
            }
            stringBuilder = new SpannableStringBuilder(mContext.getString(R.string.addressmanage_default_address));
            stringBuilder.append(bean.getProvinceName() + bean.getCityName() + bean.getRegionName() + bean.getDetailAddress());
            ForegroundColorSpan span;
            if (mDefaultColorId == 0) {
                span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.theme_color));
            } else {
                span = new ForegroundColorSpan(mContext.getResources().getColor(mDefaultColorId));
            }
            stringBuilder.setSpan(span, 0, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } else {
            stringBuilder = new SpannableStringBuilder();
            stringBuilder.append(bean.getProvinceName() + bean.getCityName() + bean.getRegionName() + bean.getDetailAddress());
        }

        holder.lay_rv_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });

        holder.tv_name.setText(bean.getUserName());
        holder.tv_telNum.setText(bean.getMobile());
        holder.tv_address.setText(stringBuilder);

    }

    public void setmRadioBtnBackResId(int resId) {
        mRadioBtnBackResId = resId;
    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.addressmanage_item_select_address, null);
        SelectAddressAdapter.ViewHolder holder = new ViewHolder(view);
        if (mSelectAddressResId != 0) {
            holder.btn_default.setButtonDrawable(mSelectAddressResId);
        }
        return holder;
    }

    public void setDefaultAddressColor(int colorId) {
        mDefaultColorId = colorId;
    }

    public void setSelectAddressCheckBoxResId(int resId) {
        this.mSelectAddressResId = resId;
    }
    //    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder = null;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.addressmanage_item_select_address, null);
//            holder = new ViewHolder();
//            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_select_name);
//            holder.tv_telNum = (TextView) convertView.findViewById(R.id.tv_select_telNum);
//            holder.btn_default = (RadioButton) convertView.findViewById(R.id.btn_select_default);
//            holder.tv_address = (TextView) convertView.findViewById(R.id.tv_select_address);
//            holder.lay_first = (LinearLayout) convertView.findViewById(R.id.lay_first);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        AddressBean.Address bean = datas.get(position);
//
//        SpannableStringBuilder stringBuilder ;
//        final int[] lay_width = new int[1];
//        final ViewHolder finalHolder = holder;
//        holder.lay_first.post(new Runnable() {
//            @Override
//            public void run() {
//                lay_width[0] = finalHolder.lay_first.getWidth();
//                finalHolder.tv_name.setMaxWidth(lay_width[0] - dip2px(mContext,120));
//            }
//        });
//
//        //设置选中地址
//        if(selected_addressId == bean.getId()){
//            holder.btn_default.setChecked(true);
//
//        }else {
//            holder.btn_default.setChecked(false);
//        }
//
//        if (bean.getDefaultIs() == 1) {
//            //如果没有选择地址，把默认地址选中
//            if(selected_addressId == -1){
//                holder.btn_default.setChecked(true);
//            }
//            stringBuilder = new SpannableStringBuilder(mContext.getString(R.string.addressmanage_default_address));
//            stringBuilder.append(bean.getProvinceName() + bean.getCityName() + bean.getRegionName() + bean.getDetailAddress());
//            ForegroundColorSpan span = new ForegroundColorSpan(mContext.getResources().getColor(R.color.red));
//            stringBuilder.setSpan(span, 0, 6, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
//        } else {
//            stringBuilder = new SpannableStringBuilder();
//            stringBuilder.append(bean.getProvinceName() + bean.getCityName() + bean.getRegionName() + bean.getDetailAddress());
//        }
//
//
//        holder.tv_name.setText(bean.getUserName());
//        holder.tv_telNum.setText(bean.getMobile());
//        holder.tv_address.setText(stringBuilder);
//
//        return convertView;
//    }

    class ViewHolder extends BaseRecyclerViewHolder {
        TextView tv_address;
        TextView tv_name;
        TextView tv_telNum;
        RadioButton btn_default;
        LinearLayout lay_first;
        LinearLayout lay_rv_item;
        TextView tv_certification;

        public ViewHolder(final View itemView) {
            super(itemView);
            tv_name = (TextView) itemView.findViewById(R.id.tv_select_name);
            tv_telNum = (TextView) itemView.findViewById(R.id.tv_select_telNum);
            btn_default = (RadioButton) itemView.findViewById(R.id.btn_select_default);
            tv_address = (TextView) itemView.findViewById(R.id.tv_select_address);
            lay_first = (LinearLayout) itemView.findViewById(R.id.lay_first);
            lay_rv_item = (LinearLayout) itemView.findViewById(R.id.lay_rv_item);
            tv_certification= (TextView) itemView.findViewById(R.id.tv_certification);

        }
    }

    public interface onItemClickListener {
        void onItemClick(int position);
    }

    private onItemClickListener itemClickListener;

    public void setOnItemClickListener(onItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setDatas(List<AddressBean.Address> list) {
        datas = list;
        findDefault(datas);
        this.notifyDataSetChanged();
    }

    private void findDefault(List<AddressBean.Address> list) {
        int n = list.size();
        for (int i = 0; i < n; i++) {
            if (list.get(i).getDefaultIs() == 1) {
                addressDefault = list.get(i);
                list.remove(i);
                list.add(0, addressDefault);
                return;
            }
        }
    }

    public int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
