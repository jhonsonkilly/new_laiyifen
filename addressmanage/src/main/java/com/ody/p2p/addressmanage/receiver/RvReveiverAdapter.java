package com.ody.p2p.addressmanage.receiver;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.ody.p2p.addressmanage.R;
import com.ody.p2p.addressmanage.bean.AddressBean;
import com.ody.p2p.base.BaseRecyclerViewAdapter;
import com.ody.p2p.base.BaseRecyclerViewHolder;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.utils.JumpUtils;
import com.ody.p2p.views.ProgressDialog.CustomDialog;

import java.util.List;


/**
 * Created by ody on 2016/6/13.
 */
public class RvReveiverAdapter extends BaseRecyclerViewAdapter<AddressBean.Address> {

    private Context mContext;
    public CustomDialog dialog;
    private ReceiverPresenterImp presenterImp;
    private ReceiverView mView;
    private AddressBean.Address addressDefault;
    private int radioBtnbackResId = 0;
    private int radioBtntextColorId = 0;


    public RvReveiverAdapter(Context context, List<AddressBean.Address> list, ReceiverPresenterImp presenterImp, ReceiverView view) {
        super(context, list);
        mContext = context;
        this.presenterImp = presenterImp;
        this.mView = view;
        findDefault(mDatas);
        dialog = new CustomDialog(mContext, mContext.getString(R.string.addressmanage_delete_address));

    }


//    @Override
//    public int getCount() {
//        return datas.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return datas.get(position);
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//
//    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    protected void showViewHolder(final BaseRecyclerViewHolder holders, final int position) {
        final MyViewHolder holder = (MyViewHolder) holders;
        final AddressBean.Address bean = (AddressBean.Address) mDatas.get(position);

        final int[] lay_width = new int[1];
        holder.lay_all.post(new Runnable() {
            @Override
            public void run() {
                lay_width[0] = holder.lay_all.getWidth();
                holder.tv_name.setMaxWidth(lay_width[0] - dip2px(mContext, 120));
            }
        });

//        if(position ==0){
        holder.view_first.setVisibility(View.VISIBLE);
//        }else {
//            holder.view_first.setVisibility(View.GONE);
//        }


        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle bundle = new Bundle();
                bundle.putSerializable("bean", bean);
                bundle.putInt("isEdit", 1);
//                ActivityRoute activityRoute = (ActivityRoute) Router.getRoute("activity://editaddress");
//                activityRoute.addExtras(bundle);
//                activityRoute.open();
                JumpUtils.ToActivity(JumpUtils.EDIT_ADDRESS, bundle);
            }
        });
        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
                    @Override
                    public void Confirm() {
                        presenterImp.deleteAddressByNet(bean.getDefaultIs(), bean.getId() + "");
                    }
                });
                dialog.show();
            }
        });


        holder.tv_address_detail.setText(bean.getProvinceName() + bean.getCityName() + bean.getRegionName() + bean.getDetailAddress());
        holder.tv_name.setText(bean.getUserName());
        holder.tv_telNum.setText(bean.getMobile());
        holder.btn_default.setChecked(false);
//        if(radioBtntextColorId ==0 ) {
        holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.main_title_color));
//        }else {
//            holder.btn_default.setTextColor(mContext.getResources().getColor(radioBtntextColorId));
//        }
        if (radioBtnbackResId != 0) {
            holder.btn_default.setButtonDrawable(radioBtnbackResId);
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
            holder.btn_default.setChecked(true);
//            holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.red));
            holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.theme_color));
//            if (radioBtntextColorId == 0) {
//                holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.theme_color));
//            } else {
//                holder.btn_default.setTextColor(mContext.getResources().getColor(radioBtntextColorId));
//            }
        }

        holder.btn_default.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bean.getDefaultIs() == 1) {
                    return;
                }
                presenterImp.setDefaultAddressByNet(bean.getId() + "", bean.getUserName(), bean.getProvinceCode() + "", bean.getCityId() + "", bean.getRegionId() + "", bean.getDetailAddress(), bean.getMobile(), 1);
            }
        });
    }

    public void setRadioBtn(int backResId, int textSelctedColorResId) {
        radioBtnbackResId = backResId;
        radioBtntextColorId = textSelctedColorResId;

    }

    @Override
    protected BaseRecyclerViewHolder createViewHold(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        View view = mInflater.inflate(R.layout.addressmanage_item_manage_address, parent, false);
        viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //    @Override
//    public View getView(final int position, View convertView, ViewGroup parent) {
//        final ViewHolder holder;
//        if (convertView == null) {
//            convertView = LayoutInflater.from(mContext).inflate(R.layout.addressmanage_item_manage_address, null);
//            holder = new ViewHolder();
//            holder.tv_edit = (TextView) convertView.findViewById(R.id.tv_address_edit);
//            holder.tv_delete = (TextView) convertView.findViewById(R.id.tv_address_delete);
//            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_user_name);
//            holder.tv_telNum = (TextView) convertView.findViewById(R.id.tv_user_telNum);
//            holder.tv_address_detail = (TextView) convertView.findViewById(R.id.tv_address_detail);
//            holder.btn_default = (RadioButton) convertView.findViewById(R.id.radio_btn_default);
//            holder.lay_all = (LinearLayout) convertView.findViewById(R.id.lay_address_all);
//            holder.view_first = convertView.findViewById(R.id.view_first);
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//        final AddressBean.Address[] bean = {(AddressBean.Address) getItem(position)};
//
//        final int[] lay_width = new int[1];
//        holder.lay_all.post(new Runnable() {
//            @Override
//            public void run() {
//                lay_width[0] = holder.lay_all.getWidth();
//                holder.tv_name.setMaxWidth(lay_width[0] - dip2px(mContext,120));
//            }
//        });
//
//        if(position ==0){
//            holder.view_first.setVisibility(View.VISIBLE);
//        }else {
//            holder.view_first.setVisibility(View.GONE);
//        }
//
//
//
//        holder.tv_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                AddressBean.Address bean = (AddressBean.Address) getItem(position);
//                Intent in = new Intent(mContext, EditAddressActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("bean", bean);
//                bundle.putInt("isEdit", 1);
//                in.putExtras(bundle);
//                mContext.startActivity(in);
//            }
//        });
//        holder.tv_delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                View view = LayoutInflater.from(mContext).inflate(R.layout.addressmanage_dialog_item, null);
////                TextView tv_cancel = (TextView) view.findViewById(R.id.tv_dialog_cancel);
////                TextView tv_positive = (TextView) view.findViewById(R.id.tv_dialog_positive);
////
////                tv_cancel.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        dialog.dismiss();
////                    }
////                });
////                tv_positive.setOnClickListener(new View.OnClickListener() {
////                    @Override
////                    public void onClick(View v) {
////                        presenterImp.deleteAddressByNet(bean[0].getDefaultIs(), bean[0].getId());
////                        dialog.dismiss();
////                    }
////                });
////
////                dialog = new AlertDialog.Builder(mContext, R.style.CircleDialog)
////                        .setView(view)
////                        .show();
//                dialog = new CustomDialog(mContext,mContext.getString(R.string.addressmanage_delete_address));
//                dialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
//                    @Override
//                    public void Confirm() {
//                        presenterImp.deleteAddressByNet(bean[0].getDefaultIs(), bean[0].getId());
//                    }
//                });
//                dialog.show();
//            }
//        });
//
//
//        holder.tv_address_detail.setText(bean[0].getProvinceName() + bean[0].getCityName() + bean[0].getRegionName() + bean[0].getDetailAddress());
//        holder.tv_name.setText(bean[0].getUserName());
//        holder.tv_telNum.setText(bean[0].getMobile());
//        holder.btn_default.setChecked(false);
//        holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.textColor3));
//
//        if (bean[0].getDefaultIs() == 1) {
//            holder.btn_default.setChecked(true);
//            holder.btn_default.setTextColor(mContext.getResources().getColor(R.color.red));
//        }
//
//        holder.btn_default.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(bean[0].getDefaultIs() == 1){
//                    return;
//                }
//
//                presenterImp.setDefaultAddressByNet(bean[0].getId(), bean[0].getUserName(), bean[0].getProvinceCode()+"", bean[0].getProvinceCode()+"", bean[0].getCityId()+"", bean[0].getRegionId()+"", bean[0].getDetailAddress(), bean[0].getMobile(),1);
//            }
//        });
//
//        return convertView;
//    }
//
//    class ViewHolder {
//        TextView tv_edit;
//        TextView tv_delete;
//        TextView tv_name;
//        TextView tv_telNum;
//        TextView tv_address_detail;
//        RadioButton btn_default;
//        LinearLayout lay_all;
//        View view_first;
//    }

    public void setDatas(List<AddressBean.Address> list) {
        mDatas.clear();
        mDatas.addAll(list);
        findDefault(mDatas);
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

    class MyViewHolder extends BaseRecyclerViewHolder {
        TextView tv_edit;
        TextView tv_delete;
        TextView tv_name;
        TextView tv_telNum;
        TextView tv_address_detail;
        RadioButton btn_default;
        LinearLayout lay_all;
        View view_first;
        TextView tv_certification;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_address_edit);
            tv_delete = (TextView) itemView.findViewById(R.id.tv_address_delete);
            tv_name = (TextView) itemView.findViewById(R.id.tv_user_name);
            tv_telNum = (TextView) itemView.findViewById(R.id.tv_user_telNum);
            tv_address_detail = (TextView) itemView.findViewById(R.id.tv_address_detail);
            btn_default = (RadioButton) itemView.findViewById(R.id.radio_btn_default);
            lay_all = (LinearLayout) itemView.findViewById(R.id.lay_address_all);
            view_first = itemView.findViewById(R.id.view_first);
            tv_certification= (TextView) itemView.findViewById(R.id.tv_certification);

        }

    }


}
