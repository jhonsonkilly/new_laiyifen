package com.ody.p2p.check.myorder;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ody.p2p.check.R;
import com.ody.p2p.receiver.ConfirmOrderBean;
import com.ody.p2p.utils.GlideUtil;

import java.util.List;

/**
 * Created by ${tang} on 2016/8/28.
 */
public class ProductChangeDialog extends Dialog {

    private ConfirmOrderBean.DataEntity.Errors error;
    private CallBack callBack;
    private Context context;
    public String screenheight;
    public String screenwidth;

    public ProductChangeDialog(Context context,ConfirmOrderBean.DataEntity.Errors error) {
        super(context);
        init();
        this.context=context;
        this.error=error;
        View view= LayoutInflater.from(context).inflate(R.layout.layout_product_change,null);
        TextView tv_title= (TextView) view.findViewById(R.id.tv_title);
        ListView lv_product= (ListView) view.findViewById(R.id.lv_product);
        LinearLayout ll_bottom= (LinearLayout) view.findViewById(R.id.ll_bottom);
        TextView tv_goback= (TextView) view.findViewById(R.id.tv_goback);
        TextView tv_ignore= (TextView) view.findViewById(R.id.tv_ignore);
        TextView tv_goback_two= (TextView) view.findViewById(R.id.tv_goback_two);
        tv_title.setText(error.message);
        if(error!=null){
            if(error.type==0){
                ll_bottom.setVisibility(View.VISIBLE);
                tv_goback_two.setVisibility(View.GONE);
                lv_product.setVisibility(View.GONE);
                tv_goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.goBacktoShopCar();
                    }
                });
                tv_ignore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.goOnCheck();
                    }
                });
            }else if(error.type==1){
                ll_bottom.setVisibility(View.VISIBLE);
                tv_goback_two.setVisibility(View.GONE);
                if(error.data!=null&&error.data.size()>0){
                    lv_product.setVisibility(View.VISIBLE);
                    ChangeProductAdapter changeProductAdapter=new ChangeProductAdapter();
                    lv_product.setAdapter(changeProductAdapter);
                }else{
                    lv_product.setVisibility(View.GONE);
                }
                tv_goback.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.goBacktoShopCar();
                    }
                });
                tv_ignore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.goOnCheck();
                    }
                });
            }else{
                ll_bottom.setVisibility(View.GONE);
                tv_goback_two.setVisibility(View.VISIBLE);
                lv_product.setVisibility(View.GONE);
                tv_goback_two.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callBack.goBacktoShopCar();
                    }
                });
            }
        }
        setContentView(view);
    }
    private void init() {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay();
        screenheight = d.getHeight() + "";
        screenwidth = d.getWidth() + "";
        WindowManager.LayoutParams p = getWindow().getAttributes();
        double rote = d.getWidth() / 480.0;
        p.width = (int) (480 * rote);
        dialogWindow.setAttributes(p);
        setCanceledOnTouchOutside(false);
    }

    public class ChangeProductAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return error.data.size();
        }

        @Override
        public Object getItem(int position) {
            return error.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView==null){
                convertView=LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_change_dialog,parent,false);
                holder=new ViewHolder();
                holder.iv_product= (ImageView) convertView.findViewById(R.id.iv_product);
                holder.tv_name= (TextView) convertView.findViewById(R.id.tv_name);
                convertView.setTag(holder);
            }else{
                holder= (ViewHolder) convertView.getTag();
            }

            holder.tv_name.setText(error.data.get(position).name);
            if(!TextUtils.isEmpty(error.data.get(position).imgUrl)){
                GlideUtil.display(context,error.data.get(position).imgUrl).override(50,50).into(holder.iv_product);
            }
            return convertView;
        }
    }

    private class ViewHolder{
        private ImageView iv_product;
        private TextView tv_name;
    }

    public void setCallBack(CallBack callBack){
        this.callBack=callBack;
    }

    public interface CallBack{
        void goBacktoShopCar();

        void goOnCheck();
    }

}
