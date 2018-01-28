package com.ody.p2p.productdetail.productdetail.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.view.menu.ExpandedMenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ody.p2p.productdetail.productdetail.bean.PromotionBean;
import com.ody.p2p.produtdetail.R;
import com.ody.p2p.utils.StringUtils;

import java.util.List;

/**
 * Created by ody on 2016/8/15.
 */
public class promotionInfoAdapter extends BaseExpandableListAdapter {
    Context mContext;
    //数据的集合
    List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList> group_GiftList;
    List<PromotionBean.Data.PromotionInfo.Promotions.PromotionRuleList> group_RuleList;
    String type;

    public promotionInfoAdapter(Activity context, List<PromotionBean.Data.PromotionInfo.Promotions.PromotionGiftDetailList> promotionGiftDetailList, String Type) {
        this.mContext = context;
        this.group_GiftList = promotionGiftDetailList;
        this.type = Type;
    }

    public promotionInfoAdapter(Activity context, List<PromotionBean.Data.PromotionInfo.Promotions.PromotionRuleList> promotionRuleList) {
        this.mContext = context;
        this.group_RuleList = promotionRuleList;
    }

    /**
     * 获取组的个数
     *
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupCount()
     */
    @Override
    public int getGroupCount() {
        if (null != type && !"".equals(type)) {
            return group_GiftList.size();
        } else {
            return group_RuleList.size();

        }
    }

    /**
     * 获取指定组中的子元素个数
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildrenCount(int)
     */
    @Override
    public int getChildrenCount(int groupPosition) {
        if (!StringUtils.isEmpty(type)) {

            if (null != group_GiftList && group_GiftList.size() > 0
                    && null != group_GiftList.get(groupPosition).getSingleCouponInfoList() &&
                    group_GiftList.get(groupPosition).getSingleCouponInfoList().size() > 0 || null != group_GiftList && group_GiftList.size() > 0
                    && null != group_GiftList.get(groupPosition).getSingleGiftInfoList()
                    && group_GiftList.get(groupPosition).getSingleGiftInfoList().size() > 0) {
                return 1;

            }
        }
        return 0;

    }

    /**
     * 获取指定组中的数据
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroup(int)
     */
    @Override
    public Object getGroup(int groupPosition) {
        if (null != type && !"".equals(type)) {
            return group_GiftList.get(groupPosition);
        } else {
            return group_RuleList.get(groupPosition);

        }
    }

    /**
     * 获取指定组中的指定子元素数据。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChild(int, int)
     */
    @Override
    public Object getChild(int groupPosition, int childPosition) {
        if (null != type && !"".equals(type)) {
            if (null != group_GiftList.get(groupPosition).getSingleCouponInfoList() && group_GiftList.get(groupPosition).getSingleCouponInfoList().size() > 0) {
                return group_GiftList.get(groupPosition).getSingleGiftInfoList();

            } else if (null != group_GiftList.get(groupPosition).getSingleGiftInfoList() && group_GiftList.get(groupPosition).getSingleGiftInfoList().size() > 0) {
                return group_GiftList.get(groupPosition).getSingleGiftInfoList();
            } else {
                return 0;
            }
        } else {
            return null;
        }
    }

    /**
     * 获取指定组的ID，这个组ID必须是唯一的
     *
     * @param groupPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupId(int)
     */
    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    /**
     * 获取指定组中的指定子元素ID
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#getChildId(int, int)
     */
    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    /**
     * 组和子元素是否持有稳定的ID,也就是底层数据的改变不会影响到它们。
     *
     * @return
     * @see android.widget.ExpandableListAdapter#hasStableIds()
     */
    @Override
    public boolean hasStableIds() {
        return true;
    }

    /**
     * 获取显示指定组的视图对象
     *
     * @param groupPosition 组位置
     * @param isExpanded    该组是展开状态还是伸缩状态
     * @param convertView   重用已有的视图对象
     * @param parent        返回的视图对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getGroupView(int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupHolder groupHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.produt_promotion_grupitme, null);
            groupHolder = new GroupHolder();
            groupHolder.txt_promotiontitle = (TextView) convertView.findViewById(R.id.txt_promotiontitle);
            groupHolder.img_promotion_item = (ImageView) convertView.findViewById(R.id.img_promotion_item);
            groupHolder.line_group = convertView.findViewById(R.id.line_group);
            convertView.setTag(groupHolder);
        } else {
            groupHolder = (GroupHolder) convertView.getTag();
        }
        if (null != type && !"".equals(type)) {
            groupHolder.txt_promotiontitle.setText(group_GiftList.get(groupPosition).getDescription());//促销活动的描述
        } else {
            groupHolder.txt_promotiontitle.setText(group_RuleList.get(groupPosition).getDescription());
        }
        if (isExpanded && null != type && !"".equals(type)) {
            groupHolder.line_group.setVisibility(View.GONE);
            groupHolder.img_promotion_item.setBackgroundResource(R.drawable.ic_arrowup_gray);
        } else if (!isExpanded && null != type && !"".equals(type)) {
            groupHolder.line_group.setVisibility(View.VISIBLE);
            groupHolder.img_promotion_item.setBackgroundResource(R.drawable.ic_arrowdown_gray);
        } else {
            groupHolder.line_group.setVisibility(View.VISIBLE);
            groupHolder.img_promotion_item.setVisibility(View.GONE);
        }
        return convertView;
    }

    /**
     * 获取一个视图对象，显示指定组中的指定子元素数据。
     *
     * @param groupPosition 组位置
     * @param childPosition 子元素位置
     * @param isLastChild   子元素是否处于组中的最后一个
     * @param convertView   重用已有的视图(View)对象
     * @param parent        返回的视图(View)对象始终依附于的视图组
     * @return
     * @see android.widget.ExpandableListAdapter#getChildView(int, int, boolean, View,
     * ViewGroup)
     */
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ItemHolder itemHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.produt_promotion_childrenitme, null);
            itemHolder = new ItemHolder();
            itemHolder.txt_promotiondescription = (TextView) convertView.findViewById(R.id.txt_promotiondescription);
            itemHolder.txt_promotionspecdescription = (TextView) convertView.findViewById(R.id.txt_promotionspecdescription);
            itemHolder.txt_promotionstime = (TextView) convertView.findViewById(R.id.txt_promotionstime);
            itemHolder.recy_singlegiftinfo = (RecyclerView) convertView.findViewById(R.id.recy_singlegiftinfo);
            itemHolder.line_child = convertView.findViewById(R.id.line_child);
            convertView.setTag(itemHolder);
        } else {
            itemHolder = (ItemHolder) convertView.getTag();
        }
        if (null != type && !"".equals(type)) {
            itemHolder.txt_promotionspecdescription.setText(group_GiftList.get(groupPosition).getSpecificDescription());
            if (group_GiftList.get(groupPosition).getGiftType() == 1 && null != group_GiftList.get(groupPosition).getSingleGiftInfoList()
                    && group_GiftList.get(groupPosition).getSingleGiftInfoList().size() > 0) {
                ProdutPresenAdapter produtPresenAdapter = new ProdutPresenAdapter(mContext, group_GiftList.get(groupPosition).getSingleGiftInfoList());
                produtPresenAdapter.setGiftType(1);   //这是赠送的赠品
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                itemHolder.recy_singlegiftinfo.setLayoutManager(linearLayoutManager);
                itemHolder.recy_singlegiftinfo.setAdapter(produtPresenAdapter);
            } else if (group_GiftList.get(groupPosition).getGiftType() == 2 && null != group_GiftList.get(groupPosition).getSingleCouponInfoList()
                    && group_GiftList.get(groupPosition).getSingleCouponInfoList().size() > 0) {
                ProdutPresenAdapter produtPresenAdapter = new ProdutPresenAdapter(mContext, group_GiftList.get(groupPosition).getSingleCouponInfoList());
                produtPresenAdapter.setGiftType(2);   //这是优惠券
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                itemHolder.recy_singlegiftinfo.setLayoutManager(linearLayoutManager);
                itemHolder.recy_singlegiftinfo.setAdapter(produtPresenAdapter);
            } else {
                itemHolder.recy_singlegiftinfo.setVisibility(View.GONE);
            }
        }

        return convertView;
    }

    /**
     * 是否选中指定位置上的子元素。
     *
     * @param groupPosition
     * @param childPosition
     * @return
     * @see android.widget.ExpandableListAdapter#isChildSelectable(int, int)
     */
    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


    class GroupHolder {
        public TextView txt_promotiontitle;
        public View line_group;
        public ImageView img_promotion_item;
    }

    class ItemHolder {
        public TextView txt_promotiondescription;
        public TextView txt_promotionspecdescription;
        public TextView txt_promotionstime;
        public View line_child;

        RecyclerView recy_singlegiftinfo;
    }


}

