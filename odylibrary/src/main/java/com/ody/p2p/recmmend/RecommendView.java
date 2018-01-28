package com.ody.p2p.recmmend;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;

/**
 * Created by pzy on 2016/12/14.
 */
public class RecommendView {

    /**
     * @param mContext
     * @param mData
     * @param recommendPageSize
     * @param CallBack
     * @return
     */
    public static List<RecommendAdapter> setRecommendData(Context mContext, List<Recommedbean.DataList> mData, int recommendPageSize, RecommendAdapter.RecommendCallBack CallBack) {
        return setRecommendData(mContext, mData, recommendPageSize, RecommendAdapter.STANDARD, CallBack);
    }

    /**
     * @param mContext
     * @param mData
     * @param recommendPageSize
     * @param JumpType          关于详情的跳转方式
     * @param CallBack
     * @return
     */
    public static List<RecommendAdapter> setRecommendData(Context mContext, List<Recommedbean.DataList> mData, int recommendPageSize, int JumpType, RecommendAdapter.RecommendCallBack CallBack) {
        List<RecommendAdapter> listRe;
        RecommendAdapter adapter;
        List<Recommedbean.DataList> recommendList;
        int page;
        if (mData.size() % recommendPageSize == 0) {
            listRe = new ArrayList<>();
            page = mData.size() / recommendPageSize;
            for (int i = 0; i < page; i++) {
                recommendList = new ArrayList<>();
                for (int j = 0; j < recommendPageSize; j++) {
                    recommendList.add(mData.get(recommendPageSize * i + j));
                }
                adapter = new RecommendAdapter(mContext, recommendList);
                adapter.setJumpType(JumpType);
                adapter.setRecommendCallBack(CallBack);
                listRe.add(adapter);
            }
        } else {
            listRe = new ArrayList<>();
            page = mData.size() / recommendPageSize;
            for (int i = 0; i < page; i++) {
                recommendList = new ArrayList<>();
                for (int j = 0; j < recommendPageSize; j++) {
                    recommendList.add(mData.get(recommendPageSize * i + j));
                }
                adapter = new RecommendAdapter(mContext, recommendList);
                adapter.setJumpType(JumpType);
                adapter.setRecommendCallBack(CallBack);
                listRe.add(adapter);
            }
            if (page * recommendPageSize < mData.size()) {
                recommendList = new ArrayList<>();
                for (int k = page * recommendPageSize; k < mData.size(); k++) {
                    recommendList.add(mData.get(k));
                }
                adapter = new RecommendAdapter(mContext, recommendList);
                adapter.setJumpType(JumpType);
                adapter.setRecommendCallBack(CallBack);
                listRe.add(adapter);
            }
        }
        return listRe;
    }

}