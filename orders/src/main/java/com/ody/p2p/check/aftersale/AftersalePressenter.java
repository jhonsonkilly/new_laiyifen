package com.ody.p2p.check.aftersale;

/**
 * Created by ody on 2016/6/29.
 */
public interface AftersalePressenter {

    void uploadingPhotps(String file);

    void getafterSaleCauseList(int status);

    void initRefound(String orderCode,String type);

    void applyRefoundProduct(String orderCode, String returnReasonId, String returnRemark, String picUrlList,String returnSoItemList,int type,String swapProducts,boolean isEdit,String returnId);

    void applyReFund(String orderCode, int reasonId, String remark);

    void aftersaleType(String orderCode);

    void aftersaleChangeProduct(String orderCode,String returnMpId,String soItemId);
}
