package com.lyfen.android.ui.activity.redpacket.redpacket.redpacketlist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.laiyifen.lyfframework.base.ActionBarActivity;
import com.lyfen.android.ui.activity.redpacket.RedPacketNetWorkApi;
import com.lyfen.android.ui.activity.redpacket.RedPacketRetrofit;
import com.lyfen.android.ui.activity.redpacket.redpacket.RedPacketActivity;
import com.lyfen.android.ui.activity.redpacket.redpacket.RedPacketDetailActivity;
import com.ody.p2p.Constants;
import com.ody.p2p.base.OdyApplication;
import com.ody.p2p.main.R;
import com.ody.p2p.utils.statusbar.StatusBarUtil;
import com.ody.p2p.views.swiprefreshview.OdySwipeRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 作者：qiujie on 17/2/14
 */
public class RedPacketListActivity extends ActionBarActivity {


    private RecyclerView mRecyclerView;
    private OdySwipeRefreshLayout mOdySwipeRefreshLayout;
    private RedPacketAdapter mRedPacketAdapter;
    public static String METHOD = "method";
    public static String REDPACKET_SENDRED = "redpacket.sendred";
    public static String REDPACKET_GETRED = "redpacket.getred";
    private String mStringExtra;
    private View mHead;
    int pno = 0;
    public List<RedenvelopesListBean> mRedenvelopesListBeans;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.layout_swiprefresh);
        StatusBarUtil.setColor(this, ContextCompat.getColor(this, R.color.CFF6900));
        mRedenvelopesListBeans = new ArrayList<>();
        mOdySwipeRefreshLayout = (OdySwipeRefreshLayout) findViewById(R.id.refresh);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        mOdySwipeRefreshLayout.setOdyDefaultView(true);
        mRedPacketAdapter = new RedPacketAdapter();


        mOdySwipeRefreshLayout.setOnPullRefreshListener(new OdySwipeRefreshLayout.OnPullRefreshListener() {
            @Override
            public void onRefresh() {
                pno = 0;

                getRedenvelopesDetailBeanObservable(pno).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Action1<RedenvelopesDetailBean>() {
                            @Override
                            public void call(RedenvelopesDetailBean redenvelopesDetailBean) {
                                if (redenvelopesDetailBean != null && redenvelopesDetailBean.data != null) {
                                    mRedenvelopesListBeans.clear();
                                    mRedenvelopesListBeans.addAll(mStringExtra.equals(REDPACKET_SENDRED) ? redenvelopesDetailBean.data.sendred : redenvelopesDetailBean.data.getred);
                                    mRedPacketAdapter.setNewData(mRedenvelopesListBeans);
                                    mRedPacketAdapter.notifyDataSetChanged();

                                }

                            }
                        });
            }

            @Override
            public void onPullDistance(int distance) {

            }

            @Override
            public void onPullEnable(boolean enable) {

            }
        });

        mOdySwipeRefreshLayout.setOnPushLoadMoreListener(new OdySwipeRefreshLayout.OnPushLoadMoreListener() {
            @Override
            public void onLoadMore() {
                pno = pno + 1;

                getRedenvelopesDetailBeanObservable(pno).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                        .unsubscribeOn(Schedulers.io())
                        .subscribe(new Action1<RedenvelopesDetailBean>() {
                            @Override
                            public void call(RedenvelopesDetailBean redenvelopesDetailBean) {
                                if (redenvelopesDetailBean != null && redenvelopesDetailBean.data != null) {
                                    mRedenvelopesListBeans.addAll(mStringExtra.equals(REDPACKET_SENDRED) ? redenvelopesDetailBean.data.sendred : redenvelopesDetailBean.data.getred);
                                    mRedPacketAdapter.setNewData(mRedenvelopesListBeans);
                                    mRedPacketAdapter.notifyDataSetChanged();

                                }
                            }
                        });

            }

            @Override
            public void onPushDistance(int distance) {

            }

            @Override
            public void onPushEnable(boolean enable) {

            }
        });

        mRecyclerView.addOnItemTouchListener(new OnItemClickListener() {
            @Override
            public void onSimpleItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent intent = new Intent(RedPacketListActivity.this, RedPacketDetailActivity.class);
                intent.putExtra("bean", mRedenvelopesListBeans.get(position));
                startActivity(intent);

            }
        });
        doBusiness(this);
    }

    public void doBusiness(final Context mContext) {
        mStringExtra = getIntent().getStringExtra(METHOD);
        setTitle(mStringExtra.equals(REDPACKET_SENDRED) ? "发送的红包" : "收到的红包");

        mHead = View.inflate(mContext, mStringExtra.equals(REDPACKET_SENDRED) ? R.layout.my_sendred_header : R.layout.my_getred_header, null);

        Observable<RedenvelopesDetailBean> observable = getRedenvelopesDetailBeanObservable(pno);
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<RedenvelopesDetailBean>() {
                    @Override
                    public void onCompleted() {


                    }


                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(RedenvelopesDetailBean loadResult) {
                        if (loadResult != null && loadResult.data != null) {

                            mRedenvelopesListBeans.clear();
                            mRedenvelopesListBeans.addAll(mStringExtra.equals(REDPACKET_SENDRED) ? loadResult.data.sendred : loadResult.data.getred);
                            mRedPacketAdapter.setNewData(mRedenvelopesListBeans);
                            mRedPacketAdapter.addHeaderView(mHead);
                            mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            mRecyclerView.setAdapter(mRedPacketAdapter);


                            TextView username = (TextView) mHead.findViewById(R.id.userName);
                            TextView getRedNumber = (TextView) mHead.findViewById(R.id.getRedNumber);
                            TextView getGoodNumber = (TextView) mHead.findViewById(R.id.getGoodNumber);
                            TextView allMoney = (TextView) mHead.findViewById(R.id.allMoney);
                            TextView sendRedNumber = (TextView) mHead.findViewById(R.id.sendRedNumber);
                            username.setText(loadResult.data.name);
                            if (mStringExtra.equals(REDPACKET_SENDRED)) {
                                allMoney.setText(loadResult.data.totalamount);
                                sendRedNumber.setText(loadResult.data.totalnum);
                            } else {
                                getRedNumber.setText(loadResult.data.getnum);
                                allMoney.setText(loadResult.data.getamount);
                                getGoodNumber.setText(loadResult.data.bestred);
                            }

                        } else {
                        }


                    }
                });


    }

    private Observable<RedenvelopesDetailBean> getRedenvelopesDetailBeanObservable(int pno) {
        RedPacketRetrofit retrofitHelper = new RedPacketRetrofit(RedPacketActivity.BETA);
        final HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("method", mStringExtra.equals(REDPACKET_SENDRED) ? REDPACKET_SENDRED : REDPACKET_GETRED);
        stringStringHashMap.put("session", OdyApplication.getValueByKey(Constants.USER_LOGIN_UT, ""));
        stringStringHashMap.put("psize", "20");
        stringStringHashMap.put("pno", pno + "");
        RedPacketNetWorkApi redPacketService = retrofitHelper.getRedPacketService();
        Observable<RedenvelopesDetailBean> observable = null;
        if (mStringExtra.equals(REDPACKET_SENDRED)) {
            observable = redPacketService.redpacketSendList(stringStringHashMap);
        } else {
            observable = redPacketService.redpacketReceiveList(stringStringHashMap);
        }
        return observable;
    }


}
