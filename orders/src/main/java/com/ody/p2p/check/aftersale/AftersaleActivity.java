package com.ody.p2p.check.aftersale;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ody.p2p.base.BaseActivity;
import com.ody.p2p.check.R;
import com.ody.p2p.check.aftersale.Bean.AfterSaleDetailBean;
import com.ody.p2p.check.aftersale.Bean.AfterSaleTypeBean;
import com.ody.p2p.check.aftersale.Bean.ApplyAfterSaleCauseListBean;
import com.ody.p2p.check.aftersale.Bean.ChangeProductBean;
import com.ody.p2p.check.aftersale.Bean.InitApplyRefundBean;
import com.ody.p2p.utils.MessageUtil;
import com.ody.p2p.utils.StringUtils;
import com.ody.p2p.utils.ToastUtils;
import com.ody.p2p.views.NoDoubleClickListener;
import com.ody.p2p.views.ProgressDialog.CustomDialog;
import com.ody.p2p.views.basepopupwindow.ProductBean;
import com.ody.p2p.views.basepopupwindow.PropertyBean;
import com.ody.p2p.views.basepopupwindow.PropertyUtil;
import com.ody.p2p.views.basepopupwindow.PropertyWindow;
import com.odysaxx.photograph.PhotoPickerActivity;

import java.util.ArrayList;
import java.util.List;

import cn.campusapp.router.annotation.RouterMap;

/**
 * 申请售后：
 * 参数说明：OrderCode--订单号
 *          type--售后类型,用于初始化退货商品
 */
@RouterMap("activity://aftersale")
public class AftersaleActivity extends BaseActivity implements View.OnClickListener, ChooseRefoundWindow.RefoundCallBack, AftersaleView, PicChooseAdapter.OnCameraClick, RefoundAdapter.OnItemChecked {
    protected EditText edit_sale_explain;
    protected TextView tv_refound_cause;
    protected TextView tv_maxleng;
    protected RadioButton readio_btn_new1;
    protected RadioButton readio_btn_new2;
    protected RadioButton readio_btn_new3;
    protected LinearLayout linear_getreuls;
    protected LinearLayout linear_view;
    private AftersalePressenter mPressenter;
    private static final int PICK_PHOTO = 100;//相片code
    private ImageView img_back;
    protected RecyclerView recycle_list;
    protected TextView btn_apply_button;
    private String keyNumber = "";//退货原因的KEY
    private String orderCode = "";
    private GridView gv_pic;
    private PicChooseAdapter adapter;
    protected RefoundAdapter madapter;
    protected LinearLayout ll_change_product;
    protected RecyclerView rv_change_product;
    protected ChangeProductAdapter changeProductAdapter;
    private int returntype=2;
    private boolean isEdit=false;
    private String returnId;
    private ImageView iv_more;
    private String type="";//售后类型（2、退货4、换货 5、仅退款）

    @Override
    public void initPresenter() {
        orderCode = getIntent().getStringExtra("OrderCode");
        type=getIntent().getStringExtra("type");
        isEdit=getIntent().getBooleanExtra("isEdit",false);
        mPressenter = new AftersaleImpr(this);
    }

    @Override
    public int bindLayout() {
        return R.layout.activity_after_slae;
    }

    @Override
    public void initView(View view) {
        img_back = (ImageView) view.findViewById(R.id.img_back);
        recycle_list = (RecyclerView) view.findViewById(R.id.recycle_list);
        readio_btn_new1 = (RadioButton) view.findViewById(R.id.readio_btn_new1);
        readio_btn_new2 = (RadioButton) view.findViewById(R.id.readio_btn_new2);
        readio_btn_new3 = (RadioButton) view.findViewById(R.id.readio_btn_new3);
        btn_apply_button = (TextView) view.findViewById(R.id.btn_apply_button);
        gv_pic= (GridView) findViewById(R.id.gv_pic);
        edit_sale_explain = (EditText) view.findViewById(R.id.edit_sale_explain);
        tv_maxleng = (TextView) view.findViewById(R.id.tv_maxleng);
        linear_getreuls = (LinearLayout) view.findViewById(R.id.linear_getreuls);
        linear_view = (LinearLayout) view.findViewById(R.id.linear_view);
        tv_refound_cause = (TextView) view.findViewById(R.id.tv_refound_cause);
        rv_change_product= (RecyclerView) view.findViewById(R.id.rv_change_product);
        ll_change_product= (LinearLayout) view.findViewById(R.id.ll_change_product);
        iv_more= (ImageView) findViewById(R.id.iv_more);
        linear_getreuls.setOnClickListener(this);
        img_back.setOnClickListener(this);
        btn_apply_button.setOnClickListener(new NoDoubleClickListener() {
            @Override
            public void onNoDoubleClick(View v) {
                submit();
            }
        });
        iv_more.setOnClickListener(this);
        edit_sale_explain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                tv_maxleng.setText(getString(R.string.can_into) + (200 -editable.length()) + getString(R.string.a_word));
            }
        });
    }

    @Override
    public void doBusiness(Context mContext) {
        pageCode = 4;
        madapter=new RefoundAdapter(this);
        changeProductAdapter=new ChangeProductAdapter(this);
        recycle_list.setLayoutManager(new LinearLayoutManager(getContext()));
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_change_product.setLayoutManager(linearLayoutManager);
        madapter.setItemcheckListener(this);
        recycle_list.setAdapter(madapter);
        rv_change_product.setAdapter(changeProductAdapter);

        adapter=new PicChooseAdapter(this);
        adapter.setListener(this);
        gv_pic.setAdapter(adapter);

        if(!isEdit){
            mPressenter.aftersaleType(orderCode);
            mPressenter.initRefound(orderCode,type);
        }else{
            setEditValue();
        }

    }

    /**
     * 编辑售后信息，不支持更改售后类型和商品信息
     */
    private void setEditValue(){
        String str = getIntent().getStringExtra("AfterSaleDetailBean");
        Gson gson = new Gson();
        AfterSaleDetailBean.Data data= gson.fromJson(str,AfterSaleDetailBean.Data.class);
        if(data==null){
            return;
        }
        returnId=data.getReturnCode();
        readio_btn_new1.setVisibility(View.VISIBLE);
        readio_btn_new1.setChecked(true);
        readio_btn_new1.setEnabled(false);
        returntype=data.getType();
        if(data.getType()==2){
            readio_btn_new1.setText("\t"+getString(R.string.refunds));
        }else if(data.getType()==4){
            readio_btn_new1.setText("\t"+getString(R.string.exchange_goods));
        }else if(data.getType()==5){
            readio_btn_new1.setText("\t"+getString(R.string.only_refund));
        }
        if(data!=null&&data.getMerchantProductVOs()!=null&&data.getMerchantProductVOs().size()>0){
            List<InitApplyRefundBean.AfterSalesProductVOs> afterSalesProductVOses=new ArrayList<>();
            InitApplyRefundBean initApplyRefundBean=new InitApplyRefundBean();
            for(int i=0;i<data.getMerchantProductVOs().size();i++){
                InitApplyRefundBean.AfterSalesProductVOs afterSalesProductVOs=initApplyRefundBean.new AfterSalesProductVOs();
                afterSalesProductVOs.checked=true;
                afterSalesProductVOs.checkedNum=data.getMerchantProductVOs().get(i).getReturnProductItemNum();//退货数量
                afterSalesProductVOs.setProductPicPath(data.getMerchantProductVOs().get(i).getProductPicPath());
                afterSalesProductVOs.setChineseName(data.getMerchantProductVOs().get(i).getChineseName());
                afterSalesProductVOs.setProductPriceFinal(data.getMerchantProductVOs().get(i).getProductPriceFinal());
                afterSalesProductVOs.setMayReturnProductItemNum(data.getMerchantProductVOs().get(i).getReturnProductItemNum());//最多可退货数量==退货数量
                afterSalesProductVOs.setMpId(data.getMerchantProductVOs().get(i).getMpId());
                afterSalesProductVOs.setSoItemId(data.getMerchantProductVOs().get(i).getId());
                afterSalesProductVOses.add(afterSalesProductVOs);
            }
            madapter.setEnable(false);
            madapter.setData(afterSalesProductVOses);
        }
        keyNumber=data.getReturnReasonId();
        tv_refound_cause.setText(data.getReturnReason());
        edit_sale_explain.setText(data.getReturnRemark());
        if(data.getPicUrlList()!=null&&data.getPicUrlList().size()>0){
            adapter.setPaths(data.getPicUrlList());
        }
    }

    @Override
    public void resume() {
    }

    @Override
    public void destroy() {

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.linear_getreuls) {//售后原因列表
            if(returntype==2){
                mPressenter.getafterSaleCauseList(2);
            }else if(returntype==4){
                mPressenter.getafterSaleCauseList(4);
            }else if(returntype==5){
                mPressenter.getafterSaleCauseList(1);
            }
        } else if (v.getId() == R.id.img_back) {
            finish();
        }else if(v.getId()==R.id.readio_btn_new1){
            returntype=2;
            ll_change_product.setVisibility(View.GONE);
        }else if(v.getId()==R.id.readio_btn_new3){
            returntype=4;
            if(madapter.getCheckedItemNum().size()>0){
                ll_change_product.setVisibility(View.VISIBLE);
                if(changeProductAdapter.getItemCount()!=madapter.getCheckedItemNum().size()){
                    changeProductAdapter.clear();
                    for(int i=0;i<madapter.getCheckedItemNum().size();i++){
                        mPressenter.aftersaleChangeProduct(orderCode,madapter.getCheckedItemNum().get(i).getMpId()+"",madapter.getCheckedItemNum().get(i).getSoItemId()+"");
                    }
                }
            }
        }else if(v.getId()==R.id.iv_more){
            MessageUtil.setMegScan(getContext(), iv_more);
        }
    }

    private void submit(){
        String picPaths="";
        List<String> picUrlList = adapter.getData();
        if(picUrlList.size()>0) {
            for (int i = 0; i < picUrlList.size(); i++) {
                if (!StringUtils.isEmpty(picUrlList.get(i))) {
                    picPaths += picUrlList.get(i).toString() + ",";
                }
            }
            if (picPaths.length() > 0) {
                picPaths = picPaths.substring(0, picPaths.length() - 1);
            }
        }
        String returnSoItemList="";
        if(madapter.getReturnProductlist()!=null&&madapter.getReturnProductlist().size()>0){
            for(int i=0;i<madapter.getReturnProductlist().size();i++){
                if(madapter.getReturnProductlist().get(i).checked){
                    returnSoItemList += madapter.getReturnProductlist().get(i).getSoItemId() + "|" + madapter.getReturnProductlist().get(i).checkedNum+",";
                }
            }
        }
        String swapProducts="";
        if(returntype==4){
            if(changeProductAdapter.getData()!=null&&changeProductAdapter.getData().size()>0){
                for(int i=0;i<changeProductAdapter.getData().size();i++){
                    if(madapter.getReturnProductlist()!=null&&madapter.getReturnProductlist().size()>0){
                        for(int j=0;j<madapter.getReturnProductlist().size();j++){
                            if(madapter.getReturnProductlist().get(j).getSoItemId().equals(changeProductAdapter.getData().get(i).soItemId)){
                                swapProducts+=changeProductAdapter.getData().get(i).soItemId+"|"+changeProductAdapter.getData().get(i).productBean.mpId+"|"+madapter.getReturnProductlist().get(j).checkedNum+",";
                            }
                        }
                    }
                }
            }
        }
        if(!TextUtils.isEmpty(returnSoItemList)){
            returnSoItemList=returnSoItemList.substring(0, returnSoItemList.length() - 1);
        }
        if(!TextUtils.isEmpty(swapProducts)){
            swapProducts=swapProducts.substring(0,swapProducts.length()-1);
        }
        if(TextUtils.isEmpty(returnSoItemList)){
            ToastUtils.showToast(getString(R.string.choose_change_product));
            return;
        }

        if(TextUtils.isEmpty(keyNumber)){
            ToastUtils.showToast(getString(R.string.choose_change_prodtcu_reason));
            return;
        }
        mPressenter.applyRefoundProduct(orderCode,keyNumber,edit_sale_explain.getText().toString(),picPaths,returnSoItemList,returntype,swapProducts,isEdit,returnId);
    }

    /**
     * 调起相片
     */
    private void openPhotos(int num) {
        Intent intent = new Intent(getContext(), PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, PhotoPickerActivity.MODE_MULTI);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN,num);
        startActivityForResult(intent, PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {//选择照片回
                List<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                if (result!=null&&result.size() > 0) {
                    for(int i=0;i<result.size();i++){
                        mPressenter.uploadingPhotps(result.get(i));
                    }
                }
            }
        }
    }

    private ChooseRefoundWindow mPopupwindow;

    @Override
    public void getaftersale(List<ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs> list) {
        if(list!=null&&list.size()>0){
            mPopupwindow = new ChooseRefoundWindow(getContext(), list);
            mPopupwindow.setRefoundCallBack(this);
            mPopupwindow.showAtLocation(linear_view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        }
    }


    /**
     * 照片的展示
     */
    @Override
    public void getPhotos(String url) {
        adapter.addPath(url);
    }

    /**
     * 该订单支持的售后类型
     * @param afterSaleTypeBean
     */
    @Override
    public void aftersaletype(AfterSaleTypeBean afterSaleTypeBean) {
        if(afterSaleTypeBean!=null&&afterSaleTypeBean.data!=null&&afterSaleTypeBean.data.size()>0){

            /**
             * 目前返回类型说明：operateType=2，4或者operateType==5二者其一（待收货的订单申请售后 只返回仅退款类型operateType==5）
             * 仅退款不能更改商品信息
             */
            for(int i=0;i<afterSaleTypeBean.data.size();i++){
                if(afterSaleTypeBean.data.get(i).operateType==2){
                    readio_btn_new1.setVisibility(View.VISIBLE);
                    readio_btn_new1.setOnClickListener(this);
                }
                if(afterSaleTypeBean.data.get(i).operateType==4){
                    readio_btn_new3.setVisibility(View.VISIBLE);
                    readio_btn_new3.setOnClickListener(this);
                }
                if(afterSaleTypeBean.data.get(i).operateType==5){
                    readio_btn_new2.setVisibility(View.VISIBLE);
                    madapter.setEnable(false);
                    madapter.notifyDataSetChanged();
                } 
            }
            if(afterSaleTypeBean.data.get(0).operateType==2){
                readio_btn_new1.setChecked(true);
                returntype=2;
            }else if(afterSaleTypeBean.data.get(0).operateType==4){
                readio_btn_new3.setChecked(true);
                returntype=4;
            }else if(afterSaleTypeBean.data.get(0).operateType==5){
                readio_btn_new2.setChecked(true);
                returntype=5;
                readio_btn_new2.setEnabled(false);
            }
        }
    }

    @Override
    public void initReturnProduct(InitApplyRefundBean initApplyRefundBean) {
        if(initApplyRefundBean.getData().getAfterSalesProductVOs().size()>0){
            if(returntype==2||returntype==4){
                madapter.setEnable(true);
            }else{
                madapter.setEnable(false);//仅退款不能编辑商品
            }
            madapter.setData(initApplyRefundBean.getData().getAfterSalesProductVOs());
        }
    }

    @Override
    public void changeProduct(ChangeProductBean changeProductBean) {
        AfterSaleChangeProductBean productBean=null;
        /**
         * 将换货商品信息封装成AfterSaleChangeProductBean
         * 包括soItemId，isSerial（是否为系列品），propertyWindow（属性弹框）、
         * productBean（子商品），map（系列品属性）
         */
        if(changeProductBean.data.isSerial==1){
            PropertyBean propertyBean=new PropertyBean();
            if(changeProductBean.data.map!=null){
                propertyBean.setData(changeProductBean.data.map);
                PropertyWindow propertyWindow=new PropertyWindow(this,propertyBean,1);
                propertyWindow.setTYPE(PropertyWindow.TYPE_CONFIRM);
                setProductSerialsStyle(propertyWindow);
                productBean=new AfterSaleChangeProductBean();
                ProductBean bean=PropertyUtil.getThisProduct(propertyBean);
                if(bean==null){
                    return;
                }
                productBean.propertyWindow=propertyWindow;
                productBean.productBean=bean;
                productBean.map=changeProductBean.data.map;
                if (changeProductBean != null && changeProductBean.data != null && changeProductBean.data.map != null && changeProductBean.data.map.getAttributes() != null){
                    productBean.productBean.attrs = new ArrayList<>();
                    for (int i = 0; i < changeProductBean.data.map.getAttributes().size(); i++) {
                        ProductBean.Attrs attr = new ProductBean.Attrs();
                        attr.attrVal = new ProductBean.Attrs.AttrVal();
                        if (changeProductBean.data.map.getAttributes().get(i) != null && changeProductBean.data.map.getAttributes().get(i).getValues() != null){
                            for (int j = 0; j < changeProductBean.data.map.getAttributes().get(i).getValues().size(); j++) {
                                if(changeProductBean.data.map.getAttributes().get(i).getValues().get(j).getChecked()){
                                    attr.attrVal.value = changeProductBean.data.map.getAttributes().get(i).getValues().get(j).getValue();
                                }
                            }
                        }
                        productBean.productBean.attrs.add(attr);
                    }
                }
            }
        }else{
            productBean=new AfterSaleChangeProductBean();
            ProductBean product=new ProductBean();
            productBean.productBean=product;
            if(!TextUtils.isEmpty(changeProductBean.data.mpId)){
                product.setMpId(Long.parseLong(changeProductBean.data.mpId));
            }
            product.setName(changeProductBean.data.chineseName+"");
            product.setPicUrl(changeProductBean.data.productUrl+"");
            product.setPrice(changeProductBean.data.productPriceFinal);
        }
        productBean.soItemId=changeProductBean.data.soItemId;
        productBean.isSerial=changeProductBean.data.isSerial;
        changeProductAdapter.addData(productBean);
    }

    @Override
    public void showDialog(String msg) {
        CustomDialog customDialog=new CustomDialog(this,msg,3);
        customDialog.SetCustomDialogCallBack(new CustomDialog.CustomDialogCallBack() {
            @Override
            public void Confirm() {
                finish();
            }
        });
        customDialog.show();
    }

    /**
     * 德生重写此方法，设定系列品弹框样式
     * @param propertyWindow
     */
    public void setProductSerialsStyle(PropertyWindow propertyWindow){
    }

    @Override
    public void chooseRefound(ApplyAfterSaleCauseListBean.OrderAfterSalesCauseVOs key) {
        tv_refound_cause.setText(key.getValue());
        keyNumber = key.getKey()+"";
        mPopupwindow.dismiss();
    }


    @Override
    public void finishActivity() {
        //跳转至详情页
        finish();
    }


    @Override
    public void oncameraClickListener(int size) {
        openPhotos(PicChooseAdapter.MAX_NUM-size);
    }

    @Override
    public void onItemCheckedListener(InitApplyRefundBean.AfterSalesProductVOs bean) {
        if(returntype==4){
            ll_change_product.setVisibility(View.VISIBLE);
            mPressenter.aftersaleChangeProduct(orderCode,bean.getMpId()+"",bean.getSoItemId()+"");
        }
    }

    @Override
    public void onItemCheckedCancelListener(InitApplyRefundBean.AfterSalesProductVOs bean) {
        if(returntype==4){
            if(changeProductAdapter.getData()!=null&&changeProductAdapter.getData().size()>0){
                for(int i=0;i<changeProductAdapter.getData().size();i++){
                    if(bean.getSoItemId().equals(changeProductAdapter.getData().get(i).soItemId)){
                        changeProductAdapter.remove(i);
                    }
                }
                if(changeProductAdapter.getData().size()==0){
                    ll_change_product.setVisibility(View.GONE);
                }
            }
        }
    }
}
