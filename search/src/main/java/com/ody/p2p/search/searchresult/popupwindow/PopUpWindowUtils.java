package com.ody.p2p.search.searchresult.popupwindow;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ody.p2p.search.R;
import com.ody.p2p.utils.PxUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.recyclerviewlayoutmanager.FullyGridLayoutManager;
import com.ody.p2p.views.recyclerviewlayoutmanager.FullyLinearLayoutManager;

import java.util.List;


/**
 * Created by lxs on 2016/6/8.
 */
public class PopUpWindowUtils {

    private PopupWindow brandPopupwindow;   //品牌弹出框
    private PopupWindow multiplePopupwindow;//排序弹出款
    private PopupWindow fitlerPopupwindow;  //筛选弹出框

    private Context context;

    public PopUpWindowUtils(Context context) {
        this.context = context;
    }


    public void showBrandPop(final View view, List<ClassBean> brandList) {
        ViewGroup mPopKeyView = null;
        RecyclerView gv_brand = null;
        TextView tv_sure = null;
        TextView tv_reset = null;
        if (brandPopupwindow == null) {
            brandPopupwindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPopKeyView = (ViewGroup) inflater.inflate(R.layout.pop_brand, null);
            tv_sure = (TextView) mPopKeyView.findViewById(R.id.tv_sure);
            tv_reset = (TextView) mPopKeyView.findViewById(R.id.tv_reset);
            gv_brand = (RecyclerView) mPopKeyView.findViewById(R.id.gv_brand);
            gv_brand.setLayoutManager(new FullyGridLayoutManager(context, 3));
            brandPopupwindow.setContentView(mPopKeyView);
        }
        BrandAdapter adapter = new BrandAdapter(brandList, context);
        gv_brand.setAdapter(adapter);
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(view.getWidth() - 4, 30);
        params.setMargins(view.getLeft() + 2, -15, 0, 0);
        imageView.setLayoutParams(params);
        imageView.setBackgroundColor(context.getResources().getColor(R.color.white));
        if (mPopKeyView != null) {
            mPopKeyView.addView(imageView);
        }
        brandPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (view.isSelected()) {
                    view.setSelected(false);
                }
            }
        });
        mPopKeyView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismissPop();
            }
        });
        brandPopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        brandPopupwindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        brandPopupwindow.setFocusable(false);
        brandPopupwindow.setOutsideTouchable(true);
        brandPopupwindow.showAsDropDown(view, 0, -15);
    }


    public void showMultiplePop(final View view, final List<ResultBean.SortBean> multipleList, int res, ColorStateList color) {
        if (multiplePopupwindow == null) {
            multiplePopupwindow = new PopupWindow();
            //view.setSelected(true);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View mPopView = inflater.inflate(R.layout.pop_multiple, null);
            View view_line = mPopView.findViewById(R.id.view_line);
            RecyclerView lv_multiple = (RecyclerView) mPopView.findViewById(R.id.lv_multiple);
            MultipleAdapter adapter = new MultipleAdapter(multipleList, context, res, color);
            mPopView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismissPop();
                }
            });
            multiplePopupwindow.setContentView(mPopView);
            lv_multiple.setLayoutManager(new FullyLinearLayoutManager(context));
            lv_multiple.setAdapter(adapter);
            adapter.setItemClick(new MultipleAdapter.ItemClick() {
                @Override
                public void click(int position) {
                    ((TextView) view).setText(multipleList.get(position).sortTypeShort);
                    view.setSelected(true);
                    //((RadioButton)view).setChecked(false);
                    listener.setMutiple(multipleList.get(position).sortTypeCode);
                }
            });
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view_line.getLayoutParams();
            layoutParams.topMargin = view.getBottom();
            view_line.setLayoutParams(layoutParams);
        }
        multiplePopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        multiplePopupwindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        multiplePopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                boolean isChoosed = false;
                for (int i = 0; i < multipleList.size(); i++) {
                    if (multipleList.get(i).isSelected){
                        isChoosed = true;
                        break;
                    }
                }
                //((RadioButton)view).setChecked(false);
                view.setSelected(isChoosed);
                ((RadioButton)view).setChecked(false);
            }
        });
        multiplePopupwindow.setFocusable(true);
        multiplePopupwindow.setOutsideTouchable(true);
        multiplePopupwindow.showAsDropDown(view, 0, -view.getBottom());
    }

    ListView recycler_filter = null;
    View mPopView = null;
    TextView tv_sure = null, tv_reset = null;
    EditText et_least_cost = null, et_most_cost = null;

    public void showFilterPop(final View view, final List<ResultBean.AttributeResult> attributeResult,String priceAngel) {
        if (fitlerPopupwindow == null) {
            fitlerPopupwindow = new PopupWindow();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            mPopView = (View) inflater.inflate(R.layout.layout_filter, null);
            recycler_filter = (ListView) mPopView.findViewById(R.id.recycler_filter);
            tv_sure = (TextView) mPopView.findViewById(R.id.tv_sure);
            tv_reset = (TextView) mPopView.findViewById(R.id.tv_reset);
            et_least_cost = (EditText) mPopView.findViewById(R.id.et_least_cost);
            et_most_cost = (EditText) mPopView.findViewById(R.id.et_most_cost);
            fitlerPopupwindow.setContentView(mPopView);
        }
        for (int i =0 ;i<attributeResult.size();i++){
            attributeResult.get(i).filterOpenFlag = false;
        }
        if (priceAngel != null && priceAngel.contains(",")){
            String [] priceArr = priceAngel.split(",");
            if (priceArr.length == 2){
                if (priceArr[0].length() > 0){
                    et_least_cost.setText(priceArr[0]);
                }
                if (priceArr[1].length() > 0){
                    et_most_cost.setText(priceArr[1]);
                }
            }
        }
        final FilterListAdapter adapter = new FilterListAdapter(context, attributeResult);
        recycler_filter.setAdapter(adapter);
        setListHeight(recycler_filter);
        final ListView finalRecycler_filter = recycler_filter;
        adapter.setHeightListener(new FilterListAdapter.InitHeightListener() {
            @Override
            public void setHeight() {
                setListHeight(finalRecycler_filter);
            }
        });
        mPopView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fitlerPopupwindow.dismiss();
            }
        });
        mPopView.setFocusable(true);
        mPopView.setFocusableInTouchMode(true);
        mPopView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dismissPop();
                    return true;
                }
                return false;
            }
        });
        fitlerPopupwindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        fitlerPopupwindow.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
        fitlerPopupwindow.setAnimationStyle(R.style.AnimationRightFade);
        fitlerPopupwindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                view.setSelected(false);
                if (filterDismissListener != null) {
                    filterDismissListener.dismiss();
                }
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (filterSureListener != null) {
                    if (!TextUtils.isEmpty(et_least_cost.getText()) && !TextUtils.isEmpty(et_most_cost.getText())) {
                        if (Double.parseDouble(et_most_cost.getText().toString()) < Double.parseDouble(et_least_cost.getText().toString())) {
                            filterSureListener.setFilter(et_most_cost.getText().toString() + "," + et_least_cost.getText().toString());
                        } else {
                            filterSureListener.setFilter(et_least_cost.getText().toString() + "," + et_most_cost.getText().toString());
                        }
                    }else{
                        filterSureListener.setFilter(null);
                    }
                }
            }
        });
        tv_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_least_cost.setText("");
                et_most_cost.setText("");
                resetSelectList(attributeResult);
                adapter.notifyDataSetChanged();
            }
        });
        fitlerPopupwindow.setFocusable(true);
        fitlerPopupwindow.setOutsideTouchable(true);
        fitlerPopupwindow.showAtLocation(view, Gravity.TOP|Gravity.RIGHT, 0, 0);
    }


    /**
     * 隐藏弹出框
     */
    public void dismissPop() {
        if (brandPopupwindow != null) {
            brandPopupwindow.dismiss();
            brandPopupwindow = null;
        }
        if (multiplePopupwindow != null) {
            multiplePopupwindow.dismiss();
            multiplePopupwindow = null;
        }
        if (fitlerPopupwindow != null) {
            fitlerPopupwindow.dismiss();
            fitlerPopupwindow = null;
        }
    }


    public interface MutipleListener {
        void setMutiple(String mutiple);
    }

    public MutipleListener listener;

    public void setMutipleListener(MutipleListener listener) {
        this.listener = listener;
    }


    public FilterDismissListener filterDismissListener;

    public interface FilterDismissListener {
        void dismiss();
    }

    public void setFilterDismissListener(FilterDismissListener filterDismissListener) {
        this.filterDismissListener = filterDismissListener;
    }

    /**
     * 设置listview的高度
     * @param listView
     */
    public void setListHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        ((ViewGroup.MarginLayoutParams) params).setMargins(10, 10, 10, 10);
        listView.setLayoutParams(params);
    }

    public interface FilterSureListener {
        void setFilter(String priceRange);
    }

    public FilterSureListener filterSureListener;

    public void setFilterSureListener(FilterSureListener filterSureListener) {
        this.filterSureListener = filterSureListener;
    }

    /**
     * 重置搜索的筛选项
     * @param attributeResult
     */
    public void resetSelectList(List<ResultBean.AttributeResult> attributeResult) {
        for (int i = 0; i < attributeResult.size(); i++) {
            attributeResult.get(i).attributeValues.get(0).isChecked = true;
            for (int j = 1; j < attributeResult.get(i).attributeValues.size(); j++) {
                attributeResult.get(i).attributeValues.get(j).isChecked = false;
            }
        }
    }

    public void resetSelectListConfirm(List<ResultBean.AttributeResult> attributeResult){
        resetSelectList(attributeResult);
        if (filterSureListener != null) {
            if(et_least_cost == null || et_most_cost == null){
                filterSureListener.setFilter(null);
            } else if (!TextUtils.isEmpty(et_least_cost.getText()) && !TextUtils.isEmpty(et_most_cost.getText())) {
                if (Double.parseDouble(et_most_cost.getText().toString()) < Double.parseDouble(et_least_cost.getText().toString())) {
                    filterSureListener.setFilter(et_most_cost.getText().toString() + "," + et_least_cost.getText().toString());
                } else {
                    filterSureListener.setFilter(et_least_cost.getText().toString() + "," + et_most_cost.getText().toString());
                }
            }else{
                filterSureListener.setFilter(null);
            }
        }
    }
}
