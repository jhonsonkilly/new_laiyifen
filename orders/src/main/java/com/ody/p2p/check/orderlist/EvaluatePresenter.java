package com.ody.p2p.check.orderlist;

/**
 * Created by ody on 2016/8/29.
 */
public interface EvaluatePresenter {

    void initEvaluate(String orderCode);
    void commiEvaluate(CommitEvaluateData data);
    void upLoadPicture(PhotoFileBean bean,int position);
    void evaluateRule();
    void submitAddtional(String inputJson);
}
