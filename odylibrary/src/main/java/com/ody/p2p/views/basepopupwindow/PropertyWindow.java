package com.ody.p2p.views.basepopupwindow;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ody.p2p.R;
import com.ody.p2p.utils.GlideUtil;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.utils.UiUtils;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/6/7 0007.
 */
public class PropertyWindow extends BasePopupWindow implements View.OnClickListener, PropertyAdapter.PropertyChange, Serializable {
    ImageView img_close;
    RecyclerView recyclerview_property;
    ProductBean product;
    public LinearLayout linear_add_or_baynow, linear_editproduct_num, linear_add_or_baynow_number, havaskotnum;
    FrameLayout framelayout_view;
    public Button btn_shopcart_sub, btn_shopcart_add, btn_property_confim, notskonum;
    public TextView btn_property_baynow, btn_property_addtoshopcart;
    ImageView popupwindow_productImage;
    public TextView popupwindow_productprice, popupwindow_productID, tv_shopcart_num;
    int checkedNum = 1;
    Context mContext;
    long stock = 0;
    PropertyBean ProductAllData;
    public PropertyAdapter adapter;
    boolean btn_shopcart_sub_Statu = true;
    int itembtn = -1;

    public int type;
    public static int TYPE_ADD_BAYNOW = 1;
    public static int TYPE_CONFIRM = 2;//只确定
    public static int TYPE_CONFIRM_ADD_SUB = 3;
    public static int TYPE_ADD_SUB = 4;//只加入购物车

    public ImageView img_shopcart_add;
    public ImageView img_shopcart_sub;

    public void setTYPE(int TYPE) {
        this.type = TYPE;
        if (type == TYPE_CONFIRM_ADD_SUB) {
            btn_property_confim.setVisibility(View.VISIBLE);
            linear_add_or_baynow.setVisibility(View.GONE);
        } else if (type == TYPE_CONFIRM) {
            btn_property_confim.setVisibility(View.VISIBLE);
            linear_add_or_baynow.setVisibility(View.GONE);
            linear_add_or_baynow_number.setVisibility(View.GONE);
            if (!managementState) {
                notskonum.setVisibility(View.GONE);
                havaskotnum.setVisibility(View.VISIBLE);
            }
        } else if (type == TYPE_ADD_SUB) {
            btn_property_confim.setVisibility(View.GONE);
            btn_property_baynow.setVisibility(View.GONE);
        } else {
            linear_editproduct_num.setVisibility(View.VISIBLE);
            linear_add_or_baynow.setVisibility(View.VISIBLE);
            btn_property_confim.setVisibility(View.GONE);
        }
    }

    public void setBtn_shopcart_sub_Statu(boolean btn_shopcart_sub_Statu) {
        this.btn_shopcart_sub_Statu = btn_shopcart_sub_Statu;
    }

    /**
     * @param mContext
     * @param picUrl     图片url
     * @param price      价格
     * @param name       name
     * @param checkedNum 选中数量
     * @param stock      库存
     * @param itembtn    按钮样式
     */
    public PropertyWindow(final Context mContext, String picUrl, double price, String name, int checkedNum, long stock, int itembtn) {
        super(mContext, picUrl);
        this.itembtn = itembtn;
        doInit(mContext, picUrl, price, name, checkedNum, stock);
    }

    /**
     * @param picUrl     图片url
     * @param price      价格
     * @param name       name
     * @param checkedNum 选中数量
     * @param stock      库存
     */
    public PropertyWindow(final Context mContext, String picUrl, double price, String name, int checkedNum, Long stock) {
        super(mContext, picUrl);
        doInit(mContext, picUrl, price, name, checkedNum, stock);
    }

    public PropertyWindow(final Context context, PropertyBean Product, int checkedNum) {
        super(context, Product);

        doInitResult(context, Product, checkedNum);
    }

    public PropertyWindow(final Context mContext, PropertyBean Product, int checkedNum, int itembtn) {
        super(mContext, Product);
        this.itembtn = itembtn;
        doInitResult(mContext, Product, checkedNum);
    }

    void doInitResult(final Context mContext, PropertyBean Product, int checkedNum) {
        this.mContext = mContext;
        this.ProductAllData = Product;
        this.checkedNum = checkedNum;
//        init();
        init(mContext, R.layout.layout_roperty_popupwindow);
        initView();
        initListener();
        if (null != ProductAllData && null != ProductAllData.getData()) {
            initData(ProductAllData);
        } else {
//            Toast.makeText(mContext, "数据异常..", Toast.LENGTH_LONG).show();
        }
    }

    void doInit(final Context context, String picUrl, double price, String name, int checkedNum, Long stock) {
        this.mContext = context;
        this.stock = stock;
        init(mContext, R.layout.layout_roperty_popupwindow);
        initView();
        initListener();
        GlideUtil.display(mContext, picUrl).override(200, 200).into(popupwindow_productImage);
        popupwindow_productprice.setText(UiUtils.getMoney(mContext, price));
//        popupwindow_productID.setText(mContext.getString(R.string.productId) + code);
        popupwindow_productID.setText(name);
        tv_shopcart_num.setText("" + checkedNum);
        recyclerview_property.setVisibility(View.GONE);
    }

    /**
     * 初始化
     */
    private void initView() {
        framelayout_view = (FrameLayout) mMenuView.findViewById(R.id.framelayout_view);
        popupwindow_productID = (TextView) mMenuView.findViewById(R.id.popupwindow_productID);
        popupwindow_productprice = (TextView) mMenuView.findViewById(R.id.popupwindow_productprice);
        tv_shopcart_num = (TextView) mMenuView.findViewById(R.id.tv_shopcart_num);
        popupwindow_productImage = (ImageView) mMenuView.findViewById(R.id.popupwindow_productImage);
        linear_add_or_baynow = (LinearLayout) mMenuView.findViewById(R.id.linear_add_or_baynow);
        linear_editproduct_num = (LinearLayout) mMenuView.findViewById(R.id.linear_editproduct_num);
        linear_add_or_baynow_number = (LinearLayout) mMenuView.findViewById(R.id.linear_add_or_baynow_number);

        recyclerview_property = (RecyclerView) mMenuView.findViewById(R.id.recyclerview_property);
        recyclerview_property.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerview_property.setHasFixedSize(true);

        img_close = (ImageView) mMenuView.findViewById(R.id.img_close);

        btn_property_baynow = (TextView) mMenuView.findViewById(R.id.btn_property_baynow);
        btn_property_addtoshopcart = (TextView) mMenuView.findViewById(R.id.btn_property_addtoshopcart);
        btn_shopcart_sub = (Button) mMenuView.findViewById(R.id.btn_shopcart_sub);
        btn_shopcart_add = (Button) mMenuView.findViewById(R.id.btn_shopcart_add);
        btn_property_confim = (Button) mMenuView.findViewById(R.id.btn_property_confim);
        havaskotnum = (LinearLayout) mMenuView.findViewById(R.id.havaskotnum);
        notskonum = (Button) mMenuView.findViewById(R.id.notskonum);

        img_shopcart_add = (ImageView) mMenuView.findViewById(R.id.img_shopcart_add);
        img_shopcart_sub = (ImageView) mMenuView.findViewById(R.id.img_shopcart_sub);
    }

    private void initListener() {
        if (btn_shopcart_sub_Statu) {
            btn_shopcart_sub.setBackgroundResource(R.drawable.sub_btn_background);
            btn_shopcart_sub.setOnClickListener(this);
        } else {
            btn_shopcart_sub.setBackgroundResource(R.drawable.no_sub_btn_background);
        }

        btn_shopcart_add.setOnClickListener(this);
        img_close.setOnClickListener(this);
        btn_property_baynow.setOnClickListener(this);
        btn_property_confim.setOnClickListener(this);
        btn_property_addtoshopcart.setOnClickListener(this);
        dismissWindow(mMenuView.findViewById(R.id.framelayout_view));

        img_shopcart_add.setOnClickListener(this);
        img_shopcart_sub.setOnClickListener(this);
    }

    /**
     * 装填数据
     */
    //这个是判断在下架按钮是否在显示状态
    boolean managementState;

    private void initData(PropertyBean productBean) {
        this.product = PropertyUtil.getThisProduct(productBean);
        layout_addshoppingsetEnabled(true, "");//初始化展示数据
        if (null == product && productBean.getData().getSerialProducts().size() > 0) {//没有匹配上就默认显示第一个
            this.product = productBean.getData().getSerialProducts().get(0).getProduct();
        }
        if (null != product) {
            if (product.promotionPrice > 0) {
                popupwindow_productprice.setText(UiUtils.getMoney(mContext, product.promotionPrice));
            } else {
                popupwindow_productprice.setText(UiUtils.getMoney(mContext, product.price));
            }
//            popupwindow_productID.setText(mContext.getString(R.string.productId) + product.code);
            popupwindow_productID.setText(product.name);
            tv_shopcart_num.setText("" + checkedNum);

            if (null != product.pics && product.pics.size() > 0) {
                GlideUtil.display(mContext, product.pics.get(0).url).override(200, 200).into(popupwindow_productImage);
            }
            //库存
            if (type != TYPE_CONFIRM && product.managementState == 0) {
                managementState = false;
                layout_addshoppingsetEnabled(false, "已下架");
            } else {
                // 商品详情界面的库存信息展示
                if (product.stockNum == 0) {
                    layout_addshoppingsetEnabled(false, "已售完");
                } else {
                    layout_addshoppingsetEnabled(true, "");
                }
            }
        }
        if (null != productBean.getData().getSerialProducts()) {
            if (null == adapter) {
                if (itembtn == -1) {
                    adapter = new PropertyAdapter(mContext, productBean.getData().getAttributes(), productBean.getData().getSerialProducts(), R.layout.layout_flowitem);
                } else {
                    adapter = new PropertyAdapter(mContext, productBean.getData().getAttributes(), productBean.getData().getSerialProducts(), itembtn);
                }
                adapter.setPropertyChange(this);
                recyclerview_property.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
            recyclerview_property.setVisibility(View.VISIBLE);
        } else {
            recyclerview_property.setVisibility(View.GONE);
        }
    }

    /**
     * 获取选中的系列属性
     * @return
     */
    public String getSerialString() {
        if (null != ProductAllData && null != ProductAllData.getData()) {
            return PropertyUtil.getKeyValue(ProductAllData.getData().getAttributes()) + getProductNumber();
        } else {
            return "" + getProductNumber();
        }
    }

    public boolean status;

    public void layout_addshoppingsetEnabled(boolean statu, String sale) {
        status = statu;
        if (!status) {
            notskonum.setVisibility(View.VISIBLE);
            havaskotnum.setVisibility(View.GONE);
            btn_shopcart_sub.setBackgroundResource(R.drawable.no_sub_btn_background);
            btn_shopcart_add.setBackgroundResource(R.drawable.add_btn_nobackground);
            btn_shopcart_sub.setEnabled(false);
            btn_shopcart_add.setEnabled(false);
            notskonum.setText(sale);
        } else {
            btn_shopcart_sub.setBackgroundResource(R.drawable.sub_btn_background);
            btn_shopcart_add.setBackgroundResource(R.drawable.add_btn_background);
            notskonum.setVisibility(View.GONE);
            havaskotnum.setVisibility(View.VISIBLE);
            btn_shopcart_sub.setEnabled(true);
            btn_shopcart_add.setEnabled(true);
        }
    }

    public void setBtnLyaout(int btnLyaout) {
        if (null != adapter) {
            adapter.setBtnLyaout(btnLyaout);
        }
    }

    @Override
    public void refresh() {
        initData(ProductAllData);
    }

    @Override
    public void dismiss() {
        super.dismiss();
        if (null != mPropertyBack && type != TYPE_CONFIRM) {
            mPropertyBack.PropertyCallBack(product, getProductNumber());
        }
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.img_close) {
            dismiss();
        }
        else if(view.getId() == R.id.img_shopcart_add){
            if (null != product) {
                if (!adapter.isAllChecked()) {
                    ToastUtils.showShort("请选择商品属性");
                } else if (Integer.parseInt(getProductNumber() + "") < Integer.parseInt(product.stockNum + "")) {
                    tv_shopcart_num.setText("" + (getProductNumber() + 1));
                } else {
                    ToastUtils.showShort("库存不足");
                }
            } else if (stock > 0) {
                if (getProductNumber() < stock) {
                    tv_shopcart_num.setText("" + (getProductNumber() + 1));
                } else {
                    ToastUtils.showShort("库存不足");
                }
            }
        }
//        else if (view.getId() == R.id.btn_shopcart_add) {
//            if (null != product) {
//                if (!adapter.isAllChecked()) {
//                    ToastUtils.showShort("请选择商品属性");
//                } else if (Integer.parseInt(getProductNumber() + "") < Integer.parseInt(product.stockNum + "")) {
//                    tv_shopcart_num.setText("" + (getProductNumber() + 1));
//                } else {
//                    ToastUtils.showShort("库存不足");
//                }
//            } else if (stock > 0) {
//                if (getProductNumber() < stock) {
//                    tv_shopcart_num.setText("" + (getProductNumber() + 1));
//                } else {
//                    ToastUtils.showShort("库存不足");
//                }
//            }
//        }
//        else if (view.getId() == R.id.btn_shopcart_sub) {
//            if (getProductNumber() > 1) {
//                tv_shopcart_num.setText("" + (getProductNumber() - 1));
//            }
//        }
        else if (view.getId() == R.id.img_shopcart_sub) {
            if (getProductNumber() > 1) {
                tv_shopcart_num.setText("" + (getProductNumber() - 1));
            }
        }
        else if (view.getId() == R.id.btn_property_baynow) {
            if (null != mPropertyBack) {
                if (null != adapter && !adapter.isAllChecked()) {
                    ToastUtils.showShort("请选择商品属性");
                } else {
                    mPropertyBack.bayNow(product, getProductNumber());
                }
//                mPropertyBack.PropertyCallBack(product, getProductNumber());
            }
        } else if (view.getId() == R.id.btn_property_addtoshopcart) {
            if (null != mPropertyBack) {
//                mPropertyBack.PropertyCallBack(product, getProductNumber());
                if (null != adapter && !adapter.isAllChecked()) {
                    ToastUtils.showShort("请选择商品属性");
                } else {
                    mPropertyBack.addToShopCart(product, getProductNumber());
                    dismiss();
                }
            }
        } else if (view.getId() == R.id.btn_property_confim) {
            if (null != mPropertyBack) {
                mPropertyBack.PropertyCallBack(product, getProductNumber());
            }
            dismiss();
        }
    }

    public void setProductNumber(String number) {
        tv_shopcart_num.setText(number);
    }

    /**
     * 获取数量
     *
     * @return
     */
    public int getProductNumber() {
        return Integer.parseInt(tv_shopcart_num.getText().toString());
    }

    PropertyBack mPropertyBack;

    public void setPropertyBack(PropertyBack mPropertyBack) {
        this.mPropertyBack = mPropertyBack;
    }

    public interface PropertyBack {

        /**
         * 关闭popupwindow时把数据返回出去
         *
         * @param product
         */
        void PropertyCallBack(ProductBean product, int num);

//        /**
//         * 编辑数量加
//         *
//         * @param checkedNumber
//         */
//        void addEditNumber(int checkedNumber);
//
//        /**
//         * 编辑数量减
//         *
//         * @param checkedNumber
//         */
//        void subEditNumber(int checkedNumber);

        /**
         * 立即购买
         *
         * @param product
         * @param productNumber
         */
        void bayNow(ProductBean product, int productNumber);

        /**
         * 添加至购物车
         *
         * @param product
         * @param productNumber
         */
        void addToShopCart(ProductBean product, int productNumber);
    }


}
