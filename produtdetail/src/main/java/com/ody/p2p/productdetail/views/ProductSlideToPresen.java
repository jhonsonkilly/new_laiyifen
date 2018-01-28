package com.ody.p2p.productdetail.views;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.ody.p2p.productdetail.productdetail.adapter.ProdutPresenAdapter;
import com.ody.p2p.productdetail.productdetail.adapter.promotionInfoAdapter;
import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.produtdetail.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ody on 2016/8/17.
 */
public class ProductSlideToPresen extends PopupWindow implements View.OnClickListener {
    int them = R.color.theme_color;
    int textThemeColor = R.color.white;
    private View popupView;
    Activity context;
    //标题
    TextView text_minukonw;
    ImageView img_close;

    //数据的集合
    List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList> promotionGiftDetailList;
    List<PromotionBean.Data.PromotionInfo.Promotions.PromotionRuleList> promotionRuleList;
    int contentType;
    ExpandableListView expand_menu;

    public ProductSlideToPresen(Activity context, int them, int textThemeColor, List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList> promotionGiftDetailList,
                                List<PromotionBean.Data.PromotionInfo.Promotions.PromotionRuleList> promotionRuleList,int contentType) {
        super(context);
        this.context = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupView = inflater.inflate(R.layout.product_slide_topresent, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(popupView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(ViewGroup.LayoutParams.FILL_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.AnimBottom);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.promotionGiftDetailList = promotionGiftDetailList;
        this.promotionRuleList = promotionRuleList;
        this.contentType=contentType;
        this.them = them;
        this.textThemeColor = textThemeColor;
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int height = popupView.findViewById(R.id.popup_anima).getTop();
                int y = (int) event.getY();
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (y < height) {
                        dismiss();
                    }
                }
                return true;
            }
        });

        init();
        bindEvent(promotionGiftDetailList, promotionRuleList,contentType);
    }
    promotionInfoAdapter proAdapter;
    private void bindEvent(List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList> promotionGiftDetailList, List<PromotionBean.Data.PromotionInfo.Promotions.PromotionRuleList> promotionRuleList,int contentType) {
        if (null!=promotionGiftDetailList&&promotionGiftDetailList.size()>0&&promotionGiftDetailList.get(0).promotionId>0&&contentType==4||null!=promotionGiftDetailList&&promotionGiftDetailList.size()>0&&promotionGiftDetailList.get(0).promotionId>0&&contentType==5){//这是满赠的类型
            proAdapter=new promotionInfoAdapter(context, promotionGiftDetailList,context.getString(R.string.with_a_gift));
            expand_menu.setAdapter(proAdapter);
            expand_menu.setDivider(null);
            for(int i = 0; i < proAdapter.getGroupCount(); i++){
                expand_menu.expandGroup(i);
            }

        }else if(null!=promotionRuleList&&promotionRuleList.size()>0&&promotionRuleList.get(0).getPromotionId()>0&&contentType==2
                ||null!=promotionRuleList&&promotionRuleList.size()>0&&promotionRuleList.get(0).getPromotionId()>0&&contentType==3){
            proAdapter=new promotionInfoAdapter(context, promotionRuleList);
            expand_menu.setAdapter(proAdapter);
            expand_menu.setDivider(null);
            for(int i = 0; i < proAdapter.getGroupCount(); i++){
                expand_menu.expandGroup(i);
            }
        }

//        expand_menu.set

    }

    private void init() {
        text_minukonw = (TextView) popupView.findViewById(R.id.text_minukonw);
        text_minukonw.setBackground(context.getResources().getDrawable(them));
        text_minukonw.setTextColor(context.getResources().getColor(textThemeColor));
        text_minukonw.setOnClickListener(this);
        img_close = (ImageView) popupView.findViewById(R.id.img_close);//关闭
        img_close.setOnClickListener(this);
        expand_menu= (ExpandableListView) popupView.findViewById(R.id.expand_menu);
    }


 /*   private void bindEvent(List<MinuBean.Data> lists) {
        if (popupView != null) {
            txt_title.setText("满赠活动");
            txt_state.setText("满99送如下赠品");
            txt_state.setText("活动有效期："+"2016.07.23-2016.08.23");
            List<String> list=new ArrayList<>();
            for (int j=0;j<10;j++){
                list.add("http://p02.sfbest.com/2013/1500017160/original_1500017160_1_1.JPG");
            }
            ProdutPresenAdapter produtPresenAdapter=new ProdutPresenAdapter(context,list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            recy_presentlist.setLayoutManager(linearLayoutManager);
            recy_presentlist.setAdapter(produtPresenAdapter);

        }

    }*/

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.text_minukonw) {
            dismiss();
        } else if (v.getId() == R.id.img_close) {
            dismiss();
        }
    }


}
