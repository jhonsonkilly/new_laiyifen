package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.views.flowLayout.FlowRadioLayout;

import java.util.List;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class PropertyAdapter extends RecyclerView.Adapter<PropertyAdapter.ViewHolder> {
    List<PropertyBean.Attributes> mData;
    List<PropertyBean.SerialProducts> serData;
    Context mContext;
    private boolean onBind;
    int btnLyaout = -1;

    public void setBtnLyaout(int btnLyaout) {
        this.btnLyaout = btnLyaout;
        notifyDataSetChanged();
    }

    public PropertyAdapter(Context mContext, List<PropertyBean.Attributes> data, List<PropertyBean.SerialProducts> serData, int BtnLayout) {
        this.mContext = mContext;
        this.mData = data;
        this.serData = serData;
        if (BtnLayout == -1) {
            btnLyaout = R.layout.layout_flowitem;
        } else {
            btnLyaout = BtnLayout;
        }

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_property_adapter, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int postion) {
        onBind = true;
        holder.recy_property_view.removeAllViews();
        final PropertyBean.Attributes attr = mData.get(postion);
        holder.tv_property_name.setText(attr.getName());
        for (int i = 0; i < attr.getValues().size(); i++) {
            final PropertyBean.Values number = attr.getValues().get(i);
            RadioButton radioButton = (RadioButton) LayoutInflater.from(mContext).inflate(btnLyaout, null);
            radioButton.setText(number.getValue());
           // radioButton.setTag(number.getId());
            radioButton.setId(postion * 1000 + i);
            if (number.getChecked()) {
                radioButton.setChecked(true);
            } else {
                radioButton.setChecked(false);
            }
            if (isCanBeCheck(postion, number.getId())) {
                radioButton.setEnabled(true);
            } else {
                radioButton.setEnabled(false);
                radioButton.setChecked(false);
                radioButton.setTextColor(mContext.getResources().getColor(R.color.grey));
                number.setChecked(false);
            }
            radioButton.setTag(new Integer[]{postion, i});
            holder.recy_property_view.setHorizontalSpacing(dip2px(mContext, 12));
            holder.recy_property_view.setVerticalSpacing(dip2px(mContext, 12));
            holder.recy_property_view.addView(radioButton);

            radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    number.setChecked(isChecked);
                }
            });
        }
//        for (final PropertyBean.Values number : attr.getValues()) {
//
//        }
        holder.recy_property_view.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                View view = holder.recy_property_view.findViewById(checkedId);
                if (view == null) {
                    return;
                }
                Integer[] indexs = (Integer[]) view.getTag();
                int position = indexs[0];
                for (int i = 0; i < attr.getValues().size(); i++) {
                    attr.getValues().get(i).setChecked(false);
                }
                attr.getValues().get(indexs[1]).setChecked(true);
//                String imgurl = getiamge(attr.getValues().get(indexs[1]).getId());
                for (int i = position + 1; i < mData.size(); i++) {
                    for (int j = 0; j < mData.get(i).getValues().size(); j++) {
                        mData.get(i).getValues().get(j).setChecked(false);
                    }
                    for (int j = 0; j < mData.get(i).getValues().size(); j++) {
                        if (isCanBeCheck(i, mData.get(i).getValues().get(j).getId())) {
                            mData.get(i).getValues().get(j).setChecked(true);
                            break;
                        } else {
                            mData.get(i).getValues().get(j).setChecked(false);
                        }
                    }
                }
                if (null != mPropertyChange && !onBind) {
                    mPropertyChange.refresh();
                }
            }
        });
        onBind = false;
    }

    private String getSelectStandard() {
        // TODO Auto-generated method stub
        String str = "";
        for (PropertyBean.Attributes sex : mData) {
            for (PropertyBean.Values value : sex.getValues()) {
                if (value.getChecked()) {
                    str = str + "  " + value.getValue();
                }
            }
        }
        return str;
    }

    public boolean isAllChecked() {
        // TODO Auto-generated method stub
        int count = 0;
        for (PropertyBean.Attributes sex : mData) {
            for (PropertyBean.Values value : sex.getValues()) {
                if (value.getChecked()) {
                    count++;
                    break;
                }
            }
        }
        if (mData.size() == count){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 匹配商品状态
     *
     * @param postion
     * @param id
     * @return
     */
    private boolean isCanBeCheck(int postion, String id) {

        for (int i = 0; i < serData.size(); i++) {
            if (serData.get(i).getKey().contains("_" + id + "_")) {
                int count = 0;
                for (int j = postion - 1; j >= 0; j--) {
                    if (serData.get(i).getKey().contains("_" + getPreCheckedId(j) + "_")) {
                        count++;
                    }
                }
                if (count == postion) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getPreCheckedId(int postion) {
        for (int i = 0; i < mData.get(postion).getValues().size(); i++) {
            if (mData.get(postion).getValues().get(i).getChecked()) {
                return mData.get(postion).getValues().get(i).getId();
            }
        }
        return "";
    }

    PropertyChange mPropertyChange;

    public void setPropertyChange(PropertyChange mPropertyChange) {
        this.mPropertyChange = mPropertyChange;
    }

    public interface PropertyChange {
        void refresh();
    }

    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_property_name;
        FlowRadioLayout recy_property_view;

        public ViewHolder(View view) {
            super(view);
            tv_property_name = (TextView) view.findViewById(R.id.tv_property_name);
            recy_property_view = (FlowRadioLayout) view.findViewById(R.id.recy_property_view);
        }
    }
}
